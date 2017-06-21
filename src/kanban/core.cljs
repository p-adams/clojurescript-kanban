(ns kanban.core
    (:require [reagent.core :as reagent]))

(defonce backlog-state (reagent/atom [{:task "create RESTful endpoints" :completed false :owner "Mary Smith"}
                                       {:task "design UI prototype" :completed false :owner "Safdar Khan"}]))
(defonce dev-state (reagent/atom [{:task "client protocol" :completed false :owner "Mark Wu"}]))
(defonce test-state (reagent/atom [{:task "server protocol" :completed false :owner "Maryam Patel"}]))
(defonce done-state (reagent/atom [{:task "define system context" :completed false :owner "John Smith"}]))
(defonce get-task (reagent/atom ""))

(defn render-activities [activities button-text]
  [:ul
  (for [activity @activities]
    ^{:key activity}
    [:li "Task: "(get activity :task) [:br] " Owner: "(get activity :owner)
     [:br][:button button-text]])])

(defn input []
  [:div [:input {:type "text"
           :value @get-task
           :on-change #(reset! get-task (-> % .-target .-value))
           :placeholder "add task..."
           :autoFocus true}]
  [:button {:disabled (= (count @get-task) 0)} "add"]])

(defn backlog []
  [:div {:class "flex-item"} [:h4 "Backlog"]
  [input]
  [render-activities backlog-state "move"]])

(defn in-development []
  [:div {:class "flex-item"} [:h4 "In development"]
  [render-activities dev-state "move"]])

(defn in-test []
  [:div {:class "flex-item"} [:h4 "In test"]
  [render-activities test-state "move"]])

(defn done []
  [:div {:class "flex-item"} [:h4 "Done"]
  [render-activities done-state "remove"]])


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
