package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.JumpingObserver;

import java.awt.*;

/**
 * Class that create a trunk of the tree that is a jumping observer and GameObject
 *
 * @author aviv.shemesh, ram3108_
 */
public class Trunk extends GameObject implements JumpingObserver {
    private static final Color ROOT_DEFAULT_COLOR = new Color(100, 50, 20);

    /**
     * constructor to trunk and prevents intersaction.
     *
     * @param topLeftCorner of the object
     * @param dimensions    dimentison of object
     */
    public Trunk(Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions,
                new RectangleRenderable(ColorSupplier.approximateColor(ROOT_DEFAULT_COLOR)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    /**
     * function called whenever the player jumps
     */
    @Override
    public void onPlayerJump() {
        Renderable renderable = new RectangleRenderable(ColorSupplier.approximateColor(ROOT_DEFAULT_COLOR));
        renderer().setRenderable(renderable);
    }
}
