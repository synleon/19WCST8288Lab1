package dungeonshooter.entity.property;

import dungeonshooter.entity.Entity;
import dungeonshooter.entity.geometry.RectangleBounds;
import dungeonshooter.utility.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * class representing the outer rectangular bounds of a shape
 */
public class HitBox{
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
    public void setBounds(double x, double y, double w, double h) {
        bounds = new RectangleBounds(x, y, w, h);
    }

    public HitBox translate(double dx, double dy) {
        prev.move(bounds.startPos());
        bounds.translate(dx, dy);
        return this;
    }

    public HitBox undoTranslate() {
        bounds.move(prev);
        return this;
    }

    /**
     * checks if a hitbox is completely within current hitbox
     *
     * @param box - input HitBox
     * @return true if current hitbox contains the input hitbox, false if not
     */
    public boolean containsBounds(HitBox box) {
        return bounds.contains(box.getBounds());
    }

    /**
     * check if a hitbox is within and or overlapping the current box.
     *
     * @param box - input HitBox
     * @return true if intersect false if not
     */
    public boolean intersectBounds(HitBox box) {
        return bounds.intersects(box.getBounds());
    }

    /**
     * Determine whether two hitboxes intersect fully
     *
     * @param box - input HitBox
     * @return true if intersect fully false if not
     */
    public boolean intersectFull(HitBox box) {
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
}
