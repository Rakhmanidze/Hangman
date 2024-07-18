package org.example.hangmanjavafx.main;

/**
 * Represents the game window with its dimensions and update count.
 */
public class GameWindow {
    private int updates = 0;

    public void update() {
        updates++;
    }

    public void resetUpdates() {
        updates = 0;
    }

    //    Getters
    public int getWindowWidth() {
        return 800;
    }

    public int getWindowHeight() {
        return 600;
    }

    public int getUpdates() {
        return updates;
    }
}
