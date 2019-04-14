package raycast.animator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import raycast.entity.geometry.PolyShape;
import utility.Point;

import java.util.ArrayList;
public class PointLightAnimator extends AbstractAnimator{
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


    /**
     * create a protected constructor and initialize the {@link AbstractAnimator#mouse} variable
     */
    public PointLightAnimator() {
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
        drawRays(gc, mouse.x(), mouse.y(), lightColor);
        //drawRays(gc, 110, 70, lightColor);
    }

    public void drawLine(GraphicsContext gc, Color color, double sx, double sy, double ex, double ey){
        gc.setLineWidth( 1);
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
        return "PointLightAnamator";
    }

    public void drawRays(GraphicsContext gc, double startX, double startY, Color color) {
        ArrayList<Point> arrayIntersects = new ArrayList<>();
        for (PolyShape entity : map.shapes()) {
            double endXBelow, endXAbove, endYBelow, endYAbove;
            for (int k = 0; k < entity.getPointCount(); k++) {
                double endX = entity.pX(k);
                double endY = entity.pY(k);

                double angle = Math.atan2(endY - startY, endX - startX);

                endXBelow = Math.cos(angle - 0.00001);
                endYBelow = Math.sin(angle - 0.00001);

                endXAbove = Math.cos(angle + 0.00001);
                endYAbove = Math.sin(angle + 0.00001);

                for (PolyShape shape : map.shapes()) {
                    for (int i = 0, j = shape.getPointCount() - 1; i < shape.getPointCount(); i++, j = i - 1) {
                        if (getIntersection(startX, startY, endX, endY,
                                shape.pX(i), shape.pY(i), shape.pX(j), shape.pY(j))) {
                            if (intersectPoint[2] > intersectResult[2]) {
                                System.arraycopy(intersectResult, 0, intersectPoint, 0, intersectPoint.length);
                            }
                        }
                    }
                }
                drawLine(gc, lightColor, startX, startY, intersectPoint[0], intersectPoint[1]);
                arrayIntersects.add(new Point(intersectPoint[0], intersectPoint[1]));
                intersectPoint[2] = Double.MAX_VALUE;

                for (PolyShape shape : map.shapes()) {
                    for (int i = 0, j = shape.getPointCount() - 1; i < shape.getPointCount(); i++, j = i - 1) {
                        if (getIntersection(startX, startY, startX + endXBelow, startY + endYBelow,
                                shape.pX(i), shape.pY(i), shape.pX(j), shape.pY(j))) {
                            if (intersectPoint[2] > intersectResult[2]) {
                                System.arraycopy(intersectResult, 0, intersectPoint, 0, intersectPoint.length);
                            }
                        }
                    }
                }
                drawLine(gc, lightColor, startX, startY, intersectPoint[0], intersectPoint[1]);
                arrayIntersects.add(new Point(intersectPoint[0], intersectPoint[1]));
                intersectPoint[2] = Double.MAX_VALUE;

                for (PolyShape shape : map.shapes()) {
                    for (int i = 0, j = shape.getPointCount() - 1; i < shape.getPointCount(); i++, j = i - 1) {
                        if (getIntersection(startX, startY, startX + endXAbove, startY + endYAbove,
                                shape.pX(i), shape.pY(i), shape.pX(j), shape.pY(j))) {
                            if (intersectPoint[2] > intersectResult[2]) {
                                System.arraycopy(intersectResult, 0, intersectPoint, 0, intersectPoint.length);
                            }
                        }
                    }
                }
                drawLine(gc, lightColor, startX, startY, intersectPoint[0], intersectPoint[1]);
                arrayIntersects.add(new Point(intersectPoint[0], intersectPoint[1]));
                // intersectPoint[2] = Double.MAX_VALUE;
            }
        }

        // sort the array of intersect points and draw
        arrayIntersects.sort((o1, o2) -> {
            double theta1 = Math.toDegrees(Math.atan2(o1.y() - startY, o1.x() - startX));
            double theta2 = Math.toDegrees(Math.atan2(o2.y() - startY, o2.x() - startX));
            // must use Double.compare method to get accurate result
            return Double.compare(theta1, theta2);
        });
        // draw polygon
        double[] arrayX = new double[arrayIntersects.size()];
        double[] arrayY = new double[arrayIntersects.size()];
        for (int l = 0; l < arrayIntersects.size(); l++) {
            arrayX[l] = arrayIntersects.get(l).x();
            arrayY[l] = arrayIntersects.get(l).y();
        }
//        gc.setStroke(Color.CYAN);
//        gc.strokePolygon(arrayX, arrayY, arrayIntersects.size());
        gc.setFill(lightColor);
        gc.fillPolygon(arrayX, arrayY, arrayIntersects.size());
    }
}
