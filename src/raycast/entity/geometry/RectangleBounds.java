package raycast.entity.geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import raycast.entity.DrawableObject;
import utility.Point;

/**
 * this object represent the boundaries of any shape in a shape of a rectangle.
 *
 * @author Shahriar (Shawn) Emami
 * @version Jan 12, 2019
 */
public class RectangleBounds implements DrawableObject<RectangleBounds> {

    /**
     * the top left corner and dimension of this rectangle
     */
    private Point start, dimension;
    /**
     * fill and stroke color values
     */
    private Color fill, stroke;
    /**
     * stroke width drawing
     */
    private double strokeWidth;

    /**
     * create a new Object with all values at zero
     */
    public RectangleBounds() {
        start = new Point();
        dimension = new Point();
        fill = Color.LIGHTPINK;
        stroke = Color.RED;
        strokeWidth = 2;
    }

    /**
     * create a new Object with given (x,y) and (h,w)
     *
     * @param x - x coordinate of top left
     * @param y - y coordinate of top left
     * @param w - width
     * @param h - height
     */
    public RectangleBounds(double x, double y, double w, double h) {
        this();
        start.set(x, y);
        dimension.set(w, h);
    }

    /**
     * move the rectangle by top left corner to a new location by the given distance
     *
     * @param dx - amount to move in x direction
     * @param dy - amount to move in y direction
     */
    public void translate(double dx, double dy) {
        start.translate(dx, dy);
    }

    /**
     * get the point with x and y value
     *
     * @return x and y as a point object
     */
    public Point pos() {
        return start;
    }

    /**
     * set the value of top left corner
     *
     * @param x - x coordinate of top left
     * @param y - y coordinate of top left
     * @return the current instance of this object
     */
    public RectangleBounds pos(double x, double y) {
        start.set(x, y);
        return this;
    }

    /**
     * get the point with width and height value
     *
     * @return width and height as a point object
     */
    public Point dimension() {
        return dimension;
    }

    /**
     * set the new width and height
     *
     * @param w - width
     * @param h - height
     * @return the current instance of this object
     */
    public RectangleBounds dimension(double w, double h) {
        dimension.set(w, h);
        return this;
    }

    /**
     * get x value of top left corner
     *
     * @return x of top left corner
     */
    public double x() {
        return start.x();
    }

    /**
     * get the center x of this rectangle
     *
     * @return center x
     */
    public double centerX() {
        return start.x() + w() / 2;
    }

    /**
     * get y value of top left corner
     *
     * @return y of top left corner
     */
    public double y() {
        return start.y();
    }

    /**
     * get the center y of this rectangle
     *
     * @return center y
     */
    public double centerY() {
        return start.y() + h() / 2;
    }

    /**
     * get the height of the rectangle
     *
     * @return height
     */
    public double h() {
        return dimension.y();
    }

    /**
     * get the y value of bottom right corner of rectangle.
     *
     * @return y bottom left corner
     */
    public double hPos() {
        return h() + y();
    }

    /**
     * get width of the rectangle
     *
     * @return width
     */
    public double w() {
        return dimension.x();
    }

    /**
     * get the x value of bottom right corner of rectangle.
     *
     * @return x bottom left corner
     */
    public double wPos() {
        return w() + x();
    }

    /**
     * check if given {@link RectangleBounds} overlaps with current {@link RectangleBounds}
     *
     * @param rect - given {@link RectangleBounds}
     * @return true if overlaps else false
     * @see <a href="https://stackoverflow.com/a/32088787/764951">detecting ovelaping rectangles</a>
     */
    public boolean intersects(RectangleBounds b) {
        return intersects(b.x(), b.y(), b.w(), b.h());
    }

    /**
     * check if given (x,y) with (w, h) overlaps with current {@link RectangleBounds}
     *
     * @param x - x coordinate of top left
     * @param y - y coordinate of top left
     * @param w - height of rectangle
     * @param h - width of rectangle
     * @return true if overlaps else false
     * @see <a href="https://stackoverflow.com/a/32088787/764951">detecting ovelaping rectangles</a>
     */
    public boolean intersects(double x, double y, double w, double h) {
        return !(x > wPos() || y > hPos() || x() > x + w || y() > y + h);
    }

    /**
     * check if given {@link RectangleBounds} is with in this {@link RectangleBounds}
     *
     * @param rect - given {@link RectangleBounds}
     * @return true if in else false
     */
    public boolean contains(RectangleBounds rect) {
        return contains(rect.x(), rect.y(), rect.w(), rect.h());
    }

    /**
     * check if given (x,y) with (w, h) is with in this {@link RectangleBounds}
     *
     * @param x - x coordinate of top left
     * @param y - y coordinate of top left
     * @param w - height of rectangle
     * @param h - width of rectangle
     * @return true if in else false
     */
    public boolean contains(double x, double y, double w, double h) {
        //if given x and y are bigger and equal to x() and y()
        //and if x+w and y+h are smaller and equal to x()+w() and y+h()
        return x >= x() && x + w <= wPos() && y >= y() && y + h <= hPos();
    }

    /**
     * check if given point is with in this {@link RectangleBounds}
     *
     * @param p - given point with x and y
     * @return true if in else false
     */
    public boolean contains(Point p) {
        return contains(p.x(), p.y());
    }

    /**
     * check if given (x,y) is with in this {@link RectangleBounds}
     *
     * @param x - x coordinate
     * @param y - y coordinate
     * @return true if in else false
     */
    public boolean contains(double x, double y) {
        //if given x and y are bigger and equal to x() and y()
        //and if x and y are smaller and equal to x()+w() and y+h()
        return x >= x() && x <= wPos() && y >= y() && y <= hPos();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Double.hashCode((start == null) ? 0 : x());
        result = prime * result + Double.hashCode((start == null) ? 0 : y());
        result = prime * result + Double.hashCode((dimension == null) ? 0 : w());
        result = prime * result + Double.hashCode((dimension == null) ? 0 : h());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RectangleBounds))
            return false;
        RectangleBounds other = (RectangleBounds) obj;
        if (dimension == null) {
            if (other.dimension != null)
                return false;
        } else if (!dimension.equals(other.dimension))
            return false;
        if (start == null) {
            if (other.start != null)
                return false;
        } else if (!start.equals(other.start))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public String toString() {
        return String.format("start:%s, size:%s", start, dimension);
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public RectangleBounds setFill(Color color) {
        fill = color;
        return this;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public RectangleBounds setStroke(Color color) {
        stroke = color;
        return this;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public RectangleBounds setWidth(double width) {
        strokeWidth = width;
        return this;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public Color getFill() {
        return fill;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public Color getStroke() {
        return stroke;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public double getWidth() {
        return strokeWidth;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(stroke);
        gc.setLineWidth(strokeWidth);
        gc.strokeRect(x(), y(), w(), h());
    }
}
