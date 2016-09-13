
(ns recursive-tasks.comp.task
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div input span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]
            [recursive-tasks.util.measure :refer [text-width]]))

(defn on-input [path]
  (fn [e dispatch!]
    (let [content (:value e)] (dispatch! :update-task [path content]))))

(def style-task {:padding "4px 8px"})

(def style-toggler
 (let [size 24]
   {:background-color (hsl 0 0 80),
    :width size,
    :cursor "pointer",
    :height size}))

(def style-done {:background-color (hsl 0 0 40)})

(defn on-toggle [path] (fn [e dispatch!] (dispatch! :toggle-task path)))

(def style-remove
 (let [size 16]
   {:background-color (hsl 0 80 70),
    :width size,
    :cursor "pointer",
    :border-radius (str (/ size 2) "px"),
    :height size}))

(defn on-remove [path] (fn [e dispatch!] (dispatch! :remove-task path)))

(defn render [task path]
  (fn [state mutate!]
    (div
      {:style (merge ui/row style-task {:align-items "flex-start"})}
      (div
        {:style (merge style-toggler (if (:done? task) style-done)),
         :event {:click (on-toggle path)}})
      (comp-space "8px" nil)
      (input
        {:style
         (merge
           ui/input
           {:width
            (max
              80
              (+
                20
                (text-width
                  (:text task)
                  14
                  (:font-family ui/input))))}),
         :event {:input (on-input path)},
         :attrs {:placeholder "write task", :value (:text task)}})
      (comp-space "8px" nil)
      (div {:style style-remove, :event {:click (on-remove path)}}))))

(def comp-task (create-comp :task render))
