package com.uni.labor6_2021_2022_javafx;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        UI ui = new UI();
        ui.start(stage);

    }
}
