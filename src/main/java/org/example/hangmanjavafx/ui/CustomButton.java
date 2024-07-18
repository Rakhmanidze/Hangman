package org.example.hangmanjavafx.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.hangmanjavafx.main.GameStates;
import java.awt.*;

/**
 * Represents a custom button used in the game.
 */
public class CustomButton {
    private final int x, y, width, height;
    private String text;
    //    check if mouse inside button:
    private Rectangle bounds;
    private boolean isButtonHovered;
    private boolean enabled = true;

    /**
     * Initializes a new instance of the CustomButton class with a specified id.
     * This constructor is used for block buttons.
     * @param text   The text displayed on the button.
     * @param x      The x-coordinate of the button.
     * @param y      The y-coordinate of the button.
     * @param width  The width of the button.
     * @param height The height of the button.
     */
    public CustomButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        initBounds();
    }

    private void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Draws the button on the canvas.
     * @param gc The graphics context to render on.
     */
    public void draw(GraphicsContext gc) {
        drawBody(gc);
        drawBorder(gc);
        drawText(gc);
    }

    /**
     * Draws the border of the button.
     * @param gc The graphics context to render on.
     */
    private void drawBorder(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(x, y, width, height, 14, 14);
    }

    /**
     * Draws the body of the button.
     * @param gc The graphics context to render on.
     */
    private void drawBody(GraphicsContext gc) {
        if (GameStates.gameState == GameStates.MENU || GameStates.gameState == GameStates.PLAYING || GameStates.gameState == GameStates.GAME_OVER || GameStates.gameState == GameStates.GAME_WON)
            if (isButtonHovered && enabled)
                gc.setFill(Color.LIGHTGRAY);
            else if (!isButtonHovered && enabled)
                gc.setFill(Color.rgb(245, 245, 220));
            else
                gc.setFill(Color.GRAY);

        gc.fillRoundRect(x, y, width, height, 14, 14);
    }

    /**
     * Draws the text on the button.
     * @param gc The graphics context to render on.
     */
    private void drawText(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        double textWidth = new Text(text).getBoundsInLocal().getWidth();
        double textHeight = new Text(text).getBoundsInLocal().getHeight();
        gc.fillText(text, x + (width - textWidth) / 2, y + (height + textHeight) / 2);
    }

    //    Getters and setters
    public Rectangle getBounds() {
        return bounds;
    }

    public void setButtonHovered(boolean isButtonHovered) {
        this.isButtonHovered = isButtonHovered;
    }

    public String getText() {
        return text;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
