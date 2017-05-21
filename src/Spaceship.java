import static java.lang.Math.max;
import static java.lang.Math.min;

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


    protected int[] x_coords;
    protected int[] y_coords;

    public double[] distanceFrom(double x, double y) {
        double[] ret_arr = new double[2];
        ret_arr[0] = x - this.mappable_x_position;
        ret_arr[1] = y - this.mappable_y_position;
        return ret_arr;
    }

    public boolean isPointInsideTriangle(int x, int y) {
        int counter = 0;
        int i;
        double x_intercept;
        int x1, y1, x2, y2;

        x1 = x_coords[0];
        y1 = y_coords[0];
        for (i = 1; i < x_coords.length; i++) {
            x2 = x_coords[i%(x_coords.length-1)];
            y2 = y_coords[i%(y_coords.length-1)];
            if (y >= min(y1, y2)) {
                if (y <= max(y1, y2)) {
                    if (x <= max(x1, x2)) {
                        if (y1 != y2) {
                            x_intercept = (y - y1) * (x2 - x1) / (y2 - y1) + x1;
                            if (x1 == x2 || x <= x_intercept) {
                                counter++;
                            }
                        }
                    }
                }
            }
            x1 = x2;
            y1 = y2;
        }
        return counter % 2 != 0;
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

    public void setXCoords(int[] x_coords) {
        this.x_coords = x_coords;
    }

    public void setYCoords(int[] y_coords) {
        this.y_coords = y_coords;
    }
}
