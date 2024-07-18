package org.example.hangmanjavafx.scenes;

import javafx.scene.canvas.GraphicsContext;

/**
 * Defines methods required for managing scenes in a game.
 */
public interface SceneMethods {
    public void render(GraphicsContext gc);
    public void mouseClicked(int x, int y);
    public void mouseMoved(int x, int y);
}
