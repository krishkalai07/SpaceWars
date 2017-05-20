
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
public class SpaceWarViewController extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    public static final int DEFAULT_BULLET_VELOCITY = 2;
    public static final int SCREEN_WIDTH = 700;
    public static final int SCREEN_HEIGHT = 430;
    //public static final int NUMBER_OF_ENEMIES = 5;

    private Vector<Bullet> bullets;
    private Vector<Asteroid> asteroids;
    private UserSpaceship user_spaceship;
    private Vector<Spaceship> enemy_spaceships;

    private Map map;

    private int mouse_x;
    private int mouse_y;

    private Timer timer;
    private int tick_numeric;

    SpaceWarViewController () {
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setBackground(new Color(0,0,0));
        addMouseListener(this);
        addMouseMotionListener(this);

        user_spaceship = new UserSpaceship(700, 430);

        bullets = new Vector<>();
        asteroids = new Vector<>();
        enemy_spaceships = new Vector<>();

        map = new Map(SCREEN_WIDTH*2, SCREEN_HEIGHT*2);

        mouse_x = 0;
        mouse_y = 0;

        tick_numeric = 0;
        timer = new Timer(20, this);
        timer.start();

        map.set(user_spaceship, Map.MAP_WIDTH/2, Map.MAP_HEIGHT/2);

        EnemySpaceship spaceship = new EnemySpaceship(270, 50, 90);
        map.set(spaceship, 50, 90);
        enemy_spaceships.add(spaceship);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        user_spaceship.draw(g, mouse_x, mouse_y);
        user_spaceship.updateLocation();
        for (int i = 0; i < bullets.size();) {
            if (bullets.get(i).isOutOfScreenBounds()) {
                bullets.remove(i);
            }
            else {
                i++;
            }
        }

        for (Bullet bullet:bullets) {
            bullet.draw(g);
            bullet.updatePosition();
        }

        for (Spaceship spaceship: enemy_spaceships) {
            EnemySpaceship enemySpaceship = (EnemySpaceship)spaceship;
            System.out.println("enemy: " + enemySpaceship.getMappableX() + ", " + enemySpaceship.getMappableY());
            double[] distance = enemySpaceship.distanceFrom(user_spaceship.getMappableX(), user_spaceship.getMappableY());
            System.out.println(java.util.Arrays.toString(distance));
            if (Math.abs(distance[0]) <= user_spaceship.getVirtualX() && Math.abs(distance[1]) <= user_spaceship.getVirtualY()) {
                enemySpaceship.draw(g, (int) distance[0], (int) distance[1]);
            }
        }
//        for (Asteroid asteroid:asteroids) {
//            asteroid.draw(g);
//        }

        g.setColor(new Color(0xFF0000));
        System.out.println("user: " + user_spaceship.getMappableX() + ", " + user_spaceship.getMappableY());
        if (user_spaceship.getMappableX() <= 430) {
            g.drawRect((int)(340 - user_spaceship.getMappableX()), 0, 1, SCREEN_HEIGHT);
        }
        if (user_spaceship.getMappableY() <= 230) {
            g.drawRect(0, (int)(195 - user_spaceship.getMappableY()), SCREEN_WIDTH, 1);
        }
        if (user_spaceship.getMappableX() >= 970) {
            g.drawRect((int)(1740 - user_spaceship.getMappableX()), 0, 1, SCREEN_HEIGHT);
        }
        if (user_spaceship.getMappableY() >= 630) {
            g.drawRect(0, (int)(1055 - user_spaceship.getMappableY()), SCREEN_WIDTH, 1);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Bullet bullet = new Bullet(user_spaceship.getAngle(), DEFAULT_BULLET_VELOCITY, user_spaceship.getVirtualX(), user_spaceship.getVirtualY(), 20.);
        bullets.add(bullet);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //
    // MouseMotionListener things here
    //

    @Override
    public void mouseDragged(MouseEvent e){
        if (tick_numeric % 20 == 0) {
            Bullet bullet = new Bullet(user_spaceship.getAngle(), DEFAULT_BULLET_VELOCITY, user_spaceship.getVirtualX(), user_spaceship.getVirtualY(), 20.);
            bullets.add(bullet);
        }
        mouse_x = e.getX();
        mouse_y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse_x = e.getX();
        mouse_y = e.getY();
    }

    //
    // ActionListener things here
    //

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        tick_numeric++;

        for(Asteroid asteroid:asteroids) {
            for (int j = 0; j < bullets.size(); j++) {
                if (asteroid.isInsideCircle((int)bullets.get(j).getXPosition(), (int)bullets.get(j).getYPosition())) {
                    asteroid.updateHP(bullets.get(j).getDamage());
                    bullets.remove(j);
                }
            }
        }
        for (int i = 0; i < asteroids.size(); i++) {
            if (asteroids.get(i).hasLostAllHP()) {
                asteroids.remove(i);
            }
        }

        if (tick_numeric % 20 == 0 && asteroids.size() < 10) {
            asteroids.add(new Asteroid(60));
        }

        for (int i = 0; i < enemy_spaceships.size(); i++) {
            EnemySpaceship enemy_spaceship = ((EnemySpaceship)(enemy_spaceships.get(i)));
            enemy_spaceship.updateLocation();
        }

        if (user_spaceship.getMappableX() <= 0 || user_spaceship.getMappableX() >= Map.MAP_WIDTH ||
            user_spaceship.getMappableY() >= Map.MAP_HEIGHT || user_spaceship.getMappableY() <= 0) {
            user_spaceship.reduceHealth(.1);
        }

        if (user_spaceship.current_hp <= 0) {
            timer.stop();
        }
    }
}
