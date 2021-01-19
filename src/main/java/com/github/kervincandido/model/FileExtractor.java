package com.github.kervincandido.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileExtractor {

    protected abstract String getCommand();

    private final List<AfterExtractionListener> afterExtractionListeners = new ArrayList<>();

    public void extract(File archive, File destinationFolder) throws IOException {
        final Process process = Runtime.getRuntime().exec(createFullCommand(archive, destinationFolder));
        process.onExit().thenRun(()-> afterExtractionListeners.forEach(AfterExtractionListener::onAfterExtraction));
    }

    public void addAfterExtraction(AfterExtractionListener listener) {
        synchronized (afterExtractionListeners) {
            this.afterExtractionListeners.add(listener);
        }
    }

    public void removeAfterExtraction(AfterExtractionListener listener) {
        synchronized (afterExtractionListeners) {
            this.afterExtractionListeners.remove(listener);
        }
    }

    private String createFullCommand(File archive, File destinationFolder) {
        return getCommand().trim() + " " + archive.toString() + " " + destinationFolder.toString();
    }
}
