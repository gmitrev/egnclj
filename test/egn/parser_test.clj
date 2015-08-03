(ns egn.parser-test
  (:require [clojure.test :refer :all]
            [egn.parser :refer :all]
            [clj-time.core :as t]))

(deftest with-invalid-egn
  (is (empty? (parse "8012991170"))))

(deftest egn-birth-date
  (testing "year"
    (is (= 1890 (t/year (:birth-date (parse "9024260162")))))
    (is (= 1980 (t/year (:birth-date (parse "8012141170")))))
    (is (= 2005 (t/year (:birth-date (parse "0541076409"))))))
  (testing "month"
    (is (= 4  (t/month (:birth-date (parse "9024260162")))))
    (is (= 12 (t/month (:birth-date (parse "8012141170")))))
    (is (= 1  (t/month (:birth-date (parse "0541076409"))))))
  (testing "day"
    (is (= 26 (t/day (:birth-date (parse "9024260162")))))
    (is (= 14 (t/day (:birth-date (parse "8012141170")))))
    (is (= 7  (t/day (:birth-date (parse "0541076409")))))))

(deftest egn-gender
  (testing "male"
    (is (= :male (:gender (parse "9006171943"))))
    (is (= :male (:gender (parse "8009074729"))))
    (is (= :male (:gender (parse "7208223361")))))
  (testing "female"
    (is (= :female (:gender (parse "0448091192"))))
    (is (= :female (:gender (parse "9509209819"))))
    (is (= :female (:gender (parse "7104041555")))))
  )
