import javax.swing.*;
/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 */
public class Main {
    public static void main(String[] argv) {
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
