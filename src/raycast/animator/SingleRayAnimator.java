package raycast.animator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import raycast.entity.geometry.PolyShape;

public class SingleRayAnimator extends AbstractAnimator{
    /**
     * Array to store the result (intersectResult) of intersection between rays fired from the
     * location of mouse to different shapes.
     */
    private double[] intersectPoint = new double[4];

    /**
     *
     */
    private final Color backgroundColor = Color.DARKGREY;
    private final Color lightColor = Color.AQUAMARINE;

    private double endX, endY, rayIncrementer;


    /**
     * create a protected constructor and initialize the {@link AbstractAnimator#mouse} variable
     */
    public SingleRayAnimator() {
        super();
    }

    /**
     * create a protected abstract method called handle, this method to be overridden by subclasses.
     *
     * @param gc  - {@link GraphicsContext} object.
     * @param now - current time in nanoseconds, represents the time that this function is called.
     */
    @Override
    void handle(GraphicsContext gc, long now) {
        clearAndFill(gc, backgroundColor);
        map.shapes().forEach(v -> v.draw(gc));
        double centerX = map.getCanvas().getWidth() / 2.0;
        double centerY = map.getCanvas().getHeight() / 2.0;
        //drawRays(gc, mouse.x(), mouse.y(), lightColor);
        drawRays(gc, centerX, centerY, lightColor);
    }

    public void drawLine(GraphicsContext gc, Color color, double sx, double sy, double ex, double ey){
        gc.setLineWidth( 2);
        gc.setStroke( color);
        gc.setFill( Color.RED);
        gc.strokeLine( sx, sy, ex, ey);
        if(map.getDrawIntersectPoint()){
            gc.fillOval( ex - 5, ey - 5, 10, 10);
        }
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
        return "SingleRayAnimator";
    }

    public void drawRays(GraphicsContext gc, double startX, double startY, Color color) {
            endX = mouse.x() - startX;
            endY = mouse.y() - startY;
            for (PolyShape shape : map.shapes()) {
                for (int i = 0, j = shape.getPointCount() - 1; i < shape.getPointCount(); i++, j = i - 1) {
                    if (getIntersection(startX, startY, startX + endX, startY + endY,
                            shape.pX(i), shape.pY(i), shape.pX(j), shape.pY(j))) {
                        if (intersectPoint[2] > intersectResult[2]) {
                            System.arraycopy(intersectResult, 0, intersectPoint, 0, intersectPoint.length);
                        }
                    }
                }
            }
            drawLine(gc, lightColor, startX, startY, intersectPoint[0], intersectPoint[1]);
            intersectPoint[2] = Double.MAX_VALUE;
    }
}
