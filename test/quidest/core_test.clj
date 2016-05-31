(ns quidest.core-test
  (:require [midje.sweet :refer :all]
            [quidest.core :refer :all]))

(facts "on split word"
       (split-word "foo")
       => (just [{:stem "foo" :ending ""}
                 {:stem "fo"  :ending "o"}
                 {:stem "f"  :ending "oo"}
                 {:stem ""  :ending "foo"}]
                :in-any-order))

(def db {:stems
         {"am" {:meaning "to love"
                :part-of-speech :verb}}
         :endings
         {"ari" {:part-of-speech :verb
                 :declination :first-declination
                 :mood :infinitive
                 :voice :passive}
          "are" {:part-of-speech :verb
                 :declination :first-declination
                 :mood :infinitive
                 :voice :active}}})


(facts "on lookup-meaning"
       (lookup-meaning db {:stem "am" :ending "are"})
       => {:meaning "to love"
           :part-of-speech :verb
           :declination :first-declination
           :mood :infinitive
           :voice :active})

(facts "on parse-word"
       (parse-word db "amare")
       => [{:meaning "to love"
            :part-of-speech :verb
            :declination :first-declination
            :mood :infinitive
            :voice :active}]
       (parse-word db "amari")
       => [{:meaning "to love"
            :part-of-speech :verb
            :declination :first-declination
            :mood :infinitive
            :voice :passive}]
       (parse-word db "amo")
       => [])
