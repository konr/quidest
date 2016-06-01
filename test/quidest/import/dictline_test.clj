(ns quidest.import.dictline-test
  (:require [quidest.import.dictline :refer :all]
            [midje.sweet :refer :all]))

(facts "on parse-word-list"
       (parse-line "abhorrid           abhorrid                                                 ADJ    1 1 POS          X X X F O rough, unsightly;")
       => {:age "X" :area "X" :declension "1"
           :degree "POS" :frequency "F" :geo "X"
           :part-of-speech :adjective :source "O"
           :stem-1 "abhorrid" :stem-2 "abhorrid"
           :variant "1"})
