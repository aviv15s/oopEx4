import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import world.Sky;
import world.Block;
import world.Terrain;
import world.daynight.Night;
import world.daynight.Sun;
import world.daynight.SunHalo;

import java.util.List;

public class PepseGameManager extends GameManager {

    public static final int SKY_LAYER = Layer.BACKGROUND;

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
    }

    public static void main(String[] args){
        new PepseGameManager().run();
    }
}
