
(ns recursive-tasks.style.widget
  (:require [respo-ui.style :as ui] [hsl.core :refer [hsl]]))

(def button
 (merge
   ui/button
   {:line-height "24px",
    :font-size "12px",
    :background-color (hsl 200 80 80),
    :border-radius "4px"}))

(def icon
 (let [size 24]
   {:color (hsl 0 0 60), :font-size "16px", :cursor "pointer"}))
