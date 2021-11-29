package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

/**
 * Abstract decorator to add functionality to the remove brick strategy, following the decorator pattern.
 * All strategy decorators should inherit from this class.
 */
public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {
    private final CollisionStrategy toBeDecorated;

    /**
     * Constructor
     *
     * @param toBeDecorated - Collision strategy object to be decorated.
     */
    RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated) {
        this.toBeDecorated = toBeDecorated;
    }


    /**
     * All collision strategy objects should hold a reference to the global
     * game object collection and be able to return it.
     *
     * @return global game object collection whose reference is held in object.
     */
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
    }

    /**
     * To be called on brick collision.
     *
     * @param thisObj  should be the brick obj. for removing
     * @param otherObj without use for now.
     * @param counter  - global brick counter. for decrement
     */
    public void onCollision(danogl.GameObject thisObj, GameObject otherObj, danogl.util.Counter counter) {
        toBeDecorated.onCollision(thisObj, otherObj, counter);

    }


}
