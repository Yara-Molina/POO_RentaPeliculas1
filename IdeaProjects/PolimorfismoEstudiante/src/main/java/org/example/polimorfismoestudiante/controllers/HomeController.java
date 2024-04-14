package org.example.polimorfismoestudiante.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.polimorfismoestudiante.models.*;

import java.io.IOException;
import java.util.ArrayList;

public class HomeController {
    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<Student, String> col1;

    @FXML
    private TableColumn<Student, String> col2;

    @FXML
    private TableColumn<Student, Integer> col3;

    @FXML
    private TableColumn<Student, Integer> col4;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button seeButton;

    @FXML
    private TableView<Student> tableViewStudents;

    private ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
    private ArrayList<IDataStudent> databases = new ArrayList<>();

    private ServiciosEscolares serviciosEscolares;


    @FXML
    public void initialize() {
        serviciosEscolares = new ServiciosEscolares();
        col1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        col2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        col3.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        col4.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEdad()).asObject());

        loadFromDatabases();
    }

    private void loadFromDatabases() {
        for (IDataStudent database : databases) {
            studentObservableList.addAll(database.getStudents());
        }
        tableViewStudents.setItems(studentObservableList);
    }

    @FXML
    void agregarAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/polimorfismoestudiante/saveStudent.fxml"));
            Parent root = loader.load();

            CrearStudentController controller = loader.getController();
            controller.initAttributes(null);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            Student student = controller.getStudent();
            if (student != null) {
                studentObservableList.add(student);
                serviciosEscolares.saveToDatabases(student);
                tableViewStudents.refresh();
                serviciosEscolares.printStudents();
            }
        } catch (IOException e) {
            showErrorAlert("Error al guardar");
        }
    }

    @FXML
    void modificarAction(ActionEvent event) {
        Student selectedStudent = tableViewStudents.getSelectionModel().getSelectedItem();

        if (selectedStudent == null) {
            showErrorAlert("Debe seleccionar un estudiante");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/polimorfismoestudiante/saveStudent.fxml"));
                Parent root = loader.load();

                CrearStudentController controller = loader.getController();
                controller.initAttributes(selectedStudent);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

                Student modifiedStudent = controller.getStudent();
                if (modifiedStudent != null) {
                    serviciosEscolares.updateInDatabases(selectedStudent, modifiedStudent);
                    tableViewStudents.refresh();
                    serviciosEscolares.printStudents();
                }
            } catch (IOException e) {
                showErrorAlert("Error al cargar la ventana de modificación");
            }
        }
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

}



