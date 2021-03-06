
(ns recursive-tasks.comp.task
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [recursive-tasks.style.widget :as widget]
            [respo.alias :refer [create-comp div input span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]
            [respo.util.detect :refer [text-width]]
            [recursive-tasks.style.color :as color]))

(defn on-input [path]
  (fn [e dispatch!]
    (let [content (:value e)] (dispatch! :update-task [path content]))))

(def style-task {:align-items "center", :padding "4px 4px"})

(def style-toggler
 {:color color/light-green, :font-size "16px", :cursor "pointer"})

(def style-done {:color color/purple})

(defn on-toggle [path] (fn [e dispatch!] (dispatch! :toggle-task path)))

(def style-remove
 (let [size 12]
   {:color color/red,
    :width size,
    :cursor "pointer",
    :opacity 0.5,
    :border-radius (str (/ size 2) "px"),
    :height size}))

(defn on-remove [path] (fn [e dispatch!] (dispatch! :remove-task path)))

(defn on-keydown [path]
  (fn [e dispatch!]
    (let [event (:original-event e)
          meta-key? (or (aget event "metaKey") (aget event "ctrlKey"))]
      (cond
        (and meta-key? (= 38 (:key-code e))) (dispatch! :move-up path)
        (and meta-key? (= 40 (:key-code e))) (dispatch!
                                               :move-down
                                               path)
        :else nil))))

(defn render [task path]
  (fn [state mutate!]
    (div
      {:style (merge ui/row style-task)}
      (div
        {:style (merge style-toggler (if (:done? task) style-done)),
         :event {:click (on-toggle path)},
         :attrs {:class-name "ion-android-done"}})
      (comp-space "8px" nil)
      (input
        {:style
         (merge
           (merge
             ui/input
             {:color (if (:done? task) color/light-green color/dark)})
           {:background-color color/white,
            :width
            (max
              80
              (+
                20
                (text-width
                  (:text task)
                  14
                  (:font-family ui/input))))}),
         :event {:keydown (on-keydown path), :input (on-input path)},
         :attrs
         {:placeholder "write task",
          :value (:text task),
          :autofocus true}})
      (comp-space "8px" nil)
      (div
        {:style (merge widget/icon style-remove),
         :event {:click (on-remove path)},
         :attrs {:class-name "ion-ios-close-empty"}}))))

(def comp-task (create-comp :task render))
