import javax.swing.*;
/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 */
public class Main {
    public static void main(String[] argv) {
        /*
        int origin_x = 150;
        int origin_y = 125;

        int forward_theta = 45;

        int radius = 20;

        double circle_point_x = (origin_x + radius * StrictMath.sin(forward_theta));
        double circle_point_y = (origin_y + radius * StrictMath.cos(forward_theta));

        System.out.println(circle_point_x + " " + circle_point_y);
        */

        JFrame window = new JFrame("Space Wars");
        SpaceWarViewController content = new SpaceWarViewController();
        window.setContentPane(content);
        window.setSize(700, 430);
        window.setLocation(100, 100);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);

    }
}
