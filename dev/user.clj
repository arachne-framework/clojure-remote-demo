 (ns user
   (:require [reloaded.repl :refer [set-init! system init start stop go reset reset-all]]
             [arachne.core :as a]
             [arachne.figwheel :as fig]
             [myorg.my-project :as app])
   )

(def ^{:dynamic true
       :doc "Bound to the config for the current application"}
  *cfg*
  nil)

(defn init-arachne
  "Create an Arachne runtime"
  [runtime]
  (let [cfg (a/config :myorg/my-project)
        rt (a/runtime cfg runtime)]
    (alter-var-root #'*cfg* (constantly cfg))
    rt))

(set-init! #(init-arachne ::app/runtime))

(defn cljs-repl
  "Launch a CLJS repl for a Figwheel in the currently running Arachne system"
  []
  (fig/repl system))



(comment
  ;; This is what is involved in starting an Arachne system:

  ;; First, build a config:
  (def cfg (a/config :myorg/my-project))
  (def rt (a/runtime cfg :myorg.my-project/runtime))

  (require '[com.stuartsierra.component :as c])

  (def started-rt (c/start rt))

  (c/stop started-rt)

  (def cfg (a/config :myorg/my-project))
  (clojure.pprint/pprint
    (arachne.core.config/pull cfg '[*] [:arachne/id :myorg.my-project/server]))

  )
