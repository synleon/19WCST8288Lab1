package raycast.animator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MultiRayAnimator extends AbstractAnimator {
    /**
     * create a protected abstract method called handle, this method to be overridden by subclasses.
     *
     * @param gc  - {@link GraphicsContext} object.
     * @param now - current time in nanoseconds, represents the time that this function is called.
     */
    @Override
    void handle(GraphicsContext gc, long now) {

    }

    public void drawLine(GraphicsContext gc, Color color, double sx, double sy, double ex, double ey){
        gc.setLineWidth( 1);
        gc.setStroke( color);
        gc.setFill( Color.MAGENTA);
        gc.strokeLine( sx, sy, ex, ey);
//        if( c.getDrawIntersectPoint()){
//            gc.fillOval( ex - 5, ey - 5, 10, 10);
//        }
    }
}
