
(ns recursive-tasks.comp.task-list
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]
            [recursive-tasks.comp.task :refer [comp-task]]
            [recursive-tasks.style.widget :as widget]
            [recursive-tasks.style.color :as color]))

(declare comp-task-list)

(declare render)

(defn on-add [path] (fn [e dispatch!] (dispatch! :add-task path)))

(def style-list
 {:border-style "solid",
  :border-left-width 1,
  :border-right-width 0,
  :border-bottom-width 0,
  :border-top-width 0,
  :border-color color/light-green})

(def style-toolbar {:padding "4px 12px"})

(defn render [tasks path]
  (fn [state mutate!]
    (div
      {:style style-list}
      (div
        {}
        (->>
          tasks
          (map-indexed
            (fn [idx task]
              (let [child-path (conj path idx)]
                [(:id task)
                 (div
                   {:style
                    (merge
                      ui/row
                      {:align-items "center", :padding "0px 8px"})}
                   (comp-task task child-path)
                   (comp-space "8px" nil)
                   (if (not (:done? task))
                     (comp-task-list
                       (:sub-tasks task)
                       (conj child-path :sub-tasks))))])))))
      (div
        {:style style-toolbar}
        (div
          {:style widget/icon,
           :event {:click (on-add path)},
           :attrs {:class-name "ion-ios-plus-empty"}})))))

(def comp-task-list (create-comp :task-list render))
