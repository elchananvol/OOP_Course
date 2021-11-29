package src.brick_strategies;

import danogl.GameObject;
/**
 * Says what to do when brick collided with ball.
 *have method  "on collision" that define the behavior, and getGameObjectCollection method for educational reasons
 */
public interface CollisionStrategy {
    danogl.collisions.GameObjectCollection getGameObjectCollection();
    void onCollision(danogl.GameObject thisObj, GameObject otherObj, danogl.util.Counter counter);



}