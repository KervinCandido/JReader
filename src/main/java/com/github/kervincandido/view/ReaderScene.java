package com.github.kervincandido.view;

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

    private void loadFromDir(File dir) {
        final FileFilter fileFilter = file -> file.getName().matches("^[0-9]+.*") &&
                            (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"));
        final File[] files = dir.listFiles(fileFilter);

        if(files == null) return;

        Stream.of(files)
                .sorted(File::compareTo)
                .map(file -> new Image(file.toURI().toString()))
                .map(ImageView::new)
                .forEach(imageView -> Platform.runLater(() -> imageList.getChildren().add(imageView)));
    }

    @FXML
    private void load(ActionEvent event) {
        final MenuItem menuItem = (MenuItem) event.getTarget();
        final Window window = menuItem.getParentPopup().getOwnerWindow();

        DirectoryChooser fileChooser = new DirectoryChooser();
        final File dir = fileChooser.showDialog(window);
        if (dir != null) {
            loadFromDir(dir);
        }
    }

    @FXML
    private void close() {
        Platform.exit();
        System.exit(0);
    }
}
