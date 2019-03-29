package dungeonshooter.entity;

import dungeonshooter.entity.geometry.RectangleBounds;
import dungeonshooter.entity.property.HitBox;
import dungeonshooter.entity.property.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class representing bullet
 * @author leon
 * @since Mar-28 2019
 */
public class Bullet implements Entity{
    /**
     * static bullet filling picture
     */
    public static Image BULLET = new Image("file:assets\\bullet\\b_3.png");

    /**
     * the angle of the direction that the bullet is firing at, in degrees
     */
    private double angle;

    /**
     * Drawable object
     */
    private Sprite sprite;

    /**
     * HitBox of the bullet
     */
    private HitBox hitBox;

    /**
     * construct a bullet
     * @param angle the angle the bullet is firing at
     * @param x the start x coordinate of the bullet
     * @param y the start y coordinate of the bullet
     */
    public Bullet(double angle, double x, double y) {
        this(angle, x, y, 6, 6);
    }

    /**
     * construct a bullet
     * @param angle the angle the bullet is firing at
     * @param x the start x coordinate of the bullet
     * @param y the start y coordinate of the bullet
     * @param w width
     * @param h height
     */
    public Bullet(double angle, double x, double y, double w, double h) {
        // Initialize the hitbox
        hitBox = new HitBox();
        hitBox.setBounds(x, y, w, h);
        // Initialize the sprite for drawing
        sprite = new Sprite() {
            private RectangleBounds bounds = hitBox.getBounds();
            @Override
            public void draw(GraphicsContext gc) {
                gc.drawImage(BULLET, bounds.x(), bounds.y(), bounds.w(), bounds.h());
            }
        };
    }

    /**
     * Returns a string representation of the object.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * update the bullet, add movement to current position
     */
    @Override
    public void update() {
        double x = Math.cos(Math.toRadians(angle)) * 7;
        double y = Math.sin(Math.toRadians(angle)) * 7;
        hitBox.translate(x, y);
    }

    /**
     * whether this entity has a hitbox
     *
     * @return - true if has a hitbox - false if doesn't has a hitbox
     */
    @Override
    public boolean hasHitbox() {
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
     * get the hitbox of current entity
     *
     * @return
     */
    @Override
    public HitBox getHitBox() {
        return hitBox;
    }
}
