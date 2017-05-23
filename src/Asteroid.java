import java.awt.*;

import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.random;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.toRadians;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
public class Asteroid implements Mappable {
    private double full_hp;
    private double current_hp;
    private double velocity;
    private double direction;

    private double virtual_x_position;
    private double virtual_y_position;
    private int radius;

    private double mappable_x_position;
    private double mappable_y_position;

    private HPBar health_bar;

    public Asteroid(int radius) {
        this.radius = 60;
        full_hp = radius * StrictMath.PI;
        current_hp = full_hp;
        virtual_x_position = random() * SpaceWarViewController.SCREEN_WIDTH;
        virtual_y_position = random() * SpaceWarViewController.SCREEN_HEIGHT;

        //virtual_x_position = 550;
        //virtual_y_position = 330;

        velocity = 3;
        direction = random() * 360;

        mappable_x_position = (int)(Math.random() * 1400);
        mappable_y_position = (int)(Math.random() * 860);

        health_bar = new HPBar((int)full_hp, (int)current_hp);
    }

    public void draw(Graphics g, double x_distance_from_user, double y_distance_from_user) {
        virtual_x_position = x_distance_from_user + 350;
        virtual_y_position = y_distance_from_user + 220;
        g.drawOval((int) virtual_x_position - radius, (int) virtual_y_position - radius, radius, radius);
        health_bar.draw(g, (int)virtual_x_position, (int)virtual_y_position);
    }

    public void updateLocation() {
        mappable_x_position += velocity * sin(toRadians(direction + 180));
        mappable_y_position += velocity * cos(toRadians(direction + 180));
    }

    public boolean hasLostAllHP() {
        return current_hp <= 0;
    }

    public void updateHP(double damage_value) {
        current_hp -= damage_value;
        health_bar.setPartialHealth(current_hp);
    }

    public boolean isInsideCircle(int x, int y) {
        return pow (x-virtual_x_position+radius/2,2) + pow (y-virtual_y_position+radius/2,2) <= pow(radius*0.5,2);
    }

    public double getMappableXPosition() {
        return mappable_x_position;
    }

    public double getMappableYPosition() {
        return mappable_y_position;
    }
}
