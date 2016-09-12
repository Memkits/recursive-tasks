
(ns recursive-tasks.core
  (:require [recursive-tasks.schema :as schema]
            [recursive-tasks.updater.core :refer [updater]]
            [recursive-tasks.updater.core :refer [updater]]
            [recursive-tasks.updater.core :refer [updater]]
            [recursive-tasks.updater.core :refer [updater]]
            [respo.core :refer [render! clear-cache!]]
            [recursive-tasks.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]))

(defonce store-ref (atom schema/store))

(defn dispatch! [op op-data]
  (let [op-id (.valueOf (js/Date.))
        new-store (updater @store-ref op op-data op-id)]
    (reset! store-ref new-store)))

(defonce states-ref (atom {}))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)))

(defn on-jsload []
  (clear-cache!)
  (render-app!)
  (println "code update."))

(defn -main []
  (enable-console-print!)
  (render-app!)
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (println "app started!"))

(set! (.-onload js/window) -main)
