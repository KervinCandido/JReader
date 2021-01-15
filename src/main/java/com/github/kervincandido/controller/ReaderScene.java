package com.github.kervincandido.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileFilter;
import java.util.stream.Stream;

public class ReaderScene {

    @FXML
    private VBox imageList;

    @FXML
    private void initialize() {

    }

    private Stream<File> loadFromDir(File dir) {
        final FileFilter fileFilter = file -> file.getName().matches("^[0-9]+.*") &&
                            (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"));
        final File[] files = dir.listFiles(fileFilter);

        return files == null ? Stream.of() : Stream.of(files);
    }

    @FXML
    private void load(ActionEvent event) {
        final MenuItem menuItem = (MenuItem) event.getTarget();
        final Window window = menuItem.getParentPopup().getOwnerWindow();

        DirectoryChooser fileChooser = new DirectoryChooser();
        final File dir = fileChooser.showDialog(window);
        if (dir != null) {
            final Stream<File> images = loadFromDir(dir);
            clear();
            showImages(images);
        }
    }

    private void showImages(Stream<File> images) {
        images.sorted(File::compareTo)
                .map(file -> new Image(file.toURI().toString()))
                .map(ImageView::new)
                .forEach(imageView -> Platform.runLater(() -> imageList.getChildren().add(imageView)));
    }
    
    private void clear() {
        Platform.runLater(() -> imageList.getChildren().clear());
    }

    @FXML
    private void close() {
        Platform.exit();
        System.exit(0);
    }
}
