package world.daynight.trees;
import danogl.GameObject;
import danogl.util.Vector2;

import java.awt.*;
import java.util.List;
public class Leafs {
    private static final Color LEEF_COLOR = new Color(50,200,30);
    private static final int  BlOCK_LEAF_SIZE = 88;
    private static final int LEAF_SIZE = 11;
    private static final int NUM_LEAFS = BlOCK_LEAF_SIZE/LEAF_SIZE;
    private static List<GameObject> leafArray;

    public Leafs(int xMin, int yMIn){
        for (int row = 0 ; row < NUM_LEAFS; row++){
            for( int col = 0; col<NUM_LEAFS;col++){
                leafArray.add(new GameObject(
                        new Vector2(xMin,yMIn+LEAF_SIZE),
                        new Vector2(LEAF_SIZE,LEAF_SIZE),
                        )
                )
            }
        }
    }
}
