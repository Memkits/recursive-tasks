
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
      {:style (merge (merge ui/global {:padding 16}))}
      (comp-task-list (:tasks store) [:tasks])
      (comment comp-debug store {:bottom 0}))))

(def comp-container (create-comp :container render))
