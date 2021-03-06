package com.backend.bank.common;

import com.google.common.io.Files;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ImageResizer {
    public void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) {
        try {
            // reads input image
            File inputFile = new File(inputImagePath);
            BufferedImage inputImage = ImageIO.read(inputFile);

            // creates output image
            BufferedImage outputImage = new BufferedImage(scaledWidth,
                    scaledHeight, inputImage.getType());

            // scales the input image to the output image
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            // extracts extension of output file
            String formatName = outputImagePath.substring(outputImagePath
                    .lastIndexOf(".") + 1);

            // writes to output file
            ImageIO.write(outputImage, formatName, new File(outputImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param percent a double number specifies percentage of the output image
     * over the input image.
     * @throws IOException
     */
//    public static void resize(String inputImagePath,
//                              String outputImagePath, double percent) throws IOException {
//        File inputFile = new File(inputImagePath);
//        BufferedImage inputImage = ImageIO.read(inputFile);
//        int scaledWidth = (int) (inputImage.getWidth() * percent);
//        int scaledHeight = (int) (inputImage.getHeight() * percent);
//        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
//    }

    /**
     * Test resizing images
     */
//    public static void main(String[] args) {
//        String inputImagePath = "https://sapotacorp.com:8443/uploads/News/31f3cb308871612f3860.jpg";
//        String outputImagePath1 = "C:/Users/HoangKha/Desktop/anhcho1.jpg";
//        String outputImagePath2 = "C:/Users/HoangKha/Desktop/anhcho2.jpg";
//        String outputImagePath3 = "C:/Users/HoangKha/Desktop/anhcho3.jpg";
//        String[] nameImage = inputImagePath.split("8443/");
//        String[] nameoutImage = nameImage[1].split("/");
//
//        String formatName = nameImage[1].substring(nameImage[1]
//                .lastIndexOf("/") + 1);
//        String formatNam = formatName.substring(formatName
//                .lastIndexOf(".") - 1);
//        try {
//            // resize to a fixed width (not proportional)
//            int scaledWidth = 1024;
//            int scaledHeight = 768;
//            ImageResizer.resize(inputImagePath, outputImagePath1, scaledWidth, scaledHeight);
//
//            // resize smaller by 50%
//            double percent = 0.5;
//            ImageResizer.resize(inputImagePath, outputImagePath2, percent);
//
//            // resize bigger by 50%
//            percent = 1.5;
//            ImageResizer.resize(inputImagePath, outputImagePath3, percent);
//
//        } catch (IOException ex) {
//            System.out.println("Error resizing the image.");
//            ex.printStackTrace();
//        }
//    }

}
