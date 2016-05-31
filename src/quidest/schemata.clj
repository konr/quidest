(ns quidest.schemata
  (:require [schema.core :as s]))

(def word (s/both s/Str (s/pred #(not (re-find #"[\s]" %)))))
(def word-pair {:stem word
                :ending word})

(def lookup-result {s/Keyword s/Any})
(def db {:stems {s/Str lookup-result}
         :endings {s/Str lookup-result}})

