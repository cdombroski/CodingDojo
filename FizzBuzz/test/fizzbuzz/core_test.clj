(ns fizzbuzz.core-test
  (:use clojure.test
        fizzbuzz.core))

(deftest sequence-is-100-items []
  (let [test (fizzbuzz)]
    (is (= (count test) 100))))

(deftest sequence-starts-at-1 []
  (let [test (fizzbuzz)]
    (is (= (first test) 1))))

(deftest multiples-of-fifteen-are-fizzbuzz []
  (let [test (fizzbuzz)]
    (doseq [n (range 14 101 15)]
      (is (= "fizzbuzz" (nth test n))))))

(deftest multiples-of-three-contain-fizz []
  (let [test (fizzbuzz)]
    (doseq [n (range 2 101 3)]
      (is (re-matches #"fizz.*" (nth test n))))))

(run-tests)
