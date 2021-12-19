package com.uni.labor6_2021_2022_javafx.application;

import com.uni.labor6_2021_2022_javafx.controller.RegistrationSystemController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LehrerView extends Application {
    private final RegistrationSystemController registrationSystemController;
    private Stage stage;
    private Long lehrerID;
    final ToolBar toolBar = new ToolBar();
    final Button buttonHome = new Button("Sign Out");
    final Button buttonExit = new Button("Exit");
    final Button enrolledStudentsButton = new Button("Enrolled Students");
    final TextArea vorlesungID = new TextArea();
    final ListView vorlesungList = new ListView();
    final ListView enrolledStudents = new ListView();

    final Label vorlesungen = new Label("Vorlesungen");
    final Label students = new Label("Enrolled Students");

    /**
     * wir erstellen ein neues Objekt von Typ "LehrerView"
     */
    public LehrerView(){
        this.registrationSystemController = new RegistrationSystemController();
    }

    public LehrerView(RegistrationSystemController registrationSystemController) {
        this.registrationSystemController = registrationSystemController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Lehrer");
        setStage(stage);

        setToolBar();
        vorlesungen.setFont(Font.font("Calibri", 21));
        students.setFont(Font.font("Calibri", 21));

        vorlesungID.setPromptText("Enter Vorlesung Id:\n");
        vorlesungenDisplay();
        enrolledStudentsDisplay();

        HBox texts = new HBox();
        texts.getChildren().addAll(vorlesungID);
        texts.setPrefSize(1, 1);
        texts.setAlignment(Pos.CENTER);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(enrolledStudentsButton);
        buttons.setSpacing(36);

        VBox vBox = new VBox(toolBar, vorlesungen, vorlesungList, texts, students, buttons, enrolledStudents);
        vBox.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #05ecb9,  #3b0ef5)");
        vBox.setSpacing(30);

        StackPane root = new StackPane();
        root.getChildren().addAll(vBox);
        Scene scene = new Scene(root, 1000, 1000);

        stage.setScene(scene);
        stage.show();
    }

    /* Getters und Setters */
    public void setLehrerID(Long lehrerID) {
        this.lehrerID = lehrerID;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * die "setToolBar"-Methode erstellt ein neues ToolBar-Objekt
     */
    private void setToolBar() {
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
     * die "vorlesungenDisplay"-Methode zeigt alle Vorlesungen, die Lehrer mit der Id = lehrerID unterrichtet
     */
    private void vorlesungenDisplay(){
        List<String> toStringVorlesung = new ArrayList<>();

        registrationSystemController.controller_getAllCourses()
                .forEach(vorlesung ->
                {if (vorlesung.getLehrer() == this.lehrerID)
                    toStringVorlesung.add("Vorlesung ID: " + vorlesung.getVorlesungID() + "\t\tVorlesung Name: " +  vorlesung.getName()  + "\t\tVorlesung Credits: " + vorlesung.getCredits());});

        ObservableList<String> vorlesungen = FXCollections.observableArrayList(toStringVorlesung);
        vorlesungList.setPrefSize(50, 100);
        vorlesungList.setItems(vorlesungen);
    }

    /**
     * die "enrolledStudentsDisplay"-Methode zeigt alle Studenten, die an einer bestimmten Vorlesung teilnehmen
     */
    private void enrolledStudentsDisplay(){
        enrolledStudentsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (vorlesungID.getText().trim().isEmpty() ||
                        registrationSystemController.controller_vorlesungRepository().findOne(Long.valueOf(vorlesungID.getText())) == null ||
                        registrationSystemController.controller_vorlesungRepository().findOne(Long.valueOf(vorlesungID.getText())).getLehrer() != lehrerID){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Register Error");
                    alert.setContentText("Please ENTER a valid ID");
                    alert.showAndWait();
                }
                else {
                    ObservableList<Long> studentList = FXCollections.observableArrayList(registrationSystemController.controller_vorlesungRepository().findOne(Long.valueOf(vorlesungID.getText())).getStudentsEnrolled());
                    vorlesungID.clear();
                    if (studentList.isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Enrolled Infrormation");
                        alert.setContentText("There are no students enrolled.");
                        alert.showAndWait();

                    }
                    else
                        enrolledStudents.setItems(studentList);
                }
            }
        });
    }
}
