package dungeonshooter;

import dungeonshooter.animator.AbstractAnimator;
import dungeonshooter.entity.PolyShape;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
    private Canvas board;

    /**
     * create a {@link AbstractAnimator} called animator. {@link AnimationTimer} provides
     * most common functionally needed to draw animations of ray casting.
     */
    private AbstractAnimator animator;

    /**
     * list of shapes
     */
    private List<PolyShape> shapes;

    /**
     * <p>
     * create an {@link IntegerProperty} called rayCount to keep track of ray count changes.<br>
     * this variable can be initialized with {@link SimpleBooleanProperty}
     * </p>
     *
     * <pre>
     * IntegerProperty i1 = new SimpleIntegerProperty( 1);
     * IntegerProperty i2 = new SimpleIntegerProperty();
     * i1.bind( i2);
     * i2.set( 100);
     * System.out.println( i1.get()); // prints 100
     * </pre>
     * <p>
     * create a getter that returns {@link IntegerProperty and a method that returns {@link IntegerProperty#get()}
     * </p>
     */
    private IntegerProperty rayCount;

    public int getRayCount() {
        return rayCount.get();
    }

    public IntegerProperty rayCountProperty() {
        return rayCount;
    }

    /**
     * <p>
     * create a set of {@link BooleanProperty}s to track some drawing options.<br>
     * create: drawLightSource, drawIntersectPoint, drawShapeJoints, drawSectors, drawBounds, drawFPS<br>
     * these variables can be initialized with {@link SimpleBooleanProperty}
     * </p>
     *
     * <pre>
     * BooleanProperty b1 = new SimpleBooleanProperty( false);
     * BooleanProperty b2 = new SimpleBooleanProperty();
     * b1.bind( b2);
     * b2.set( true);
     * System.out.println( b1.get()); // prints true
     * </pre>
     * <p>
     * create a getter that returns {@link BooleanProperty and a method that returns {@link BooleanProperty#get()}
     * for each BooleanProperty.
     * </p>
     */
    private BooleanProperty drawLightSource, drawIntersectPoint, drawShapeJoints, drawSectors, drawBounds, drawFPS;

    public boolean getDrawLightSource() {
        return drawLightSource.get();
    }

    public BooleanProperty drawLightSourceProperty() {
        return drawLightSource;
    }

    public boolean getsDrawIntersectPoint() {
        return drawIntersectPoint.get();
    }

    public BooleanProperty drawIntersectPointProperty() {
        return drawIntersectPoint;
    }

    public boolean getDrawShapeJoints() {
        return drawShapeJoints.get();
    }

    public BooleanProperty drawShapeJointsProperty() {
        return drawShapeJoints;
    }

    public boolean getDrawSectors() {
        return drawSectors.get();
    }

    public BooleanProperty drawSectorsProperty() {
        return drawSectors;
    }

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
        rayCount = new SimpleIntegerProperty();
        drawBounds = new SimpleBooleanProperty();
        drawFPS = new SimpleBooleanProperty();
        drawIntersectPoint = new SimpleBooleanProperty();
        drawLightSource = new SimpleBooleanProperty();
        drawShapeJoints = new SimpleBooleanProperty();
        drawSectors = new SimpleBooleanProperty();

        board = new Canvas();

        shapes = new ArrayList<>(20);
    }
    /**
     * create the property class variables functions here
     */

    /**
     * create a method called setAnimator.
     * set an {@link AbstractAnimator}. if an animator exists {@link CanvasMap#stop()} it and
     * call {@link CanvasMap#removeMouseEvents()}. then set the new animator and
     * call {@link CanvasMap#start()} and {@link CanvasMap#registerMouseEvents()}.
     *
     * @param newAnimator - new {@link AbstractAnimator} object
     * @return the current instance of this object
     */
    public CanvasMap setAnimator(AbstractAnimator newAnimator) {
        if (animator != null) {
            stop();
            removeMouseEvents();
        }
        this.animator = newAnimator;
        start();
        registerMouseEvents();
        return this;
    }

    /**
     * <p>create a method called registerMouseEvents.
     * register the mouse events for when the mouse is
     * {@link MouseEvent#MOUSE_MOVED} or {@link MouseEvent#MOUSE_DRAGGED}.<br>
     * call {@link CanvasMap#addEventHandler} twice and pass to it
     * {@link MouseEvent#MOUSE_DRAGGED}, {@link animator#mouseDragged} and
     * {@link MouseEvent#MOUSE_MOVED}, {@link animator#mouseMoved}.</p>
     * <p>a method can be passed directly as an argument if the method signature matches
     * the functional interface. in this example you will pass the animator method using
     * object::method syntax.</p>
     */
    public void registerMouseEvents() {
        addEventHandler(MouseEvent.MOUSE_MOVED, e -> animator.mouseMoved(e));
        // addEventHandler(MouseEvent.MOUSE_MOVED, animator::mouseMoved);
        addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> animator.mouseDragged(e));
        // addEventHandler(MouseEvent.MOUSE_DRAGGED, animator::mouseDragged);
    }

    /**
     * <p>create a method called removeMouseEvents.
     * remove the mouse events for when the mouse is
     * {@link MouseEvent#MOUSE_MOVED} or {@link MouseEvent#MOUSE_DRAGGED}.<br>
     * call {@link CanvasMap#addEventHandler} twice and pass to it
     * {@link MouseEvent#MOUSE_DRAGGED}, {@link animator#mouseDragged} and
     * {@link MouseEvent#MOUSE_MOVED}, {@link animator#mouseMoved}.</p>
     * <p>a method can be passed directly as an argument if the method signature matches
     * the functional interface. in this example you will pass the animator method using
     * object::method syntax.</p>
     */
    public void removeMouseEvents() {
        removeEventHandler(MouseEvent.MOUSE_MOVED, animator::mouseMoved);
        removeEventHandler(MouseEvent.MOUSE_DRAGGED, animator::mouseDragged);
    }

    /**
     * <p>
     * register the given {@link EventType} and {@link EventHandler}
     * </p>
     *
     * @param event   - an event such as {@link MouseEvent#MOUSE_MOVED}.
     * @param handler - a lambda to be used when registered event is triggered.
     */
    public <E extends Event> void addEventHandler(EventType<E> event, EventHandler<E> handler) {
        board.addEventHandler(event, handler);
    }

    /**
     * <p>
     * remove the given {@link EventType} registered with {@link EventHandler}
     * </p>
     *
     * @param event   - an event such as {@link MouseEvent#MOUSE_MOVED}.
     * @param handler - a lambda to be used when registered event is triggered.
     */
    public <E extends Event> void removeEventHandler(EventType<E> event, EventHandler<E> handler) {
        board.removeEventHandler(event, handler);
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
        return board;
    }

    /**
     * create a method called gc.
     * get the {@link GraphicsContext} of {@link Canvas} that allows direct drawing.
     *
     * @return {@link GraphicsContext} of {@link Canvas}
     */
    public GraphicsContext gc() {
        return board.getGraphicsContext2D();
    }

    /**
     * create a method called h.
     * get the height of the map, {@link Canvas#getHeight()}
     *
     * @return height of canvas
     */
    public double h() {
        return board.getHeight();
    }

    /**
     * create a method called w.
     * get the width of the map, {@link Canvas#getWidth()}
     *
     * @return width of canvas
     */
    public double w() {
        return board.getWidth();
    }

    /**
     * getter method for shapes
     * @return
     */
    public List<PolyShape> shapes() {
        return shapes;
    }

    /**
     * Create a bunch of sample shapes
     */
    public void addSampleShapes() {
        shapes.add(new PolyShape().setPoints(90, 120, 150, 50, 300, 80, 200, 250));
                //.setWidth(5).setStroke(Color.DARKRED).setFill(Color.LIGHTCORAL));

        shapes.add(new PolyShape().randomize(600, 600, 150, 4, 6));
                //.setWidth(5).setStroke(Color.DARKRED).setFill(Color.LIGHTCORAL));

        shapes.add(new PolyShape().randomize(150, 600, 150, 4, 6));
                //.setWidth(5).setStroke(Color.DARKRED).setFill(Color.LIGHTCORAL));

        shapes.add(new PolyShape().randomize(620, 160, 150, 4, 6));
                //.setWidth(5).setStroke(Color.DARKRED).setFill(Color.LIGHTCORAL));
    }
}
