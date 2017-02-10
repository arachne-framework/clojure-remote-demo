(ns myorg.my-project
  (:require [rum.core :as rum]))

(rum/defc hello [text]
  [:div {:class "hello"} text])

(rum/mount (hello "Goodbye, world!")
  (.getElementById js/document "app"))
