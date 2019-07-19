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
            [clojure.java.jdbc :as sql]
            [clj-postgresql.core :as postgres]
            [clojure.java.jdbc :as jdbc]
            [clj-http.client :as client]))
          
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


; this works! It will write to the DB when you hit /test

(defn post-emoji-event [name]
  (sql/insert! (System/getenv "DATABASE_URL") ;"postgresql://localhost:5432/big-words" <- use this when hitting the local DB
        :emojis [:emoji :user_name :user_id :channel_name :team_domain] [":whale:" name "246" "direct_message" "bread"]))

(defroutes app
  (GET "/" [] "Yo")
  (GET "/test-insert" request (post-emoji-event "Erin"))
  (POST "/" request (command request))
  (route/not-found "<h1>Oops, wrong turn</h1>"))

(defn -main
  [& args]
  (run-jetty (wrap-params app) {:port (Integer/parseInt (System/getenv "PORT"))}))
