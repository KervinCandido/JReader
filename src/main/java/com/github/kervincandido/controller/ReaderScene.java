package com.github.kervincandido.controller;

import com.github.kervincandido.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileFilter;
import java.util.stream.Stream;

public class ReaderScene {

    @FXML
    private ListView<ImageView> listView;

    @FXML
    private void initialize() {

    }

    @FXML
    private void load(ActionEvent event) {
        final Window window = App.getScene().getWindow();

        final MenuItem menuItem = (MenuItem) event.getTarget();
        final Window parent = menuItem.getParentPopup().getOwnerWindow();

        DirectoryChooser fileChooser = new DirectoryChooser();
        final File dir = fileChooser.showDialog(parent);
        if (dir != null) {
            final Stream<File> images = loadFromDir(dir);
            showImages(images, (window.getWidth() * 95) / 100);
        }
    }

    @FXML
    private void close() {
        Platform.runLater(() -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private Stream<File> loadFromDir(File dir) {
        final FileFilter fileFilter = file -> file.getName().matches("^[0-9]+.*") &&
                (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"));
        final File[] files = dir.listFiles(fileFilter);

        return files == null ? Stream.of() : Stream.of(files);
    }

    private void showImages(Stream<File> images, double width) {
        images.sorted(File::compareTo)
                .map(file -> new Image(file.toURI().toString()))
                .map(img -> {
                    final ImageView imageView = new ImageView(img);
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(width);
                    return imageView;
                })
                .forEach(imageView -> listView.getItems().add(imageView));

        Platform.runLater(() -> listView.refresh());
    }

}
