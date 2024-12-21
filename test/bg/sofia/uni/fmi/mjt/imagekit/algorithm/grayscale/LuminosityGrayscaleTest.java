package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LuminosityGrayscaleTest {
    @Test
    void testProcessNullImage() {
        LuminosityGrayscale lum = new LuminosityGrayscale();
        assertThrows(IllegalArgumentException.class, ()->lum.process(null));
    }
    @Test
    void testProcessValidImage() {
        BufferedImage mockImage = mock(BufferedImage.class);
        when(mockImage.getWidth()).thenReturn(2);
        when(mockImage.getHeight()).thenReturn(2);

        when(mockImage.getRGB(0, 0)).thenReturn(0xFF0000);
        when(mockImage.getRGB(1, 0)).thenReturn(0x00FF00);
        when(mockImage.getRGB(0, 1)).thenReturn(0x0000FF);
        when(mockImage.getRGB(1, 1)).thenReturn(0xFFFFFF);

        LuminosityGrayscale grayscaleProcessor = new LuminosityGrayscale();

        BufferedImage grayImage = grayscaleProcessor.process(mockImage);

        assertEquals(2, grayImage.getWidth());
        assertEquals(2, grayImage.getHeight());
        assertEquals(0x999999, grayImage.getRGB(0, 0));
        assertEquals(0x999999, grayImage.getRGB(1, 0));
        assertEquals(0x999999, grayImage.getRGB(0, 1));
        assertEquals(0x999999, grayImage.getRGB(1, 1));
    }

}
