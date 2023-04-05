module com.example.shitty_demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.shitty_demo to javafx.fxml;
    exports com.example.shitty_demo;
}