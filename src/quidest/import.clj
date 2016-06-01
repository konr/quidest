(ns quidest.import
  (:require [schema.core :as s]
            [clojure.string :as str]))

;; TODO Figure out unknown field
;; TODO What is pack?
;; TODO Define parts-of-speech in a schemata file
;; TODO 

(def parts-of-speech
  {"INTERJ" :interjection
   "ADV"    :adverb
   "PREP"   :preposition
   "CONJ"   :conjunction
   "N"      :noun
   "V"      :verb
   "PRON"   :pronoun
   "NUM"    :numeral
   "PACK"   :pack
   "ADJ"    :adjective})

(def parsers {:part-of-speech parts-of-speech})
(def default-parser str)

(defn parser-for [tag]
  (get parsers tag default-parser))

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
        pairs (map #(hash-map %1 ((parser-for %1) %2)) parts words)]
    (reduce merge pairs)))

(defn parse-file [input output]
  (let [lines (->> input slurp str/split-lines (map parse-line))]
    (println (->> lines (filter nil?) count) " unparseable entries")
    (spit output (pr-str (remove nil? lines)))))
