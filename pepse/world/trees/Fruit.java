package world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import util.JumpingObserver;
import world.Avatar;

import java.awt.*;
import java.util.Random;

public class Fruit extends GameObject implements JumpingObserver {
    public static final String FRUIT_TAG = "fruit";
    private static final int WAIT_TIME = 5;
    public static final float FRUIT_ENERGY_BONUS = 10f;
    private static final Color[] POSSIBLE_COLORS = new Color[]{
            Color.red, Color.yellow, Color.pink, Color.green
    };

    private Renderable renderable;
    public Fruit(Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions,
                new OvalRenderable(POSSIBLE_COLORS[new Random().nextInt(POSSIBLE_COLORS.length)]));
        this.setTag(FRUIT_TAG);
    }
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals(Avatar.AVATAR_TAG) &&this.renderer()!=null){
            this.renderer().setRenderable(null);
            new ScheduledTask(this,
                    WAIT_TIME,
                    false,
                    ()->transitionsActivate(this));

            Avatar avatar = (Avatar) other;
            avatar.addEnergy(FRUIT_ENERGY_BONUS);
        }
    }
    private void transitionsActivate(GameObject gameObject){
        gameObject.renderer().setRenderable(renderable);
    }

    @Override
    public void onPlayerJump() {
        renderer().setRenderable(
                new OvalRenderable(POSSIBLE_COLORS[new Random().nextInt(POSSIBLE_COLORS.length)]));
    }

}
