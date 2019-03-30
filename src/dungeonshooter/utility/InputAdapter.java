package dungeonshooter.utility;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;

/**
 * <p>
 * an abstract adapter class for {@link KeyEvent}, {@link MouseEvent} and {@link TouchEvent} events.
 * when extending this class only override methods that are needed. register method for those event methods must also be called.
 * <pre>
 * 	public class CanvasInput extends InputAdapter&lt;Canvas&gt{
 * 		protected void touchMoved( TouchEvent e){
 * 			System.out.println("Touch moved");
 * 			//call super method at the end
 * 			super.touchMoved(e)
 * 		}
 * 	}
 *
 * 	Canvas canvas = new Canvas();
 * 	InputAdapter< Canvas> input = new CanvasInput();
 * 	input.registerTouch(canvas);
 * </pre>
 * </p>
 * @param N - generic type for the {@link Node} that this adapter will use.
 * @author Shahriar (Shawn) Emami
 * @version Jan 9, 2019
 */
public abstract class InputAdapter< N extends Node> {

    /**
     * <p>
     * add the given {@link EventHandler} with {@link EventType} to given {@link Node}.
     * </p>
     * @param node - the {@link Node} from which the event handlers are removed.
     * @param event - an event such as {@link MouseEvent#MOUSE_MOVED}.
     * @param handler - a lambda to be used when registered event is triggered.
     */
    public < E extends Event> void addEventHandler( N node, EventType< E> event, EventHandler< E> handler){
        node.addEventHandler( event, handler);
    }

    /**
     * <p>
     * remove the given {@link EventHandler} registered with {@link EventType} on given {@link Node}.
     * </p>
     * @param node - the {@link Node} from which the event handlers are removed.
     * @param event - an event such as {@link MouseEvent#MOUSE_MOVED}.
     * @param handler - a lambda to be used when registered event is triggered.
     */
    public < E extends Event> void removeEventHandler( N node, EventType< E> event, EventHandler< E> handler){
        node.removeEventHandler( event, handler);
    }

    /**
     * <p>
     * when the mouse enters the area of given {@link Node}, {@link MouseEvent#MOUSE_ENTERED} is triggered
     * and subsequently {@link Node#requestFocus()} is called to force focus if {@link Node} is eligible.
     * </p>
     * @param node - the {@link Node} on to which focus is requested.
     */
    public void forceFocusWhenMouseEnters( N node){
        addEventHandler( node, MouseEvent.MOUSE_ENTERED, e->node.requestFocus());
    }

    /**
     * <p>
     * a shorthand method that will register all available {@link EventType} in this adapter by calling all the methods below.<br>
     * {@link InputAdapter#registerKey}<br>
     * {@link InputAdapter#registerTouch}<br>
     * {@link InputAdapter#registerMouseClick}<br>
     * {@link InputAdapter#registerMouseMovment}<br>
     * {@link InputAdapter#registerMouseEnterExit}<br>
     * </p>
     * @param node - the {@link Node} from which the event handlers are removed.
     */
    public void registerAll( N node){
        registerKey( node);
        registerTouch( node);
        registerMouseClick( node);
        registerMouseMovment( node);
        registerMouseEnterExit( node);
    }

    /**
     * <p>
     * a shorthand method that will remove all available {@link EventType} in this adapter by calling all the methods below.<br>
     * {@link InputAdapter#removeKey}<br>
     * {@link InputAdapter#removeTouch}<br>
     * {@link InputAdapter#removeMouseClick}<br>
     * {@link InputAdapter#removeMouseMovment}<br>
     * {@link InputAdapter#removeMouseEnterExit}<br>
     * </p>
     * @param node - the {@link Node} from which the event handlers are removed.
     */
    public void removeAll( N node){
        removeKey( node);
        removeTouch( node);
        removeMouseClick( node);
        removeMouseMovment( node);
        removeMouseEnterExit( node);
    }

    /**
     * <p>
     * add {@link MouseEvent#MOUSE_MOVED} and {@link MouseEvent#MOUSE_DRAGGED} event handlers to given {@link Node}.
     * </p>
     * @param node - the {@link Node} to which the event handlers are added.
     */
    public void registerMouseMovment( N node){
        addEventHandler( node, MouseEvent.MOUSE_MOVED, this::mouseMoved);
        addEventHandler( node, MouseEvent.MOUSE_DRAGGED, this::mouseDragged);
    }

    /**
     * <p>
     * remove {@link MouseEvent#MOUSE_MOVED} and {@link MouseEvent#MOUSE_DRAGGED} event handlers from given {@link Node}.
     * </p>
     * @param node - the {@link Node} from which the event handlers are removed.
     */
    public void removeMouseMovment( N node){
        removeEventHandler( node, MouseEvent.MOUSE_MOVED, this::mouseMoved);
        removeEventHandler( node, MouseEvent.MOUSE_DRAGGED, this::mouseDragged);
    }

