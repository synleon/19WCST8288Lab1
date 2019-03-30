package dungeonshooter.entity;

import dungeonshooter.entity.property.HitBox;
import dungeonshooter.entity.property.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import java.util.Arrays;

/**
 * class representing a shape of polygon
 *
 * @author leon
 * @since Mar-7-2019
 */
public class PolyShape implements Entity {

    /**
     * number of points
     */
    private int pointCount;

    /**
     * 2-D array to store all vertexes of the polygon
     */
    private double[][] points;

    /**
     * The min, max coordinates of x and y
     */
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

//    /**
//     * The width of stroke
//     */
//    private double strokeWidth;

//    /**
//     * Color of filling
//     */
//    private Color fill;
//
//    /**
//     * Color of stroke
//     */
//    private Color stroke;

    /**
     * Every polyshape has a hitbox
     */
    private HitBox hitBox;

//    /**
//     * The outer rectangular bound of the polygon
//     */
//    private RectangleBounds bounds;

    /**
     * Sprite for Polygon
     */
    private Sprite sprite;

    public PolyShape() {

        hitBox = new HitBox() {
            protected boolean hasIntersectFull(){
                return true;
            }
            protected double[][] getPoints(){
                return points;
            }
        };
        // initialize sprite
        sprite = new Sprite() {
            {
                setFill( new ImagePattern( new Image( "file:assets/concrete/dsc_1621.png")));
            }
            @Override
            public void draw(GraphicsContext gc) {
                // set line width
                gc.setLineWidth(getWidth());
                if (getStroke() != null) {
                    gc.setStroke(getStroke());
                    gc.strokePolygon(points[0], points[1], pointCount);
                }

                if (getFill() != null) {
                    gc.setFill(getFill());
                    gc.fillPolygon(points[0], points[1], pointCount);
                }
            }
        };
//        sprite.setFill( new ImagePattern( new Image( "file:assets/concrete/rough645.png")))
//                .setWidth(1.0f).setStroke(Color.BLACK);


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
        // bounds = new RectangleBounds(minX, minY, maxX - minX, maxY - minY);
        hitBox.setBounds(minX, minY, maxX - minX, maxY - minY);
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
            double r = Math.random() * size / 2 + size / 2 + 1;
            // vertex of polygon described in polar coordinates
            points[0][j] = r * Math.cos(Math.toRadians(thetas[j])) + centerX;
            points[1][j] = r * Math.sin(Math.toRadians(thetas[j])) + centerY;

            updateMinMax(points[0][j], points[1][j]);
        }

        // initialize rectangle bounds
        // bounds = new RectangleBounds(minX, minY, maxX - minX, maxY - minY);
        hitBox.setBounds(minX, minY, maxX - minX, maxY - minY);

        return this;
    }

//    /**
//     * getter method for bounds
//     * @return
//     */
//    public RectangleBounds getBounds() {
//        return bounds;
//    }

    /**
     * update the entity
     */
    @Override
    public void update() {

    }

    /**
     * whether this entity has a hitbox
     *
     * @return - true if has a hitbox - false if doesn't has a hitbox
     */
    @Override
    public boolean hasHitbox() {
        if (hitBox == null)
            return false;
        else
            return true;
    }

    /**
     * get the sprite of the drawable object
     *
     * @return the sprite
     */
    @Override
    public Sprite getDrawable() {
        return sprite;
    }

    /**
     * whether this entity is drawable
     *
     * @return - true if drawable - false if not
     */
    @Override
    public boolean isDrawable() {
        return true;
    }

    /**
     * get the hitbox of a polyshape
     * @return - the hitbox of a polyshape
     */
    public HitBox getHitBox() {
        return hitBox;
    }

    /**
     * getter method for points count
     * @return the number of points
     */
    public int PointCount() {
        return pointCount;
    }

    /**
     * getter method for the points array
     * @return the 2-D array of points
     */
    public double[][] Points() {
        return points;
    }
}
