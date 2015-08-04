(ns egn.parser
  (:require [clj-time.format :as f]
            [egn.validator   :as validator]
            [egn.util        :as util]))

(declare determine-birth-date determine-gender)

; public api

(defn parse
  "Extract information from an EGN"
  [egn]
  (if (validator/validate egn)
    {:birth-date (determine-birth-date egn)
     :gender     (determine-gender egn)}
    {}
  ))

; private space

(defn- determine-birth-date
  "Extract the birth date from the given number"
  [egn]
  (let [[y m day] (re-seq #"\d{1,2}" egn)
        [year month] (util/determine-date y m)
        date (str year "-" month "-" day)]
    (f/parse date)))

(defn- determine-gender
  "Extract the gender"
  [egn]
  (let [significant-number (str (nth egn 8))]
    (if (even? (read-string significant-number))
      :male
      :female)))
