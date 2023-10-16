package com.harun.liveSlide.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

public class BFImageConverter {
    public static ImageView imageToImageView(double viewportWidth , BufferedImage bf) {
        Image image = SwingFXUtils.toFXImage(bf, null);

        double pageWidth = 0;
        double pageHeight = 0;
        if (image.getWidth() > image.getHeight()) { // Horizontal
            pageWidth = (viewportWidth * 85) / 100;
        }
        else { //Vertical
            pageWidth = (viewportWidth * 50) / 100;
        }
        pageHeight = (image.getHeight() * pageWidth) / image.getWidth();

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(pageHeight);
        imageView.setFitWidth(pageWidth);
        return imageView;
    }
}
