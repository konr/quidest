(ns quidest.import.common)

;; TODO Define parts-of-speech in a schemata file
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
   "SUPINE" :supine
   "VPAR"   :vpar
   "ADJ"    :adjective})

(def parsers {:part-of-speech parts-of-speech})
(def default-parser str)

(defn parser-for [tag]
  (get parsers tag default-parser))
