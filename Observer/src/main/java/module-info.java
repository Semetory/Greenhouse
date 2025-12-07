module com.example.observer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.observer to javafx.fxml;
    exports com.example.observer;
}