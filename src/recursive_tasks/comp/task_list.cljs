
(ns recursive-tasks.comp.task-list
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]))

(defn render [tasks]
  (fn [state mutate!] (div {} (comp-text "a list" nil))))

(def comp-task-list (create-comp :task-list render))
