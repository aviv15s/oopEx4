package world.daynight.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Fruit extends GameObject {
    private static final int WAIT_TIME = 5;
    private Renderable renderable;
    public Fruit(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.renderable = renderable;
    }
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(this.renderer()!=null){
            this.renderer().setRenderable(null);
            new ScheduledTask(this,
                    WAIT_TIME,
                    false,
                    ()->transitionsActivate(this));
        }
    }
    private void transitionsActivate(GameObject gameObject){
        gameObject.renderer().setRenderable(renderable);
    }
}
