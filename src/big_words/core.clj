(ns big-words.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.pprint :refer [pprint]]))

(defn hello [request]
  (pprint request)
  {:status 200
   :headers {}
   :body "Hello, World!"})

(defroutes app
  (GET "/" request (hello request))
  (route/not-found "<h1>Page not found</h1>"))

(defn -main
  [& args]
  (println "Hello, World!")
  (run-jetty app {:port (Integer/parseInt (System/getenv "PORT"))}))
