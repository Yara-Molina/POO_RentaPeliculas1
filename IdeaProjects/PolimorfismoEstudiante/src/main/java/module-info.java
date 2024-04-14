module org.example.polimorfismoestudiante {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.polimorfismoestudiante to javafx.fxml;
    exports org.example.polimorfismoestudiante;
    exports org.example.polimorfismoestudiante.controllers;
    opens org.example.polimorfismoestudiante.controllers to javafx.fxml;
}