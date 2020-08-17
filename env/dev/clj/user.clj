(ns user
  (:require [mount.core :as mount]
            picture-gallery.core))

(defn start []
  (mount/start-without #'picture-gallery.core/repl-server))

(defn stop []
  (mount/stop-except #'picture-gallery.core/repl-server))

(defn restart []
  (stop)
  (start))


