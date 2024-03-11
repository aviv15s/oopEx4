package world.daynight.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import danogl.gui.rendering.RectangleRenderable;
import java.awt.*;

public class Tree {
    private static final Color ROOT_DEFAULT_COLOR = new Color(100,50,20);
    private static final Vector2 ROOT_SIZE = new Vector2(30,90);
    private GameObject root;
    private Leafs leafs;

    public Tree(float xCoordinate, float yCoordinate){
        leafs = new Leafs(xCoordinate,yCoordinate-ROOT_SIZE.y());
        root = new GameObject(
                new Vector2(xCoordinate,yCoordinate-ROOT_SIZE.y()),
                ROOT_SIZE,
                new RectangleRenderable(ROOT_DEFAULT_COLOR));
    }

    public GameObject getRoot() {
        return root;
    }

    public Leafs getLeafs() {
        return leafs;
    }
}
