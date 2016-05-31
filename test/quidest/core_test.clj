(ns quidest.core-test
  (:require [midje.sweet :refer :all]
            [quidest.core :refer :all]
            [schema.core :as s]))

(def test-db {:stems
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


(s/with-fn-validation
  (facts "on split word"
         (split-word "foo")
         => (just [{:stem "foo" :ending ""}
                   {:stem "fo"  :ending "o"}
                   {:stem "f"  :ending "oo"}
                   {:stem ""  :ending "foo"}]
                  :in-any-order))

  (facts "on lookup-meaning"
         (lookup-meaning test-db {:stem "am" :ending "are"})
         => {:meaning "to love"
             :part-of-speech :verb
             :declination :first-declination
             :mood :infinitive
             :voice :active})

  (facts "on parse-word"
         (parse-word test-db "amare")
         => [{:meaning "to love"
              :part-of-speech :verb
              :declination :first-declination
              :mood :infinitive
              :voice :active}]
         (parse-word test-db "amari")
         => [{:meaning "to love"
              :part-of-speech :verb
              :declination :first-declination
              :mood :infinitive
              :voice :passive}]
         (parse-word test-db "amo")
         => []))
