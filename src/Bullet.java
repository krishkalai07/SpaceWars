import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
@SuppressWarnings("ALL")
public class Bullet implements Mappable {
    private double mappable_x_position;
    private double mappable_y_position;
    private double velocity;
    private double direction;

    private Spaceship source;
    private Spaceship user;
    private double source_position_x;
    private double source_position_y;

    private double damage;

    public Bullet(double direction, int x_position, int y_position, double damage, Spaceship source) {
        this.source = source;
        this.source_position_x = source.getMappableX();
        this.source_position_y = source.getMappableY();

        this.direction = direction;
        this.velocity = 15;
        this.mappable_x_position = source.mappable_x_position;
        this.mappable_y_position = source.mappable_y_position;
        this.damage = damage;
    }

    public void draw(Graphics g) {
        if (source instanceof UserSpaceship) {
            g.setColor(new Color(0xFFFFFF));
        }
        if (source instanceof EnemySpaceship) {
            g.setColor(new Color(255, 0, 0));
        }

        double x_distance_from_user;
        double y_distance_from_user;

        if (user != null) {
             x_distance_from_user = this.mappable_x_position - user.mappable_x_position;
             y_distance_from_user = this.mappable_y_position - user.mappable_y_position;
        }
        else {
            x_distance_from_user = this.mappable_x_position - source.mappable_x_position;
            y_distance_from_user = this.mappable_y_position - source.mappable_y_position;
        }

        g.drawOval((int) x_distance_from_user + 350 - 5, (int) y_distance_from_user + 220 - 5, 10, 10);
    }

    public void updatePosition() {
        mappable_x_position += velocity*StrictMath.sin(StrictMath.toRadians(direction+180));
        mappable_y_position += velocity*StrictMath.cos(StrictMath.toRadians(direction+180));
    }

    public boolean isOutOfBounds() {
        return Math.sqrt(Math.pow(source_position_x - this.mappable_x_position,2) + Math.pow(source_position_y - this.mappable_y_position,2)) > 400 * velocity;
    }

    public double getDamage() {
        return damage;
    }

    public double getXPosition() {
        return mappable_x_position;
    }

    public double getYPosition() {
        return mappable_y_position;
    }

    public Spaceship getSource() {
        return source;
    }

    public void setUser(Spaceship spaceship) {
        user = spaceship;
    }
}
