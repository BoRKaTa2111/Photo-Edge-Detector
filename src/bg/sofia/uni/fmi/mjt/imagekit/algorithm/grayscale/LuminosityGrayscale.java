package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.validation.ImageValidation.validate;

public class LuminosityGrayscale implements GrayscaleAlgorithm {
    private static final double RED_COEFFICIENT = 0.21;
    private static final double GREEN_COEFFICIENT = 0.72;
    private static final double BLUE_COEFFICIENT = 0.07;

    public LuminosityGrayscale() {
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        validate(image);

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayscaleImage = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                double red = (color.getRed() * RED_COEFFICIENT);
                double green = (color.getGreen() * GREEN_COEFFICIENT);
                double blue = (color.getBlue() * BLUE_COEFFICIENT);
                int sum = (int)(red + green + blue);
                Color shadeOfGray = new Color(sum, sum, sum);
                grayscaleImage.setRGB(x, y, shadeOfGray.getRGB());
            }
        }

        return grayscaleImage;
    }
}
//package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;
//
//import java.awt.image.BufferedImage;
//
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.BLUE_COEFF;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.EIGHT;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.GREEN_COEFF;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.RED_COEFF;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.SIXTEEN;
////import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.TWENTY_FOUR;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.TWO_HUNDRED_FIFTY_FIVE;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.constants.Constants.TWO_HUNDRED_FIFTY_FIVE_HEX;
//import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.validation.ImageValidation.validate;
//
//public class LuminosityGrayscale implements GrayscaleAlgorithm {
//
//    public LuminosityGrayscale() {
//    }
//
//    @Override
//    public BufferedImage process(BufferedImage image) {
//        validate(image);
//        int width = image.getWidth();
//        int height = image.getHeight();
//        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
//
//        int rgb;
//        int r;
//        int g;
//        int b;
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                rgb = image.getRGB(j, i);
//                r = (rgb >> SIXTEEN) & TWO_HUNDRED_FIFTY_FIVE_HEX;
//                g = (rgb >> EIGHT) & TWO_HUNDRED_FIFTY_FIVE_HEX;
//                b = rgb & TWO_HUNDRED_FIFTY_FIVE_HEX;
//                int grayValue = (int) (RED_COEFF * r + GREEN_COEFF * g + BLUE_COEFF * b);
//                grayValue = Math.min(TWO_HUNDRED_FIFTY_FIVE, Math.max(0, grayValue));
//                rgb = (grayValue << SIXTEEN) | (grayValue << EIGHT) | grayValue;
//                grayImage.setRGB(j, i, rgb);
//            }
//        }
//        return grayImage;
//    }
//}
