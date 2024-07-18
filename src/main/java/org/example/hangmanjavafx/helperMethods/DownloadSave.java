package org.example.hangmanjavafx.helperMethods;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages image resources retrieval and logging.
 */
public class DownloadSave {
    private static final Logger LOGGER = Logger.getLogger(DownloadSave.class.getName());

    /**
     * Retrieves an image resource.
     *
     * @param imageName The name of the image file.
     * @return The image resource.
     */
    public static Image getImage(String imageName) {
        Image image = null;
        InputStream imagePath = DownloadSave.class.getClassLoader().getResourceAsStream(imageName);

        if (imagePath == null) {
            LOGGER.log(Level.SEVERE, "Image not found: " + imageName);
            return null;
        }
        try {
            image = new Image(imagePath);
            LOGGER.info("Successfully loaded image: " + imageName);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Error loading image: " + imageName, e);
        }
        return image;
    }
}
