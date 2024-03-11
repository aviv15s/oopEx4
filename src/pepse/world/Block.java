package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * A class which represents a block in the terrain of the game
 *
 * @author aviv.shemesh, ram3108_
 */
public class Block extends GameObject {
    public static final int SIZE = 30;

    /**
     * A constructor to generate the block object
     *
     * @param topLeftCorner top left corner for the position of the block
     * @param renderable    renderable object for the image
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
