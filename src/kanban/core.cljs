(ns kanban.core
    (:require [reagent.core :as reagent]))



(defn backlog []
  [:div [:h4 "backlog"]])

(defn in-development []
  [:div [:h4 "in development"]])

(defn in-test []
  [:div [:h4 "in test"]])

(defn done []
  [:div [:h4 "done"]])


(defn app []
  [:div {:class "app"}
  [:h2 {:id "title"} "Kanban"]
    [backlog]
    [in-development]
    [in-test]
    [done]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [app] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
