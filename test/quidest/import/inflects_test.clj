(ns quidest.import.inflects-test
  (:require [quidest.import.inflects :refer :all]
            [midje.sweet :refer :all]))

(facts "on parse-word-list"
       (parse-line "N     1 1 GEN S C  2 2 ae        X A")
       => {:age "X" :case "GEN" :declension "1"
           :ending "ae" :frequency "A"
           :gender "C" :key "2" :number "S"
           :part-of-speech :noun :size "2" :variant "1"})
