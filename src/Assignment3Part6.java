import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 5 sec long animation with 50+ frames.
 * Builds pyramid in green colors with a flag on top and show time from fullscale invasion.
 * Then labels appear and star snowing.
 */
public class Assignment3Part6 extends WindowProgram {
    /* General block */
    public static final int APPLICATION_WIDTH = 800;
    public static final int APPLICATION_HEIGHT = 525;
    private static final double PAUSE_TIME = 1000.0 / 50;
    private static final double PAUSE_TIME_FOR_SNOW = 10 / 50;
    RandomGenerator rgen = RandomGenerator.getInstance();


    /* Pyramid block */
    static final int BRICK_HEIGHT = 35;
    static final int BRICK_WIDTH = 60;
    static final int BRICKS_IN_BASE = 9;
    int totalBricks = (BRICKS_IN_BASE * (BRICKS_IN_BASE + 1)) / 2;

    /* Flag block */
    private static final int FLAG_WIDTH = BRICK_WIDTH * 2;
    private static final int FLAG_HEIGHT = BRICK_HEIGHT * 2;
    private static final int FLAGPOLE_HEIGHT = BRICK_HEIGHT * 3;
    int yFlagpole = APPLICATION_HEIGHT - BRICKS_IN_BASE * BRICK_HEIGHT - FLAGPOLE_HEIGHT;

    /* Label block */
    private final Font COMIC_SANS = new Font("Comic Sans", Font.BOLD, 24);
    private final Font COMIC_SANS_PLAIN = new Font("Comic Sans", Font.PLAIN, 18);
    GLabel labelTime;

    /* Color block */ Color lighterGreen = new Color(68, 143, 102);
    Color darkerGreen = new Color(31, 66, 47);
    Color iceColdStare = new Color(190, 227, 252);
    Color greySky = new Color(150, 175, 196);

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        setBackground(iceColdStare);
        buildPyramid();
        putTheFlag();
        setBackground(greySky);
        addLabels();
        while (System.currentTimeMillis() - start < 5000) {
            letItSnow();
        }
        add(createLabelTime());
        System.out.println(System.currentTimeMillis() - start); // print info to check duration
    }

    /* Pyramid block */
    /**
     * Builds a dugout (pyramid) row by row
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
            labelTime = createLabelTime();
            add(labelTime);
            pause(PAUSE_TIME);
            remove(labelTime);
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
        add(drawFlagpole());
        drawFlag();
        pause(PAUSE_TIME);
    }
    /**
     * Paints a flagpole
     */
    private GRect drawFlagpole() {
        GRect flagpole = new GRect(getWidth() / 2 - BRICK_WIDTH / 2, yFlagpole, 1, FLAG_HEIGHT);
        flagpole.setFilled(true);
        flagpole.setColor(Color.black);
        return flagpole;
    }
    /**
     * Paints ukrainian flag
     */
    private void drawFlag() {
        GRect strip;
        double xStrip = getWidth() / 2 - BRICK_WIDTH / 2;
        double yStrip = yFlagpole - FLAG_HEIGHT / 2;

        for (int i = 0; i < 2; i++) {
            strip = new GRect(xStrip, yStrip, FLAG_WIDTH, FLAG_HEIGHT / 2);
            if (i == 0) {
                strip.setColor(Color.yellow);
            } else {
                strip.setColor(Color.blue);
            }
            strip.setFilled(true);
            add(strip);
            yStrip -= FLAG_HEIGHT / 2;
        }
    }


    /* Label block */
    /**
     * Add 2 main labels to the screen
     */
    void addLabels() {
        GLabel label1 = createLabel("Our soldiers need", darkerGreen, getWidth() - 257, 220);
        add(label1);
        GLabel label1_1 = createLabel("more support now!", darkerGreen, getWidth() - 260, 250);
        add(label1_1);

        GLabel label2 = createLabel("It`s winter again...", darkerGreen, 60, 160);
        add(label2);
    }
    /**
     * Creates a label
     */
    private GLabel createLabel(String string, Color color, int x, int y) {
        GLabel label = new GLabel(string, x, y);
        label.setFont(COMIC_SANS);
        label.setColor(color);
        return label;
    }

    /**
     * Creates a label with time from fullscale invasion started
     */
    private GLabel createLabelTime() {
        GLabel label = createLabel(getTimeFromFullInvasion(), lighterGreen, 10, 30);
        label.setFont(COMIC_SANS_PLAIN);
        return label;
    }

    /**
     * Calculates time from fullscale invasion started 3:40 24.02.2022)
     */
    private String getTimeFromFullInvasion() {
        LocalDateTime startDate = LocalDateTime.of(2022, 2, 24, 3, 40);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(startDate, now);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        String result = String.format("%d days %02d hours %02d minutes %02d seconds " + "passed from fullscale invasion", days, hours, minutes, seconds);
        return result;
    }


    /* Snow block */
    /**
     * Return a random color from snow palette
     */
    public Color nextSnowColor() {
        Color[] allowedColors = {new Color(239, 250, 255), //alice blue
                new Color(226, 245, 254), //Wizard White
                new Color(195, 234, 252), //Blue Hijab
                new Color(188, 231, 251), //Arctic Paradise
                new Color(158, 220, 249), //Ganon Blue
                new Color(184, 207, 213), //Starlight Blue
                new Color(190, 211, 217), //Wind Speed
                new Color(139, 176, 187), //Kitchen Blue
        };
        int i = rgen.nextInt(0, 7);
        return allowedColors[i];
    }
    /**
     * Creates a snowflake in random color from snow palette,
     * that appears in point at the top of the screen with random x-coordinate
     */
    private GOval makeSnowflake() {
        double x = rgen.nextDouble(rgen.nextInt(1, getWidth()));
        GOval snowflake = new GOval(x, 25, 2, 2);
        snowflake.setFilled(true);
        snowflake.setColor(nextSnowColor());
        add(snowflake);
        return snowflake;
    }
    /**
     * Creates animation with snowing and counting seconds
     */
    private void letItSnow() {
        GOval[] snow = new GOval[5];
        for (int i = 0; i < snow.length; i++) {
            labelTime = createLabelTime();
            add(labelTime);
            snow[i] = makeSnowflake();
            GOval snowFlake = snow[i]; //variable for the easier reading code below

            // 1 of 5 snowflake moves only ones to random point on screen
            if (i % 5 == 0) {
                snowFlake.setLocation(rgen.nextDouble(0, getWidth()), rgen.nextDouble(25, getHeight()));
            }
            // other 4 of 5 snowflakes moves till it reaches an obstacle
            else {
                fallSnowFlakeTillObstacle(snowFlake);
            }
            remove(labelTime); // for changing seconds
        }
    }

    /**
     * Moves snowflake with different speed on different trajectories
     * till it reaches the flag, the ground or the dugout
     */
    private void fallSnowFlakeTillObstacle(GOval snowFlake) {
        double dx = rgen.nextDouble(-5, 5); //for different trajectores
        double dy = rgen.nextDouble(1, 10); //for different speed
        // sets limits for moving in screen bounds
        while ((snowFlake.getX() < getWidth() && snowFlake.getX() > -10) && snowFlake.getY() < getHeight() - 10) {
            //checks if there is the dugout or flag
            GObject comp = getElementAt(snowFlake.getX(), snowFlake.getY());
            if (comp instanceof GRect) {
                break;
            } else {
                snowFlake.move(dx, dy);
            }
            pause(PAUSE_TIME_FOR_SNOW);
        }
    }
}





