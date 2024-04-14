package org.example.polimorfismoestudiante.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.polimorfismoestudiante.models.*;


public class CrearStudentController {
    @FXML
    private Button saveButton;

    @FXML
    private TextField textFieldApellido;

    @FXML
    private TextField textFieldEdad;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldnombre;
    private Student student;
    private ServiciosEscolares serviciosEscolares;

    public void initialize() {
        serviciosEscolares = new ServiciosEscolares();
    }

    public void initAttributes(Student student) {
        this.student = student;

        if (student != null) {
            textFieldnombre.setText(student.getNombre());
            textFieldApellido.setText(student.getApellido());
            textFieldID.setText(String.valueOf(student.getId()));
            textFieldEdad.setText(String.valueOf(student.getEdad()));
        }
    }
    @FXML
    void guardarAction(ActionEvent event) {
        String nombre = textFieldnombre.getText();
        String apellido = textFieldApellido.getText();
        String edadText = textFieldEdad.getText();
        String idText = textFieldID.getText();


        if (nombre.isEmpty() || apellido.isEmpty() || edadText.isEmpty() || idText.isEmpty()) {
            showErrorAlert("Todos los campos son requeridos.");
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadText);
        } catch (NumberFormatException e) {
            showErrorAlert("Ingrese un número válido para la edad.");
            return; // Salir del método si la edad no es válida
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            showErrorAlert("Ingrese un número válido para el ID.");
            return;
        }


        Student newStudent = new Student(nombre, apellido, id, edad);

        if (student != null) {
            student.setNombre(nombre);
            student.setApellido(apellido);
            student.setEdad(edad);
            student.setId(id);
        } else {
            student = newStudent;
            serviciosEscolares.saveToDatabases(student);
        }
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
        alert.getDialogPane().getStylesheets().add("alert");
        alert.showAndWait();
    }

    public Student getStudent() {
        return student;
    }
}






