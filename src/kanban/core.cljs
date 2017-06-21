(ns kanban.core
    (:require [reagent.core :as reagent]))

(defonce backlog-state (reagent/atom [{:task "define system context" :completed false :owner "John Smith"}
                                       {:task "find use cases" :completed true :owner "Mary Smith"}
                                       {:task "design UI prototype" :completed false :owner "Safdar Khan"}]))

(defonce dev-state (reagent/atom []))
(defonce test-state (reagent/atom []))
(defonce done-state (reagent/atom []))


(defn render-backlog []
  [:ul
  (for [backlog @backlog-state]
    ^{:key backlog}
    [:li "Task: "(get backlog :task) [:br] " Owner: "(get backlog :owner)
     [:br][:button "move"]])])

(defn backlog []
  [:div {:class "flex-item"} [:h4 "Backlog"]
   [render-backlog]])

(defn in-development []
  [:div {:class "flex-item"} [:h4 "In development"]])

(defn in-test []
  [:div {:class "flex-item"} [:h4 "In test"]])

(defn done []
  [:div {:class "flex-item"} [:h4 "Done"]])


(defn app []
  [:div {:class "app"}
  [:h2 {:id "title"} "Kanban"]
   [:div {:class "flex-container"}
    [backlog]
    [in-development]
    [in-test]
    [done]]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [app] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
