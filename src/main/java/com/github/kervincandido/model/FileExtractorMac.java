package com.github.kervincandido.model;

public class FileExtractorMac extends FileExtractor{

    @Override
    protected String getCommand() {
        return "unrar x";
    }
}
