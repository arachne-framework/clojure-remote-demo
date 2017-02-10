(require '[arachne.core.dsl :as a])
(require '[arachne.http.dsl :as h])
(require '[arachne.pedestal.dsl :as p])
(require '[arachne.assets.dsl :as aa])
(require '[arachne.pedestal-assets.dsl :as pa])
(require '[arachne.cljs.dsl :as cljs])
(require '[arachne.figwheel.dsl :as fig])
(require '[myorg.my-project.dsl :as rh])

;; Make things shorter to type...
(alias 'app 'myorg.my-project)

;; Always in dev mode, for now
(def dev? (constantly true))

;; Runtime setup
(if (dev?)
  (a/runtime ::app/runtime [::app/figwheel ::app/server])
  (a/runtime ::app/runtime [::app/server]))

;; A Robohash component
#_(a/component ::app/robohash
  'myorg.my-project.visual-hash/new-robohash)

#_(a/transact
  [{:arachne/id ::app/robohash
    :arachne.component/constructor :myorg.my-project.visual-hash/new-robohash
    :myorg.my-project.robo-hash/base-url "https://robohash.org/"}])

(rh/robohash ::app/robohash "https://robohash.org/")

;; HTTP Server setup
(p/server ::app/server 8080

  (pa/interceptor ::app/asset-interceptor :index? true)

  (h/endpoint :get "/healthcheck"
    (h/handler 'myorg.my-project.web/healthcheck))

  (h/endpoint :get "/robot/:name"
    (h/handler 'myorg.my-project.web/robot {:vhash ::app/robohash}))

  )

;; ClojureScript setup
(def cljs-opts {:main 'myorg.my-project
                :optimizations (if (dev?) :none :advanced)
                :asset-path "js/out"
                :output-to "js/app.js"
                :output-dir "js/out"
                :source-map-timestamp true})

(aa/input-dir ::app/src-dir "src" :watch? (dev?))
(cljs/build ::app/cljs cljs-opts)
(aa/pipeline [::app/src-dir ::app/cljs])

;; Asset Pipeline setup
(aa/input-dir ::app/public-dir "public" :classpath? true :watch? (dev?))
(aa/pipeline [::app/public-dir ::app/asset-interceptor]
             [::app/cljs ::app/asset-interceptor])

;; Figwheel (dynamic CLJS development)
(fig/server ::app/figwheel cljs-opts :port 8888)

(aa/pipeline
  [::app/src-dir ::app/figwheel #{:src}]
  [::app/public-dir ::app/figwheel #{:public}])
