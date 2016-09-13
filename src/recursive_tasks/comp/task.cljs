
(ns recursive-tasks.comp.task
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div input span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]))

(defn on-input [path]
  (fn [e dispatch!]
    (let [content (:value e)] (dispatch! :update-task [path content]))))

(def style-task {:padding 8})

(def style-toggler
 {:background-color (hsl 0 0 80),
  :width 40,
  :cursor "pointer",
  :height 40})

(def style-done {:background-color (hsl 0 0 40)})

(defn on-toggle [path] (fn [e dispatch!] (dispatch! :toggle-task path)))

(def style-remove
 {:background-color (hsl 0 80 70),
  :width 24,
  :cursor "pointer",
  :height 24})

(defn on-remove [path] (fn [e dispatch!] (dispatch! :remove-task path)))

(defn render [task path]
  (fn [state mutate!]
    (div
      {:style (merge ui/row style-task)}
      (div
        {:style (merge style-toggler (if (:done? task) style-done)),
         :event {:click (on-toggle path)}})
      (comp-space "8px" nil)
      (input
        {:style ui/input,
         :event {:input (on-input path)},
         :attrs {:placeholder "write task", :value (:text task)}})
      (comp-space "8px" nil)
      (div {:style style-remove, :event {:click (on-remove path)}}))))

(def comp-task (create-comp :task render))
