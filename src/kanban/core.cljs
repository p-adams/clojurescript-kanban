(ns kanban.core
    (:require [reagent.core :as reagent]))






(defn app []
  [:div {:class "app"} [:h2 {:on-click #(js/alert "meow")} "Kanban"]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [app] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
