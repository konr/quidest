(ns quidest.core
  (:require [clojure.string :as str]))

(defn- split-word-at [word position]
    {:stem   (.substring word 0 position)
     :ending (.substring word position)})

(defn split-word [word]
  (->> word count inc range (map (partial split-word-at word))))

(defn- valid-merge? [stem ending]
  (and stem ending))

(defn lookup-meaning [db word-pair]
  (let [stem (get (:stems db) (:stem word-pair))
        ending (get (:endings db) (:ending word-pair))]
     (when (valid-merge? stem ending)
      (merge stem ending))))

(defn parse-word [db word]
  (->> word split-word
       (map (partial lookup-meaning db))
       (remove nil?)))
