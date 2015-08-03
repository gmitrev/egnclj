(ns egn.validator
  (:require [clj-time.format :as f]
            [clojure.string :as string :only [split]]))

(declare validate-length validate-date validate-checksum)

(defn validate
  "Calls all validators"
  [egn]
  (and (validate-length egn)
       (validate-date egn)
       (validate-checksum egn)))

(defn- validate-length
  "Ensure EGN length is 10"
  [egn]
  (= (count egn) 10))

(defn- determine-date
  "
  The EGN can have three different formats depending on the century. It can be determined by examining the month.
  The rules are as follows:
  * For people born in 1900..1999 the month does not change
  * For people born in 1800..1899 the month is increased by 20 (e.g January is 21)
  * For people born in 2000..2099 the month is increased by 40 (e.g December is 52)
  "
  [y m]
  (let [year  (read-string y)
        month (read-string m)]
    (cond
      (contains? (set (range  1 13)) month) [(+ 1900 year) month]
      (contains? (set (range 21 33)) month) [(+ 1800 year) (- month 20)]
      (contains? (set (range 41 53)) month) [(+ 2000 year) (- month 40)])))

(defn- validate-date
  "Check if the extracted date is valid"
  [egn]
  (let [[y m day] (re-seq #"\d{1,2}" egn)
        [year month] (determine-date y m)
        date (str year "-" month "-" day)]
    (some? (f/parse date))))

(def weights '(2 4 8 5 10 9 7 3 6))

(defn- explode
  "Turn the EGN into a list of integers"
  [egn]
  (map read-string (drop-while empty? (string/split egn #""))))

(defn- calculate-checksum
  "The checksum formula is explained at http://www.grao.bg/esgraon.html#section2"
  [egn]
  (let [sum (reduce + (map (partial reduce *) (map vector (explode egn) weights)))
        _rest (mod sum 11)]
    (if (< _rest 10) _rest 0)))

(defn- validate-checksum
  "The last number in the EGN is the checksum - validate that it is in fact correct"
  [egn]
  (let [checksum (calculate-checksum egn)
        control-number (last (explode egn))]
    (= checksum control-number)))
