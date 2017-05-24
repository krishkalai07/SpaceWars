import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/16/17
 * VERSION: 1
 */
public class UserSpaceship extends Spaceship {
    public UserSpaceship(int screen_width, int screen_height) {
        virtual_x_position = screen_width/2;
        virtual_y_position = screen_height/2;

        mappable_x_position = 700;
        mappable_y_position = 430;

        velocity = 2;

        full_hp = 100;
        current_hp = full_hp;

        health = new HPBar(100, 100);

        angle = 0;

        immunity_timer = -1;
    }

    public void draw(Graphics g, int mouse_x, int mouse_y) {
        g.setColor(new Color(0xFFA000));

        double x_fraction = mouse_x-virtual_x_position;
        double y_fraction = mouse_y-virtual_y_position;
        angle = (StrictMath.toDegrees(StrictMath.atan(x_fraction/y_fraction)));
        if (mouse_y >= virtual_y_position) {
            angle += 180;
        }
        Triangle.draw(g, virtual_x_position, virtual_y_position, angle);

        health.draw(g, virtual_x_position+15, virtual_y_position+15);
    }

    public void updateLocation() {
        mappable_x_position += velocity*StrictMath.sin(StrictMath.toRadians(angle+180));
        mappable_y_position += velocity*StrictMath.cos(StrictMath.toRadians(angle+180));
    }

    public boolean isDead() {
        return current_hp <= 0;
    }
}
