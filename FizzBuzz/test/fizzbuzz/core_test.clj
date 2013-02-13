(ns fizzbuzz.core-test
  (:use clojure.test
        fizzbuzz.core))

(deftest sequence-is-100-items []
  (let [test (fizzbuzz)]
    (is (= (count test) 100))))

(run-tests)
