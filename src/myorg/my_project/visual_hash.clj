(ns myorg.my-project.visual-hash
  (:import [java.net URL]))

(defprotocol VisualHash
  (vhash [this s] "Given a string, return an image (as an InputStream)"))

(defrecord RoboHash [base-url]
  VisualHash
  (vhash [this s]
    (let [url (URL. (str base-url s))]
      (.openStream url))))

(defn new-robohash
  "Constructor function for a RoboHash"
  [entity-map]
  (let [url (:myorg.my-project.robo-hash/base-url entity-map)]
    (println "constructing with url:" url)
    (->RoboHash url)))
