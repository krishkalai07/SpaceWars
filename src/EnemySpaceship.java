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
        this.velocity = 1.5;
        this.full_hp = 400;
        this.current_hp = 400;

        health = new HPBar(400, 400);
    }

    public void draw(Graphics g, int x_distance_from_user, int y_distance_from_user) {
        g.setColor(new Color(0xFF0000));

        virtual_x_position = -x_distance_from_user + 350;
        virtual_y_position = -y_distance_from_user + 220;
        int[][] point = Triangle.draw(g, virtual_x_position, virtual_y_position, angle);
        x_coords = point[0];
        y_coords = point[1];

        health.draw(g, virtual_x_position, virtual_y_position);
    }

    public void updateLocation() {
        mappable_x_position += velocity*StrictMath.sin(StrictMath.toRadians(angle+180));
        mappable_x_position += velocity*StrictMath.cos(StrictMath.toRadians(angle+180));
        switch ((int)(Math.random() * 3)) {
            case 0:
                angle += 10;
                break;
            case 1:
                angle -= 10;
                break;
        }
    }

    public void updateHealth(double damage_dealt) {
        current_hp -= damage_dealt;
        health.setPartialHealth(current_hp);
    }

    public boolean hasLostAllHP() {
        return current_hp <= 0;
    }
}
