import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/16/17
 * VERSION: 1
 */
public class EnemySpaceship extends Spaceship {


    EnemySpaceship(double angle, int x_position, int y_position) {
        this.angle = angle;
        this.mappable_x_position = x_position;
        this.mappable_y_position = y_position;
        this.virtual_x_position = -1;
        this.virtual_x_position = -1;
        this.velocity = 2;

        health = new HPBar(400, 400);
    }

    public void draw(Graphics g, int x_distance_from_user, int y_distance_from_user) {
        g.setColor(new Color(0xFF0000));
        virtual_x_position = -x_distance_from_user + 350;
        virtual_y_position = -y_distance_from_user + 220;
        System.out.println("virtual" + virtual_x_position + " " + virtual_y_position);
        Triangle.draw(g, virtual_x_position, virtual_y_position, angle);
        health.draw(g, virtual_x_position, virtual_y_position);
    }

    public void updateLocation() {
        mappable_x_position += velocity*StrictMath.sin(StrictMath.toRadians(angle+180));
        mappable_x_position += velocity*StrictMath.cos(StrictMath.toRadians(angle+180));
        angle += Math.random() * 2 == 1 ? 1 : -1;
    }
}
