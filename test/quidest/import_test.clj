(ns quidest.import-test
  (:require [quidest.import :refer :all]
            [midje.sweet :refer :all]))

(facts "on parse-word-list"
       (parse-line "vae INTERJ 1 1337")
       => {:stem "vae" :part-of-speech :interjection
           :unknown "1" :id "1337"})
