package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * it presents an object that come out from center of brick that broken.
 * it goes down in constant velocity, and disappear when it crosses the window.
 */
public class StatusDefines extends GameObject {
    private static final float BALL_SPEED = 200;
    static final private Vector2 VELOCITY = new Vector2(0, BALL_SPEED / 2);
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 windowDimensions;

    /**
     * constructor
     *
     * @param topLeftCorner        Position of the object, in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param dimensions           Width and height in window coordinates.
     * @param renderable           The renderable representing the object. Can be null, in which case
     * @param gameObjectCollection gameObjectCollection
     * @param brick                brick the collision make it appear.
     * @param windowDimensions     window dimensions
     */
    public StatusDefines(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                         GameObjectCollection gameObjectCollection, GameObject brick, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.setVelocity(VELOCITY);
        this.setCenter(brick.getCenter());
        this.windowDimensions = windowDimensions;
    }

    /**
     * implementation: make to this object to remove from game.
     *
     * @param other     its paddle object.
     * @param collision details of collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        gameObjectCollection.removeGameObject(this, Layer.STATIC_OBJECTS);
    }

    /**
     * this method determine in which object our object collide with.
     *
     * @param other other object.
     * @return it only paddle in our case,so true if the other object is paddle false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {

        return other instanceof Paddle;
    }

    /**
     * implementation: if object cross the window dimension -  it disappears.
     *
     * @param deltaTime deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getTopLeftCorner().y() > windowDimensions.y()) {
            gameObjectCollection.removeGameObject(this, Layer.STATIC_OBJECTS);
        }
    }
}
