import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
public class Bullet implements Mappable {
    private double virtual_x_position;
    private double virtual_y_position;
    private double mappable_x_position;
    private double mappable_y_position;
    private double velocity;
    private double direction;

    private Spaceship source;
    private double source_position_x;
    private double source_position_y;

    private double damage;

    public Bullet(double direction, int velocity, int x_position, int y_position, double damage, Spaceship source) {
        this.source = source;
        this.source_position_x = source.getMappableX();
        this.source_position_y = source.getMappableY();

        this.direction = direction;
        this.velocity = velocity;
        this.virtual_x_position = x_position;
        this.virtual_y_position = y_position;
        this.mappable_x_position = source.mappable_x_position;
        this.mappable_y_position = source.mappable_y_position;
        this.damage = damage;
        this.damage = 500;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0xFFFFFF));
        double x_distance_from_user = this.mappable_x_position - source.mappable_x_position;
        double y_distance_from_user = this.mappable_y_position - source.mappable_y_position;

        g.drawString(x_distance_from_user + ", " + y_distance_from_user, 0, 24);

        virtual_x_position = x_distance_from_user + 350;
        virtual_y_position = y_distance_from_user + 220;

        g.drawOval((int) virtual_x_position - 5, (int) virtual_y_position -5, 10, 10);
    }

    public void updatePosition() {
        mappable_x_position += velocity*StrictMath.sin(StrictMath.toRadians(direction+180));
        mappable_y_position += velocity*StrictMath.cos(StrictMath.toRadians(direction+180));
        //virtual_x_position += velocity*StrictMath.sin(StrictMath.toRadians(direction+180));
        //virtual_y_position += velocity*StrictMath.cos(StrictMath.toRadians(direction+180));
    }

    public boolean isOutOfBounds() {
        return Math.sqrt(Math.pow(source_position_x - this.mappable_x_position,2) + Math.pow(source_position_y - this.mappable_y_position,2)) > 400;
    }

    public boolean isOutOfScreenBounds() {
        return virtual_x_position > SpaceWarViewController.SCREEN_WIDTH || virtual_x_position < 0 || virtual_y_position < 0 || virtual_y_position > SpaceWarViewController.SCREEN_HEIGHT;
        //return Math.abs(mappable_x_position - source.mappable_x_position) >= 430 || Math.abs(mappable_y_position - source.mappable_y_position) >= 215;
    }

    public double getDamage() {
        return damage;
    }

    public double getXPosition() {
        return virtual_x_position;
    }

    public double getYPosition() {
        return virtual_y_position;
    }

    public double getVirtualXPosition() {
        return virtual_x_position;
    }

    public double getVirtualYPosition() {
        return virtual_y_position;
    }
}
