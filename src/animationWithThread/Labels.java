package animationWithThread;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

public class Labels extends WindowProgram implements Runnable {
    /* Label block */
    private final Font COMIC_SANS = new Font("Comic Sans", Font.BOLD, 24);
    private final Font COMIC_SANS_PLAIN = new Font("Comic Sans", Font.PLAIN, 18);
    private final AtomicBoolean stopExecution;
    GLabel labelTime;
    Color lighterGreen = new Color(68, 143, 102);
    Color darkerGreen = new Color(31, 66, 47);
    static final double PAUSE_TIME = 10.0 / 50;
    GCanvas canvas = Animation.canvas;

    public Labels(AtomicBoolean stopExecution) {
        this.stopExecution = stopExecution;
    }

    @Override
    public void run() {
        addLabels();
    }

    /**
     * Add 2 main labels to the screen
     */
    void addLabels() {

        GLabel label1 = createLabel("Our soldiers need", darkerGreen, canvas.getWidth() - 257, 220);
        canvas.add(label1);
        GLabel label1_1 = createLabel("more support now!", darkerGreen, canvas.getWidth() - 260, 250);
        canvas.add(label1_1);

        GLabel label2 = createLabel("It`s winter again...", darkerGreen, 60, 160);
        canvas.add(label2);
        while (!stopExecution.get()) {
            labelTime = createLabelTime();
            canvas.add(labelTime);
            pause(PAUSE_TIME);
            canvas.remove(labelTime);
        }
        canvas.add(labelTime);
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

}
