package dungeonshooter;

import dungeonshooter.animator.AbstractAnimator;
import dungeonshooter.animator.Animator;
import dungeonshooter.entity.Bullet;
import dungeonshooter.entity.Entity;
import dungeonshooter.entity.PolyShape;
import dungeonshooter.entity.property.HitBox;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents the drawing area. it is backed by {@link Canvas} class.
 * this class itself does not handle any of the drawing. this task is accomplished
 * by the {@link AnimationTimer}.
 *
 * @author Shahriar (Shawn) Emami
 * @version Jan 13, 2019
 */
public class CanvasMap {

    /**
     * <p>
     * create a {@link Canvas} object call board. it provides the tools to draw in JavaFX. this is also a {@link Node}
     * which means can be added to our JavaFX application. the object needed to draw on a {@link Canvas}
     * is {@link GraphicsContext} which is retrieved using {@link Canvas#getGraphicsContext2D()} method.
     * </p>
     */
    private Canvas map;

    /**
     * create a {@link AbstractAnimator} called animator. {@link AnimationTimer} provides
     * most common functionally needed to draw animations of ray casting.
     */
    private Animator animator;

    /**
     * background and general shape map, it can be simply a square you can make it more complicated.
     */
    private PolyShape border;
    /**
     * Indicator for whether to draw bounds
     */
    private BooleanProperty drawBounds;

    /**
     * Indicator for whether to draw FPSCounter
     */
    private BooleanProperty drawFPS;

    /**
     * List of players
     */
    private List<Entity> players;

    /**
     * List of projectiles
     */
    private List<Entity> projectiles;

    /**
     * list of buffers
     */
    private List<Entity> buffer;

    /**
     * list of shapes
     */
    private List<PolyShape> staticShapes;

    public boolean getDrawBounds() {
        return drawBounds.get();
    }

    public BooleanProperty drawBoundsProperty() {
        return drawBounds;
    }

    public boolean getDrawFPS() {
        return drawFPS.get();
    }

    public BooleanProperty drawFPSProperty() {
        return drawFPS;
    }

    /**
     * create a constructor and initialize all class variables.
     */
    public CanvasMap() {
        // two switch controlling whether HitBox and FPS should be drew on canvas
        drawBounds = new SimpleBooleanProperty();
        drawFPS = new SimpleBooleanProperty();

        // initialize only 1 player
        players = new ArrayList<>(1);

        // initialize capacity for 50 static shapes
        staticShapes = new ArrayList<>(50);

        // Initialize 500 projectiles and buffers
        projectiles = new ArrayList<>(500);
        buffer = new ArrayList<>(500);

        //Initialize border
        border = new PolyShape().setPoints(0, 0, 0, 0, 0, 0, 0, 0);
        border.getDrawable().setFill(new ImagePattern(
                new Image( "file:assets/floor/pavin.png"), 0, 0, 256, 256, false));

        setDrawingCanvas(new Canvas());


    }

    /**
     * setter method for canvas
     * @param map canvas
     * @return this
     */
    public CanvasMap setDrawingCanvas(Canvas map) {
        if (map == null)
            throw new NullPointerException();
        else {
            this.map = map;
            map.heightProperty().addListener(event -> {
                double height = map.getHeight();
                double width = map.getWidth();
               border.setPoints(0, 0, width, 0, width, height, 0, height);
            });
            map.widthProperty().addListener(event -> {
                double height = map.getHeight();
                double width = map.getWidth();
                border.setPoints(0, 0, width, 0, width, height, 0, height);
            });
        }
        return this;
    }

    /**
     * create a method called setAnimator.
     * set an {@link Animator}. if an animator exists {@link CanvasMap#stop()} it.
     * then set the new animator.
     *
     * @param newAnimator - new {@link AbstractAnimator} object
     * @return the current instance of this object
     */
    public CanvasMap setAnimator(Animator newAnimator) {
        if (animator != null) {
            stop();
        }
        this.animator = newAnimator;
        start();
        return this;
    }
    /**
     * create a method called start.
     * start the animator. {@link AnimationTimer#start()}
     */
    public void start() {
        animator.start();
    }

    /**
     * create a method called stop.
     * stop the animator. {@link AnimationTimer#stop()}
     */
    public void stop() {
        animator.stop();
    }

    /**
     * create a method called getCanvas.
     * get the JavaFX {@link Canvas} node
     *
     * @return {@link Canvas} node
     */
    public Canvas getCanvas() {
        return map;
    }

    /**
     * create a method called gc.
     * get the {@link GraphicsContext} of {@link Canvas} that allows direct drawing.
     *
     * @return {@link GraphicsContext} of {@link Canvas}
     */
    public GraphicsContext gc() {
        return map.getGraphicsContext2D();
    }

    /**
     * create a method called h.
     * get the height of the map, {@link Canvas#getHeight()}
     *
     * @return height of canvas
     */
    public double h() {
        return map.getHeight();
    }

    /**
     * create a method called w.
     * get the width of the map, {@link Canvas#getWidth()}
     *
     * @return width of canvas
     */
    public double w() {
        return map.getWidth();
    }

    /**
     * getter method for shapes
     * @return
     */
    public List<PolyShape> shapes() {
        return staticShapes;
    }

    /**
     * Create a bunch of sample shapes
     */
    public CanvasMap addSampleShapes() {
        staticShapes.add(new PolyShape().setPoints(50, 120, 110, 50, 260, 80, 160, 250));
                //.setWidth(5).setStroke(Color.DARKRED).setFill(Color.LIGHTCORAL));

        staticShapes.add(new PolyShape().randomize(540, 480, 130, 4, 6));
                //.setWidth(5).setStroke(Color.DARKRED).setFill(Color.LIGHTCORAL));

        staticShapes.add(new PolyShape().randomize(140, 480, 130, 4, 6));
                //.setWidth(5).setStroke(Color.DARKRED).setFill(Color.LIGHTCORAL));

        staticShapes.add(new PolyShape().randomize(540, 140, 130, 4, 6));
                //.setWidth(5).setStroke(Color.DARKRED).setFill(Color.LIGHTCORAL));

        return this;
    }

    public List<PolyShape> staticShapes() {
        return staticShapes;
    }

    public List<Entity> players() {
        return players;
    }

    public List<Entity> projectiles() {
        return projectiles;
    }

    /**
     * called by player when left mouse is clicked
     * @param bullet
     */
    public void fireBullet(Bullet bullet) {
        //todo:
        buffer.add(bullet);
    }

    public void updateProjectilesList() {
        projectiles.addAll(buffer);
        buffer.clear();
    }

    public PolyShape getMapShape() {
        return border;
    }

    /**
     * check if the HitBox argument is still within the boarder
     * @param hitBox
     * @return
     */
    public boolean inMap(HitBox hitBox) {
        //TODO:
        return border.getHitBox().containsBounds(hitBox);
    }
}
