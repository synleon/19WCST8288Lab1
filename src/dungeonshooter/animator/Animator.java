package dungeonshooter.animator;

import dungeonshooter.entity.Bullet;
import dungeonshooter.entity.Entity;
import dungeonshooter.entity.Player;
import dungeonshooter.entity.PolyShape;
import dungeonshooter.entity.property.HitBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class Animator extends AbstractAnimator {

    private Color background = Color.ANTIQUEWHITE;
    /**
     * create a protected abstract method called handle, this method to be overridden by subclasses.
     *
     * @param gc  - {@link GraphicsContext} object.
     * @param now - current time in nanoseconds, represents the time that this function is called.
     */
    @Override
    public void handle(GraphicsContext gc, long now) {
        updateEntities();
        clearAndFill(gc, background);
        drawEntities(gc);
    }

    public void updateEntities() {

        map.updateProjectilesList();

        for (Entity entity : map.projectiles()) {
            entity.update();
        }

        for (Entity entity : map.players()) {
            entity.update();
        }

        for (PolyShape e : map.staticShapes()) {
            e.update();
        }

        if (map.getDrawBounds()) {
            // loop update each bullet
            map.projectiles().forEach(e -> e.getHitBox().getDrawable().setStroke(Color.RED));
            // loop update each player
            map.players().forEach(entity -> entity.getHitBox().getDrawable().setStroke(Color.RED));
        }

        // loop to check every staticShape has intersected with every payer and projectile
        map.staticShapes().forEach(e -> {
            proccessEntityList(map.projectiles().iterator(), e.getHitBox());
            proccessEntityList(map.players().iterator(), e.getHitBox());
        });
    }

    public void proccessEntityList(Iterator<Entity> iterator, HitBox shapeHitBox) {
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            HitBox bounds = entity.getHitBox();
            //TODO:
            if (!map.inMap(bounds)) {
                if (entity instanceof Player) {
                    ((Player) entity).stepBack();
                }
                else if (entity instanceof Bullet) {
                    iterator.remove();
                }
            }
            else if (shapeHitBox.intersectBounds(bounds)) {
                if (map.getDrawBounds()) {
                    bounds.getDrawable().setStroke(Color.BLUEVIOLET);
                }

                if (shapeHitBox.intersectFull(bounds)) {
                    if (entity instanceof Player) {
                        ((Player) entity).stepBack();
                    }
                    else if (entity instanceof Bullet) {
                        iterator.remove();
                    }
                }
            }
        }
    }
}
