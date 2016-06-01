(ns quidest.core
  (:require [clojure.string :as str]
            [quidest.schemata :as qs]
            [schema.core :as s]
            [clojure.java.io :as jio]))

(s/defn ^:private split-word-at :- qs/word-pair
  [word :- qs/word
   position :- s/Int]
    {:stem   (.substring word 0 position)
     :ending (.substring word position)})

(s/defn split-word :- [qs/word-pair]
  [word :- qs/word]
  (->> word count inc range (map (partial split-word-at word))))

(s/defn ^:private valid-merge? :- s/Bool
  [stem :- (s/maybe qs/lookup-result)
   ending :- (s/maybe qs/lookup-result)]
  (boolean
   (and stem ending
        (= (:part-of-speech stem)
           (:part-of-speech ending)))))

(s/defn lookup-meaning :- [(s/maybe qs/lookup-result)]
  [db :- qs/db
   word-pair :- qs/word-pair]
  (for [stem (get (:stems db) (:stem word-pair))
        ending (get (:endings db) (:ending word-pair))]
    (when (valid-merge? stem ending)
      (merge stem ending))))

(s/defn parse-word :- [qs/lookup-result]
  [db :- qs/db
   word :- qs/word]
  (->> word split-word
       (mapcat (partial lookup-meaning db))
       (remove nil?)
       distinct))

(s/defn ^:private read-and-group
  [file :- s/Str
   group :- s/Keyword]
  (->> file jio/resource slurp read-string (group-by group)))

(s/defn make-db :- qs/db []
  {:stems (read-and-group "stemlist.edn" :stem)
   :endings (read-and-group "inflects.edn" :ending)})

