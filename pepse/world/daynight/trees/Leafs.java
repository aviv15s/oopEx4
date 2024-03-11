package world.daynight.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import danogl.gui.rendering.RectangleRenderable;
import java.util.Random;

public class Leafs {
    private static final Color LEEF_COLOR = new Color(50, 200, 30);
    private static final int BlOCK_LEAF_SIZE = 150;
    private static final int LEAF_SIZE = 15;
    private static final float OFFSET_X = BlOCK_LEAF_SIZE/2;
    private static final float OFFSET_Y = BlOCK_LEAF_SIZE/2;
    private static final int NUM_LEAFS = BlOCK_LEAF_SIZE / LEAF_SIZE;
    private float xMin;
    private float yMIn;
    private  List<GameObject> leafArray;

    public Leafs(float xMin, float yMIn) {
        this.xMin = xMin-OFFSET_X;
        this.yMIn = yMIn+OFFSET_Y;
        leafArray = new ArrayList<GameObject>();
        generateLeafs();
    }
    public void generateLeafs(){
        Random random = new Random();
        Double probability;
        for (int row = 0; row < NUM_LEAFS; row++) {
            for (int col = 0; col < NUM_LEAFS; col++) {
                probability = random.nextDouble();
                if(probability<0.2)
                    leafArray.add(new GameObject(
                            new Vector2(xMin + LEAF_SIZE * row, yMIn - LEAF_SIZE * col),
                            new Vector2(LEAF_SIZE, LEAF_SIZE),
                            new RectangleRenderable(LEEF_COLOR))
                    );
            }
        }
    }

    public List<GameObject> getLeafArray() {
        return leafArray;
    }
}
