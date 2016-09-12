
(ns recursive-tasks.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]))

(defn render [] (fn [state mutate!] (div {:style (merge ui/global)})))

(def comp-container (create-comp :container render))