    /**
     * <p>
     * add {@link MouseEvent#MOUSE_ENTERED_TARGET} and {@link MouseEvent#MOUSE_EXITED_TARGET} event handlers to given {@link Node}.
     * </p>
     * @param node - the {@link Node} to which the event handlers are added.
     */
    public void registerMouseEnterExit( N node){
        addEventHandler( node, MouseEvent.MOUSE_ENTERED_TARGET, this::mouseEntered);
        addEventHandler( node, MouseEvent.MOUSE_EXITED_TARGET, this::mouseExited);
    }

    /**
     * <p>
     * remove {@link MouseEvent#MOUSE_ENTERED_TARGET} and {@link MouseEvent#MOUSE_EXITED_TARGET} event handlers from given {@link Node}.
     * </p>
     * @param node - the {@link Node} from which the event handlers are removed.
     */
    public void removeMouseEnterExit( N node){
        removeEventHandler( node, MouseEvent.MOUSE_ENTERED_TARGET, this::mouseEntered);
        removeEventHandler( node, MouseEvent.MOUSE_EXITED_TARGET, this::mouseExited);
    }

    /**
     * <p>
     * add {@link MouseEvent#MOUSE_PRESSED} and {@link MouseEvent#MOUSE_RELEASED} event handlers to given {@link Node}.
     * </p>
     * @param node - the {@link Node} to which the event handlers are added.
     */
    public void registerMouseClick( N node){
        addEventHandler( node, MouseEvent.MOUSE_PRESSED, this::mousePressed);
        addEventHandler( node, MouseEvent.MOUSE_RELEASED, this::mouseReleased);
    }

    /**
     * <p>
     * remove {@link MouseEvent#MOUSE_PRESSED} and {@link MouseEvent#MOUSE_RELEASED} event handlers from given {@link Node}.
     * </p>
     * @param node - the {@link Node} from which the event handlers are removed.
     */
    public void removeMouseClick( N node){
        removeEventHandler( node, MouseEvent.MOUSE_PRESSED, this::mousePressed);
        removeEventHandler( node, MouseEvent.MOUSE_RELEASED, this::mouseReleased);
    }

    /**
     * <p>
     * add {@link TouchEvent#TOUCH_PRESSED}, {@link TouchEvent#TOUCH_MOVED} and {@link TouchEvent#TOUCH_RELEASED}
     * event handlers to given {@link Node}.
     * </p>
     * @param node - the {@link Node} to which the event handlers are added.
     */
    public void registerTouch( N node){
        addEventHandler( node, TouchEvent.TOUCH_PRESSED, this::touchPressed);
        addEventHandler( node, TouchEvent.TOUCH_MOVED, this::touchMoved);
        addEventHandler( node, TouchEvent.TOUCH_RELEASED, this::touchReleased);
    }

    /**
     * <p>
     * remove {@link TouchEvent#TOUCH_PRESSED}, {@link TouchEvent#TOUCH_MOVED} and {@link TouchEvent#TOUCH_RELEASED}
     * event handlers from given {@link Node}.
     * </p>
     * @param node - the {@link Node} from which the event handlers are removed.
     */
    public void removeTouch( N node){
        removeEventHandler( node, TouchEvent.TOUCH_PRESSED, this::touchPressed);
        removeEventHandler( node, TouchEvent.TOUCH_MOVED, this::touchMoved);
        removeEventHandler( node, TouchEvent.TOUCH_RELEASED, this::touchReleased);
    }

    /**
     * <p>
     * add {@link KeyEvent#KEY_PRESSED} and {@link KeyEvent#KEY_RELEASED} event handlers to given {@link Node}.
     * for full functionality {@link InputAdapter#forceFocusWhenMouseEnters} should be called as {@link KeyEvent} needs focus.
     * </p>
     * @param node - the {@link Node} to which the event handlers are added.
     */
    public void registerKey( N node){
        addEventHandler( node, KeyEvent.KEY_PRESSED, this::keyPressed);
        addEventHandler( node, KeyEvent.KEY_RELEASED, this::keyReleased);
    }

    /**
     * <p>
     * remove {@link KeyEvent#KEY_PRESSED} and {@link KeyEvent#KEY_RELEASED} event handlers from given {@link Node}.
     * </p>
     * @param node - the {@link Node} from which the event handlers are removed.
     */
    public void removeKey( N node){
        removeEventHandler( node, KeyEvent.KEY_PRESSED, this::keyPressed);
        removeEventHandler( node, KeyEvent.KEY_RELEASED, this::keyReleased);
    }

    /**
     * <p>
     * called when the mouse is moving, not dragged, in the area of the registered {@link Node}.
     * </p>
     * <p>
     * this method will never have a body, a simple template to be overridden. super call to this method is not needed.
     * </p>
     * @param x - current x position of mouse in registered Node
     * @param y - current y position of mouse in registered Node
     */
    protected void moved( double x, double y){

    }

    /**
     * <p>
     * called when of the keys on the mouse is pressed.
     * </p>
     * <p>
     * this method will never have a body, a simple template to be overridden. super call to this method is not needed.
     * </p>
     * @param x - current x position of mouse in registered Node
     * @param y - current y position of mouse in registered Node
     */
    protected void pressed( double x, double y){

    }

