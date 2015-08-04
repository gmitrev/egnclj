(ns egn.util
  (:require [clojure.string  :as string :only [split]]))

(def weights '(2 4 8 5 10 9 7 3 6))

(defn- parse-int
  "wtf"
  [s]
  (Integer. (re-find  #"\d+" s )))

(defn determine-date
  "
  The EGN can have three different formats depending on the century. It can be determined by examining the month.
  The rules are as follows:
  * For people born in 1900..1999 the month does not change
  * For people born in 1800..1899 the month is increased by 20 (e.g January is 21)
  * For people born in 2000..2099 the month is increased by 40 (e.g December is 52)
  "
  [y m]
  (let [year  (parse-int y)
        month (parse-int m)]
    (cond
      (contains? (set (range  1 13)) month) [(+ 1900 year) month]
      (contains? (set (range 21 33)) month) [(+ 1800 year) (- month 20)]
      (contains? (set (range 41 53)) month) [(+ 2000 year) (- month 40)])))


(defn explode
  "Turn the EGN into a list of integers"
  [egn]
  (map read-string (drop-while empty? (string/split egn #""))))

(defn calculate-checksum
  "The checksum formula is explained at http://www.grao.bg/esgraon.html#section2"
  [egn]
  (let [sum (reduce + (map (partial reduce *) (map vector (explode egn) weights)))
        _rest (mod sum 11)]
    (if (< _rest 10) _rest 0)))
