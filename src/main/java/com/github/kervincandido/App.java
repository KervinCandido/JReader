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

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        final var resource = getClass().getResource("view/ReaderScene.fxml");
        scene = new Scene(FXMLLoader.load(resource));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }

}