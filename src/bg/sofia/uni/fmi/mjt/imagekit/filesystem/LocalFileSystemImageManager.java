package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import bg.sofia.uni.fmi.mjt.imagekit.filesystem.filter.ImageFileFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import static bg.sofia.uni.fmi.mjt.imagekit.algorithm.validation.ImageValidation.validate;
import static bg.sofia.uni.fmi.mjt.imagekit.filesystem.systemvalidation.SystemValidation.validateDir;
import static bg.sofia.uni.fmi.mjt.imagekit.filesystem.systemvalidation.SystemValidation.validateFile;
import static bg.sofia.uni.fmi.mjt.imagekit.filesystem.systemvalidation.SystemValidation.validateImageFile;

public class LocalFileSystemImageManager implements FileSystemImageManager {
    public LocalFileSystemImageManager() {
    }

    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        validateFile(imageFile);

        BufferedImage loadedImage = ImageIO.read(imageFile);
        if (loadedImage == null) {
            throw new IOException("Unsupported image format or corrupt file: " + imageFile.getName());
        }

        BufferedImage rgbImage = new BufferedImage(
                loadedImage.getWidth(),
                loadedImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        rgbImage.getGraphics().drawImage(loadedImage, 0, 0, null);
        return rgbImage;
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        validateDir(imagesDirectory);

        List<BufferedImage> images = new ArrayList<>();
        File[] files = imagesDirectory.listFiles(new ImageFileFilter());
        if (files == null) {
            throw new IOException("Error reading files from the directory");
        }
        for (File file : files) {
            try {
                BufferedImage loadedImage = ImageIO.read(file);
                if (loadedImage != null) {
                    BufferedImage rgbImage = new BufferedImage(
                            loadedImage.getWidth(),
                            loadedImage.getHeight(),
                            BufferedImage.TYPE_INT_RGB
                    );
                    rgbImage.getGraphics().drawImage(loadedImage, 0, 0, null);
                    images.add(rgbImage);
                } else {
                    System.err.println("Skipping unsupported or corrupt image file: " + file.getName());
                }
            } catch (IOException e) {
                System.err.println("Failed to load image: " + file.getName());
            }
        }
        return images;
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {
        validate(image);
        validateImageFile(imageFile);
        File parentDir = imageFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            throw new IOException("Parent directory does not exist: " + parentDir.getAbsolutePath());
        }
        String fileName = imageFile.getName();
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        if (!fileExtension.equals("png") && !fileExtension.equals("jpg")
                && !fileExtension.equals("jpeg") && !fileExtension.equals("bmp")
                && !fileExtension.equals("gif")) {
            throw new IOException("Unsupported file format: " + fileExtension);
        }
        if (image.getType() != BufferedImage.TYPE_INT_RGB) {
            BufferedImage rgbImage = new BufferedImage(
                    image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            rgbImage.getGraphics().drawImage(image, 0, 0, null);
            image = rgbImage;
        }
        if (!ImageIO.write(image, fileExtension, imageFile)) {
            throw new IOException("Failed to save image to file: " + imageFile.getAbsolutePath());
        }
    }
}

