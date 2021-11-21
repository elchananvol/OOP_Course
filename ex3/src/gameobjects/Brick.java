package gameobjects;

import brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.util.Counter;

/**
 * holds a Brick object of the game.
 */
public class Brick extends GameObject {
    private Counter counter;
    private CollisionStrategy collisionStrategy;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Brick(danogl.util.Vector2 topLeftCorner,
                  danogl.util.Vector2 dimensions,
                  danogl.gui.rendering.Renderable renderable,
                  CollisionStrategy collisionStrategy,
                  Counter counter) {
        super(topLeftCorner,dimensions,renderable);
        this.collisionStrategy = collisionStrategy;
        this.counter=counter;

    }

    /**
     * Overrides onCollisionEnter in class danogl.GameObject
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other,collision);
        collisionStrategy.onCollision(this,other,counter);
    }
}
