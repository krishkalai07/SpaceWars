import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
public class Bullet implements Mappable{
    private double x_position;
    private double y_position;
    private double velocity;
    private double direction;

    private double damage;

    public Bullet(double direction, int velocity, int x_position, int y_position, double damage) {
        this.direction = direction;
        this.velocity = velocity;
        this.x_position = x_position;
        this.y_position = y_position;
        this.damage = damage;
    }

    public void draw(Graphics g) {
        g.drawOval((int)x_position-5, (int)y_position-5, 10, 10);
    }

    public void updatePosition() {
        System.out.println(direction);
        System.out.println(StrictMath.sin(StrictMath.toRadians(direction+180)) + ", " + StrictMath.cos(StrictMath.toRadians(direction+180)));
        System.out.println();
        x_position += velocity*StrictMath.sin(StrictMath.toRadians(direction+180));
        y_position += velocity*StrictMath.cos(StrictMath.toRadians(direction+180));
        System.out.println(x_position + ", " + y_position);
    }

    public boolean isOutOfScreenBounds() {
        return x_position > MainView.SCREEN_WIDTH || x_position < 0 || y_position < 0 || y_position > MainView.SCREEN_HEIGHT;
    }

    public double getDamage() {
        return damage;
    }
}
