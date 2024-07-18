package org.example.hangmanjavafx.scenes;

import org.example.hangmanjavafx.Hangman;
import org.example.hangmanjavafx.helperMethods.DownloadSave;

/**
 * Represents the game won scene in the Hangman game.
 * Displays a victory image and provides replay and menu buttons.
 */
public class GameWon extends GameEndScene implements SceneMethods {

    /**
     * Constructor for initializing the game won scene.
     * @param hangman The Hangman instance controlling the game.
     */
    public GameWon(Hangman hangman) {
        super(hangman);
        initEndSceneImage();
    }

    /**
     * Initializes the game won image by loading it from a file.
     * Uses the DownloadSave class to retrieve the image resource.
     */
    @Override
    protected void initEndSceneImage() {
        endSceneImage = DownloadSave.getImage("gamewon.jpg");
    }
}

