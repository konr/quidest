(ns quidest.import.inflects
  (:require [clojure.string :as str]
            [quidest.import.common :as common]))

;; Discovered via `cat INFLECTS.LAT | awk '/^[A-Z]/ {gsub("--.*",""); print}' | awk '{print $1 " " NF}' | sort | uniq`
(def parts-in
  {[:adjective 11] [:part-of-speech :declension :variant :case :number :gender :degree :key :size         :age :frequency]
   [:adjective 12] [:part-of-speech :declension :variant :case :number :gender :degree :key :size :ending :age :frequency]
   [:adverb 6] [:part-of-speech :degree :declension :variant :age :frequency]
   [:conjunction 5] [:part-of-speech :declension :variant :age :frequency]
   [:interjection 5] [:part-of-speech :declension :variant :age :frequency]
   [:noun 10] [:part-of-speech :declension :variant :case :number :gender :key :size         :age :frequency]
   [:noun 11] [:part-of-speech :declension :variant :case :number :gender :key :size :ending :age :frequency]
   [:numeral 11] [:part-of-speech :declension :variant :case :number :gender :numeral-type :key :size         :age :frequency]
   [:numeral 12] [:part-of-speech :declension :variant :case :number :gender :numeral-type :key :size :ending :age :frequency]
   [:preposition 6] [:part-of-speech :case :declension :variant :age :frequency]
   [:pronoun 10] [:part-of-speech :declension :variant :case :number :gender :key :size         :age :frequency]
   [:pronoun 11] [:part-of-speech :declension :variant :case :number :gender :key :size :ending :age :frequency]
   [:supine 11] [:part-of-speech :declension :variant :case :number :gender :key :variant :ending :age :frequency]
   [:verb 12] [:part-of-speech :declension :variant :tense :voice :mood :person :number :key :size         :age :frequency]
   [:verb 13] [:part-of-speech :declension :variant :tense :voice :mood :person :number :key :size :ending :age :frequency]
   [:vpar 14] [:part-of-speech :declension :variant :case :number :gender :tense :voice :mood :key :size :ending :age :frequency]})

(def flags [:age :area :geo :frequency :source])

(defn parse-line [line]
  (when (re-find #"^[A-Z]" line)
    (let [data (str/replace line #"--.*" "")
          words (re-seq #"\w+" data)
          parts (parts-in [(common/parts-of-speech (first words)) (count words)])
          pairs (map #(hash-map %1 ((common/parser-for %1) %2)) parts words)]
      (reduce merge pairs))))
