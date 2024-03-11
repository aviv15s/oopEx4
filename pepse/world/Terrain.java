package world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import util.ColorSupplier;
import util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for generating and handling the terrain of the game
 *
 * @author aviv.shemesh, ram3108_
 */
public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final float GROUND_HEIGHT_FACTOR = 0.8f;
    private static final int NOISE_AMPLITUDE_FACTOR = 7;
    private final int groundHeightAtX0;
    private final NoiseGenerator noiseGenerator;

    /**
     * Constructor for the class
     *
     * @param windowDimensions window dimensions
     * @param seed             seed for random generation
     */

    public Terrain(Vector2 windowDimensions, int seed) {
        groundHeightAtX0 = calculateGroundHeightAtX0(windowDimensions);
        noiseGenerator = new NoiseGenerator(seed, groundHeightAtX0);
    }

    /**
     * calculates the ground height at the middle of the screen
     * @param windowDimensions window dimensions
     * @return height of terrain at middle of screen.
     */
    public static int calculateGroundHeightAtX0(Vector2 windowDimensions){
        return (int) (windowDimensions.y() * GROUND_HEIGHT_FACTOR);
    }

    /**
     * Calculates the ground height at given x pos
     *
     * @param x x value
     * @return ground height *not* clamped to block size
     */
    public float groundHeightAt(float x) {
        return (float) noiseGenerator.noise(x, NOISE_AMPLITUDE_FACTOR * Block.SIZE) + groundHeightAtX0;
    }

    /**
     * Generates list of blocks in a given coordinate range
     *
     * @param minX minimum X value (will be rounded down to multiple of block size)
     * @param maxX maximum X value (will be rounded up to multiple of block size)
     * @return list of all block gameObjects
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> list = new ArrayList<>();

        minX = (int) (Math.floor((float) minX / Block.SIZE) * Block.SIZE);
        maxX = (int) (Math.ceil((float) maxX / Block.SIZE) * Block.SIZE);
        for (int x = minX; x < maxX; x += Block.SIZE) {
            int blockHeight = (int) (Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE);
            for (int i = 0; i < TERRAIN_DEPTH; i++) {
                Renderable renderable =
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                Block block = new Block(new Vector2(x, blockHeight+i*Block.SIZE), renderable);
                list.add(block);
            }
        }
        return list;
    }

}
