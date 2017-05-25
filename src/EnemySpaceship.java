import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/16/17
 * VERSION: 1
 */
public class EnemySpaceship extends Spaceship {
    private int start_time;
    private boolean turn;

    EnemySpaceship(double angle, int x_position, int y_position) {
        this.angle = angle;
        this.mappable_x_position = x_position;
        this.mappable_y_position = y_position;
        this.virtual_x_position = -1;
        this.virtual_x_position = -1;
        this.velocity = 0;
        this.full_hp = 400;
        this.current_hp = 400;

        this.turn = false;
        this.start_time = -1;

        this.health = new HPBar(400, 400);

        this.immune = false;
        this.immunity_timer = -1;
    }

    public void draw(Graphics g, int x_distance_from_user, int y_distance_from_user) {
        g.setColor(new Color(0xFF0000));

        virtual_x_position = -x_distance_from_user + 350;
        virtual_y_position = -y_distance_from_user + 220;
        int[][] point = Triangle.draw(g, virtual_x_position, virtual_y_position, angle);
        x_coords = point[0];
        y_coords = point[1];

        health.draw(g, virtual_x_position+20, virtual_y_position+20);
    }

    public void updateLocation() {
        mappable_x_position += velocity*StrictMath.sin(StrictMath.toRadians(angle+180));
        mappable_y_position += velocity*StrictMath.cos(StrictMath.toRadians(angle+180));

        if (mappable_x_position < 270 || mappable_x_position > 1130 || mappable_y_position < 115 || mappable_y_position > 785) {
            if (!turn) {
                angle += (int)(Math.random() * 2) == 0 ? Math.random() * 45.0 : Math.random() * -45.0;
                turn = true;
            }
        }
        if (mappable_x_position <= 0 || mappable_x_position >= 1400 || mappable_y_position <= 0 || mappable_y_position >= 860) {
            angle += 90;
            start_time = -1;
        }
    }


    public boolean hasLostAllHP() {
        return current_hp <= 0;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean getTurn() {
        return turn;
    }

    public int getStartTime() {
        return start_time;
    }

    public void setStartTime(int start_time) {
        this.start_time = start_time;
    }
}
