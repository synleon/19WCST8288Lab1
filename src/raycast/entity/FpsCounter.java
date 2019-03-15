package raycast.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FpsCounter implements DrawableObject<FpsCounter> {

    public static final double ONE_SECOND = 1000000000L;
    public static final double HALF_SECOND = ONE_SECOND / 2F;
    private Font fpsFont;
    private String fpsDisplay;
    private int frameCount;
    private double lastTime;
    private double strokeWidth;
    private Color fill;
    private Color stroke;
    private double x;
    private double y;

    /**
     * default constructor
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public FpsCounter(double x, double y) {
        setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BLACK, 24));
        setPos(x, y);
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

    @Override
    public void draw(GraphicsContext gc) {
        Font font = gc.getFont();
        gc.setFont(fpsFont);
        gc.setFill(getFill());
        gc.fillText(fpsDisplay, x, y);
        gc.setStroke(stroke);
        gc.setLineWidth(strokeWidth);
        gc.strokeText(fpsDisplay, x, y);
        gc.setFont(font);
    }

    @Override
    public FpsCounter setFill(Color color) {
        this.fill = color;
        return this;
    }

    @Override
    public FpsCounter setStroke(Color color) {
        this.stroke = color;
        return this;
    }

    @Override
    public FpsCounter setWidth(double width) {
        this.strokeWidth = width;
        return this;
    }

    @Override
    public Color getFill() {
        return fill;
    }

    @Override
    public Color getStroke() {
        return stroke;
    }

    @Override
    public double getWidth() {
        return strokeWidth;
    }
}
