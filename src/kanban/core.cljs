(ns kanban.core
    (:require [reagent.core :as reagent]))

(defonce backlog-state (reagent/atom [{:task "create RESTful endpoints" :completed false :owner "Mary Smith"}
                                       {:task "design UI prototype" :completed false :owner "Safdar Khan"}]))
(defonce dev-state (reagent/atom [{:task "client protocol" :completed false :owner "Mark Wu"}]))
(defonce test-state (reagent/atom [{:task "server protocol" :completed false :owner "Maryam Patel"}]))
(defonce done-state (reagent/atom [{:task "define system context" :completed false :owner "John Smith"}]))


(defn render-activity [activity]
  [:ul
  (for [act @activity]
    ^{:key act}
    [:li "Task: "(get act :task) [:br] " Owner: "(get act :owner)
     [:br][:button "move"]])])

(defn backlog []
  [:div {:class "flex-item"} [:h4 "Backlog"]
   [render-activity backlog-state]])

(defn in-development []
  [:div {:class "flex-item"} [:h4 "In development"]
  [render-activity dev-state]])

(defn in-test []
  [:div {:class "flex-item"} [:h4 "In test"]
  [render-activity test-state]])

(defn done []
  [:div {:class "flex-item"} [:h4 "Done"]
  [render-activity done-state]])


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
