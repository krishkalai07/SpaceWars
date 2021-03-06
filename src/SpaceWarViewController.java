import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
@SuppressWarnings("ALL")
public class SpaceWarViewController extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    public static final int SCREEN_WIDTH = 700;
    public static final int SCREEN_HEIGHT = 450;
    public static final int MAP_WIDTH = SCREEN_WIDTH * 2;
    public static final int MAP_HEIGHT = SCREEN_HEIGHT * 2;

    public static final int NUMBER_OF_ENEMIES = 5;
    public static final int NUMBER_OF_ASTEROIDS = 10;

    private boolean did_begin;
    private boolean did_lose;
    private boolean did_win;

    private Vector<Bullet> bullets;
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

        did_lose = false;
        did_win = false;

        user_spaceship = new UserSpaceship(SCREEN_WIDTH, SCREEN_HEIGHT);

        bullets = new Vector<>();
        enemy_spaceships = new Vector<>(NUMBER_OF_ENEMIES);

        mouse_x = 0;
        mouse_y = 0;

        tick_numeric = 0;
        timer = new Timer(20, this);

        for (int i = 1; i <= NUMBER_OF_ENEMIES; i++) {
            EnemySpaceship spaceship = new EnemySpaceship(270, 275, i*((MAP_HEIGHT)/(NUMBER_OF_ENEMIES+1)));
            enemy_spaceships.add(spaceship);
        }
    }

    //
    // JPanel Override
    //

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!did_begin) {
            g.setFont(new Font("Arial", Font.PLAIN, 26));
            g.setColor(new Color(255, 255, 255));
            g.drawString("Click to start", SCREEN_WIDTH/2 - 65, 2 * SCREEN_HEIGHT/3);
        }

        if (did_lose) {
            g.setColor(new Color(0x00_00_00));
            g.drawRect(0,0,SCREEN_WIDTH, SCREEN_HEIGHT);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.drawString("You lose", SCREEN_WIDTH/2 - 50, SCREEN_HEIGHT/2 - 26);
            return;
        }

        if (did_win) {
            g.setColor(new Color(0x00_00_00));
            g.drawRect(0,0,SCREEN_WIDTH, SCREEN_HEIGHT);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.drawString("You win", SCREEN_WIDTH/2 - 40, SCREEN_HEIGHT/2 - 26);
            return;
        }

        g.setColor(new Color(0xFF0000));
        // Left
        if (user_spaceship.getMappableX() <= 430) {
            g.drawRect((int) (355 - user_spaceship.getMappableX()), 0, 1, SCREEN_HEIGHT);
        }
        // Top
        if (user_spaceship.getMappableY() <= 215) {
            g.drawRect(0, (int) (225 - user_spaceship.getMappableY()), SCREEN_WIDTH, 1);
        }
        // Right
        if (user_spaceship.getMappableX() >= SCREEN_WIDTH - 430) {
            g.drawRect((int) (1755 - user_spaceship.getMappableX()), 0, 1, SCREEN_HEIGHT);
        }
        // Bottom
        if (user_spaceship.getMappableY() >= MAP_HEIGHT - 215) {
            g.drawRect(0, (int) (1125 - user_spaceship.getMappableY()), SCREEN_WIDTH, 1);
        }

        g.setColor(new Color(128, 128, 128, 128));
        for (int i = 1; i < SCREEN_WIDTH / 25; i++) {
            g.drawRect((int) (((i + 7) * 50) - user_spaceship.getMappableX()+5), 0, 1, SCREEN_HEIGHT);
        }
        for (int i = 1; i < SCREEN_HEIGHT / 25 + 1; i++) {
            g.drawRect(0, (int) (((i + 4) * 50) - user_spaceship.getMappableY()), SCREEN_WIDTH, 1);
        }

        user_spaceship.draw(g, mouse_x, mouse_y);
        if (mouse_x != 450 && mouse_y != 225) {
            user_spaceship.updateLocation();
        }

        for (Bullet bullet: bullets) {
            bullet.draw(g);
            bullet.updatePosition();
        }

        for (Spaceship spaceship: enemy_spaceships) {
            EnemySpaceship enemySpaceship = (EnemySpaceship) spaceship;
            double[] distance = enemySpaceship.distanceFrom(user_spaceship.getMappableX(), user_spaceship.getMappableY());
            if (Math.abs(distance[0]) <= SCREEN_WIDTH/2 && Math.abs(distance[1]) <= SCREEN_HEIGHT/2) {
                enemySpaceship.draw(g, (int) distance[0], (int) distance[1]);
            }
        }
    }

    //
    // MouseListener impl.
    //

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!did_begin) {
            timer.start();
            did_begin = true;
            return;
        }
        Bullet bullet = new Bullet(user_spaceship.getAngle(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 40., user_spaceship);
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
    // MouseMotionListener Impl.
    //

    @Override
    public void mouseDragged(MouseEvent e){
        if (tick_numeric % 5 == 0) {
            Bullet bullet = new Bullet(user_spaceship.getAngle(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 20., user_spaceship);
            bullets.add(bullet);
        }
        mouse_x = e.getX();
        mouse_y = e.getY();
        double distance_from_origin = Math.sqrt(Math.pow(mouse_x - SCREEN_WIDTH/2, 2) + Math.pow(mouse_y - SCREEN_HEIGHT/2,2));
        double t = (distance_from_origin/(SCREEN_HEIGHT/2));
        t = t < 1 ? t : 1;
        user_spaceship.setVelocity(t*10);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse_x = e.getX();
        mouse_y = e.getY();
        double distance_from_origin = Math.sqrt(Math.pow(mouse_x - SCREEN_WIDTH/2, 2) + Math.pow(mouse_y - SCREEN_HEIGHT/2,2));
        double t = (distance_from_origin/(SCREEN_HEIGHT/2));
        t = t < 1 ? t : 1;
        user_spaceship.setVelocity(t*10);
        //((b-a)(x-min))/(max-min) + a
    }

    //
    // ActionListener Impl.
    //

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        tick_numeric++;

        // Winning condition
        if (enemy_spaceships.size() == 0) {
            this.did_win = true;
            repaint();
            timer.stop();
        }

        // Losing condition
        if (user_spaceship.isDead()) {
            this.did_lose = true;
            repaint();
            timer.stop();
        }

        // Spaceship's immunity timer (for spaceship contact)
        if (user_spaceship.immunity_timer != -1) {
            if ((tick_numeric - user_spaceship.immunity_timer) % 10 == 0) {
                user_spaceship.immunity_timer = -1;
                user_spaceship.setImmune(false);
            }
        }

        // All enemy immunity timer
        for (Spaceship spaceship: enemy_spaceships) {
            if ((tick_numeric - spaceship.immunity_timer) % 10 == 0) {
                spaceship.immunity_timer = -1;
                spaceship.setImmune(false);
            }
        }

        // Enemy shoots
        if (tick_numeric % 2 == 0) {
            for (Spaceship spaceship : enemy_spaceships) {
                double delta_x = spaceship.getMappableX() - user_spaceship.getMappableX();
                double delta_y = user_spaceship.getMappableY() - spaceship.getMappableY();
                double theta_radians = StrictMath.atan2(delta_y, delta_x);
                double angle_to_user = Math.toDegrees(theta_radians);

                if (angle_to_user < 0) {
                    angle_to_user += 360;
                }

                angle_to_user += 90;

                if (angle_to_user + 45 > spaceship.getAngle() % 360 && angle_to_user - 45 < spaceship.getAngle() % 360) {
                    int deviance = (int)(Math.random() * 3) - 2;
                    Bullet bullet = new Bullet(spaceship.getAngle() + deviance, (int)spaceship.getMappableX(), (int)spaceship.getMappableY(), 1., spaceship);
                    bullet.setUser(user_spaceship);
                    bullets.add(bullet);
                }
            }
        }

        // Enemy bullets hit user
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet.getSource() instanceof EnemySpaceship) {
                if (user_spaceship.isPointinsideCircle((int)bullet.getXPosition(), (int)bullet.getYPosition())) {
                    bullets.remove(i);
                    user_spaceship.reduceHealth(bullet.getDamage());
                }
            }
        }

        // User shoots enemy
        for (Spaceship spaceship: enemy_spaceships) {
            EnemySpaceship enemy_spaceship = (EnemySpaceship)spaceship;
            for (int i = 0; i < bullets.size(); i++) {
                if (Math.abs(enemy_spaceship.getMappableX() - user_spaceship.getMappableX()) <= 200 && Math.abs(enemy_spaceship.getMappableY() - user_spaceship.getMappableY()) <= 200) {
                    if (enemy_spaceship.isPointinsideCircle((int)bullets.get(i).getXPosition(), (int)bullets.get(i).getYPosition())) {
                        if (bullets.get(i).getSource() != spaceship) {
                            enemy_spaceship.reduceHealth(bullets.get(i).getDamage());
                            bullets.remove(i);
                        }
                    }
                }
            }
        }

        // Enemy dies
        for (int i = 0; i < enemy_spaceships.size(); i++) {
            EnemySpaceship enemy_spaceship = (EnemySpaceship)(enemy_spaceships.get(i));
            if (enemy_spaceship.hasLostAllHP()) {
                enemy_spaceships.remove(i);
            }
        }

        // Enemy Movement
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

        // Bullets out of arena bounds
        for (int i = 0; i < bullets.size();) {
            if(bullets.get(i).getXPosition() <= 0 || bullets.get(i).getXPosition() >= MAP_WIDTH ||
               bullets.get(i).getYPosition() >= MAP_HEIGHT || bullets.get(i).getYPosition() <= 0) {
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

        // Enemy rams into user (or vice versa)
        for (Spaceship spaceship: enemy_spaceships) {
            if (spaceship.isPointinsideCircle((int)user_spaceship.getMappableX(), (int)user_spaceship.getMappableY())) {
                spaceship.reduceHealth(400/3);
                spaceship.setImmune(true);
                spaceship.immunity_timer = tick_numeric;

                user_spaceship.reduceHealth(100/3);
                user_spaceship.setImmune(true);
                user_spaceship.immunity_timer = tick_numeric;
            }
        }

        // User out of bounds damage
        if (user_spaceship.getMappableX() <= 0 || user_spaceship.getMappableX() >= MAP_WIDTH ||
            user_spaceship.getMappableY() >= MAP_HEIGHT || user_spaceship.getMappableY() <= 0) {
            user_spaceship.reduceHealth(0.5);
        }
    }
}
