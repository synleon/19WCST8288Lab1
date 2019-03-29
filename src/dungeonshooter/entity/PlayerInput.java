package dungeonshooter.entity;

import dungeonshooter.utility.InputAdapter;
import javafx.beans.property.BooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 */
public class PlayerInput extends InputAdapter<Canvas> {

    /**
     *  The coordinates of player
     */
    private double x, y;

    /**
     * Whether left/right/up/down key has been pressed
     */
    private boolean left, right, up, down;

    /**
     * whether left/middle/right mouse key has been pressed
     */
    private boolean leftClick, middleClick, rightClick;

    /**
     * whether space/shift key has been pressed
     */
    private BooleanProperty space, shift;

    /**
     * Constructor
     */
    public PlayerInput() {
    }

    /**
     * getter for x
     * @return
     */
    public double x() {return x;}

    /**
     * getter for y
     * @return
     */
    public double y() {return y;}

    public boolean leftClicked() {
        return leftClick;
    }

    /**
     * getter for middleClicked
     * @return
     */
    public boolean middleClicked() {
        return middleClick;
    }

    /**
     * getter for rightClicked
     * @return
     */
    public boolean rightClicked() {
        return rightClick;
    }

    /**
     * getter for space
     * @return
     */
    public boolean isSpace() {
        return space.getValue();
    }

    /**
     * getter for shift
     * @return
     */
    public boolean isShift() {
        return shift.getValue();
    }

    public int leftOrRight() {
        if (!left && !right) {
            return 0;
        } else {
            if (right) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public int upOrDown() {
        if (!up && !down) {
            return 0;
        } else {
            if (down) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     * quick checker which returns true of any of the direction Booleans are true
     * @return
     */
    public boolean hasMoved() {
        return left || right || up || down;
    }

    /**
     * <p>
     * called when the mouse is moving, not dragged, in the area of the registered {@link Node}.
     * </p>
     * <p>
     * this method will never have a body, a simple template to be overridden. super call to this method is not needed.
     * </p>
     *
     * @param x - current x position of mouse in registered Node
     * @param y - current y position of mouse in registered Node
     */
    @Override
    protected void moved(double x, double y) {
        this.x = x;
        this.y = y;
        super.moved(x, y);
    }

    public void changeKeyStatus(KeyCode key, boolean isPressed) {
        switch (key) {
            case W:
                up = isPressed;
                break;
            case S:
                down = isPressed;
                break;
            case A:
                left = isPressed;
                break;
            case D:
                right = isPressed;
                break;
            case SHIFT:
                shift.set(isPressed);
                break;
            case SPACE:
                space.set(isPressed);
                break;
            default:
                break;
        }
    }

    /**
     * <p>
     * called while one of the keys on the is pressed and mouse is moving in the area of registered {@link Node}.
     * </p>
     * <p>
     * this method will never have a body, a simple template to be overridden. super call to this method is not needed.
     * </p>
     *
     * @param x - current x position of mouse in registered Node
     * @param y - current y position of mouse in registered Node
     */
    @Override
    protected void dragged(double x, double y) {
        this.x = x;
        this.y = y;
        super.dragged(x, y);
    }

    /**
     * <p>
     * called when a keyboard key is pressed. works only when the registered {@link Node} has focus.
     * to force focus call {@link InputAdapter#forceFocusWhenMouseEnters}. it will also call {@link InputAdapter#keyPressed}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     *
     * @param e - event object holding relevant information.
     */
    @Override
    protected void keyPressed(KeyEvent e) {
        changeKeyStatus(e.getCode(), true);
        super.keyPressed(e);
    }

    /**
     * <p>
     * called when a keyboard key is released. works only when the registered {@link Node} has focus.
     * to force focus call {@link InputAdapter#forceFocusWhenMouseEnters}. it will also call {@link InputAdapter#keyReleased}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     *
     * @param e - event object holding relevant information.
     */
    @Override
    protected void keyReleased(KeyEvent e) {
        changeKeyStatus(e.getCode(), false);
        super.keyReleased(e);
    }

    /**
     * <p>
     * called when one of they keys on the mouse is released. it will also call {@link InputAdapter#released}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     *
     * @param e - event object holding relevant information.
     */
    @Override
    protected void mouseReleased(MouseEvent e) {
        leftClick = rightClick = middleClick = false;
        super.mouseReleased(e);
    }

    /**
     * <p>
     * called when of the keys on the mouse is pressed. it will also call {@link InputAdapter#pressed}.
     * </p>
     * <p>
     * if this method is overridden, in the overridden method a super call to this method should be provided as this method is not empty.
     * </p>
     *
     * @param e - event object holding relevant information.
     */
    @Override
    protected void mousePressed(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        this.leftClick = e.isPrimaryButtonDown();
        this.rightClick = e.isSecondaryButtonDown();
        this.middleClick = e.isMiddleButtonDown();
        super.mousePressed(e);
    }

}
