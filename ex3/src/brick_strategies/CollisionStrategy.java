package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class CollisionStrategy {
    private GameObjectCollection gameObjectCollection;

    public CollisionStrategy(GameObjectCollection gameObjectCollection)
    {
        this.gameObjectCollection = gameObjectCollection;


    }

    /**
     *
     * @param thisObj
     * @param otherObj
     * @param counter - global brick counter.
     */
    public void onCollision(danogl.GameObject thisObj, danogl.GameObject otherObj, danogl.util.Counter counter){
        System.out.println("collision with brick detected");
        gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        counter.decrement();

    }
}