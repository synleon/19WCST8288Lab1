package dungeonshooter.animator;

import dungeonshooter.CanvasMap;
import dungeonshooter.entity.Entity;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import dungeonshooter.entity.FpsCounter;
import dungeonshooter.utility.Point;

import java.util.function.Consumer;

/**
 * this class must extend {@link AnimationTimer}. job of this class is to hold common functionality among animators.
 *
 * @author Shahriar (Shawn) Emami
 * @version Jan 13, 2019
 */
public abstract class AbstractAnimator extends AnimationTimer {

    /**
     * create a protected class variable of type {@link CanvasMap} and name it map.
     */
    protected CanvasMap map;
    /**
     * create a protected class variable of type {@link Point} and name it mouse.
     */
    protected Point mouse;

    /**
     * FpsCounter
     */
    private FpsCounter fps;

    /**
     * create a protected constructor and initialize the {@link AbstractAnimator#mouse} variable
     */
    protected AbstractAnimator() {
        mouse = new Point();
        // intersectResult = new double[4];
        // Initialize fpsCounter
        fps = new FpsCounter(10, 20);
    }

    /**
     * create a setter called setCanvas to inject (set) the {@link CanvasMap}
     *
     * @param map - {@link CanvasMap} object
     */
    public void setCanvas(CanvasMap map) {
        this.map = map;
    }

    /**
     * <p>create a method called handle that is inherited from {@link AnimationTimer#handle(long)}.
     * this method is called by JavaFX application, it should not be called directly.</p>
     * <p>inside of this method call the abstract handle method {@link AbstractAnimator#handle(GraphicsContext, long)}.
     * {@link GraphicsContext} can be retrieved from {@link CanvasMap#gc()}</p>
     *
     * @param now - current time in nanoseconds, represents the time that this function is called.
     */
    @Override
    public void handle(long now) {
        GraphicsContext gc = map.gc();


        handle(gc, now);

        if (map.getDrawFPS()) {
            fps.calculateFPS(now);
            fps.getDrawable().draw(gc);
        }
    }

    /**
     * create a protected abstract method called handle, this method to be overridden by subclasses.
     *
     * @param gc  - {@link GraphicsContext} object.
     * @param now - current time in nanoseconds, represents the time that this function is called.
     */
    protected abstract void handle(GraphicsContext gc, long now);

    /**
     * Clear the canvas
     *
     * @param gc         - Graphics Context
     * @param background - the background color
     */
    public void clearAndFill(GraphicsContext gc, Color background) {
        gc.setFill(background);
        gc.clearRect(0, 0, map.w(), map.h());
        gc.fillRect(0, 0, map.w(), map.h());
    }

    /**
     * Draw all the antities
     * @param gc GraphicsContext object
     */
    public void drawEntities(GraphicsContext gc) {
         Consumer<Entity> draw = entity -> {
            if (entity.isDrawable()) {
                entity.getDrawable().draw(gc);
                if (map.getDrawBounds()) {
                    entity.getHitBox().getDrawable().draw(gc);
                }
            }
        };

        draw.accept(map.getMapShape());

        map.staticShapes().forEach(draw);

        map.projectiles().forEach(draw);

        map.players().forEach(draw);
    }
}