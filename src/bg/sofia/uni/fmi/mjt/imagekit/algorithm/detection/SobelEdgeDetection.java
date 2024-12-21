package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.validation.ImageValidation.validate;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {
    private static final int[][] SOBEL_X = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };
    private static final int[][] SOBEL_Y = {
            {-1, -2, -1},
            { 0,  0,  0},
            { 1,  2,  1}
    };

    private static final int FF = 255;

    private final ImageAlgorithm grayscaleAlgorithm;

    public SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm) {
        this.grayscaleAlgorithm = grayscaleAlgorithm;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        validate(image);
        BufferedImage grayImage = grayscaleAlgorithm.process(image);
        int width = grayImage.getWidth();
        int height = grayImage.getHeight();
        BufferedImage edgeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int gx = 0;
                int gy = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        Color neighborColor = new Color(grayImage.getRGB(x + kx, y + ky));
                        int intensity = neighborColor.getRed();
                        gx += intensity * SOBEL_X[ky + 1][kx + 1];
                        gy += intensity * SOBEL_Y[ky + 1][kx + 1];
                    }
                }
                int edgeStrength = (int) Math.sqrt(gx * gx + gy * gy);
                edgeStrength = Math.min(FF, edgeStrength);
                Color edgeColor = new Color(edgeStrength, edgeStrength, edgeStrength);
                edgeImage.setRGB(x, y, edgeColor.getRGB());
            }
        }
        return edgeImage;
    }
}
//package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;
//
//import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
//
//import java.awt.image.BufferedImage;
//
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.EIGHT;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.SIXTEEN;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.THRESHOLD_VAL;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.TWO_HUNDRED_FIFTY_FIVE;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.TWO_HUNDRED_FIFTY_FIVE_HEX;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.validation.ImageValidation.validate;
//
//public class SobelEdgeDetection implements EdgeDetectionAlgorithm {
//
//    private ImageAlgorithm grayscaleAlgorithm;
//
//    public SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm) {
//        this.grayscaleAlgorithm = grayscaleAlgorithm;
//    }
//
//    @Override
//    public BufferedImage process(BufferedImage image) {
//        validate(image);
//        int width = image.getWidth();
//        int height = image.getHeight();
//        BufferedImage edgeImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
//        int[][] vert = new int[width][height];
//        int[][] hor = new int[width][height];
//        int[][] edgeWeight = new int[width][height];
//        for (int y = 1; y < height - 1; y++) {
//            for (int x = 1; x < width - 1; x++) {
//                vert[x][y] = (image.getRGB(x + 1, y - 1) & TWO_HUNDRED_FIFTY_FIVE_HEX) +
//                        2 * (image.getRGB(x + 1, y) & TWO_HUNDRED_FIFTY_FIVE_HEX) +
//                        (image.getRGB(x + 1, y + 1) & TWO_HUNDRED_FIFTY_FIVE_HEX) -
//                        (image.getRGB(x - 1, y - 1) & TWO_HUNDRED_FIFTY_FIVE_HEX) -
//                        2 * (image.getRGB(x - 1, y) & TWO_HUNDRED_FIFTY_FIVE_HEX) -
//                        (image.getRGB(x - 1, y + 1) & TWO_HUNDRED_FIFTY_FIVE_HEX);
//                hor[x][y] = (image.getRGB(x - 1, y + 1) & TWO_HUNDRED_FIFTY_FIVE_HEX) +
//                        2 * (image.getRGB(x, y + 1) & TWO_HUNDRED_FIFTY_FIVE_HEX) +
//                        (image.getRGB(x + 1, y + 1) & TWO_HUNDRED_FIFTY_FIVE_HEX) -
//                        (image.getRGB(x - 1, y - 1) & TWO_HUNDRED_FIFTY_FIVE_HEX) -
//                        2 * (image.getRGB(x, y - 1) & TWO_HUNDRED_FIFTY_FIVE_HEX) -
//                        (image.getRGB(x + 1, y - 1) & TWO_HUNDRED_FIFTY_FIVE_HEX);
//                edgeWeight[x][y] = (int) Math.sqrt(vert[x][y] * vert[x][y] + hor[x][y] * hor[x][y]);
//                int pixelValue = (edgeWeight[x][y] > THRESHOLD_VAL) ? TWO_HUNDRED_FIFTY_FIVE : 0;
//                int grayscaleColor = (pixelValue << SIXTEEN) | (pixelValue << EIGHT) | pixelValue;
//                edgeImage.setRGB(x, y, grayscaleColor);
//            }
//        }
//        return edgeImage;
//    }
//}
