(ns tst.demo.core
  (:use demo.core
        tupelo.core
        tupelo.test)
  (:require
    [clojure.java.io :as io]
    [clojure.edn :as edn]
    [clj-http.client :as http-client]
    [schema.core :as s]
    [tupelo.core :as t]
    [tupelo.schema :as tsk]))


(verify
  ; enable to ping server to print/save sample data
  (when false
    (let [resp (http-client/get "http://localhost:8000/LATEST/search"
                 {:query-params {:q "comvex.com"}
                  :timeout      999
                  :headers      {"accept" "application/json"}
                  :digest-auth  ["admin" "admin"]
                  ;  :debug true
                  })
          body (json->edn (grab :body resp))
          ]
      (is= 200 (grab :status resp))
      (spyx-pretty resp)
      (spyx-pretty body)
      (let [resp-str (with-out-str (spyx-pretty resp))]
        (spit "resp-01.tmp" resp-str))
      (with-map-vals body [results]
        (spyx (count results))
        (let [r0  (nth results 0)
              uri (grab :uri r0)]
          (spyx-pretty r0)
          (spyx uri))))))

(verify
  (let [body {:metrics        {:query-resolution-time   "PT0.00212S"
                               :snippet-resolution-time "PT0.001299S"
                               :total-time              "PT0.004421S"}
              :page-length    10
              :qtext          "comvex.com"
              :results
              [{:confidence 0.1590108
                :fitness    0.2357023
                :format     "json"
                :href       "/v1/documents?uri=%2FUsers%2Falan%2Fexpr%2Fmarklogic%2Fdata%2Fcust7.json"
                :index      1
                :matches    [{:match-text ["blancheblevins@" {:highlight "comvex.com"}]
                              :path       "fn:doc(\"/Users/alan/expr/marklogic/data/cust7.json\")/text(\"Email\")"}]
                :mimetype   "application/json"
                :path       "fn:doc(\"/Users/alan/expr/marklogic/data/cust7.json\")"
                :score      2048
                :uri        "/Users/alan/expr/marklogic/data/cust7.json"}
               {:confidence 0.1590108
                :fitness    0.2357023
                :format     "json"
                :href       "/v1/documents?uri=%2FUsers%2Falan%2Fexpr%2Fmarklogic%2Fdata%2Fcust8.json"
                :index      2
                :matches    [{:match-text ["carissastein@" {:highlight "comvex.com"}]
                              :path       "fn:doc(\"/Users/alan/expr/marklogic/data/cust8.json\")/text(\"Email\")"}]
                :mimetype   "application/json"
                :path       "fn:doc(\"/Users/alan/expr/marklogic/data/cust8.json\")"
                :score      2048
                :uri        "/Users/alan/expr/marklogic/data/cust8.json"}]
              :snippet-format "snippet"
              :start          1
              :total          9}]
    (is= (body->uris body)
      ["/Users/alan/expr/marklogic/data/cust7.json"
       "/Users/alan/expr/marklogic/data/cust8.json"])))

(verify
  (let [resp-01 (edn/read-string (slurp (io/resource "resp-01.txt")))]
    ; query results are in random order => need set comparison
    (is-set= (resp->uris resp-01)
      ["/Users/alan/expr/marklogic/data/cust1.json"
       "/Users/alan/expr/marklogic/data/cust2.json"
       "/Users/alan/expr/marklogic/data/cust3.json"
       "/Users/alan/expr/marklogic/data/cust4.json"
       "/Users/alan/expr/marklogic/data/cust5.json"
       "/Users/alan/expr/marklogic/data/cust6.json"
       "/Users/alan/expr/marklogic/data/cust7.json"
       "/Users/alan/expr/marklogic/data/cust8.json"
       "/Users/alan/expr/marklogic/data/cust9.json"])))