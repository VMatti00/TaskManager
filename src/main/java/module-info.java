module se.kth.saeedvan.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.kth.saeedvan.taskmanager to javafx.fxml;
    exports se.kth.saeedvan.taskmanager;
}