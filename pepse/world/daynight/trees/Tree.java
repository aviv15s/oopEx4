package world.daynight.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.OvalRenderable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tree {
    private static final Color ROOT_DEFAULT_COLOR = new Color(100,50,20);
    private static final Color LEEF_COLOR = new Color(50, 200, 30);
    private static final Color FRUIT_COLOR = new Color(50,50,50);
    private static final double LEAF_MAX_FRUIT_MIN_PROP = 0.2;
    private static final double MAX_FRUIT_PROP = 0.25;
    private static final int BlOCK_TOP_SIZE = 150;
    private static final int LEAF_SIZE = 20;
    private static final int FRUIT_SIZE = 20;
    private static final float OFFSET_X = BlOCK_TOP_SIZE/2;
    private static final float OFFSET_Y = BlOCK_TOP_SIZE/2;
    private static final int NUM_LEAFS = BlOCK_TOP_SIZE / LEAF_SIZE;
    private static final Vector2 ROOT_SIZE = new Vector2(20,200);
    private float topTreeXStart;
    private float topTreeYStart;
    private Trunk trunk;
    private List<Leaf> leafArray;
    private List<Fruit> fruitArray;


    public Tree(float xCoordinate, float yCoordinate){
        topTreeXStart = xCoordinate - OFFSET_X;
        topTreeYStart = yCoordinate + OFFSET_Y -ROOT_SIZE.y();
        leafArray = new ArrayList<Leaf>();
        fruitArray = new ArrayList<Fruit>();
        generateLeafs();
        trunk = new Trunk( new Vector2(xCoordinate,yCoordinate-ROOT_SIZE.y()),
                ROOT_SIZE,
                new RectangleRenderable(ROOT_DEFAULT_COLOR));
    }

    private void generateLeafs(){
        Random random = new Random();
        Double probability;
        for (int row = 0; row < NUM_LEAFS; row++) {
            for (int col = 1; col <= NUM_LEAFS; col++) {
                probability = random.nextDouble();
                float x = topTreeXStart + LEAF_SIZE * row;
                float y = topTreeYStart - LEAF_SIZE * col;
                if (probability < LEAF_MAX_FRUIT_MIN_PROP) {
                    Leaf leaf = new Leaf(
                            new Vector2(x, y),
                            new Vector2(LEAF_SIZE, LEAF_SIZE),
                            new RectangleRenderable(LEEF_COLOR));
                    leafArray.add(leaf);
                }
                if(LEAF_MAX_FRUIT_MIN_PROP<probability&&probability<MAX_FRUIT_PROP){
                    Fruit fruit = new Fruit(
                            new Vector2(x, y),
                            new Vector2(FRUIT_SIZE, FRUIT_SIZE),
                            new OvalRenderable(FRUIT_COLOR));
                    fruitArray.add(fruit);
                }
            }
        }
    }
    public GameObject getTrunk() {
        return trunk;
    }

    public List<Leaf> getLeafArray() {
        return leafArray;
    }

    public List<Fruit> getFruitArray() {
        return fruitArray;
    }
}
