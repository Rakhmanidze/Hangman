package org.example.hangmanjavafx.main;

import javafx.scene.canvas.GraphicsContext;
import org.example.hangmanjavafx.Hangman;
import static org.example.hangmanjavafx.main.GameStates.*;

/**
 * Handles rendering based on the current game state.
 */
public class Render {
    private final Hangman hangman;

    public Render(Hangman hangman) {
        this.hangman = hangman;
    }

    /**
     * Renders the game based on the current game state.
     * @param gc The GraphicsContext used for rendering.
     */
    public void render(GraphicsContext gc) {
        if (GameStates.gameState == MENU)
            hangman.getMenu().render(gc);
        if (GameStates.gameState == PLAYING)
            hangman.getPlaying().render(gc);
        if (GameStates.gameState == GAME_OVER)
            hangman.getGameOver().render(gc);
        if (GameStates.gameState == GAME_WON)
            hangman.getGameWon().render(gc);
    }
}
