package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;

/**
 * this class present paddle at center of window.
 */
public class MockPaddle extends Paddle {
    private final int numCollisionsToDisappear;
    private final GameObjectCollection gameObjectCollection;
    private int counter;
    /**
     * isInstantiated parameter is true if mock paddle is in game.
     */
    public static boolean isInstantiated = false;

    /**
     * @param topLeftCorner            Position of the object, in window coordinates (pixels).
     * @param dimensions               Width and height in window coordinates.
     * @param renderable               The renderable representing the object. Can be null, in which case
     * @param inputListener            inputListener object
     * @param windowDimensions         window dimensions
     * @param gameObjectCollection     game object collection object
     * @param minDistanceFromEdge      minDistanceFromEdge that paddle can achieve. should be the border length
     * @param numCollisionsToDisappear as name. after this number of collision the nockpaddle disappear
     */
    public MockPaddle(danogl.util.Vector2 topLeftCorner, danogl.util.Vector2 dimensions, danogl.gui.rendering.Renderable
            renderable, danogl.gui.UserInputListener inputListener, danogl.util.Vector2 windowDimensions,
                      danogl.collisions.GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge, int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        this.counter = 0;
        isInstantiated = true;

    }

    /**
     * @param other     ball object
     * @param collision all details of collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball) {
            counter++;
        }
        if (counter == numCollisionsToDisappear) {
            isInstantiated = false;
            gameObjectCollection.removeGameObject(this);
        }

    }
}