    /**
     * <p>
     * called while one of the keys on the is pressed and mouse is moving in the area of registered {@link Node}.
     * </p>
     * <p>
     * this method will never have a body, a simple template to be overridden. super call to this method is not needed.
     * </p>
     * @param x - current x position of mouse in registered Node
     * @param y - current y position of mouse in registered Node
     */
    protected void dragged( double x, double y){

    }

    /**
     * <p>
     * called when one of they keys on the mouse is released.
     * </p>
     * <p>
     * this method will never have a body, a simple template to be overridden. super call to this method is not needed.
     * </p>
     * @param x - current x position of mouse in registered Node
     * @param y - current y position of mouse in registered Node
     */
    protected void released( double x, double y){

    }

    /**
     * <p>
     * called when mouse entered the area of registered {@link Node}.
     * </p>
     * <p>
     * this method will never have a body, a simple template to be overridden. super call to this method is not needed.
     * </p>
     * @param x - current x position of mouse in registered Node
     * @param y - current y position of mouse in registered Node
     */
    protected void entered( double x, double y){

    }

    /**
     * <p>
     * called when mouse leaves the area of registered {@link Node}.
     * </p>
     * <p>
     * this method will never have a body, a simple template to be overridden. super call to this method is not needed.
     * </p>
     * @param x - current x position of mouse in registered Node
     * @param y - current y position of mouse in registered Node
     */
    protected void exited( double x, double y){

    }

    /**
     * <p>
     * called when a keyboard key is pressed. works only when the registered {@link Node} has focus.
     * to force focus call {@link InputAdapter#forceFocusWhenMouseEnters}.
     * </p>
     * <p>
     * this method will never have a body, a simple template to be overridden. super call to this method is not needed.
     * </p>
     * @param key - {@link KeyCode} of the pressed key.
     */
    protected void keyPressed( KeyCode key){

    }

    /**
     * <p>
     * called when a keyboard key is released. works only when the registered {@link Node} has focus.
     * to force focus call {@link InputAdapter#forceFocusWhenMouseEnters}.
     * </p>
     * <p>
     * this method will never have a body, a simple template to be overridden. super call to this method is not needed.
     * </p>
     * @param key - {@link KeyCode} of the pressed key.
     */
    protected void keyReleased( KeyCode key){

    }

    /**
     * <p>
     * called when a keyboard key is pressed. works only when the registered {@link Node} has focus.
     * to force focus call {@link InputAdapter#forceFocusWhenMouseEnters}. it will also call {@link InputAdapter#keyPressed}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void keyPressed( KeyEvent e){
        keyPressed( e.getCode());
    }

    /**
     * <p>
     * called when a keyboard key is released. works only when the registered {@link Node} has focus.
     * to force focus call {@link InputAdapter#forceFocusWhenMouseEnters}. it will also call {@link InputAdapter#keyReleased}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void keyReleased( KeyEvent e){
        keyReleased( e.getCode());
    }

    /**
     * <p>
     * called when touch is detected. it will also call {@link InputAdapter#pressed}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void touchPressed( TouchEvent e){
        pressed( e.getTouchPoint().getX(), e.getTouchPoint().getY());
    }

    /**
     * <p>
     * called when touch moving. it will also call {@link InputAdapter#dragged}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void touchMoved( TouchEvent e){
        dragged( e.getTouchPoint().getX(), e.getTouchPoint().getY());
    }

    /**
     * <p>
     * called when touch is released. it will also call {@link InputAdapter#released}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void touchReleased( TouchEvent e){
        released( e.getTouchPoint().getX(), e.getTouchPoint().getY());
    }

    /**
     * <p>
     * called when of the keys on the mouse is pressed. it will also call {@link InputAdapter#pressed}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void mousePressed( MouseEvent e){
        pressed( e.getX(), e.getY());
    }

    /**
     * <p>
     * called while one of the keys on the is pressed and mouse is moving in the area of registered {@link Node}. it will also call {@link InputAdapter#dragged}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void mouseDragged( MouseEvent e){
        dragged( e.getX(), e.getY());
    }

    /**
     * <p>
     * called when one of they keys on the mouse is released. it will also call {@link InputAdapter#released}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void mouseReleased( MouseEvent e){
        released( e.getX(), e.getY());
    }

    /**
     * <p>
     * called when mouse entered the area of registered {@link Node}. it will also call {@link InputAdapter#entered}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void mouseEntered( MouseEvent e){
        entered( e.getX(), e.getY());
    }

    /**
     * <p>
     * called when mouse leaves the area of registered {@link Node}. it will also call {@link InputAdapter#exited}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void mouseExited( MouseEvent e){
        exited( e.getX(), e.getY());
    }

    /**
     * <p>
     * called when the mouse is moving, not dragged, in the area of the registered {@link Node}. it will also call {@link InputAdapter#moved}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     * @param e - event object holding relevant information.
     */
    protected void mouseMoved( MouseEvent e){
        moved(e.getX(), e.getY());
    }
}

