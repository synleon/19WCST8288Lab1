package dungeonshooter.entity;

import dungeonshooter.CanvasMap;
import dungeonshooter.entity.property.HitBox;
import dungeonshooter.entity.property.Sprite;
import dungeonshooter.utility.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import javafx.geometry.Point2D;

import java.util.Arrays;


/**
 * class represents one player
 */
public class Player implements Entity {
    public enum WEAPON {KNIFE, RIFLE, SHOTGUN};

    /**
     * Weapon type
     */
    private WEAPON weapon;

    /**
     * Rotation used to rotate the player icon
     */
    private Rotate rotationPlayer;

    /**
     * the direction the player point at
     */
    private double angle;

    /**
     *
     */
    private double playerFrame = 0;

    /**
     *
     */
    private double muzzleFrame = 0;

    /**
     * the position of the player
     */
    private Point pos;

    /**
     *
     */
    private Point dimension;

    private Point prev;

    /**
     * Drawable object
     */
    private Sprite sprite;

    /**
     * HitBox of the player
     */
    private HitBox hitBox;

    /**
     *
     */
    private PlayerInput input;

    /**
     * Reference to CanvasMap for spawning bullets
     */
    private CanvasMap map;

    public Player(double x, double y, double w, double h) {
        this(x, y, w, h, WEAPON.RIFLE);
    }

    /**
     * Constructor
     *
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public Player(double x, double y, double w, double h, WEAPON weapon) {

        // create a rotation player with default constructor
        rotationPlayer = new Rotate();

        pos = new Point(x - w / 2, y - 5 / 2);

        prev = new Point(pos);

        dimension = new Point(w, h);

        this.weapon = weapon;

        sprite = new Sprite() {
            //player and muzzle each have 20 and 16 set of images than can be loaded
            private final Image[] PLAYER = new Image[20];
            private final Image[] MUZZLE = new Image[16];

            {
                //load the images
                for (int i = 0; i < PLAYER.length; i++) {
                    switch (weapon) {
                        case RIFLE:
                            PLAYER[i] = new Image("file:assets\\rifle\\idle\\survivor-idle_rifle_" + i + ".png");
                            break;
                        case KNIFE:
                            PLAYER[i] = new Image("file:assets\\knife\\idle\\survivor-idle_knife_" + i + ".png");
                            break;
                        case SHOTGUN:
                            PLAYER[i] = new Image("file:assets\\shotgun\\idle\\survivor-idle_shotgun_" + i + ".png");
                            break;
                        default:
                            break;
                    }
                }
                for (int i = 0; i < MUZZLE.length; i++) {
                    MUZZLE[i] = new Image("file:assets\\muzzle_flashs\\m_" + i + ".png");
                }
            }

            @Override
            public void draw(GraphicsContext gc) {
                gc.save();
                //rotate gc for drawing
                calculateAngles();
                gc.setTransform(rotationPlayer.getMxx(), rotationPlayer.getMyx(),
                        rotationPlayer.getMxy(), rotationPlayer.getMyy(),
                        rotationPlayer.getTx(), rotationPlayer.getTy());
                //if left click display fire animation
                if (input.leftClicked()||input.isSpace()) {
                    gc.drawImage(MUZZLE[(int) muzzleFrame],
                            getRifleMuzzleX() - 8, getRifleMuzzleY() - 25,
                            50, 50); //this number is how fast the next frame of fire animation will be drawn. The higher the faster.
                    muzzleFrame += .5;

                    // calculate the starting point of bullet using rotation
                    Point2D point = rotationPlayer.transform(getRifleMuzzleX(), getRifleMuzzleY());
                    Bullet bullet = new Bullet(angle, point.getX(), point.getY());
                    map.fireBullet(bullet);
                }
                //draw player image
                gc.drawImage(PLAYER[(int) playerFrame], pos.x(), pos.y(), dimension.x(), dimension.y());
                gc.restore(); // this number is how fast the next frame of player animation will be drawn. The higher the faster.
                playerFrame += 0.25;
                //reset frame counts if reach the max frame
                if (playerFrame >= PLAYER.length) {
                    playerFrame = 0;
                }
                if (muzzleFrame >= MUZZLE.length || !input.leftClicked()) {
                    muzzleFrame = 0;
                }
            }
        };

        double size = h * .74;

        hitBox = new HitBox().setBounds(pos.x() + dimension.x() * 0.33 - size / 2,
                pos.y() + dimension.y() * .58 - size / 2,
                size, size);
    }

    /**
     * setter method for map
     *
     * @param map
     * @return
     */
    public Player setMap(CanvasMap map) {
        this.map = map;
        return this;
    }

    /**
     * setter for player input
     *
     * @param input PlayerInput object
     */
    public void setInput(PlayerInput input) {
        this.input = input;
    }

    /**
     * calculate the center x of player
     *
     * @return
     */
    public double getPlayerCenterX() {
        return pos.x() + dimension.x() * .303;
    }

    /**
     * calculate the center y of player
     *
     * @return
     */
    public double getPlayerCenterY() {
        return pos.y() + dimension.y() * .58;
    }

    /**
     * calculate the center x of muzzle
     *
     * @return
     */
    public double getRifleMuzzleX() {
        return pos.x() + dimension.x() * .93;
    }

    /**
     * calculate the center y of muzzle
     *
     * @return
     */
    public double getRifleMuzzleY() {
        return pos.y() + dimension.y() * .73;
    }

    /**
     * getter for weapon type
     * @return
     */
    public WEAPON getWeapon() {
        return weapon;
    }

    /**
     * find which direction the bullet should fly if any and what directly the player image should be rotated to
     */
    public void calculateAngles() {
        this.angle = Math.toDegrees(Math.atan2(input.y() - getPlayerCenterY(), input.x() - getPlayerCenterX()));
        rotationPlayer.setAngle(angle);
        rotationPlayer.setPivotX(getPlayerCenterX());
        rotationPlayer.setPivotY(getPlayerCenterY());
    }

    /**
     * Handle the movement when player hits a wall
     */
    public void stepBack() {
        hitBox.undoTranslate();
        // undo last move
        pos.move(prev);
    }

    /**
     * ddddddddddddddddddd the entity
     */
    @Override
    public void update() {
        if (input.hasMoved()) {
            // save previous position
            prev.set(pos.x(), pos.y());
            double dx = input.leftOrRight() * 5;
            double dy = input.upOrDown() * 5;
            // update position
            pos.move(pos.x() + dx, pos.y() + dy);
            // update hitbox
            hitBox.translate(dx, dy);
        }
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
