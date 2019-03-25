package dungeonshooter.entity;

import dungeonshooter.entity.property.Drawable;
import dungeonshooter.entity.property.HitBox;
import dungeonshooter.entity.property.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FpsCounter implements Entity {

    public static final double ONE_SECOND = 1000000000L;
    public static final double HALF_SECOND = ONE_SECOND / 2F;
    private Font fpsFont;
    private String fpsDisplay;
    private int frameCount;
    private double lastTime;
    private double x;
    private double y;

    private Sprite sprite;

    /**
     * default constructor
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public FpsCounter(double x, double y) {
        setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BLACK, 24));
        setPos(x, y);
        // Initialize sprite object
        sprite = new Sprite() {
            @Override
            public void draw(GraphicsContext gc) {
                Font font = gc.getFont();
                gc.setFont(fpsFont);
                // gc.setFill(getFill());
                gc.fillText(fpsDisplay, x, y);
                // gc.setStroke(stroke);
                // gc.setLineWidth(strokeWidth);
                gc.strokeText(fpsDisplay, x, y);
                gc.setFont(font);
            }
        };
        sprite.setFill(Color.LIGHTGRAY).setStroke(Color.DARKGREEN).setWidth(2);
    }

    public void calculateFPS(long now) {
        if ((now - lastTime) > HALF_SECOND) {
            fpsDisplay = String.format("%s", frameCount * 2);
            frameCount = 0;
            lastTime = now;
        }
        frameCount++;
    }

    public FpsCounter setFont(Font font) {
        this.fpsFont = font;
        return this;
    }

    public FpsCounter setPos(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

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
