import java.util.Vector;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/15/17
 * VERSION: 1
 */
public class Map {
    public final static int MAP_WIDTH = SpaceWarViewController.SCREEN_WIDTH * 2;
    public final static int MAP_HEIGHT = SpaceWarViewController.SCREEN_HEIGHT * 2;

    private Mappable[][] item_location;
    private int num_rows;
    private int num_cols;
    public Map(int width, int height) {
        item_location = new Mappable[width][height];

        this.num_rows = width;
        this.num_cols = height;
    }

    public void set(Mappable item, int row, int col) {
        item_location[row][col] = item;
    }

    public void remove(int row, int col) {
        item_location[row][col] = null;
    }
}
