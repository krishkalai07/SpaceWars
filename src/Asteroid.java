import sun.tools.javac.*;

import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
public class Asteroid implements Mappable {
    private float full_hp;
    private float current_hp;
    private double velocity;
    private double direction;

    private double x_position;
    private double y_position;
    private int radius;

    public Asteroid(int radius) {
        this.radius = radius;
        full_hp = radius * 3.1415926f;
        current_hp = full_hp;
        x_position = StrictMath.random() * MainView.SCREEN_WIDTH;
        y_position = StrictMath.random() * MainView.SCREEN_HEIGHT;

        velocity = 3;
        direction = StrictMath.random() * 360;
    }

    public void draw(Graphics g) {
        g.drawOval((int)x_position-radius, (int)y_position-5, radius, radius);
    }

    public void updateLocation() {
        x_position += velocity*StrictMath.sin(StrictMath.toRadians(direction+180));
        y_position += velocity*StrictMath.cos(StrictMath.toRadians(direction+180));
    }

    public boolean hasLostAllHP() {
        return current_hp <= 0;
    }

    public void updateHP(double damage_value) {
        current_hp -= damage_value;
    }
}
