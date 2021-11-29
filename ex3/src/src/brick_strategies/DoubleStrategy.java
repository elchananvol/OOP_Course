package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;
import java.util.Random;

/**
 * this strategy choose to randoms behaviors (include itself at first time only).
 * it gets those behaviors at constructor.
 */
public class DoubleStrategy extends RemoveBrickStrategyDecorator{
    private final Random generator = new Random();
    private final CollisionStrategy strategyFirst;
    private final CollisionStrategy strategySecond;

    /**
     *
     * @param toBeDecorated - Collision strategy object to be decorated.
     * @param array array of CollisionStrategy to choose from it.
     */
    DoubleStrategy(CollisionStrategy toBeDecorated,CollisionStrategy[] array){
        super(toBeDecorated);
        int randomIndexFirst = generator.nextInt(array.length+1);
        int randomIndexSecond = generator.nextInt(array.length+1);
        if (randomIndexFirst == array.length || randomIndexSecond == array.length){
            this.strategyFirst = new DoubleStrategy(toBeDecorated,array);
            randomIndexSecond = generator.nextInt(array.length);

        }
        else{
            this.strategyFirst = array[randomIndexFirst];
        }
        this.strategySecond = array[randomIndexSecond];

    }


    /**
     * implementation explanation: second strategy is not double strategy as Written in constructor,
     * so is get the counter for down the number of bricks and first get null.
     * @param thisObj should be the brick obj. for removing
     * @param otherObj without use for now.
     * @param counter - global brick counter. for decrement
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        this.strategyFirst.onCollision(thisObj,otherObj,null);
        this.strategySecond.onCollision(thisObj,otherObj,counter);

    }
}
