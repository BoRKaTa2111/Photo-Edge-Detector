package bg.sofia.uni.fmi.mjt.imagekit.filesystem.systemvalidation;

import java.io.File;
import java.io.IOException;

public class SystemValidation {
    public static void validateFile(File imageFile) throws IOException {
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file cannot be null");
        }
        if (!imageFile.exists() || !imageFile.isFile()) {
            throw new IOException("Image file " + imageFile + " does not exist or is not a file");
        }
    }

    public static void validateDir(File imagesDirectory) throws IOException {
        if (imagesDirectory == null) {
            throw new IllegalArgumentException("Image directory cannot be null");
        }
        if (!imagesDirectory.exists() || !imagesDirectory.isDirectory() || !imagesDirectory.canRead()) {
            throw new IOException("Image directory is not valid");
        }
    }

    public static void validateImageFile(File imageFile) throws IOException {
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file cannot be null");
        }
        if (imageFile.exists()) {
            throw new IOException("File already exists: " + imageFile.getAbsolutePath());
        }
    }
}
