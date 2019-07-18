(ns big-words.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.pprint :refer [pprint]]
            [ring.util.request :refer [body-string]]
            [ring.middleware.params :refer [wrap-params]]
            [clojure.string :as str]
            [big-words.alphabet :refer [alphabet]]
            [clojure.data.json :as json]
            [clj-http.client :as client]))

; create a nil atom
(def DATABASE_USER (atom nil))

; reset env var in a function that is called at runtime
(defn init-config []
  (reset! DATABASE_USER (System/getenv "DATABASE_USER"))
  (pprint DATABASE_USER))
          
(defn conversion [text]
  (let [[phrase emoji] (str/split text #" / ")
  phrase (.toUpperCase phrase)
  phrase (str/split phrase #"")
  big-letters (for [letter phrase] (get alphabet letter))]
  (.replaceAll
    (.replaceAll
      (str/join
        "\n"
        (for [row (range 5)]
          (str/join
            " "
            (for [letter big-letters]
              (nth letter row)))))
              " " ":blank:")
              "#" emoji)))
            
(defn command [request]
  (pprint request)
  (pprint (body-string request))
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str {:response_type "in_channel"
           :text (conversion (get-in request [:params "text"]))})})

(defroutes app
  (GET "/" [] "Yo")
  (POST "/" request (command request))
  (route/not-found "<h1>Oops, wrong turn</h1>"))

(defn -main
  [& args]
  (run-jetty (wrap-params app) {:port (Integer/parseInt (System/getenv "PORT"))}))
