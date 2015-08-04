(ns egn.validator
  (:require [egn.util        :as util]
            [clj-time.format :as f]))

(declare validate-length validate-date validate-checksum)

; public api

(defn validate
  "Calls all validators"
  [egn]
  (let [egn-str (str egn)]
    (and (validate-length egn-str)
         (validate-date egn-str)
         (validate-checksum egn-str))))

; private space

(defn- validate-length
  "Ensure EGN length is 10 symbols"
  [egn]
  (= (count egn) 10))


(defn- validate-date
  "Check if the extracted date is valid"
  [egn]
  (let [[y m day] (re-seq #"\d{1,2}" egn)
        [year month] (util/determine-date y m)
        date (str year "-" month "-" day)]
    (some? (f/parse date))))

(defn- validate-checksum
  "The last number in the EGN is the checksum - validate that
  it is in fact correct"
  [egn]
  (let [checksum (util/calculate-checksum egn)
        control-number (last (util/explode egn))]
    (= checksum control-number)))
