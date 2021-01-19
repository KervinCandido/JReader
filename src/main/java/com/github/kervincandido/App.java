package com.github.kervincandido;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            final var resource = getClass().getResource("view/ReaderScene.fxml");
            Scene scene = new Scene(FXMLLoader.load(resource));
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            System.err.println("Falha ao carregar a UI");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}