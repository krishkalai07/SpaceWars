
@SuppressWarnings("ALL")
public abstract class Spaceship implements Mappable {
    protected double mappable_x_position;
    protected double mappable_y_position;
    protected double angle;
    protected double velocity;

    protected HPBar health;

    protected double full_hp;
    protected double current_hp;

    protected boolean immune;
    protected int immunity_timer;

    public double[] distanceFrom(double x, double y) {
        double[] ret_arr = new double[2];
        ret_arr[0] = x - this.mappable_x_position;
        ret_arr[1] = y - this.mappable_y_position;
        return ret_arr;
    }

    public boolean isPointinsideCircle(int x, int y) {
        return Math.pow(x-mappable_x_position,2) + Math.pow(y-mappable_y_position,2) <= Math.pow(20,2);
    }

    public double getAngle() {
        return angle;
    }

    public double getMappableX() {
        return mappable_x_position;
    }

    public double getMappableY() {
        return mappable_y_position;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void reduceHealth(double hp_lost) {
        if (!immune) {
            current_hp -= hp_lost;
            health.setPartialHealth(current_hp);
        }
        else {
            current_hp -= 0;
            health.setPartialHealth(current_hp);
        }
    }

    public void setImmune(boolean immune) {
        this.immune = immune;
    }
}
