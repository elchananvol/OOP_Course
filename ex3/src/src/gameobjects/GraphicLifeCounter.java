package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Display a graphic object on the game window showing a numeric count of lives left.
 */
public class GraphicLifeCounter extends danogl.GameObject {

    private final GameObjectCollection gameObjectCollection;
    private final Counter counter;
    private int numOfLives;
    private List<GameObject> widgets;

    /**
     *the constractor made "livesCounter" times wigets in row, every one of them in "widgetDimensions" size
     * and "widgetRenderable" image, and most left coordinate of left widget is in "widgetTopLeftCorner"
     * @param widgetTopLeftCorner top left corner of left most life widgets. Other widgets will be displayed
     *                           to its right, aligned in hight.
     * @param widgetDimensions dimensions of widgets to be displayed.
     * @param livesCounter  global lives counter of game.
     * @param widgetRenderable image to use for widgets.
     * @param gameObjectsCollection global game object collection managed by game manager.
     * @param numOfLives global setting of number of lives a player will have in a game.
     *                   i don't sure why we have this.
     */
    public GraphicLifeCounter(danogl.util.Vector2 widgetTopLeftCorner,
                               danogl.util.Vector2 widgetDimensions,
                               danogl.util.Counter livesCounter,
                               danogl.gui.rendering.Renderable widgetRenderable,
                               danogl.collisions.GameObjectCollection gameObjectsCollection,
                               int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.gameObjectCollection = gameObjectsCollection;
        widgets = new ArrayList<>();

        Vector2 location =widgetTopLeftCorner;
        for(int i =0;i<livesCounter.value()-1;i++){
            location =location.add(new Vector2(widgetDimensions.x(),0));
            GameObject live = new GameObject(location, widgetDimensions, widgetRenderable);
            widgets.add(live);
            gameObjectsCollection.addGameObject(live, Layer.BACKGROUND);
        }
        gameObjectsCollection.addGameObject(this, Layer.BACKGROUND);
        counter = livesCounter;
        this.numOfLives = numOfLives-1;




    }

    /**
     * Overrides update in class danogl.GameObject
     * if counter.value()<numOfLives+1 then is mean that global counter decrease because the gamer lose life,
     * so we remuve the right most widget from screen.
     */
    @Override
    public void update(float deltaTime){

        if (counter.value()<numOfLives+1)
        {
            gameObjectCollection.removeGameObject(widgets.get(numOfLives-1),Layer.BACKGROUND);
            widgets.remove(numOfLives-1);
            numOfLives--;
        }
    }
}
