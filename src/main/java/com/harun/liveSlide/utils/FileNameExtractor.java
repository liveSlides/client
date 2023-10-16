package com.harun.liveSlide.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileNameExtractor {
    public static String getFileNameFromPath(String filePath) {
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }
}
