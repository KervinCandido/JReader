package com.github.kervincandido.model;

public class FileExtractorWindows extends FileExtractor{
    @Override
    protected String getCommand() {
        return "WINRAR -x";
    }
}
