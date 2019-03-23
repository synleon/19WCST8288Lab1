package dungeonshooter.animator;

import dungeonshooter.entity.Entity;
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

//        for (Entity entity : map.players()) {
//            entity.update();
//        }

//        for (Entity entity : map.projectiles()) {
//            entity.update();
//        }

        for (PolyShape e : map.staticShapes()) {
            e.update();
        }

//        if (map.getDrawBounds()) {
//            // loop update each bullet
//            // loop update each player
//            for (Entity player : map.players()) {
//                player.getHitBox().getDrawable().setStroke(Color.RED);
//            }
//        }

//        for (PolyShape shape : map.staticShapes()) {
//            // proccessEntityList(map.bulltes().iterator(), shape.getHitBox());
//            proccessEntityList(map.players().iterator(), shape.getHitBox());
//        }
    }

    public void proccessEntityList(Iterator<Entity> iterator, HitBox shapeHitBox) {
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            HitBox hitbox = entity.getHitBox();
            //TODO:
        }
    }
}
