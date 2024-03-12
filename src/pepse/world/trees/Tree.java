package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.util.JumpingObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Class that create a tree object that creates the root, leafs and fruits.
 *
 * @author aviv.shemesh, ram3108_
 */
public class Tree {
    private static final double LEAVES_PROBABILITY = 0.5;
    private static final double FRUIT_PROBABILITY = 0.1;
    private static final int BlOCK_TOP_SIZE = 150;
    private static final int LEAF_SIZE = 20;
    private static final int FRUIT_SIZE = 20;
    private static final float OFFSET_X = BlOCK_TOP_SIZE / 2;
    private static final float OFFSET_Y = BlOCK_TOP_SIZE / 2;
    private static final int NUM_LEAFS = BlOCK_TOP_SIZE / LEAF_SIZE;
    private static final Vector2 ROOT_SIZE = new Vector2(20, 200);
    private float topTreeXStart;
    private float topTreeYStart;
    private Trunk trunk;
    private List<Leaf> leafArray;
    private List<Fruit> fruitArray;

    /**
     * @param xCoordinate
     * @param yCoordinate
     * @param jumpingObserver
     */
    public Tree(float xCoordinate, float yCoordinate, Consumer<JumpingObserver> jumpingObserver) {
        topTreeXStart = xCoordinate - OFFSET_X;
        topTreeYStart = yCoordinate + OFFSET_Y - ROOT_SIZE.y();
        leafArray = new ArrayList<Leaf>();
        fruitArray = new ArrayList<Fruit>();
        generateLeafs(jumpingObserver);
        trunk = new Trunk(new Vector2(xCoordinate, yCoordinate - ROOT_SIZE.y()),
                ROOT_SIZE);
        jumpingObserver.accept(trunk);
    }

    /**
     * set all the leafs and fruits with rand function. and use the consumer.
     *
     * @param jumpingObserver consumer to activate on the created leafs and fruits
     */
    private void generateLeafs(Consumer<JumpingObserver> jumpingObserver) {
        Random random = new Random();
        Double leafProbability, fruitProbability;
        for (int row = 0; row < NUM_LEAFS; row++) {
            for (int col = 1; col <= NUM_LEAFS; col++) {
                leafProbability = random.nextDouble();
                fruitProbability = random.nextDouble();
                if (leafProbability < LEAVES_PROBABILITY) {
                    Leaf leaf = new Leaf(
                            new Vector2(topTreeXStart + LEAF_SIZE * row, topTreeYStart - LEAF_SIZE * col),
                            new Vector2(LEAF_SIZE, LEAF_SIZE));
                    leafArray.add(leaf);
                    jumpingObserver.accept(leaf);
                }
                if (fruitProbability < FRUIT_PROBABILITY) {
                    Fruit fruit = new Fruit(
                            new Vector2(topTreeXStart + FRUIT_SIZE * row, topTreeYStart - FRUIT_SIZE * col),
                            new Vector2(FRUIT_SIZE, FRUIT_SIZE));
                    fruitArray.add(fruit);
                    jumpingObserver.accept(fruit);
                }
            }
        }
    }

    /**
     * @return Trunk object
     */
    public GameObject getTrunk() {
        return trunk;
    }

    /**
     * @return list of Leafs object
     */
    public List<Leaf> getLeafArray() {
        return leafArray;
    }

    /**
     * @return list of Fruit object
     */
    public List<Fruit> getFruitArray() {
        return fruitArray;
    }
}
