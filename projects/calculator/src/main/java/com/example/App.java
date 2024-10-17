package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("calculator.fxml"));
        stage.setTitle("Calculator (JavaFX)");
        stage.setScene(new Scene(root, 435, 560));
        String icon = getClass().getResource("/img/icon.png").getPath().replaceAll("%20", " ");
        stage.getIcons().add(new Image(new File(icon).getAbsolutePath()));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}