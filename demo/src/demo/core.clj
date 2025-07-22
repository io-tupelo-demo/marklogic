(ns demo.core
  (:use tupelo.core)
  (:require
    [schema.core :as s])
  (:gen-class))

(defn -main
  [& args]
  (spy :main--enter)
  (spyx args)
  (spy :main--leave))
