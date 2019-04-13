package raycast.animator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MultiRayAnimator extends AbstractAnimator {
    /**
     * Array to store the result (intersectResult) of intersection between rays fired from the
     * location of mouse to different shapes.
     */
    private double[] intersectPoint;

    /**
     * create a protected abstract method called handle, this method to be overridden by subclasses.
     *
     * @param gc  - {@link GraphicsContext} object.
     * @param now - current time in nanoseconds, represents the time that this function is called.
     */
    @Override
    void handle(GraphicsContext gc, long now) {
        clearAndFill(gc, Color.BLUEVIOLET);
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

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "MultiRayAnimator";
    }

    public void drawRays(GraphicsContext gc, double startX, double startY, Color color) {

    }
}
