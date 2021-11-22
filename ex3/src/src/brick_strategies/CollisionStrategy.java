package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

/**
 * Says what to do when brick collided with ball.
 *
 */
public class CollisionStrategy {
    private final GameObjectCollection gameObjectCollection;

    /**
     * default constructor.
     * @param gameObjectCollection iterable object that present all gameObjects
     */
    public CollisionStrategy(GameObjectCollection gameObjectCollection)
    {
        this.gameObjectCollection = gameObjectCollection;
    }
    /**
     * To be called on brick collision.
     * @param thisObj should be the brick obj. for removing
     * @param otherObj without use for now.
     * @param counter - global brick counter. for decrement
     */
    public void onCollision(danogl.GameObject thisObj, GameObject otherObj, danogl.util.Counter counter){
        gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        counter.decrement();

    }
}