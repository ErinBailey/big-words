(ns big-words.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]))

(defroutes app
  (GET "/" [] "<h1>Hello, World</h1>")
  (route/not-found "<h1>Page not found</h1>"))

(defn -main
  [& args]
  (println "Hello, World!")
  (run-jetty app {:port (Integer/parseInt (System/getenv "PORT"))}))
