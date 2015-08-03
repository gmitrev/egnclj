# Egn

EGN(ЕГН) is the national identification number of Bulgarian citizens.
This clojure library is designed to validate and parse such numbers.

## Installation

With Leiningen:
```clj
[egn "0.1.0"]
```

With Maven:
```xml
<dependency>
  <groupId>egn</groupId>
  <artifactId>egn</artifactId>
  <version>0.1.0</version>
</dependency>

```

## Usage
```clj
(require '[egn.core :as egn]))

; Validate numbers
(egn/validate "7104041555")
=> true

(egn/validate "7194041555")
=> false

; Parse them for information
(egn/parse "7104041555")
=> {:birth-date #<DateTime 1971-04-04T00:00:00.000Z> :gender :female}

(:gender (egn/parse "9509209819"))
=> :female

(:birth-date (egn/parse "9509209819"))
=> #<DateTime 1995-09-20T00:00:00.000Z>
```
## License

Copyright © 2015 Georgi Mitrev

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
