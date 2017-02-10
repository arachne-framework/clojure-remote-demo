(ns myorg.my-project.schema
  (:require [arachne.core.config :refer [tempid]]
            [arachne.core.config.model :as m]))

(defn schema []
  (m/type :myorg.my-project/RoboHash [:arachne/Component]
    "Entity defining a RoboHash component"
    (m/attr :myorg.my-project.robo-hash/base-url :one :string
      "The base URL of the visual hash component")))
