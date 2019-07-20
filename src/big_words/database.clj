(ns bigwords.database
    (:require '[clojure.java.jdbc :as sql]))

(def db-spec {
  :subprotocol "postgresql"
  :subname (str "//" (System/getenv "DATABASE_SUBNAME"))
  :user (System/getenv "DATABASE_USER")
  :password (System/getenv "DATABASE_PASSWORD")})


  ; create databse if it does't exist
  (defn db-schema-migrated? []
    (-> (sql/query db-spec
        [(str "select count(*) from information_schema.tables "
              "where table_name='emojis'")])
                first :count pos?))
  

  (defn apply-schema-migration
    "Apply the schema to the database"
    []
    (when (not (db-schema-migrated?))
        (sql/db-do-commands db-spec ;"postgresql://localhost:5432/big-words" ;DB name and whatnot
                            (sql/create-table-ddl
                            :emojis
                            [:id :serial "PRIMARY KEY"]
                            [:emoji :text "NOT NULL"]
                            [:user_name :text "NOT NULL"]
                            [:user_id :text "NOT NULL"]
                            [:channel_name :text "NOT NULL"]
                            [:team_domain :text "NOT NULL"]
                            [:called_at :timestamp
                            "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]))))
; File is not being used at the moment