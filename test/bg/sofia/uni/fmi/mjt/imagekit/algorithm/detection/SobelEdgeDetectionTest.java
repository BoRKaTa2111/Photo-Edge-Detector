package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SobelEdgeDetectionTest {
        private SobelEdgeDetection sobelEdgeDetection;
        private ImageAlgorithm mockGrayscaleAlgorithm;

        @BeforeEach
        void setUp() {
            mockGrayscaleAlgorithm = mock(ImageAlgorithm.class);
            sobelEdgeDetection = new SobelEdgeDetection(mockGrayscaleAlgorithm);
        }
        @Test
        void testProcessNullImage() {
            assertThrows(IllegalArgumentException.class, () -> sobelEdgeDetection.process(null));
        }
    @Test
    void testProcessValidImage() {
        BufferedImage mockImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

        mockImage.setRGB(0, 0, 0xFF0000);
        mockImage.setRGB(0, 1, 0x00FF00);
        mockImage.setRGB(0, 2, 0x0000FF);
        mockImage.setRGB(1, 0, 0xFFFFFF);
        mockImage.setRGB(1, 1, 0xFF0000);
        mockImage.setRGB(1, 2, 0x00FF00);
        mockImage.setRGB(2, 0, 0x0000FF);
        mockImage.setRGB(2, 1, 0xFFFFFF);
        mockImage.setRGB(2, 2, 0xFF0000);

        LuminosityGrayscale grayscaleAlgorithm = new LuminosityGrayscale();
        BufferedImage grayscaleImage = grayscaleAlgorithm.process(mockImage);

        SobelEdgeDetection sobelEdgeDetection = new SobelEdgeDetection(grayscaleAlgorithm);
        BufferedImage edgeImage = sobelEdgeDetection.process(mockImage);

        assertEquals(mockImage.getWidth(), edgeImage.getWidth());
        assertEquals(mockImage.getHeight(), edgeImage.getHeight());

        assertNotEquals(grayscaleImage.getRGB(1, 1), edgeImage.getRGB(1, 1));

    }
}
