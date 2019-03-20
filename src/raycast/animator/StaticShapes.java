package raycast.animator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import raycast.entity.geometry.PolyShape;


/**
 * class that holds several static shape
 */
public class StaticShapes extends AbstractAnimator {

    public StaticShapes() {
        super();
    }

    public StaticShapes(int number) {
        super(number);
    }

    private final Color BACKGROUND = Color.DARKGREY;
    @Override
    void handle(GraphicsContext gc, long now) {
        clearAndFill(gc, BACKGROUND);
        for (PolyShape shape : map.shapes()) {
            shape.draw(gc);
        }
    }

    @Override
    public String toString() {
        return "StaticShapes";
    }
}
