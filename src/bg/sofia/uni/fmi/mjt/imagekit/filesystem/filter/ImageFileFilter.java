package bg.sofia.uni.fmi.mjt.imagekit.filesystem.filter;

import java.io.File;
import java.io.FilenameFilter;

public class ImageFileFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        String lowerCaseName = name.toLowerCase();
        return lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".jpeg")
                || lowerCaseName.endsWith(".png") || lowerCaseName.endsWith(".bmp")
                || lowerCaseName.endsWith(".gif");
    }
}
