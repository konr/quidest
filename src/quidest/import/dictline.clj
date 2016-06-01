(ns quidest.import.dictline
  (:require [clojure.string :as str]
            [quidest.import.common :as common]))

;; Discovered via `cat DICTLINE.GEN | cut -c1-110 | awk '{print substr($0,77,7) " " NF}' | sort | uniq`
(def parts-in
  {[:adjective 10]   [:stem-1                         :part-of-speech :declension :variant :degree]
   [:adjective 11]   [:stem-1 :stem-2                 :part-of-speech :declension :variant :degree]
   [:adjective 13]   [:stem-1 :stem-2 :stem-3 :stem-4 :part-of-speech :declension :variant :degree]
   [:adverb 10]      [:stem-1 :stem-2 :stem-3 :part-of-speech :degree]
   [:adverb 8]       [:stem-1                 :part-of-speech :degree]
   [:conjunction 7]  [:stem-1 :part-of-speech]
   [:interjection 7] [:stem-1 :part-of-speech]
   [:noun 11]        [:stem-1         :part-of-speech :declension :variant :gender :number]
   [:noun 12]        [:stem-1 :stem-2 :part-of-speech :declension :variant :gender :number]
   [:numeral 11]     [:stem-1                         :part-of-speech :declension :variant :kind :amount]
   [:numeral 14]     [:stem-1 :stem-2 :stem-3 :stem-4 :part-of-speech :declension :variant :kind :amount]
   [:pack 11]        [:stem-1 :stem-2 :part-of-speech :declension :variant :kind]
   [:preposition 8]  [:stem-1 :part-of-speech :case]
   [:pronoun 11]     [:stem-1 :stem-2 :part-of-speech :declension :variant :kind]
   [:verb 10]        [:stem-1                         :part-of-speech :declension :variant :transitivity]
   [:verb 13]        [:stem-1 :stem-2 :stem-3 :stem-4 :part-of-speech :declension :variant :transitivity]})

(def flags [:age :area :geo :frequency :source])

(defn parse-line [line]
  (let [data (.substring line 0 110)
        description (.substring line 110)
        pos   (->> (.substring line 76 82) str/trim common/parts-of-speech)
        words (re-seq #"\w+" data)
        parts (-> [pos (count words)] parts-in (concat flags))
        pairs (map #(hash-map %1 ((common/parser-for %1) %2)) parts words)]
    (reduce merge pairs)))
