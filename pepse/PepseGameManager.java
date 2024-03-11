import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import world.Avatar;
import world.Sky;
import world.Block;
import world.Terrain;
import world.daynight.Night;
import world.daynight.Sun;
import world.daynight.SunHalo;
import world.trees.Flora;
import world.trees.Tree;

import java.util.List;
import java.util.random.RandomGenerator;

public class PepseGameManager extends GameManager {

    public static final int SKY_LAYER = Layer.BACKGROUND;
    public static final int LEAF_LAYER = -3;
    public static final int BLOCK_SIZE = 30;
    public static final int FRUIT_LAYER=-2;
    public static final int AVATAR_LAYER = 0;
    public static final int SUN_LAYER = -150;
    public static final int CYCLE_LENGTH = 30;
    public static final int TERRAIN_LAYER = Layer.STATIC_OBJECTS;
    public static final int TRUNK_LAYER = -1;
    public static final Vector2 TEXT_OBJECT_DIMENSIONS = Vector2.ONES.mult(50f);

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, SKY_LAYER);

        Terrain terrain = new Terrain(windowController.getWindowDimensions(),
                RandomGenerator.getDefault().nextInt());
        for (Block b: terrain.createInRange(0,(int)windowController.getWindowDimensions().x())){
            gameObjects().addGameObject(b, TERRAIN_LAYER);
        }

        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);

        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(sun, SUN_LAYER);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, SUN_LAYER);


        Avatar avatar = new Avatar(new Vector2(
                windowController.getWindowDimensions().x()/2,
                terrain.groundHeightAt(
                        windowController.getWindowDimensions().x()/2) - Avatar.DIMENSIONS.y()),
                inputListener,
                imageReader
        );
        gameObjects().addGameObject(avatar, AVATAR_LAYER);


        TextRenderable textRenderable = new TextRenderable("");
        GameObject gameObject = new GameObject(Vector2.ZERO, TEXT_OBJECT_DIMENSIONS, textRenderable);
        gameObjects().addGameObject(gameObject, Layer.FOREGROUND);
        gameObject.addComponent(deltaTime -> textRenderable.setString((int)avatar.getEnergy()+"%"));

        Flora flora = new Flora(terrain::groundHeightAt, avatar::subscribeToPlayerJumping);
        List<Tree> treeArray = flora.createInRange(0,(int) windowController.getWindowDimensions().x());
        gameObjects().layers().shouldLayersCollide(LEAF_LAYER,LEAF_LAYER, false);
        gameObjects().layers().shouldLayersCollide(FRUIT_LAYER,FRUIT_LAYER, false);
        gameObjects().layers().shouldLayersCollide(FRUIT_LAYER,AVATAR_LAYER, true);
        for (Tree tree:treeArray){
            gameObjects().addGameObject(tree.getTrunk(), TRUNK_LAYER);
            for(GameObject leaf:tree.getLeafArray()){
                gameObjects().addGameObject(leaf,LEAF_LAYER);
            }
            for(GameObject fruit:tree.getFruitArray()){
                gameObjects().addGameObject(fruit, FRUIT_LAYER);
            }
        }

        gameObjects().layers().shouldLayersCollide(TRUNK_LAYER, AVATAR_LAYER, true);
        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.STATIC_OBJECTS, false);
        gameObjects().layers().shouldLayersCollide(LEAF_LAYER, LEAF_LAYER, false);
        gameObjects().layers().shouldLayersCollide(FRUIT_LAYER, FRUIT_LAYER, false);
        gameObjects().layers().shouldLayersCollide(SUN_LAYER, SUN_LAYER, false);


    }


    public static void main(String[] args){
        new PepseGameManager().run();
    }
}
