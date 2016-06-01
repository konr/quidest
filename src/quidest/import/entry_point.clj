(ns quidest.import.entry-point
  (:require [clojure.string :as str]
            [clojure.java.io :as jio]
            [quidest.import.stemlist :as stemlist]
            [quidest.import.inflects :as inflects]
            [quidest.import.dictline :as dictline]))

(defn parse-file [line-parser input]
  (->> input slurp str/split-lines (map line-parser)))

(defn convert-file [line-parser input output]
  (spit output (pr-str (remove nil? (parse-file line-parser input)))))

(defn convert-all []
  (let [files [{:from "dictline.lat"
               :to "/tmp/dictline.edn"
               :via dictline/parse-line}
               {:from "inflects.lat"
                :to "/tmp/inflects.edn"
                :via inflects/parse-line}
               {:from "stemlist.lat"
                :to "/tmp/stemlist.edn"
                :via stemlist/parse-line}]]
    (doseq [{:keys [from to via] :as file} files]
      (convert-file via (jio/resource from) to))))
