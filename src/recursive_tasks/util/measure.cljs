
(ns recursive-tasks.util.measure)

(defonce ctx (.getContext (.createElement js/document "canvas") "2d"))

(defn text-width [content font-size font-family]
  (aset ctx "font" (str font-size "px " font-family))
  (aget (.measureText ctx content) "width"))
