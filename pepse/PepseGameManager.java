import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import world.Avatar;
import world.Sky;
import world.Block;
import world.Terrain;
import world.daynight.Night;
import world.daynight.Sun;
import world.daynight.SunHalo;
import world.daynight.trees.Flora;
import world.daynight.trees.Flora;
import world.daynight.trees.Tree;

import java.awt.*;
import java.util.List;

public class PepseGameManager extends GameManager {

    public static final int SKY_LAYER = Layer.BACKGROUND;
    public static final int LEAF_LAYER = 31;
    public static final int BLOCK_SIZE = 30;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, SKY_LAYER);

        Terrain terrain = new Terrain(windowController.getWindowDimensions(), 0);
        List<Block> blockList = terrain.createInRange(0,(int)windowController.getWindowDimensions().x());
        for (Block b: blockList){
            gameObjects().addGameObject(b, Layer.STATIC_OBJECTS);
        }

        GameObject night = Night.create(windowController.getWindowDimensions(), 30);
        gameObjects().addGameObject(night, Layer.FOREGROUND);

        GameObject sun = Sun.create(windowController.getWindowDimensions(), 30);
        gameObjects().addGameObject(sun, Layer.FOREGROUND);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.FOREGROUND);

        Flora flora = new Flora(terrain::groundHeightAt);
        List<Tree> treeArray = flora.createInRange(0,(int) windowController.getWindowDimensions().x());
        gameObjects().layers().shouldLayersCollide(LEAF_LAYER,LEAF_LAYER, false);
        for (Tree tree:treeArray){
            gameObjects().addGameObject(tree.getRoot(),Layer.DEFAULT);
            for(GameObject leaf:tree.getLeafs().getLeafArray()){
                gameObjects().addGameObject(leaf,LEAF_LAYER);
            }
        }

        Avatar avatar = new Avatar(new Vector2(
                windowController.getWindowDimensions().x()/2,
                terrain.groundHeightAt(windowController.getWindowDimensions().x()/2) - Avatar.DIMENSIONS.y()),
                inputListener,
                imageReader
                );
        gameObjects().addGameObject(avatar, Layer.DEFAULT);


//        textRenderable = new TextRenderable(String.valueOf(initialHearts));
//        textRenderable.setColor(Color.GREEN);
//        GameObject gameObject = new GameObject(new Vector2(
//                MARGIN, windowController.getWindowDimensions().y()-HEART_SIZE-MARGIN
//        ), Vector2.ONES.mult(HEART_SIZE), textRenderable);
//        gameObjects.addGameObject(gameObject, Layer.FOREGROUND);
        }

    public static void main(String[] args){
        new PepseGameManager().run();
    }
}
