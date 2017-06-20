(ns kanban.core
    (:require [reagent.core :as reagent]))



(defn backlog []
  [:div [:h4 "backlog"]])

(defn in-development []
  [:div [:h4 "in developlment"]])

(defn in-test []
  [:div [:h4 "in test"]])

(defn done []
  [:div [:h4 "done"]])


(defn app []
  [:div {:class "app"} [:h2 {:on-click #(js/alert "meow")} "Kanban"]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [app] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
