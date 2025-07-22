(ns demo.core
  (:use tupelo.core)
  (:require
    [clojure.java.io :as io]
    [clojure.edn :as edn]
    [clj-http.client :as http-client]
    [schema.core :as s]
    [tupelo.core :as t]
    [tupelo.schema :as tsk])
  (:gen-class))

(s/defn body->uris :- [s/Str]
  "Given a Marklogic query response return a vector of the URIs found"
  [resp-body :- tsk/KeyMap]
  (with-map-vals resp-body [results]
    (forv [result results]
      (grab :uri result))))

(s/defn resp->uris :- [s/Str]
  "Given a Marklogic query response return a vector of the URIs found"
  [http-resp :- tsk/KeyMap]
  (assert (= 200 (grab :status http-resp)))
  (let [body (json->edn (grab :body http-resp))]
    (body->uris body)))

(defn -main
  [& args]
  (spy :main--enter)
  (spyx args)
  (spy :main--leave))
