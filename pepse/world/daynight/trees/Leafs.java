package world.daynight.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import java.awt.*;
import java.util.List;
import danogl.gui.rendering.RectangleRenderable;
import java.util.Random;

public class Leafs {
    private static final Color LEEF_COLOR = new Color(50, 200, 30);
    private static final int BlOCK_LEAF_SIZE = 88;
    private static final int LEAF_SIZE = 11;
    private static final int NUM_LEAFS = BlOCK_LEAF_SIZE / LEAF_SIZE;
    private static int xMin;
    private static int yMIn;
    private static List<GameObject> leafArray;

    public Leafs(int xMin, int yMIn) {
        this.xMin = xMin;
        this.yMIn = yMIn;
        generateLeafs();
    }
    public void generateLeafs(){
        Random random = new Random();
        Double probability;
        for (int row = 0; row < NUM_LEAFS; row++) {
            for (int col = 1; col <= NUM_LEAFS; col++) {
                probability = random.nextDouble();
                if(probability<0.5)
                    leafArray.add(new GameObject(
                            new Vector2(xMin + LEAF_SIZE * row, yMIn + LEAF_SIZE * col),
                            new Vector2(LEAF_SIZE, LEAF_SIZE),
                            new RectangleRenderable(LEEF_COLOR))
                    );
            }
        }
    }

    public static List<GameObject> getLeafArray() {
        return leafArray;
    }
}
