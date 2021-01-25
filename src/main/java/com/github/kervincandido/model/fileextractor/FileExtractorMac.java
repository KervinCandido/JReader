package com.github.kervincandido.model.fileextractor;

public class FileExtractorMac extends FileExtractor {

    @Override
    protected String getCommand() {
        return "unrar x";
    }
}
