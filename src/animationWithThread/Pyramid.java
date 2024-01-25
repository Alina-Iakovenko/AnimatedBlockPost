package animationWithThread;

import acm.graphics.GCanvas;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.lang.Runnable;



public class Pyramid extends WindowProgram implements Runnable {
    /* Pyramid block */
    static final int BRICK_HEIGHT = 35;
    static final int BRICK_WIDTH = 60;
    static final int BRICKS_IN_BASE = 9;
    static int totalBricks = (BRICKS_IN_BASE * (BRICKS_IN_BASE + 1)) / 2;

    /* Flag block */
    private static final int FLAG_WIDTH = BRICK_WIDTH * 2;
    private static final int FLAG_HEIGHT = BRICK_HEIGHT * 2;
    private static final int FLAGPOLE_HEIGHT = BRICK_HEIGHT * 3;
    int yFlagpole = Animation.APPLICATION_HEIGHT - BRICKS_IN_BASE * BRICK_HEIGHT - FLAGPOLE_HEIGHT;
    Color iceColdStare = new Color(190, 227, 252);
    Color greySky = new Color(150, 175, 196);
    RandomGenerator rgen = RandomGenerator.getInstance();
    static final double PAUSE_TIME = (double) (Animation.ANIMATION_DURATION - 1000) / totalBricks;
    GCanvas canvas = Animation.canvas;


    @Override
    public void run() {
        canvas.setBackground(iceColdStare);
        buildPyramid();
        putTheFlag();
        canvas.setBackground(greySky);
    }

    /**
     * Builds a dugout (pyramid) row by row
     */
    private void buildPyramid() {
        int bricksInRow = BRICKS_IN_BASE;
        int xStarterBrick = (canvas.getWidth() - BRICK_WIDTH * BRICKS_IN_BASE) / 2;
        int yRow = canvas.getHeight() - BRICK_HEIGHT;

        while (bricksInRow > 0) {
            buildPyramidRow(bricksInRow, xStarterBrick, yRow);
            bricksInRow--;
            xStarterBrick += BRICK_WIDTH / 2;
            yRow -= BRICK_HEIGHT;
        }
    }

    /**
     * Builds 1 row of bricks
     *
     * @param bricksInRow   number ob bricks in the row
     * @param xStarterBrick x coordinate of a left top corner of the first brick
     * @param yRow          y coordinate of the brick's top
     */
    private void buildPyramidRow(int bricksInRow, int xStarterBrick, int yRow) {
        int xBrick = xStarterBrick;

        for (int i = 0; i < bricksInRow; i++) {
            canvas.add(moldBrick(xBrick, yRow));
            xBrick += BRICK_WIDTH;
            pause(PAUSE_TIME);
        }
    }

    /**
     * Create a different colored brick with black bound
     *
     * @param x coordinate of a left top corner
     * @param y coordinate of a left top corner
     */
    private GRect moldBrick(int x, int y) {
        GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        brick.setFilled(true);
        brick.setColor(Color.black);
        brick.setFillColor(nextGreenColor());
        return brick;
    }
    /**
     * Return a random color from green palette
     */
    public Color nextGreenColor() {
        Color[] allowedColors = {new Color(41, 98, 72), //plantation
                new Color(60, 138, 103), //virydian
                new Color(81, 182, 135), //oceanGreen
                new Color(195, 206, 186), //jadeLightGreen
                new Color(240, 243, 238), //snowbelt
                new Color(36, 42, 30), //pineTree
                new Color(145, 165, 128), //huntingtonGarden
                new Color(175, 217, 194), //celadonSorbet
                new Color(68, 143, 102), //illuminatiGreen
                new Color(97, 180, 134), //crazyEyes
                new Color(31, 66, 47) //burnham
        };
        int i = rgen.nextInt(0, 10);
        return allowedColors[i];
    }

    /* Flag block */
    /**
     * Paints ukrainian flag on the top of the dugout (pyramid)
     */
    private void putTheFlag() {
        pause(PAUSE_TIME);
        canvas.add(drawFlagpole());
        drawFlag();
        pause(PAUSE_TIME);
    }
    /**
     * Paints a flagpole
     */
    private GRect drawFlagpole() {
        GRect flagpole = new GRect(canvas.getWidth() / 2 - BRICK_WIDTH / 2, yFlagpole, 1, FLAG_HEIGHT);
        flagpole.setFilled(true);
        flagpole.setColor(Color.black);
        return flagpole;
    }
    /**
     * Paints ukrainian flag
     */
    private void drawFlag() {
        GRect strip;
        double xStrip = canvas.getWidth() / 2 - BRICK_WIDTH / 2;
        double yStrip = yFlagpole - FLAG_HEIGHT / 2;

        for (int i = 0; i < 2; i++) {
            strip = new GRect(xStrip, yStrip, FLAG_WIDTH, FLAG_HEIGHT / 2);
            if (i == 0) {
                strip.setColor(Color.yellow);
            } else {
                strip.setColor(Color.blue);
            }
            strip.setFilled(true);
            canvas.add(strip);
            yStrip -= FLAG_HEIGHT / 2;
        }
    }

}
