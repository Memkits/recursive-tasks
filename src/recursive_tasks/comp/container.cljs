
(ns recursive-tasks.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-text]]
            [recursive-tasks.comp.task-list :refer [comp-task-list]]))

(defn render [store]
  (fn [state mutate!]
    (div
      {:style (merge ui/global)}
      (comp-task-list (:tasks store) [:tasks])
      (comp-debug store {:bottom 0}))))

(def comp-container (create-comp :container render))
