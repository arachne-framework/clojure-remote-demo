(ns myorg.my-project.dsl
  (:require [arachne.core.config :as cfg]
            [arachne.core.dsl :as core-dsl]
            [arachne.core.config.script :as script :refer [defdsl]]
            [arachne.core.config.specs :as cfg-specs]
            [arachne.core.util :as util]

            [clojure.spec :as s]))

(defdsl robohash
  "Create a robohash component.

  Arguments are:

  Arachne ID (optional) - The arachne ID of the component to be created.
  url - The base URL for the robohash component.

  Returns the entity ID of the newly-created component."
  (s/cat :arachne-id (s/? ::core-dsl/arachne-id)
    :url string?)
  [<arachne-id> url]
  (let [tid (cfg/tempid)
        entity (util/mkeep {:db/id tid
                            :arachne/id (:arachne-id &args)
                            :arachne.component/constructor :myorg.my-project.visual-hash/new-robohash
                            :myorg.my-project.robo-hash/base-url url})]
    (script/transact [entity] tid)))