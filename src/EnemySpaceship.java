import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/16/17
 * VERSION: 1
 */
public class EnemySpaceship extends Spaceship{

    EnemySpaceship(double angle, int x_position, int y_position) {
        this.angle = angle;
        this.mappable_x_position = x_position;
        this.mappable_y_position = y_position;
        this.virtual_x_position = -1;
        this.virtual_x_position = -1;
    }

    public void draw(Graphics g, int mouse_x, int mouse_y) {
        double x_fraction = mouse_x-virtual_x_position;
        double y_fraction = mouse_y-virtual_y_position;
        angle = (StrictMath.toDegrees(StrictMath.atan(x_fraction/y_fraction)));
        if (mouse_y >= virtual_y_position) {
            angle += 180;
        }
        Triangle.draw(g, virtual_x_position, virtual_y_position, angle);

        mappable_x_position += velocity*StrictMath.sin(StrictMath.toRadians(angle+180));
    }
}
