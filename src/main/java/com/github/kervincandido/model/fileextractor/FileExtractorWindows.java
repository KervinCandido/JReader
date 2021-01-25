package com.github.kervincandido.model.fileextractor;

public class FileExtractorWindows extends FileExtractor {
    @Override
    protected String getCommand() {
        return "WINRAR -x";
    }
}
