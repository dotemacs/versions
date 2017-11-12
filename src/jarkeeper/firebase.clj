(ns jarkeeper.firebase
  (:require [matchbox.core :as m]
            [environ.core :refer [env]]
            [clojure.string :as str]))

(def fire-root (m/connect "https://deps-versions.firebaseio.com"))
(if (env :firebase-token)
  (m/auth-custom fire-root (env :firebase-token) prn-str))

(defn deref-sync [ref]
  (let [p (promise)]
    (m/deref ref (fn [x] (deliver p x)))
    @p))

(defn safe [k]
  (str/replace k "." "__DOT__"))
