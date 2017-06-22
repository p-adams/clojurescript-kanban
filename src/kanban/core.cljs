(ns kanban.core
    (:require [reagent.core :as reagent]))

(defonce backlog-state (reagent/atom {1 {:id 1 :task "create RESTFUL endpoints" :completed false :owner "Mary Smitgh"}
                                  2 {:id 2 :task "design UI prototype" :completed false :owner "Safdar Khan"}}))
(defonce dev-state (reagent/atom {3 {:id 3 :task "client protocol" :completed false :owner "Mark Wu"}}))
(defonce test-state (reagent/atom {4 {:id 4 :task "server protocol" :completed false :owner "Maryam Patel"}}))
(defonce done-state (reagent/atom {5 {:id 5 :task "define system context" :completed false :owner "John Smith"}}))
(defonce id-counter (reagent/atom 6))                        
(defonce get-task (reagent/atom ""))
(defonce get-owner (reagent/atom ""))

(defn reset-inputs []
  "reset the input fields after adding new task" 
  (reset! get-task "")
  (reset! get-owner ""))

(defn remove-activity [id activities]
  "remove given activity from actitivies"
  (swap! activities dissoc id))

(defn move-activity [activity activities destination]
  "move an activity to a new destination"
  (js/alert (for [dest destination] dest)))

(defn render-activities [activities button-text destination]
  "iterate through activities; render each activity as list item"
  "move activity to new destination on button click"
  [:ul
  (for [activity @activities]
    ^{:key activity}
    [:li "Task: "(get (val activity) :task) [:br] " Owner: "(get (val activity) :owner)
     [:br]
     [:button {:on-click #(remove-activity (get (val activity) :id) activities)
                ;:on-click #(move-activity (get activity :task) activities destination)
                }
                button-text]])])

(defn add-task-to-backlog [task owner]
  "add new task to backlog and reset the inputs"
  (let [id (swap! id-counter inc)]
    (swap! backlog-state assoc id {:id id :task task :completed false :owner owner}))
  (reset-inputs))

(defn input []
  "input component; renders two input fields and button to add new task"
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
  [:div {:class "flex-item"} [:p "Backlog"]
  [input]
  [render-activities backlog-state "move" dev-state]])

(defn in-development []
  "in development component"
  [:div {:class "flex-item"} [:p "In development"]
  [render-activities dev-state "move" test-state]])

(defn in-test []
  "in test component"
  [:div {:class "flex-item"} [:p "In test"]
  [render-activities test-state "move" done-state]])

(defn done []
  "done component"
  [:div {:class "flex-item"} [:p "Done"]
  [render-activities done-state "remove" nil]])


(defn app []
  "main app component"
  [:div {:class "app"}
  [:h1 {:id "title"} "kanban"]
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
