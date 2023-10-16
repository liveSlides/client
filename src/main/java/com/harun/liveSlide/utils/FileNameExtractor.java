package com.harun.liveSlide.utils;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileNameExtractor {
    public static String getFileNameFromPath(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return path.getFileName().toString();
        }
        catch (InvalidPathException e) {
            return filePath;
        }
    }
}
