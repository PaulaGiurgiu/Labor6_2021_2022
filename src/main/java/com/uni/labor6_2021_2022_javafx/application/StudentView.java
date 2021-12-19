package com.uni.labor6_2021_2022_javafx.application;

import com.uni.labor6_2021_2022_javafx.controller.RegistrationSystemController;
import com.uni.labor6_2021_2022_javafx.exception.RegisterException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StudentView extends Application{
    private final RegistrationSystemController registrationSystemController;
    private Stage stage;
    private Long studentID;
    private Scene scene;
    final ToolBar toolBar = new ToolBar();
    final Button buttonHome = new Button("Sign Out");
    final Button buttonExit = new Button("Exit");

    final Button registerButton = new Button("Register");
    final ListView vorlesungList = new ListView();
    final ListView studentListView = new ListView();
    final TextField vorlesungID = new TextField();

    final Button creditButton = new Button("Credit");
    final TextArea textArea = new TextArea();

    final Button vorlesungButton = new Button("Vorlesungen");

    final Label vorlesungen = new Label("Verfügbare Vorlesungen");
    final Label credits = new Label("Credits");
    final Label vorlesungenStudent = new Label("Eigene Vorlesungen");

    /**
     * wir erstellen ein neues Objekt von Typ "StudentView"
     */
    public StudentView() {
        this.registrationSystemController = new RegistrationSystemController();
    }

    public StudentView(RegistrationSystemController registrationSystemController){
        this.registrationSystemController = registrationSystemController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Student");
        setStage(stage);
        setToolBar();

        registerButton.setAlignment(Pos.TOP_LEFT);
        creditButton.setAlignment(Pos.BASELINE_CENTER);
        vorlesungButton.setAlignment(Pos.CENTER_RIGHT);

        setRegisterButton();
        setCreditButton();
        setVorlesungButton();

        vorlesungen.setFont(Font.font("Calibri", 21));
        credits.setFont(Font.font("Calibri", 21));
        vorlesungenStudent.setFont(Font.font("Calibri", 21));

        HBox texts = new HBox();
        texts.getChildren().addAll(vorlesungID);
        texts.setAlignment(Pos.CENTER);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(registerButton, creditButton, vorlesungButton);
        buttons.setSpacing(36);
        buttons.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(toolBar, vorlesungen, vorlesungList, texts, credits, textArea, vorlesungenStudent, studentListView, buttons);
        vBox.setSpacing(30);
        vBox.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #ffffff,  #aed50c)");

        StackPane root = new StackPane();
        root.getChildren().addAll(vBox);
        scene = new Scene(root, 1000, 1000);

        stage.setScene(scene);
        stage.show();
    }

    /* Getters und Setters */
    public void setStudentID(Long studentID) { this.studentID = studentID; }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * die "setToolBar"-Methode erstellt ein neues ToolBar-Objekt
     */
    private void setToolBar(){
        toolBar.setStyle("-fx-background-color: #4f69ef");
        toolBar.getItems().addAll(buttonHome, buttonExit);
        buttonHome.setOnAction(actionEvent -> getStage().close());
        buttonExit.setOnAction(actionEvent ->
        {   final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setContentText("Do you want to leave the application?");

            alert.showAndWait().ifPresent(buttonType ->
            {
                if (buttonType == ButtonType.OK) {
                    Platform.exit();
                }
            });
        }
        );
    }

    /**
     * die "setRegisterButton"-Methode fügt einen neuen Student an der gegebenen Vorlesung ein
     */
    private void setRegisterButton(){
        List<String> toStringVorlesung = new ArrayList<>();
        registrationSystemController.controller_getAllCourses()
                .forEach(vorlesung -> {
                    final boolean add = toStringVorlesung.add("Vorlesung ID: " + vorlesung.getVorlesungID() + "\t\tVorlesung Name: " +  vorlesung.getName()  + "\t\tVorlesung Credits: " + vorlesung.getCredits());
                });
        ObservableList<String> vorlesungen = FXCollections.observableArrayList(toStringVorlesung);
        vorlesungList.setPrefSize(50, 100);
        vorlesungList.setItems(vorlesungen);

        vorlesungID.setPromptText("Enter Vorlesung Id:\n");
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (vorlesungID.getText().trim().isEmpty() || registrationSystemController.controller_vorlesungRepository().findOne(Long.valueOf(vorlesungID.getText())) == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Register Error");
                    alert.setContentText("Please ENTER an ID");
                    alert.showAndWait();
                }
                else {
                    try {
                        registrationSystemController.controller_register(Long.parseLong(vorlesungID.getText()), studentID);
                    } catch (RegisterException e) {
                        final Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Exception Error");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
                vorlesungID.clear();
            }
        });

    }

    /**
     * die "setCreditButton"-Methode stellt die gesamte Anzahl der Credits dar
     */
    private void setCreditButton(){
        textArea.setPrefRowCount(1);
        textArea.setPrefColumnCount(1);
        creditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textArea.setText("Total Credits:\t" + String.valueOf(registrationSystemController.controller_studentRepository().findOne(studentID).getTotalCredits()));
            }
        });

    }

    /**
     * die "setVorlesungButton"-Methode zeigt alle Vorlesungen, an deren der Student teilnimmt
     */
    private void setVorlesungButton(){
        vorlesungButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObservableList<Long> studentList = FXCollections.observableArrayList(registrationSystemController.controller_studentRepository().findOne(studentID).getEnrolledCourses());
                studentListView.setItems(studentList);
            }
        });
    }
}
