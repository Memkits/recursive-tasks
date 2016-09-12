
(ns recursive-tasks.schema)

(def task {:done? false, :id nil, :sub-tasks [], :text ""})

(def store {:tasks []})
