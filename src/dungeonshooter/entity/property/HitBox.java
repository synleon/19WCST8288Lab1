package dungeonshooter.entity.property;

import dungeonshooter.entity.Entity;
import dungeonshooter.entity.geometry.RectangleBounds;
import dungeonshooter.utility.IntersectUtil;
import dungeonshooter.utility.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;

/**
 * class representing the outer rectangular bounds of a shape
 */
public class HitBox implements Entity {
    private Point prev;
    private RectangleBounds bounds;
    private Sprite sprite;
    private double[][] points;
    private double[] result;


    public HitBox() {
        // initialize points and results array
        points = new double[2][4];
        result = new double[4];

        // initialize previous point
        prev = new Point();

        // initialize sprite
        sprite = new Sprite() {
            @Override
            public void draw(GraphicsContext gc) {
                gc.setStroke(getStroke());
                gc.setLineWidth(getWidth());
                gc.strokeRect(bounds.x(), bounds.y(), bounds.w(), bounds.h());
            }
        };
        sprite.setStroke(Color.RED).setWidth(3);
    }

    /**
     * update the entity
     */
    public void update() {

    }

    /**
     * whether this entity has a hitbox
     *
     * @return - true if has a hitbox - false if doesn't has a hitbox
     */
    public boolean hasHitbox() {
        return false;
    }

    /**
     * Get the current HitBox
     * @return
     */
    public HitBox getHitBox() {
        return this;
    }

    /**
     * get the sprite of the drawable object
     *
     * @return the sprite
     */
    public Sprite getDrawable() {
        return sprite;
    }

    /**
     * whether this entity is drawable
     *
     * @return - true if drawable - false if not
     */
    public boolean isDrawable() {
        return true;
    }

    /**
     * version 1 of setter method of bounds
     *
     * @param bounds
     */
    public void setBounds(RectangleBounds bounds) {
        this.bounds = bounds;
    }

    /**
     * another version of setter method of bounds
     *
     * @param x - x coordinate of top left
     * @param y - y coordinate of top left
     * @param w - width
     * @param h - height
     */
    public HitBox setBounds(double x, double y, double w, double h) {
        setBounds(new RectangleBounds(x, y, w, h));
        return this;
    }

    public HitBox translate(double dx, double dy) {
        prev.move(bounds.startPos());
        bounds.translate(dx, dy);
        return this;
    }

    public HitBox undoTranslate() {
        if (prev.x() != 0.0 && prev.y() != 0.0)
            bounds.move(prev);
        return this;
    }

    /**
     * checks if a hitbox is completely within current hitbox
     *
     * @param hitBox - input HitBox
     * @return true if current hitbox contains the input hitbox, false if not
     */
    public boolean containsBounds(HitBox hitBox) {
        return bounds.contains(hitBox.getBounds());
    }

    /**
     * check if a hitbox is within and or overlapping the current box.
     *
     * @param hitBox - input HitBox
     * @return true if intersect false if not
     */
    public boolean intersectBounds(HitBox hitBox) {
        return bounds.intersects(hitBox.getBounds());
    }

    /**
     * Determine whether two hitboxes intersect fully
     *
     * @param box - input HitBox
     * @return true if intersect fully false if not
     */
    public boolean intersectFull(HitBox box) {
        return intersectFull( box.getPoints());
    }

    /**
     * Determine whether input points intersect fully with current box
     * @param otherPoints
     * @return
     */
    protected boolean intersectFull(double[][] otherPoints) {
        // outer loop loop current hitbox points

        // IntersectUtil.getIntersection(result, )

        //TODO

        return false;
    }

    protected boolean hasIntersectFull() {
        // TODO
        return false;
    }

    /**
     * getter for prev
     *
     * @return
     */
    public Point getPrev() {
        return prev;
    }

    /**
     * getter method for bounds
     *
     * @return
     */
    public RectangleBounds getBounds() {
        return bounds;
    }

    /**
     * getter method for points
     *
     * @return
     */
    protected double[][] getPoints() {
        return bounds.toArray(points);
    }

    /**
     * Overridden version to toString
     * @return
     */
    @Override
    public String toString() {
        return "HitBox{" +
                "prev=" + prev +
                ", bounds=" + bounds +
                ", sprite=" + sprite +
                ", points=" + Arrays.toString(points) +
                ", result=" + Arrays.toString(result) +
                '}';
    }
}
