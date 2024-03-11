package world;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import util.ColorSupplier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123,74);
    private static final int TERRAIN_DEPTH = 20;
    private final int groundHeightAtX0;
    private final Vector2 windowDimensions;

    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = 4*(int) windowDimensions.y() / 5;
        this.windowDimensions = windowDimensions;
    }

    public float groundHeightAt(float x) {
        return groundHeightAtX0;
    }

    public List<Block> createInRange(int minX, int maxX) {
        List<Block> list = new ArrayList<>();
        Renderable renderable = new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));

        minX = (int) Math.floor(minX / Block.SIZE) * Block.SIZE;
        maxX = (int) Math.ceil(maxX / Block.SIZE) * Block.SIZE;
        for (int x = minX; x < maxX; x+=Block.SIZE) {
            int blockHeight = (int) Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE;
            for (int y = blockHeight; y < windowDimensions.y(); y+=Block.SIZE) {
                Block block = new Block(Vector2.ZERO, renderable);
                block.setTopLeftCorner(new Vector2(x, y));
                list.add(block);
            }
        }


        return list;
    }

}
