(ns kanban.core
    (:require [reagent.core :as reagent]))

(defonce backlog-state (reagent/atom [{:task "create RESTful endpoints" :completed false :owner "Mary Smith"}
                                       {:task "design UI prototype" :completed false :owner "Safdar Khan"}]))
(defonce dev-state (reagent/atom [{:task "client protocol" :completed false :owner "Mark Wu"}]))
(defonce test-state (reagent/atom [{:task "server protocol" :completed false :owner "Maryam Patel"}]))
(defonce done-state (reagent/atom [{:task "define system context" :completed false :owner "John Smith"}]))
(defonce get-task (reagent/atom ""))
(defonce get-owner (reagent/atom ""))

(defn reset-inputs []
  "reset the input fields after adding new task" 
  (reset! get-task "")
  (reset! get-owner ""))

(defn remove-activity [activity activities]
  "remove given activity from actitivies")

(defn move-activity [activity activities destination]
  "move an activity to a new destination"
  (js/alert (for [dest destination] dest)))

(defn render-activities [activities button-text destination]
  "iterate through activities; render each activity as list item"
  "move activity to new destination on button click"
  [:ul
  (for [activity @activities]
    ^{:key activity}
    [:li "Task: "(get activity :task) [:br] " Owner: "(get activity :owner)
     [:br]
     [:button {:on-click #(move-activity activity activities destination)}
                button-text]])])

(defn add-task-to-backlog [task owner]
  (swap! backlog-state conj {:task task :completed false :owner owner})
  (reset-inputs))

(defn input []
  "input component; renders two input field and button to add new task"
  [:div [:input {:type "text"
           :value @get-task
           :on-change #(reset! get-task (-> % .-target .-value))
           :placeholder "add task..."
           :autoFocus true}]
        [:input {:type "text"
          :value @get-owner
          :on-change #(reset! get-owner(-> % .-target .-value))
          :placeholder "add owner..."
          }]
        [:button {:on-click #(add-task-to-backlog @get-task @get-owner)
                  :disabled
                    (or
                    (= (count @get-task) 0)
                    (= (count @get-owner) 0))} "add"]])

(defn backlog []
  "backlog component"
  [:div {:class "flex-item"} [:h4 "Backlog"]
  [input]
  [render-activities backlog-state "move" @dev-state]])

(defn in-development []
  "in development component"
  [:div {:class "flex-item"} [:h4 "In development"]
  [render-activities dev-state "move" @test-state]])

(defn in-test []
  "in test component"
  [:div {:class "flex-item"} [:h4 "In test"]
  [render-activities test-state "move" @done-state]])

(defn done []
  "done component"
  [:div {:class "flex-item"} [:h4 "Done"]
  [render-activities done-state "remove" nil]])


(defn app []
  "main app component"
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
