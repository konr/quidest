(ns quidest.import.stemlist
  (:require [schema.core :as s]
            [quidest.import.common :as common]
            [clojure.string :as str]))

;; TODO Figure out unknown field
;; TODO What is pack?

(def words-in-word-list
  {"INTERJ" [:stem :part-of-speech :unknown :id]
   "ADV"    [:stem :part-of-speech :form :unknown :id]
   "PREP"   [:stem :part-of-speech :case :unknown :id]
   "CONJ"   [:stem :part-of-speech :unknown :id]
   "N"      [:stem :part-of-speech :declension :variant :gender :number :unknown :id]
   "V"      [:stem :part-of-speech :declension :variant :transitivity :unkown :id]
   "PRON"   [:stem :part-of-speech :declension :variant :kind :unknown :id]
   "NUM"    [:stem :part-of-speech :declension :variant :kind :amount :unknown :id]
   "PACK"   [:stem :part-of-speech :declension :variant :kind :unknown :id]
   "ADJ"    [:stem :part-of-speech :declension :variant :case :unknown :id]})


(defn parse-line [line]
  (let [words (re-seq #"\w+" line)
        parts (get words-in-word-list (second words))
        pairs (map #(hash-map %1 ((common/parser-for %1) %2)) parts words)]
    (reduce merge pairs)))
