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
    private float full_hp;
    private float current_hp;
    private double velocity;
    private double direction;

    private double virtual_x_position;
    private double virtual_y_position;
    private int radius;

    private int mappable_x_position;
    private int mappable_y_position;

    HPBar health_bar;

    public Asteroid(int radius) {
        this.radius = radius;
        full_hp = radius * 3.1415926f;
        current_hp = full_hp;
        virtual_x_position = random() * SpaceWarViewController.SCREEN_WIDTH;
        virtual_y_position = random() * SpaceWarViewController.SCREEN_HEIGHT;

        //virtual_x_position = 550;
        //virtual_y_position = 330;

        velocity = 3;
        direction = random() * 360;

        mappable_x_position = (int)(Math.random() * Map.MAP_WIDTH);
        mappable_y_position = (int)(Math.random() * Map.MAP_HEIGHT);

        health_bar = new HPBar((int)full_hp, (int)current_hp);
    }

    public void draw(Graphics g) {
        g.drawOval((int) virtual_x_position - radius, (int) virtual_y_position - radius, radius, radius);
        health_bar.draw(g, (int)virtual_x_position, (int)virtual_y_position);
    }

    public void updateLocation() {
        virtual_x_position += velocity* sin(toRadians(direction+180));
        virtual_y_position += velocity* cos(toRadians(direction+180));
    }

    public boolean hasLostAllHP() {
        return current_hp <= 0;
    }

    public void updateHP(double damage_value) {
        current_hp -= damage_value;
        health_bar.setPartialHealth(current_hp);
    }

    public boolean isInsideCircle(int x, int y) {
        //System.out.println(pow (x-virtual_x_position,2) + pow (y-virtual_y_position,2) + " " + pow(radius,2));
        return pow (x-virtual_x_position,2) + pow (y-virtual_y_position,2) <= pow(radius,2);
    }
}
