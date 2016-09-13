
(ns recursive-tasks.comp.task-list
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]
            [recursive-tasks.comp.task :refer [comp-task]]))

(defn on-add [path] (fn [e dispatch!] (dispatch! :add-task path)))

(defn render [tasks path]
  (fn [state mutate!]
    (div
      {}
      (div
        {}
        (->>
          tasks
          (map-indexed
            (fn [idx task] [(:id task)
                            (div
                              {}
                              (comp-task task (conj path idx)))]))))
      (div
        {}
        (div
          {:style ui/button, :event {:click (on-add path)}}
          (comp-text "add one" nil))))))

(def comp-task-list (create-comp :task-list render))
