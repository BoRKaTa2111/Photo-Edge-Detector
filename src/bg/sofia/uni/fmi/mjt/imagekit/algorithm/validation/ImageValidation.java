package bg.sofia.uni.fmi.mjt.imagekit.algorithm.validation;

import java.awt.image.BufferedImage;

public class ImageValidation {
    public static void validate(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("image is null");
        }
    }
}
