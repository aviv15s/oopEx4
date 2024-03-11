package world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import util.ColorSupplier;
import util.JumpingObserver;

import java.awt.*;

public class Trunk extends GameObject implements JumpingObserver {
    private static final Color ROOT_DEFAULT_COLOR = new Color(100,50,20);

    public Trunk(Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions,
                new RectangleRenderable(ColorSupplier.approximateColor(ROOT_DEFAULT_COLOR)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    @Override
    public void onPlayerJump() {
        Renderable renderable = new RectangleRenderable(ColorSupplier.approximateColor(ROOT_DEFAULT_COLOR));
        renderer().setRenderable(renderable);
    }
}
