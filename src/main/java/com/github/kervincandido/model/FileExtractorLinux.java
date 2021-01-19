package com.github.kervincandido.model;

public class FileExtractorLinux extends FileExtractor{
    @Override
    protected String getCommand() {
        return "unrar x";
    }
}
