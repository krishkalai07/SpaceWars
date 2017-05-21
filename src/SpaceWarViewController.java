
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
    public transient static final int DEFAULT_BULLET_VELOCITY = 4;
    public transient static final int SCREEN_WIDTH = 700;
    public transient static final int SCREEN_HEIGHT = 430;
    //public static final int NUMBER_OF_ENEMIES = 5;

    private Vector<Bullet> bullets;
    private Vector<Asteroid> asteroids;
    private UserSpaceship user_spaceship;
    private Vector<Spaceship> enemy_spaceships;

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

        mouse_x = 0;
        mouse_y = 0;

        tick_numeric = 0;
        timer = new Timer(20, this);
        timer.start();

        for (int i = 0; i < 5; i++) {
            EnemySpaceship spaceship = new EnemySpaceship(270, 50, i*90);
            enemy_spaceships.add(spaceship);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        user_spaceship.draw(g, mouse_x, mouse_y);
        user_spaceship.updateLocation();

        for (Bullet bullet:bullets) {
            bullet.draw(g);
            bullet.updatePosition();
        }

        for (Spaceship spaceship: enemy_spaceships) {
            EnemySpaceship enemySpaceship = (EnemySpaceship)spaceship;
            double[] distance = enemySpaceship.distanceFrom(user_spaceship.getMappableX(), user_spaceship.getMappableY());
            if (Math.abs(distance[0]) <= user_spaceship.getVirtualX() && Math.abs(distance[1]) <= user_spaceship.getVirtualY()) {
                enemySpaceship.draw(g, (int) distance[0], (int) distance[1]);
            }
        }
//        for (Asteroid asteroid:asteroids) {
//            asteroid.draw(g);
//        }

        g.setColor(new Color(0xFF0000));
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

        g.setColor(Color.WHITE);
        g.drawString(user_spaceship.mappable_x_position + ", " + user_spaceship.mappable_y_position, 0, 12);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Bullet bullet = new Bullet(user_spaceship.getAngle(), DEFAULT_BULLET_VELOCITY, user_spaceship.getVirtualX(), user_spaceship.getVirtualY(), 20., user_spaceship);
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
        timer.restart();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        timer.stop();
    }

    //
    // MouseMotionListener things here
    //

    @Override
    public void mouseDragged(MouseEvent e){
        if (tick_numeric % 20 == 0) {
            Bullet bullet = new Bullet(user_spaceship.getAngle(), DEFAULT_BULLET_VELOCITY, user_spaceship.getVirtualX(), user_spaceship.getVirtualY(), 20., user_spaceship);
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

        for (Spaceship spaceship: enemy_spaceships) {
            EnemySpaceship enemy_spaceship = (EnemySpaceship)spaceship;
            for (int i = 0; i < bullets.size(); i++) {
                if (Math.abs(enemy_spaceship.getMappableX() - user_spaceship.getMappableX()) <= 200 && Math.abs(enemy_spaceship.getMappableY() - user_spaceship.getMappableY()) <= 200) {
                    if (enemy_spaceship.isPointInsideTriangle((int) bullets.get(i).getVirtualXPosition(), (int) bullets.get(i).getVirtualYPosition())) {
                        enemy_spaceship.updateHealth(bullets.get(i).getDamage());
                        bullets.remove(i);
                    }
                }
            }
        }

        for (int i = 0; i < enemy_spaceships.size(); i++) {
            EnemySpaceship enemy_spaceship = (EnemySpaceship)(enemy_spaceships.get(i));
            if (enemy_spaceship.hasLostAllHP()) {
                enemy_spaceships.remove(i);
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

        for (int i = 0; i < bullets.size();) {
            if (bullets.get(i).isOutOfBounds()) {
                bullets.remove(i);
            }
            else {
                i++;
            }
        }

        if (user_spaceship.getMappableX() <= 0 || user_spaceship.getMappableX() >= 1400 ||
            user_spaceship.getMappableY() >= 860 || user_spaceship.getMappableY() <= 0) {
            user_spaceship.reduceHealth(.1);
        }

        if (user_spaceship.current_hp <= 0) {
            timer.stop();
        }
    }
}
