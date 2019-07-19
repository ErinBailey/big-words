(defproject big-words "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.0"]
                 [ring/ring-jetty-adapter "1.6.2"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "3.10.0"]
                 [org.clojure/java.jdbc "0.4.2"]
                ;  [mysql/mysql-connector-java "5.1.38"]
                ;  [postgresql/postgresql "9.1-901-1.jdbc4"]
                 [clj-postgresql "0.7.0"]]
  :main ^:skip-aot big-words.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
