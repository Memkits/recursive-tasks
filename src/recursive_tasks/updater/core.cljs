
(ns recursive-tasks.updater.core
  (:require [recursive-tasks.schema :as schema]))

(defn remove-task [store op-data op-id]
  (let [path op-data]
    (update-in
      store
      (butlast path)
      (fn [tasks]
        (let [idx (last path) len (count tasks)]
          (cond
            (= idx 0) (subvec tasks 1)
            (= idx (dec len)) (subvec tasks 0 idx)
            :else (vec
                    (concat
                      (subvec tasks 0 idx)
                      (subvec tasks (inc idx))))))))))

(defn move-up [store op-data op-id]
  (let [path op-data]
    (update-in
      store
      (butlast path)
      (fn [tasks]
        (let [position (last path)]
          (if (pos? position)
            (-> tasks
             (assoc position (get tasks (dec position)))
             (assoc (dec position) (get tasks position)))
            tasks))))))

(defn toggle-task [store op-data op-id]
  (let [path op-data]
    (update-in store path (fn [task] (update task :done? not)))))

(defn add-task [store op-data op-id]
  (let [path op-data]
    (update-in
      store
      path
      (fn [tasks] (conj tasks (merge schema/task {:id op-id}))))))

(defn update-task [store op-data op-id]
  (let [[path content] op-data]
    (update-in store path (fn [task] (assoc task :text content)))))

(defn default-handler [store op-data op-id] store)

(defn move-down [store op-data op-id]
  (let [path op-data position (last path)]
    (update-in
      store
      (butlast path)
      (fn [tasks]
        (if (< position (dec (count tasks)))
          (-> tasks
           (assoc position (get tasks (inc position)))
           (assoc (inc position) (get tasks position)))
          tasks)))))

(defn updater [store op op-data op-id]
  (let [handler (case
                  op
                  :add-task
                  add-task
                  :update-task
                  update-task
                  :remove-task
                  remove-task
                  :toggle-task
                  toggle-task
                  :move-up
                  move-up
                  :move-down
                  move-down
                  default-handler)]
    (handler store op-data op-id)))
