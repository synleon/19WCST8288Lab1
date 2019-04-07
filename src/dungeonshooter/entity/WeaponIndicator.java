package dungeonshooter.entity;

import dungeonshooter.entity.property.HitBox;
import dungeonshooter.entity.property.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WeaponIndicator implements Entity {

    /**
     * Font used to draw the Weapon Indicator
     */
    private Font weaponFont;

    /**
     * The weapon information string
     */
    private String weaponDisplay;

    /**
     * Use this reference to get the weapon information
     */
    private Player player;

    /**
     * The sprite to draw graphics
     */
    private Sprite sprite;

    /**
     * The position to display weapon information
     */
    private double x, y;

    public WeaponIndicator(double x, double y) {
        setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BLACK, 24));
        setPos(x, y);
        // Initialize sprite object
        sprite = new Sprite() {
            @Override
            public void draw(GraphicsContext gc) {
                Font font = gc.getFont();
                gc.setFont(weaponFont);
                // gc.setFill(getFill());
                gc.fillText(weaponDisplay, x, y);
                // gc.setStroke(stroke);
                // gc.setLineWidth(strokeWidth);
                gc.strokeText(weaponDisplay, x, y);
                gc.setFont(font);
            }
        };
        sprite.setFill(Color.LIGHTGRAY).setStroke(Color.DARKGREEN).setWidth(2);
    }

    /**
     * update the entity
     */
    @Override
    public void update() {
        switch (player.getWeapon()) {
            case SHOTGUN:
                weaponDisplay = "SHOTGUN";
                break;
            case KNIFE:
                weaponDisplay = "KNIFE";
                break;
            case RIFLE:
                weaponDisplay = "RIFLE";
        }
    }

    public WeaponIndicator setFont(Font font) {
        this.weaponFont = font;
        return this;
    }

    public WeaponIndicator setPos(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public WeaponIndicator setPlayer(Player player) {
        this.player = player;
        return this;
    }

    /**
     * whether this entity has a hitbox
     *
     * @return - true if has a hitbox - false if doesn't has a hitbox
     */
    @Override
    public boolean hasHitbox() {
        return false;
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
        return null;
    }
}
