
(ns recursive-tasks.core
  (:require [cljs.reader :refer [read-string]]
            [recursive-tasks.schema :as schema]
            [recursive-tasks.updater.core :refer [updater]]
            [recursive-tasks.updater.core :refer [updater]]
            [recursive-tasks.updater.core :refer [updater]]
            [recursive-tasks.updater.core :refer [updater]]
            [respo.core :refer [render! clear-cache!]]
            [recursive-tasks.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]))

(def storage-key "recursive-tasks")

(defonce store-ref
 (atom
   (let [content (.getItem js/localStorage storage-key)]
     (merge schema/store (if (some? content) (read-string content))))))

(defn dispatch! [op op-data]
  (println "dispatch!" op op-data)
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

(defn save-store! []
  (let [content (pr-str @store-ref)]
    (.setItem js/localStorage storage-key content)))

(defn -main []
  (enable-console-print!)
  (render-app!)
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (.addEventListener js/window "beforeunload" save-store!)
  (println "app started!"))

(set! (.-onload js/window) -main)
