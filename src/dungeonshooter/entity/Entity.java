package dungeonshooter.entity;

import dungeonshooter.entity.property.Drawable;
import dungeonshooter.entity.property.HitBox;

/**
 * Common interface of all entity, all entities in Dungeon must implements this interface
 */
public interface Entity {
    /**
     *  update the entity
     */
    void update();

    /**
     * whether this entity has a hitbox
     * @return - true if has a hitbox - false if doesn't has a hitbox
     */
    boolean hasHitbox();

    /**
     * get the sprite of the drawable object
     * @return the sprite
     */
    Drawable<?> getDrawable();

    /**
     * whether this entity is drawable
     * @return - true if drawable - false if not
     */
    boolean isDrawable();

    /**
     * get the hitbox of current entity
     * @return
     */
    HitBox getHitBox();
}
