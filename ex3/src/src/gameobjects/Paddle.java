package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * holds a Paddle object of the game.
 */
public class Paddle extends GameObject {
    private static final float MOVEMENTS_SPEED = 300;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int MIN_DISTANCE_FROM_SCREEN_EDGE;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner       Position of the object, in window coordinates (pixels).
     * @param dimensions          Width and height in window coordinates of widget.
     * @param renderable          The renderable representing the object. Can be null, in which case
     * @param inputListener       the object that responsible on get input from user
     * @param windowDimensions    the dimensions of window. need for determine when paddle out from window.
     * @param minDistanceFromEdge the border size or slide bar. as window dimensions it's needed to determine
     *                            when paddle out from window.
     */
    public Paddle(danogl.util.Vector2 topLeftCorner,
                  danogl.util.Vector2 dimensions,
                  danogl.gui.rendering.Renderable renderable,
                  danogl.gui.UserInputListener inputListener,
                  danogl.util.Vector2 windowDimensions,
                  int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.MIN_DISTANCE_FROM_SCREEN_EDGE = minDistanceFromEdge;

    }

    /**
     * Overrides update in class danogl.GameObject
     * move the paddle right or left. if the paddle exceed from the border then do nothing
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movement = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movement = movement.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movement = movement.add(Vector2.RIGHT);

        }
        setVelocity(movement.mult(MOVEMENTS_SPEED));

        float leftEgde = windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x() - 1;
        float rightEgde = MIN_DISTANCE_FROM_SCREEN_EDGE + 1;
        if (getTopLeftCorner().x() < rightEgde) {
            setTopLeftCorner(new Vector2(rightEgde, getTopLeftCorner().y()));
        }

        if (getTopLeftCorner().x() > leftEgde) {
            setTopLeftCorner(new Vector2(leftEgde, getTopLeftCorner().y()));
        }
    }
}
