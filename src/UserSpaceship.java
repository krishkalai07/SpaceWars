import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/16/17
 * VERSION: 1
 */
@SuppressWarnings("ALL")
public class UserSpaceship extends Spaceship {
    private int drawing_x;
    private int drawing_y;

    public UserSpaceship(int screen_width, int screen_height) {
        drawing_x = screen_width/2;
        drawing_y = screen_height/2;

        mappable_x_position = 700;
        mappable_y_position = 430;

        velocity = 2;

        full_hp = 100;
        current_hp = full_hp;

        health = new HPBar(100, 100);

        angle = 270;

        immunity_timer = -1;
    }

    public void draw(Graphics g, int mouse_x, int mouse_y) {
        g.setColor(new Color(0xFFA000));

        double x_fraction = mouse_x-drawing_x;
        double y_fraction = mouse_y-drawing_y;
        angle = (StrictMath.toDegrees(StrictMath.atan(x_fraction/y_fraction)));
        if (mouse_y >= drawing_y) {
            angle += 180;
        }
        Triangle.draw(g, drawing_x, drawing_y, angle);

        health.draw(g, drawing_x+15, drawing_y+15);
    }

    public void updateLocation() {
        mappable_x_position += velocity*StrictMath.sin(StrictMath.toRadians(angle+180));
        mappable_y_position += velocity*StrictMath.cos(StrictMath.toRadians(angle+180));
    }

    public boolean isDead() {
        return current_hp <= 0;
    }
}
