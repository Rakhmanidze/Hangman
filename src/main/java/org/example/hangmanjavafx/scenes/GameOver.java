package org.example.hangmanjavafx.scenes;

import org.example.hangmanjavafx.Hangman;
import org.example.hangmanjavafx.helperMethods.DownloadSave;

/**
 * Represents the game over scene in the Hangman game.
 * Displays a game over image and provides replay and menu buttons.
 */
public class GameOver extends GameEndScene implements SceneMethods {

    /**
     * Constructor for initializing the game over scene.
     * @param hangman The Hangman instance controlling the game.
     */
    public GameOver(Hangman hangman) {
        super(hangman);
        initEndSceneImage();
    }

    /**
     * Initializes the game over image by loading it from a file.
     * Uses the DownloadSave class to retrieve the image resource.
     */
    @Override
    protected void initEndSceneImage() {
        endSceneImage = DownloadSave.getImage("gameover.png");
    }
}
