package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Sky;
import pepse.world.Block;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Tree;

import java.util.List;
import java.util.Random;

/**
 * the Pepse game manager
 *
 * @author aviv.shemesh, ram3108_
 */
public class PepseGameManager extends GameManager {

    private static final int SKY_LAYER = Layer.BACKGROUND;
    private static final int LEAF_LAYER = -2;
    private static final int FRUIT_LAYER = -1;
    private static final int AVATAR_LAYER = 0;
    private static final int SUN_LAYER = -150;
    private static final int CYCLE_LENGTH = 30;
    private static final int TERRAIN_LAYER = Layer.STATIC_OBJECTS;
    private static final int TRUNK_LAYER = -3;
    private static final Vector2 TEXT_OBJECT_DIMENSIONS = Vector2.ONES.mult(50f);

    /**
     * initialize our method
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Terrain terrain = setBackground(windowController);
        Avatar avatar = createAvatar(imageReader, inputListener, windowController);
        textCreator(avatar);
        Flora flora = new Flora(terrain::groundHeightAt, avatar::subscribeToPlayerJumping);
        List<Tree> treeArray = flora.createInRange(0, (int) windowController.getWindowDimensions().x());
        for (Tree tree : treeArray) {
            gameObjects().addGameObject(tree.getTrunk(), TRUNK_LAYER);
            for (GameObject leaf : tree.getLeafArray()) {
                gameObjects().addGameObject(leaf, LEAF_LAYER);
            }
            for (GameObject fruit : tree.getFruitArray()) {
                gameObjects().addGameObject(fruit, FRUIT_LAYER);
            }
        }
        layersCollide();

    }

    /**
     * energy show on screen
     *
     * @param avatar to get energy from
     */
    private void textCreator(Avatar avatar) {
        TextRenderable textRenderable = new TextRenderable("");
        GameObject gameObject = new GameObject(Vector2.ZERO, TEXT_OBJECT_DIMENSIONS, textRenderable);
        gameObjects().addGameObject(gameObject, Layer.FOREGROUND);
        gameObject.addComponent(deltaTime -> textRenderable.setString((int) avatar.getEnergy() + "%"));
    }

    /**
     * create all the background
     *
     * @param windowController to get the sizes
     * @return the terrain
     */
    private Terrain setBackground(WindowController windowController) {
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, SKY_LAYER);
        Random rand = new Random();
        Terrain terrain = new Terrain(windowController.getWindowDimensions(),
                rand.nextInt());
        for (Block b : terrain.createInRange(0, (int) windowController.getWindowDimensions().x())) {
            gameObjects().addGameObject(b, TERRAIN_LAYER);
        }
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);
        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(sun, SUN_LAYER);
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, SUN_LAYER);
        return terrain;
    }

    /**
     * set all the layers that should or shouldn't collide.
     */
    private void layersCollide() {
        gameObjects().layers().shouldLayersCollide(FRUIT_LAYER, FRUIT_LAYER, false);
        gameObjects().layers().shouldLayersCollide(FRUIT_LAYER, AVATAR_LAYER, true);
        gameObjects().layers().shouldLayersCollide(TRUNK_LAYER, AVATAR_LAYER, true);
        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.STATIC_OBJECTS,
                false);
        gameObjects().layers().shouldLayersCollide(LEAF_LAYER, LEAF_LAYER, false);
        gameObjects().layers().shouldLayersCollide(FRUIT_LAYER, FRUIT_LAYER, false);
        gameObjects().layers().shouldLayersCollide(SUN_LAYER, SUN_LAYER, false);
    }

    /**
     * @param imageReader      image reader
     * @param inputListener    input listener
     * @param windowController windowController to place Avatar
     * @return the avatar created
     */
    private Avatar createAvatar(ImageReader imageReader, UserInputListener inputListener,
                                WindowController windowController) {
        Avatar avatar = new Avatar(new Vector2(
                windowController.getWindowDimensions().x() / 2,
                0),
                inputListener,
                imageReader
        );
        gameObjects().addGameObject(avatar, AVATAR_LAYER);
        return avatar;
    }

    /**
     * main function that runs
     *
     * @param args getting from terminal
     */
    public static void main(String[] args) {
        PepseGameManager pepseGameManager = new PepseGameManager();
        pepseGameManager.run();
    }
}
