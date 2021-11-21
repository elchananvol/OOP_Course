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
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener
     * @param screenWidth
     */
    public Paddle(danogl.util.Vector2 topLeftCorner,
                   danogl.util.Vector2 dimensions,
                   danogl.gui.rendering.Renderable renderable,
                   danogl.gui.UserInputListener inputListener,
                   danogl.util.Vector2 windowDimensions,
                   int minDistanceFromEdge) {
        super(topLeftCorner,dimensions,renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.MIN_DISTANCE_FROM_SCREEN_EDGE = minDistanceFromEdge;

    }

    /**
     * Overrides update in class danogl.GameObject
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movement = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT))
        {
            movement= movement.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
        {
            movement= movement.add(Vector2.RIGHT);

        }
        setVelocity(movement.mult(MOVEMENTS_SPEED));
        if(getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE)
        {
            setTopLeftCorner(new Vector2(MIN_DISTANCE_FROM_SCREEN_EDGE,getTopLeftCorner().y()));
        }
        float leftEgde =windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x();
        if(getTopLeftCorner().x() > leftEgde)
        {
            setTopLeftCorner(new Vector2(leftEgde,getTopLeftCorner().y()));
        }
    }
}
