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
            ))

(defn conversion [text]
  (let [[word emoji] (str/split text #" ")
        word (.toUpperCase word)
        word (str/split word #"")
        big-letters (for [letter word] (get alphabet letter))]
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
   :headers {}
   :body (json/write-str {:response_type "in_channel"
           :text (conversion (get-in request [:params "text"]))})})

(defroutes app
  (GET "/" [] "Yo")
  (POST "/" request (command request))
  (route/not-found "<h1>Page not found</h1>"))

(defn -main
  [& args]
  (run-jetty (wrap-params app) {:port (Integer/parseInt (System/getenv "PORT"))}))
