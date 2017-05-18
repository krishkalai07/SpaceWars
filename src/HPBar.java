import java.awt.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/17/17
 * VERSION: 1
 */
public class HPBar {
    double full_health;
    double partial_health;

    HPBar(int partial_health, int full_health) {
        this.partial_health = partial_health;
        this.full_health = full_health;
    }

    public void setPartialHealth(double partial_health) {
        this.partial_health = partial_health;
    }

    public void draw(Graphics g, int item_location_x, int item_location_y) {
        g.drawRect(item_location_x, item_location_y, 60, 5);
        g.fillRect(item_location_x, item_location_y, (int)(partial_health/full_health * 60), 5);
    }
}
