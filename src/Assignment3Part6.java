package com.shpp.p2p.cs.aiakovenko.assignment3;

import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

import static java.awt.Color.blue;

/**
 * 5 sec long animation with 50+ frames
 */
public class Assignment3Part6 extends WindowProgram {
    /* General block */
    public static final int APPLICATION_WIDTH = 1000;
    public static final int APPLICATION_HEIGHT = 700;
    long start;


    /* Pyramid block */
    // The amount of time to pause between frames (48fps).
    private static final double PAUSE_TIME = 1000.0 / 50;
    static final int BRICK_HEIGHT = 40;
    static final int BRICK_WIDTH = 70;
    static final int BRICKS_IN_BASE = 9;

    /* Flag block */
    private static final int FLAG_WIDTH = BRICK_WIDTH * 2;
    private static final int FLAG_HEIGHT = BRICK_HEIGHT * 2;
    private static final int FLAGPOLE_HEIGHT = BRICK_HEIGHT * 3;
    int yFlagpole = APPLICATION_HEIGHT - BRICKS_IN_BASE * BRICK_HEIGHT - FLAGPOLE_HEIGHT;

    /* Cycle block */
    // The amount of time to pause between frames (48fps).
    private static final double DAY_PAUSE_TIME = 700.0;
    static final int DIAMETER = 50;

    RandomGenerator rgen = RandomGenerator.getInstance();

    @Override
    public void run() {
        start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 5000) {
            changeDayTime("day");
            buildPyramid();
            putTheFlag();
            changeDayNight();
        }
        System.out.println(System.currentTimeMillis() - start);

    }

    /* Flag block */
    private void putTheFlag() {
//        pause(PAUSE_TIME);
        add(putFlagpole());
        drawFlag();
    }

    private GRect putFlagpole() {
        GRect flagpole = new GRect(getWidth() / 2 - BRICK_WIDTH / 2, yFlagpole, 1, FLAG_HEIGHT);
        flagpole.setFilled(true);
        flagpole.setColor(Color.black);
        flagpole.sendToFront();
        return flagpole;
    }

    /**
     * Paints a centered flag with 3 green vertical strips with the same width
     */
    private void drawFlag() {
//        GRect flag = new GRect((getWidth()/2 - BRICK_WIDTH/2)-1, (yFlagpole - BRICK_HEIGHT*2)-1,
//                FLAG_WIDTH, FLAG_HEIGHT+2);
//        flag.setColor(Color.black);
//        add(flag);
        addColoredStrip("blue", 1);
        addColoredStrip("yellow", 2);
    }

    /**
     * Paints a horizontal strip
     *
     * @param color         determines the color of the strip
     * @param numberOfStrip the position in the flag that determines x-coordinate
     */
    private void addColoredStrip(String color, int numberOfStrip) {
        double xStrip = getWidth() / 2 - BRICK_WIDTH / 2;
        double yStrip = yFlagpole - FLAG_HEIGHT;

        switch (numberOfStrip) {
            case 1:
                break;
            case 2:
                yStrip = yFlagpole - FLAG_HEIGHT / 2;
                break;
        }

        GRect strip = new GRect(xStrip, yStrip, FLAG_WIDTH, FLAG_HEIGHT / 2);
        strip.setFilled(true);

        switch (color) {
            case "blue":
                strip.setColor(blue);
                break;
            case "yellow":
                strip.setColor(Color.yellow);
                break;
        }
        add(strip);
        strip.sendToFront();
    }

    /* Pyramid block */

    /**
     * Builds a pyramid row by row
     */
    private void buildPyramid() {
        int bricksInRow = BRICKS_IN_BASE;
        int xStarterBrick = (getWidth() - BRICK_WIDTH * BRICKS_IN_BASE) / 2;
        int yRow = getHeight() - BRICK_HEIGHT;

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
            add(moldBrick(xBrick, yRow));
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
        brick.sendToFront();
        return brick;
    }

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

    /* Cycle block */

    private void changeDayTime(String string) {
        GRect screen = new GRect(0, 0, APPLICATION_WIDTH, APPLICATION_HEIGHT);
        screen.setBounds(0, 0, APPLICATION_WIDTH, APPLICATION_HEIGHT);

        if (string.equalsIgnoreCase("day")) {
            screen.setFilled(false);
//            screen.setColor(Color.white);
        } else if (string.equalsIgnoreCase("night")) {
            screen.setFilled(true);
            Color transparentGray = new Color(55f,55f,51f,1.00f);
            screen.setColor(Color.darkGray);
        }
        add(screen);
//        screen.sendToBack();
    }

    private void movePlanet(String string) {
        int xPlanet = 0;
        int yPlanet = getHeight() - DIAMETER;

        double deltaX = 30;
        double deltaY = 30;
//        int deltaX = getWidth()/2/30;
//        int deltaY = getHeight()/30;

        GOval planet = new GOval(xPlanet, yPlanet, DIAMETER, DIAMETER);
        planet.setFilled(true);
        if (string.equalsIgnoreCase("moon")) {
            planet.setColor(Color.lightGray);
        } else if (string.equalsIgnoreCase("sun")) {
            planet.setColor(Color.yellow);
        }
        add(planet);

        pause(DAY_PAUSE_TIME / 4);
        planet.setLocation((getWidth() / 4 - DIAMETER / 2), getHeight() / 2);
        pause(DAY_PAUSE_TIME / 4);
        planet.setLocation((getWidth() / 2 - DIAMETER / 2), 0);
        pause(DAY_PAUSE_TIME / 4);
        planet.setLocation((getWidth() / 4 - DIAMETER / 2) * 3, getHeight() / 2);
        pause(DAY_PAUSE_TIME / 4);
        planet.setLocation((getWidth() - DIAMETER), getHeight() - DIAMETER);
        pause(DAY_PAUSE_TIME / 4);
        planet.setLocation(getWidth(), getHeight());


//        while (xPlanet < 0 && yPlanet < 0) {
//            xPlanet += deltaX;
//            yPlanet -= deltaY;
//            planet.move(xPlanet,yPlanet);
//            pause(DAY_PAUSE_TIME/10);
//        }
    }

    private void changeDayNight() {
        while (System.currentTimeMillis() - start < 5000) {
            changeDayTime("day");
            movePlanet("sun");
            pause(DAY_PAUSE_TIME);

            changeDayTime("night");
            movePlanet("moon");
            pause(DAY_PAUSE_TIME);
        }
    }
}

