(ns big-words.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.pprint :refer [pprint]]
            [ring.util.request :refer [body-string]]
            [ring.middleware.params :refer [wrap-params]]))

(defn hello [request]
  (pprint request)
  (pprint (body-string request))
  {:status 200
   :headers {}
   :body (get-in request [:params "text"])})

(defroutes app
  (GET "/" request (hello request))
  (POST "/" request (hello request))
  (route/not-found "<h1>Page not found</h1>"))

(defn -main
  [& args]
  (run-jetty (wrap-params app) {:port (Integer/parseInt (System/getenv "PORT"))}))
