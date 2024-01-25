package animationWithThread;

import acm.graphics.*;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Snow extends WindowProgram implements Runnable{
    static final double PAUSE_TIME_FOR_SNOW = 1 / 5; // чому ця пауза працює не атк само як 0,2 ???
    RandomGenerator rgen = RandomGenerator.getInstance();
    GCanvas canvas = Animation.canvas;

    @Override
    public void run() {
        while (Animation.snowThread.isAlive()) {
            letItSnow();
        }
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
        double x = rgen.nextDouble(1, canvas.getWidth());
        GOval snowflake = new GOval(x, 25, 2, 2);
        snowflake.setFilled(true);
        snowflake.setColor(nextSnowColor());
        canvas.add(snowflake);
        return snowflake;
    }
    /**
     * Creates animation with snowing and counting seconds
     */
    private void letItSnow() {
        GOval[] snow = new GOval[5];
        for (int i = 0; i < snow.length; i++) {
            snow[i] = makeSnowflake();
            GOval snowFlake = snow[i]; //variable for the easier reading code below

            // 1 of 5 snowflake moves only ones to random point on screen
            if (i % 5 == 0) {
                snowFlake.setLocation(rgen.nextDouble(0, canvas.getWidth()), rgen.nextDouble(25, canvas.getHeight()));
            }
            // other 4 of 5 snowflakes moves till it reaches an obstacle
            else {
                fallSnowFlakeTillObstacle(snowFlake);
            }
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
        while ((snowFlake.getX() < canvas.getWidth() && snowFlake.getX() > -10) && snowFlake.getY() < canvas.getHeight() - 10) {
            //checks if there is the dugout or flag
            GObject comp = canvas.getElementAt(snowFlake.getX(), snowFlake.getY());
            if (comp instanceof GRect) {
                break;
            } else {
                snowFlake.move(dx, dy);
            }
            pause(PAUSE_TIME_FOR_SNOW);
        }
    }
}
