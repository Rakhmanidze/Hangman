package org.example.hangmanjavafx.scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.hangmanjavafx.Hangman;
import org.example.hangmanjavafx.ui.CustomButton;
import java.util.logging.Logger;
import static org.example.hangmanjavafx.main.GameStates.*;

/**
 * Abstract class for managing end-game scenes in the Hangman game.
 * Implements basic scene rendering and button handling.
 */
public abstract class GameEndScene implements SceneMethods {
    protected Hangman hangman;
    protected Image endSceneImage;
    protected CustomButton menuButton, replayButton;
    protected static final Logger LOGGER = Logger.getLogger(GameEndScene.class.getName());

    /**
     * Constructor for initializing the game end scene.
     * @param hangman The Hangman instance controlling the game.
     */
    public GameEndScene(Hangman hangman) {
        this.hangman = hangman;
        initButtons();
    }

    /**
     * Initializes replay and menu buttons for the end scene.
     */
    private void initButtons() {
        int buttonWidth = 150;
        int buttonHeight = buttonWidth / 3;
        int x = 606 / 2 - buttonWidth / 2;
        int y = 450;
        int xOffset = 190;

        replayButton = new CustomButton("Replay", x, y, buttonWidth, buttonHeight);
        menuButton = new CustomButton("Menu",  x + xOffset, y, buttonWidth, buttonHeight);
    }

    /**
     * Abstract method to initialize the end scene image.
     * Each subclass must implement its own way of loading and setting the image.
     */
    protected abstract void initEndSceneImage();

    /**
     * Draws the replay and menu buttons on the scene.
     * @param gc The GraphicsContext used for rendering.
     */
    private void drawButtons(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        replayButton.draw(gc);
        menuButton.draw(gc);
    }

    /**
     * Draws the end scene image on the canvas.
     * @param gc The GraphicsContext used for rendering.
     */
    private void drawEndSceneImage(GraphicsContext gc) {
        gc.drawImage(endSceneImage, 0, 0);
    }

    /**
     * Renders the end game scene by drawing the image and buttons.
     * @param gc The GraphicsContext used for rendering.
     */
    @Override
    public void render(GraphicsContext gc) {
        drawEndSceneImage(gc);
        drawButtons(gc);
    }

    /**
     * Handles mouse click events on the end game scene.
     * Opens the menu or replays the game based on button clicks.
     * @param x The x-coordinate of the mouse click.
     * @param y The y-coordinate of the mouse click.
     */
    @Override
    public void mouseClicked(int x, int y) {
        if (menuButton.getBounds().contains(x, y)) {
            setGameState(MENU);
            LOGGER.info("Menu scene opened");
        } else if (replayButton.getBounds().contains(x, y)) {
            replayGame();
            LOGGER.info("Playing scene opened");
        }
    }

    /**
     * Handles mouse movement events on the end game scene.
     * Updates button hover states based on mouse position.
     * @param x The x-coordinate of the mouse cursor.
     * @param y The y-coordinate of the mouse cursor.
     */
    @Override
    public void mouseMoved(int x, int y) {
        menuButton.setButtonHovered(menuButton.getBounds().contains(x, y));
        replayButton.setButtonHovered(replayButton.getBounds().contains(x, y));
    }

    /**
     * Resets the game state and starts a new game session.
     */
    private void replayGame() {
        resetEverything();
        setGameState(PLAYING);
    }

    /**
     * Resets everything in the current game session.
     * This method is specific to resetting the game state after replaying.
     */
    private void resetEverything() {
        hangman.getPlaying().resetEverything();
    }
}

