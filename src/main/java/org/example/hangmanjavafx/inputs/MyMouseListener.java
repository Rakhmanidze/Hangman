package org.example.hangmanjavafx.inputs;


import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.example.hangmanjavafx.main.GameStates;
import org.example.hangmanjavafx.Hangman;
import static org.example.hangmanjavafx.main.GameStates.*;

/**
 * EventHandler implementation for handling mouse input in the TowerDefense game.
 * It delegates mouse events to the appropriate method in the corresponding state classes based on the game state.
 */
public class MyMouseListener implements EventHandler<MouseEvent> {
    private final Hangman hangman;

    /**
     * Constructs a MyMouseListener object with the specified TowerDefense game.
     *
     * @param hangman  the TowerDefense game to delegate mouse events to
     */
    public MyMouseListener (Hangman hangman) {
        this.hangman = hangman;
    }

    /**
     * Handles mouse events such as mouse clicks, mouse moves, and mouse drags.
     * @param event The MouseEvent to handle.
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {

            if (GameStates.gameState == MENU)
                hangman.getMenu().mouseClicked((int) event.getX(), (int) event.getY());
            else if (GameStates.gameState == PLAYING)
                hangman.getPlaying().mouseClicked((int) event.getX(), (int) event.getY());
            else if (GameStates.gameState == GAME_OVER)
                hangman.getGameOver().mouseClicked((int) event.getX(), (int) event.getY());
            else if (GameStates.gameState == GAME_WON)
                hangman.getGameWon().mouseClicked((int) event.getX(), (int) event.getY());

        } else if (event.getEventType() == MouseEvent.MOUSE_MOVED) {

            if (GameStates.gameState == MENU)
                hangman.getMenu().mouseMoved((int) event.getX(), (int) event.getY());
            else if (GameStates.gameState == PLAYING)
                hangman.getPlaying().mouseMoved((int) event.getX(), (int) event.getY());
            else if (GameStates.gameState == GAME_OVER)
                hangman.getGameOver().mouseMoved((int) event.getX(), (int) event.getY());
            else if (GameStates.gameState == GAME_WON)
                hangman.getGameWon().mouseMoved((int) event.getX(), (int) event.getY());
        }
    }
}