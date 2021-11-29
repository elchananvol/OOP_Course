package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;


public class RemoveBrickStrategy implements CollisionStrategy {

    private final GameObjectCollection gameObjectCollection;

    /**
     * default constructor.
     *
     * @param gameObjectCollection iterable object that present all gameObjects
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * All collision strategy objects should hold a reference to the global
     * game object collection and be able to return it.
     *
     * @return global game object collection whose reference is held in object.
     */
    public GameObjectCollection getGameObjectCollection() {

        return gameObjectCollection;
    }


    /**
     * To be called on brick collision.
     *
     * @param thisObj  should be the brick obj. for removing
     * @param otherObj without use for now.
     * @param counter  - global brick counter. for decrement
     */
    public void onCollision(danogl.GameObject thisObj, GameObject otherObj, danogl.util.Counter counter) {
        gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        if (counter != null) {
            counter.decrement();
        }

    }
}
