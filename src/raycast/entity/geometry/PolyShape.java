package raycast.entity.geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import raycast.entity.DrawableObject;

import java.util.Arrays;
import java.util.Random;

/**
 * class representing a shape of polygon
 *
 * @author leon
 * @since Mar-7-2019
 */
public class PolyShape implements DrawableObject<PolyShape> {

    /**
     * number of points
     */
    private int pointCount;

    /**
     * 2-D array to store all vertexes of the polygon
     */
    private double[][] points;

    /**
     *
     */
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    /**
     * The width of stroke
     */
    private double strokeWidth;

    /**
     * Color of filling
     */
    private Color fill;

    /**
     * Color of stroke
     */
    private Color stroke;

    /**
     * The outer rectangular bound of the polygon
     */
    private RectangleBounds bounds;

    public PolyShape() {
        // set stroke width to 1
        setWidth(1.0f);

        // set fill to null(no fill)
        setFill(null);

        // set stroke to black
        setStroke(Color.BLACK);
    }

    @Override
    public PolyShape setFill(Color color) {
        this.fill = color;
        return this;
    }

    @Override
    public PolyShape setStroke(Color color) {
        this.stroke = color;
        return this;
    }

    @Override
    public PolyShape setWidth(double width) {
        this.strokeWidth = width;
        return this;
    }

    @Override
    public Color getFill() {
        return fill;
    }

    @Override
    public Color getStroke() {
        return stroke;
    }

    @Override
    public double getWidth() {
        return strokeWidth;
    }

    /**
     * draw the shape
     *
     * @param gc - {@link GraphicsContext} object
     */
    @Override
    public void draw(GraphicsContext gc) {
        // set line width
        gc.setLineWidth(strokeWidth);

        if (stroke != null) {
            gc.setStroke(stroke);
            gc.strokePolygon(points[0], points[1], pointCount);
        }

        if (fill != null) {
            gc.setFill(fill);
            gc.fillPolygon(points[0], points[1], pointCount);
        }
    }

    /**
     * draw little circles on the corners of the shape plus a little number
     *
     * @param gc - {@link GraphicsContext} object
     */
    public void drawCorners(GraphicsContext gc) {
        for (int i = 0; i < pointCount; i++) {
            Paint fill = gc.getFill();
            gc.setFill(Color.BLACK);
            gc.fillText(Integer.toString(i), points[0][i] - 5, points[1][i] - 5);
            gc.fillOval(points[0][i] - 5, points[1][i] - 5, 10, 10);
            gc.setStroke(fill);

        }
    }

    /**
     * get the x coordinates of the point with the index
     *
     * @param index - the index of the point in the points array
     * @return - x coordinate
     */
    public double pX(int index) {
        return points[0][index];
    }

    /**
     * get the x coordinates of the point with the index
     *
     * @param index - the index of the point in the points array
     * @return - y coordinate
     */
    public double pY(int index) {
        return points[1][index];
    }

    /**
     * set the points using input vararg list
     *
     * @param nums - an array of double coordinates, expecting even number
     * @return - PolyShape object
     */
    public PolyShape setPoints(double... nums) {
        // set the initial boundary with the coordinates of the first input point
        minX = maxX = nums[0];
        minY = maxY = nums[1];

        pointCount = nums.length / 2;

        points = new double[2][pointCount];

        for (int i = 0; i < nums.length; i += 2) {
            updateMinMax(nums[i], nums[i + 1]);
            points[0][i / 2] = nums[i];
            points[1][i / 2] = nums[i + 1];
        }

        // initialize rectangle bounds
        bounds = new RectangleBounds(minX, minY, maxX - minX, maxY - minY);
        return this;
    }

    /**
     * check whether input (x,y) less than minX, minY or greater than maxX maxY, update minX minY maxX maxY accordingly
     *
     * @param x - x coordinates of a point
     * @param y - y coordinates of a point
     */
    private void updateMinMax(double x, double y) {
        if (x < minX) minX = x;
        if (x > maxX) maxX = x;
        if (y < minY) minY = y;
        if (y > maxY) maxY = y;
    }

    /**
     * Randomly generate a polygon
     * @param centerX - the x coordinate of the center of the polygon
     * @param centerY - the y coordinate of the center of the polygon
     * @param size
     * @param minPoints - the minimum number of the points of the polygon
     * @param maxPoints - the maximum number of the points of the polygon
     * @return
     */
    public PolyShape randomize(double centerX, double centerY, double size, int minPoints, int maxPoints) {
        pointCount = minPoints + (int)(Math.random() * (maxPoints - minPoints) + 1);
        points = new double[2][pointCount];

        // randomly generates angles
        double thetas[] = new double[pointCount];
        for (int i = 0; i < pointCount; ++i) {
            thetas[i] = Math.random() * 360;
        }

        // sort angles ascending
        Arrays.sort(thetas);

        minX = minY = Double.POSITIVE_INFINITY;
        maxX = maxY = Double.NEGATIVE_INFINITY;

        for (int j = 0; j < pointCount; ++j) {
            // generate R randomly
            double r = Math.random() * size + 1;
            // vertex of polygon described in polar coordinates
            points[0][j] = r * Math.cos(Math.toRadians(thetas[j])) + centerX;
            points[1][j] = r * Math.sin(Math.toRadians(thetas[j])) + centerY;

            updateMinMax(points[0][j], points[1][j]);
        }

        // initialize rectangle bounds
        bounds = new RectangleBounds(minX, minY, maxX - minX, maxY - minY);

        return this;
    }

    /**
     * getter method for bounds
     * @return
     */
    public RectangleBounds getBounds() {
        return bounds;
    }
}
