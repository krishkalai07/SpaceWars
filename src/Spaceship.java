import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
public abstract class Spaceship implements Mappable {
    protected int virtual_x_position;
    protected int virtual_y_position;
    protected int mappable_x_position;
    protected int mappable_y_position;
    protected double angle;
    protected double velocity;

    public double getAngle() {
        return angle;
    }

    public int getVirtualX() {
        return virtual_x_position;
    }

    public int getVirtualY() {
        return virtual_y_position;
    }

    public int getMappableX() {
        return mappable_x_position;
    }

    public int getMappableY() {
        return mappable_y_position;
    }
}
