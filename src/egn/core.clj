(ns egn.core
  (:require [egn.validator :only [validate]]
            [egn.parser    :only [parse]]))

(defn validate
  "Shortcut"
  [egn]
  (egn.validator/validate egn))

(defn parse
  "Shortcut"
  [egn]
  (egn.parser/parse egn))
