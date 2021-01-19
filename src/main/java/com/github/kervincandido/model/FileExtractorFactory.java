package com.github.kervincandido.model;

public class FileExtractorFactory {

    public static FileExtractor getFileExtractor(OS os) {
        return switch (os) {
            case MAC -> new FileExtractorMac();
            case LINUX -> new FileExtractorLinux();
            case WINDOWS -> new FileExtractorWindows();
        };
    }
}
