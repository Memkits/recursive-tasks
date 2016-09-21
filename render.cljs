
(ns recursive-tasks.boot
  (:require
    [respo.alias :refer [html head title script style meta' div link body]]
    [respo.render.static-html :refer [make-html make-string]]
    [recursive-tasks.comp.container :refer [comp-container]]
    [planck.core :refer [spit]]))

(defn use-text [x] {:attrs {:innerHTML x}})
(defn html-dsl [data html-content ssr-stages]
  (make-html
    (html {}
    (head {}
      (title (use-text (str "Recursive Tasks")))
      (link {:attrs {:rel "icon" :type "image/png" :href "mvc-works-192x192.png"}})
      (link {:attrs {:rel "stylesheet" :type "text/css"
        :href "http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"}})
      (meta' {:attrs {:charset "utf-8"}})
      (meta' {:attrs {:name "viewport" :content "width=device-width, initial-scale=1"}})
      (meta' {:attrs {:id "ssr-stages" :content (pr-str ssr-stages)}})
      (style (use-text "body {margin: 0;}"))
      (style (use-text "body * {box-sizing: border-box;}"))
      (script {:attrs {:id "config" :type "text/edn" :innerHTML (pr-str data)}}))
    (body {}
      (div {:attrs {:id "app" :innerHTML html-content}})
      (script {:attrs {:src "main.js"}})))))

(defn generate-html [ssr-stages]
  (let [ tree (comp-container {} ssr-stages)
         html-content (make-string tree)]
    (html-dsl {:build? true} html-content ssr-stages)))

(defn -main []
  (spit "target/index.html" (generate-html #{:shell})))

(-main)