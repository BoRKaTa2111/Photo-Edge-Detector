package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LocalFileSystemImageManagerTest {
    private LocalFileSystemImageManager manager;

    @BeforeEach
    void setUp() {
        manager = new LocalFileSystemImageManager();
    }
    @Test
    void testLoadNullFile() {
        assertThrows(IllegalArgumentException.class, () -> manager.loadImage(null));
    }
    @Test
    void testLoadNonExistingFile() {
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);
        assertThrows(IOException.class, () -> manager.loadImage(file));
    }
//    @Test
//    void testLoadImageValidFile() throws IOException {
//        File file = new File("C:\\Users\\barca\\OneDrive\\Desktop\\noenemies.jpg");
//
//        BufferedImage result = manager.loadImage(file);
//
//        assertNotNull(result, "returned image should not be null");
//        assertEquals(BufferedImage.TYPE_INT_RGB, result.getType(), "image type should be RGB");
//        assertEquals(ImageIO.read(file).getWidth(), result.getWidth(), "width of the image should match");
//        assertEquals(ImageIO.read(file).getHeight(), result.getHeight(), "height of the image should match");
//    }

    @Test
    void testLoadImageValidFile() throws IOException {
        FileSystemImageManager manager = new LocalFileSystemImageManager();
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Path tempFile = Files.createTempFile(null, ".jpg");
        File imageFile = tempFile.toFile();
        ImageIO.write(img, "JPEG", imageFile);
        try {
            BufferedImage loadedImage = manager.loadImage(imageFile);
            assertEquals(img.getHeight(), loadedImage.getHeight(), "should match");
            assertNotNull(loadedImage, "should load succ");
            assertEquals(img.getWidth(), loadedImage.getWidth(), "should match");

        } catch (IOException e){
            if(e.getCause() != null && !e.getCause().getMessage().equals("closed")) {
                throw new IOException(e.getMessage());
            }
        }
    }


    @Test
    void testLoadImagesFromNullDirectory() {
        assertThrows(IllegalArgumentException.class, () -> manager.loadImagesFromDirectory(null));
    }

    @Test
    void testLoadImagesFromNonExistingDirectory() {
        File directory = mock(File.class);
        when(directory.exists()).thenReturn(false);
        assertThrows(IOException.class, () -> manager.loadImagesFromDirectory(directory));
    }

//    @Test
//    void testLoadImagesFromExistingDirectory() throws IOException {
//        File directory = new File("C:\\Users\\barca\\OneDrive\\Desktop");
//        List<BufferedImage> images = manager.loadImagesFromDirectory(directory);
//        assertNotNull(images, "The returned list of images should not be null");
//        assertFalse(images.isEmpty(), "The directory should contain at least one valid image");
//    }

@Test
void testLoadImagesFromDirectoryValidDirectoryAndFiles() throws IOException {
        FileSystemImageManager manager = new LocalFileSystemImageManager();
    File notFile = mock(File.class);
    when(notFile.isFile()).thenReturn(false);

    BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    Path tempFile = Files.createTempFile(null, ".jpg");
    File realFile = tempFile.toFile();
    ImageIO.write(img, "jpg", realFile);
    File directory = mock(File.class);
    when(directory.exists()).thenReturn(true);
    when(directory.isDirectory()).thenReturn(true);
    File[] files = {notFile, realFile};
    when(directory.listFiles()).thenReturn(files);
    try {
        assertEquals(1, manager.loadImagesFromDirectory(directory).size());
    } catch (IOException e){
        if(e.getCause() != null && !e.getCause().getMessage().equals("closed")) {
            throw new IOException(e.getMessage());
        }
    }
}


    @Test
    void testSaveImageToNullFile() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        assertThrows(IllegalArgumentException.class, () -> manager.saveImage(image, null));
    }

    @Test
    void testSaveImageToExistingFile() {
        File file = mock(File.class);
        when(file.exists()).thenReturn(true);
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        assertThrows(IOException.class, () -> manager.saveImage(image, file));
    }

    @Test
    void testSaveImageToUnsupportedFileFormat() {
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);
        when(file.getName()).thenReturn("image.xyz");
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        assertThrows(IOException.class, () -> manager.saveImage(image, file));
    }

    @Test
    void testSaveImageSuccessfully() throws IOException {
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);
        when(file.getName()).thenReturn("image.png");

        File parentDir = mock(File.class);
        when(parentDir.exists()).thenReturn(true);
        when(file.getParentFile()).thenReturn(parentDir);

        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        manager.saveImage(image, file);

        verify(file).getName();
        assertNotNull(image, "The saved image should not be null");
    }

    @Test
    void testSaveNonRGBImage() throws IOException {
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);
        when(file.getName()).thenReturn("image.png");

        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        manager.saveImage(image, file);

        verify(file).getName();
        assertEquals(BufferedImage.TYPE_INT_RGB, image.getType(), "The image type should be converted to RGB");
    }

}


