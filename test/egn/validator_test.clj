(ns egn.validator-test
  (:require [clojure.test :refer :all]
            [egn.validator :refer :all]))

(deftest egn-length
  (testing "with correct input size"
    (is (true? (validate "8012141170"))))
  (testing "with incorrect input size"
    (is (false? (validate "")))
    (is (false? (validate "801214117")))
    (is (false? (validate "80121411700")))
    (is (false? (validate "80121411708012141170")))))

(deftest egn-date
  (testing "with correct dates"
    (is (true? (validate "8012141170")))
    (is (true? (validate "9004232654")))
    (is (true? (validate "1452277450"))))
  (testing "with incorrect dates"
    (is (false? (validate "8012321170")))
    (is (false? (validate "9002302654")))
    (is (false? (validate "1492277450")))))

(deftest egn-date
  (testing "with valid checksum"
    (is (true? (validate "8012141170")))
    (is (true? (validate "9004232654")))
    (is (true? (validate "1452277450"))))
  (testing "with invalid checksum"
    (is (false? (validate "8012141171")))
    (is (false? (validate "9004232657")))
    (is (false? (validate "1452277452")))))
