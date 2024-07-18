package org.example.hangmanjavafx.scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import org.example.hangmanjavafx.helperMethods.DownloadSave;
import org.example.hangmanjavafx.ui.CustomButton;
import java.util.logging.Logger;
import static org.example.hangmanjavafx.main.GameStates.PLAYING;
import static org.example.hangmanjavafx.main.GameStates.setGameState;

/**
 * Represents the main menu scene where players can start playing the game or exit the game.
 * Implements SceneMethods.
 */
public class Menu implements SceneMethods {
    private CustomButton buttonPlay,  buttonExit;
    private Image menuBackgroundImage;
    private static final Logger LOGGER = Logger.getLogger(Menu.class.getName());

    /**
     * Constructs a Menu object with the specified TowerDefense game.
     */
    public Menu() {
        initButtons();
        initMenuBackgroundImage();
    }

    /**
     * Initializes play and exit buttons for the end scene.
     */
    private void initButtons() {
        int buttonWidth = 150;
        int buttonHeight = buttonWidth / 3;
        int x = 606 / 2 - buttonWidth / 2;
        int y = 450;
        int xOffset = 190;

        buttonPlay = new CustomButton("Play", x, y, buttonWidth, buttonHeight);
        buttonExit = new CustomButton("Exit", x + xOffset, y , buttonWidth, buttonHeight);
    }

    /**
     * Initializes the menu background image by loading it from a file.
     */
    private void initMenuBackgroundImage() {
        menuBackgroundImage = DownloadSave.getImage("menu.jpg");
    }

    /**
     * Draws the buttons displayed in the menu scene.
     * @param gc The graphics context to draw on.
     */
    private void drawButtons(GraphicsContext gc) {
        gc.setFont(Font.font(18));
        buttonPlay.draw(gc);
        buttonExit.draw(gc);
    }


    /**
     * Draws the menu background image on the canvas.
     * @param gc The graphics context to draw on.
     */
    public void drawMenuBackgroundImage(GraphicsContext gc) {
        gc.drawImage(menuBackgroundImage, 0, 0);
    }

    /**
     * Renders the menu scene, displaying background, buttons, and text.
     * @param gc The graphics context to render on.
     */
    @Override
    public void render(GraphicsContext gc) {
        drawMenuBackgroundImage(gc);
        drawButtons(gc);
    }

    /**
     * Handles mouse click events in the menu scene.
     * @param x The x-coordinate of the mouse click.
     * @param y The y-coordinate of the mouse click.
     */
    @Override
    public void mouseClicked(int x, int y) {
        if (buttonPlay.getBounds().contains(x, y)) {
            setGameState(PLAYING);
            LOGGER.info("Playing scene opened");
        } else if (buttonExit.getBounds().contains(x, y)) {
            LOGGER.info("Game exit");
            System.exit(0);
        }
    }

    /**
     * Handles mouse movement events in the menu scene.
     * @param x The x-coordinate of the mouse.
     * @param y The y-coordinate of the mouse.
     */
    @Override
    public void mouseMoved(int x, int y) {
        buttonPlay.setButtonHovered(buttonPlay.getBounds().contains(x, y));
        buttonExit.setButtonHovered(buttonExit.getBounds().contains(x, y));
    }
}
