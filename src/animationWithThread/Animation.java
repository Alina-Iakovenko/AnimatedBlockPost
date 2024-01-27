package animationWithThread;

import acm.graphics.GCanvas;
import com.shpp.cs.a.graphics.WindowProgram;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 5 sec long animation with 50+ frames.
 * Builds pyramid in green colors with a flag on top and show time from fullscale invasion.
 * Then labels appear and star snowing.
 */
public class Animation extends WindowProgram {
    /* General block */
    public static final int APPLICATION_WIDTH = 800;
    public static final int APPLICATION_HEIGHT = 525;
    static final long ANIMATION_DURATION = 5000;
    static long startTime;

    static GCanvas canvas;
    static Thread pyramidThread;
    static Thread labelsThread;
    static Thread snowThread;

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        canvas = getGCanvas();
        AtomicBoolean stopExecution = new AtomicBoolean(false);
        pyramidThread = new Thread(new Pyramid());
        labelsThread = new Thread(new Labels(stopExecution));
        snowThread = new Thread(new Snow(stopExecution));

        pyramidThread.setPriority(1);
        labelsThread.setPriority(2);
        snowThread.setPriority(3);

        pyramidThread.start();
        labelsThread.start();
        snowThread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        stopExecution.set(true);

        System.out.println((System.currentTimeMillis() - startTime) + " passed");
    }
}




