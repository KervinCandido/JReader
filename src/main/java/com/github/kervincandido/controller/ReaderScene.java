package com.github.kervincandido.controller;

import com.github.kervincandido.model.OS;
import com.github.kervincandido.model.fileextractor.FileExtractor;
import com.github.kervincandido.model.fileextractor.FileExtractorFactory;
import com.github.kervincandido.model.filemanager.FileManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReaderScene {

    @FXML
    private ListView<ImageView> listView;

    @FXML
    private void initialize() {
    }

    @FXML
    private void loadFolder(ActionEvent event) {
        Window window = listView.getScene().getWindow();

        final MenuItem menuItem = (MenuItem) event.getTarget();
        final Window parent = menuItem.getParentPopup().getOwnerWindow();

        DirectoryChooser fileChooser = new DirectoryChooser();
        final File dir = fileChooser.showDialog(parent);
        if (dir != null) {
            final Stream<File> images = loadFromDir(dir);
            showImages(images, getPageWidth(window));
        }
    }

    @FXML
    private void loadCompressedFile(ActionEvent event) {
        Window window = listView.getScene().getWindow();
        final MenuItem menuItem = (MenuItem) event.getTarget();
        final Window parent = menuItem.getParentPopup().getOwnerWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(".rar", "rar"));
        final File dir = fileChooser.showOpenDialog(parent);

        if (dir != null) {
            String fileUncompressed = getFileUncompressedName(dir);

            FileManager fileManager = new FileManager();
            final File destinationFolder = fileManager.getJreaderFolder();
            final File filesDir = new File(destinationFolder.toString() + "/" + fileUncompressed);

            try {
                if (filesDir.exists()) {
                    System.out.println("ARQUIVOS JA EXTRAIDOS ANTERIOMENTE");
                    loadAndShowPages(window, filesDir);
                } else {
                    final FileExtractor fileExtractor = FileExtractorFactory.getFileExtractor(OS.getInstance());
                    fileExtractor.addAfterExtraction(() -> loadAndShowPages(window, filesDir));
                    fileExtractor.extract(dir, destinationFolder);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void close() {
        Platform.runLater(() -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private String getFileUncompressedName(File dir) {
        final String[] nameSplitted = dir.getName().split("\\.");
        return Arrays.stream(nameSplitted)
                .limit(nameSplitted.length - 1)
                .collect(Collectors.joining("."));
    }

    private double getPageWidth(Window window) {
        return (window.getWidth() * 98) / 100;
    }

    private Stream<File> loadFromDir(File dir) {
        final FileFilter fileFilter = file -> file.getName().matches("^[0-9]+.*") &&
                (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"));
        final File[] files = dir.listFiles(fileFilter);

        return files == null ? Stream.of() : Stream.of(files);
    }

    private void loadAndShowPages(Window window, File filesDir) {
        final Stream<File> images = loadFromDir(filesDir);
        final double width = getPageWidth(window);
        showImages(images, width);
    }

    private void showImages(Stream<File> images, double width) {
        final ObservableList<ImageView> items = listView.getItems();

        Platform.runLater(() -> items.clear());
        images.sorted(File::compareTo)
                .map(file -> new Image(file.toURI().toString()))
                .map(img -> {
                    final ImageView imageView = new ImageView(img);
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(width);
                    return imageView;
                })
                .forEach(image -> Platform.runLater(() -> items.add(image)));
    }

}
