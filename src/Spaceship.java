/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
public abstract class Spaceship implements Mappable {
    protected int virtual_x_position;
    protected int virtual_y_position;
    protected double mappable_x_position;
    protected double mappable_y_position;
    protected double angle;
    protected double velocity;

    protected HPBar health;

    protected double full_hp;
    protected double current_hp;

    public double[] distanceFrom(double x, double y) {
        double[] ret_arr = new double[2];
        ret_arr[0] = x - this.mappable_x_position;
        ret_arr[1] = y - this.mappable_y_position;
        return ret_arr;
    }

    public double getAngle() {
        return angle;
    }

    public int getVirtualX() {
        return virtual_x_position;
    }

    public int getVirtualY() {
        return virtual_y_position;
    }

    public double getMappableX() {
        return mappable_x_position;
    }

    public double getMappableY() {
        return mappable_y_position;
    }
}
