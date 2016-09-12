
(ns recursive-tasks.comp.task
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]))

(defn render [task]
  (fn [state mutate!] (div {} (comp-text "task" nil))))

(def comp-task (create-comp :task render))
