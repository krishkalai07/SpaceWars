import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
public class Spaceship implements Mappable {
    private Coordinate2D location;
    private double angle;

    Spaceship(int screen_width, int screen_height) {
        location = new Coordinate2D(screen_width/2, screen_height/2);
    }

    void draw(Graphics g, int mouse_x, int mouse_y) {
        double x_fraction = mouse_x-location.getLatitude();
        double y_fraction = mouse_y-location.getLongitude();
        angle = (StrictMath.toDegrees(StrictMath.atan(x_fraction/y_fraction)));
        if (mouse_y >= location.getLongitude()) {
            angle += 180;
        }
        Triangle.draw(g, location.getLatitude(), location.getLongitude(), angle);
    }

    public Coordinate2D getLocation() {
        return location;
    }

    public double getAngle() {
        return angle;
    }
}
