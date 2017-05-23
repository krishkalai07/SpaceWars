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
    public static final int SCREEN_WIDTH = 700;
    public static final int SCREEN_HEIGHT = 450;
    public static final int MAP_WIDTH = SCREEN_WIDTH * 2;
    public static final int MAP_HEIGHT = SCREEN_HEIGHT * 2;

    public static final int NUMBER_OF_ENEMIES = 5;
    public static final int NUMBER_OF_ASTEROIDS = 10;

    private boolean did_begin;

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

        did_begin = false;

        user_spaceship = new UserSpaceship(SCREEN_WIDTH, SCREEN_HEIGHT);

        bullets = new Vector<>();
        asteroids = new Vector<>(NUMBER_OF_ASTEROIDS);
        enemy_spaceships = new Vector<>(NUMBER_OF_ENEMIES);

        mouse_x = 0;
        mouse_y = 0;

        tick_numeric = 0;
        timer = new Timer(20, this);
        timer.start();

        for (int i = 1; i <= NUMBER_OF_ENEMIES; i++) {
            EnemySpaceship spaceship = new EnemySpaceship(270, 400, i*((MAP_HEIGHT)/(NUMBER_OF_ENEMIES+1)));
            //EnemySpaceship spaceship = new EnemySpaceship(90, 1400, 860);
            enemy_spaceships.add(spaceship);
        }
        //enemy_spaceships.add(new EnemySpaceship(0, 680, 180));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        user_spaceship.draw(g, mouse_x, mouse_y);
        user_spaceship.updateLocation();

        for (Bullet bullet : bullets) {
            bullet.draw(g);
            bullet.updatePosition();
        }

        for (Spaceship spaceship : enemy_spaceships) {
            EnemySpaceship enemySpaceship = (EnemySpaceship) spaceship;
            double[] distance = enemySpaceship.distanceFrom(user_spaceship.getMappableX(), user_spaceship.getMappableY());
            if (Math.abs(distance[0]) <= user_spaceship.getVirtualX() && Math.abs(distance[1]) <= user_spaceship.getVirtualY()) {
                enemySpaceship.draw(g, (int) distance[0], (int) distance[1]);
            }
        }

        for (Asteroid asteroid : asteroids) {
            //asteroid.draw(g, asteroid.getMappableXPosition() - user_spaceship.getMappableX(), asteroid.getMappableYPosition() - user_spaceship.getMappableY());
        }

        g.setColor(new Color(0xFF0000));
        if (user_spaceship.getMappableX() <= 430) {
            g.drawRect((int) (355 - user_spaceship.getMappableX()), 0, 1, SCREEN_HEIGHT);
        }
        if (user_spaceship.getMappableY() <= 215) {
            g.drawRect(0, (int) (225 - user_spaceship.getMappableY()), SCREEN_WIDTH, 1);
        }
        if (user_spaceship.getMappableX() >= SCREEN_WIDTH - 430) {
            g.drawRect((int) (1750 - user_spaceship.getMappableX()), 0, 1, SCREEN_HEIGHT);
        }
        if (user_spaceship.getMappableY() >= MAP_HEIGHT - 215) {
            g.drawRect(0, (int) (1090 - user_spaceship.getMappableY()), SCREEN_WIDTH, 1);
        }

        g.setColor(new Color(128, 128, 128, 128));
        for (int i = 1; i < SCREEN_WIDTH / 25; i++) {
            g.drawRect((int) (((i + 7) * 50) - user_spaceship.getMappableX()+5), 0, 1, SCREEN_HEIGHT);
        }
        for (int i = 1; i < SCREEN_HEIGHT / 25; i++) {
            g.drawRect(0, (int) (((i + 4) * 50) - user_spaceship.getMappableY()), SCREEN_WIDTH, 1);
        }


        g.setColor(Color.WHITE);
        g.drawString(user_spaceship.mappable_x_position + ", " + user_spaceship.mappable_y_position, 0, 12);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Bullet bullet = new Bullet(user_spaceship.getAngle(), user_spaceship.getVirtualX(), user_spaceship.getVirtualY(), 20., user_spaceship);
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
            Bullet bullet = new Bullet(user_spaceship.getAngle(), user_spaceship.getVirtualX(), user_spaceship.getVirtualY(), 20., user_spaceship);
            bullets.add(bullet);
        }
        mouse_x = e.getX();
        mouse_y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse_x = e.getX();
        mouse_y = e.getY();
        double distance_from_origin = Math.sqrt(Math.pow(mouse_x - user_spaceship.getVirtualX(), 2) + Math.pow(mouse_y - user_spaceship.getVirtualY(),2));
        double t = (4*(distance_from_origin/(SCREEN_HEIGHT/2)));
        user_spaceship.setVelocity(t);
        //((b-a)(x-min))/(max-min) + a
    }

    //
    // ActionListener things here
    //

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        tick_numeric++;
        //System.out.println(tick_numeric);

        if (!did_begin) {

        }

        for (Asteroid asteroid: asteroids) {
            for (int j = 0; j < bullets.size(); j++) {
                if (asteroid.isInsideCircle((int)bullets.get(j).getVirtualXPosition(), (int)bullets.get(j).getVirtualYPosition())) {
                    asteroid.updateHP(bullets.get(j).getDamage());
                    bullets.remove(j);
                }
            }
        }

        for (Spaceship spaceship: enemy_spaceships) {
            EnemySpaceship enemy_spaceship = (EnemySpaceship)spaceship;
            for (int i = 0; i < bullets.size(); i++) {
                if (Math.abs(enemy_spaceship.getMappableX() - user_spaceship.getMappableX()) <= 200 && Math.abs(enemy_spaceship.getMappableY() - user_spaceship.getMappableY()) <= 200) {
                    if (enemy_spaceship.isPointinsideCircle((int)bullets.get(i).getVirtualXPosition(), (int)bullets.get(i).getVirtualYPosition())) {
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

        if (tick_numeric % 20 == 0 && asteroids.size() < NUMBER_OF_ASTEROIDS) {
            asteroids.add(new Asteroid((int)(Math.random() * 60) + 20));
        }

        for (Spaceship enemy_spaceship1 : enemy_spaceships) {
            EnemySpaceship enemy_spaceship = ((EnemySpaceship) enemy_spaceship1);
            enemy_spaceship.updateLocation();
            if (enemy_spaceship.getTurn()) {
                if ((tick_numeric - enemy_spaceship.getStartTime()) % 100 == 0) {
                    enemy_spaceship.setTurn(false);
                    enemy_spaceship.setStartTime(-1);
                }
                if (enemy_spaceship.getStartTime() != -1) {
                    enemy_spaceship.setStartTime(tick_numeric);
                }
            }
        }

        for (int i = 0; i < bullets.size();) {
            if(bullets.get(i).getXPosition() <= 0 || bullets.get(i).getXPosition() >= 1400 ||
               bullets.get(i).getYPosition() >= 860 || bullets.get(i).getYPosition() <= 0) {
                bullets.remove(i);
                continue;
            }
            if (bullets.get(i).isOutOfBounds()) {
                bullets.remove(i);
            }
            else {
                i++;
            }
        }

        if (user_spaceship.getMappableX() <= 0 || user_spaceship.getMappableX() >= 1400 ||
            user_spaceship.getMappableY() >= 860 || user_spaceship.getMappableY() <= 0) {
            user_spaceship.reduceHealth(0.1);
        }

        if (user_spaceship.current_hp <= 0) {
            timer.stop();
        }
    }

    public UserSpaceship getUserSpaceship() {
        return user_spaceship;
    }
}
