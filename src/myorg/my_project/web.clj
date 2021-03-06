(ns myorg.my-project.web
  (:require [myorg.my-project.visual-hash :refer [vhash]]))

(defn healthcheck
  [req]
  {:status 200
   :body "OK"})

(defn robot
  [req]
  (let [name (get-in req [:path-params :name])
        component (:vhash req)]
    {:status 200
     :headers {"Content-Type" "image/png"}
     :body (vhash component name)}))