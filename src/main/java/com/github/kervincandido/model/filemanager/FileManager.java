package com.github.kervincandido.model.filemanager;

import java.io.File;

public class FileManager {

    private final File jreaderFolder;

    public FileManager() {
        final String userDir = System.getProperty("user.home");
        jreaderFolder = new File(userDir, ".jreader");
        createFolder();
    }

    private void createFolder() {
        jreaderFolder.mkdirs();
    }

    public File getJreaderFolder() {
        return this.jreaderFolder;
    }
}
