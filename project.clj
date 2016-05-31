(defproject quidest "0.1.0-SNAPSHOT"
  :description "a modern latin dictionary"
  :url "http://example.com/FIXME"
  :license {:name "GPL"
            :url "http://www.gnu.org/licenses/"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [prismatic/schema "1.1.1"]
                 [com.stuartsierra/component "0.3.1"]]
  :plugins [[lein-midje "3.1.3"]]
  :profiles {:dev {:dependencies [[midje "1.8.3" :exclusions [org.clojure/clojure]]]}}
  )
