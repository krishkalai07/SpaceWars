import java.awt.*;

import static java.lang.StrictMath.*;

/**
 * AUTHOR:  Krish Kalai
 * Date:    5/10/17
 * VERSION: 1
 */
@SuppressWarnings("ALL")
public class Triangle {
    public static int[][] draw(Graphics g, int origin_x, int origin_y, double heading) {
        int radius = 20;
        int []triangle_x = {(int)(origin_x + radius * sin(toRadians(heading+180))),
                            (int)(origin_x + radius * sin(toRadians(heading+120+180))),
                            (int)(origin_x + radius * sin(toRadians(heading+240+180)))};
        int []triangle_y = {(int)(origin_y + radius * cos(toRadians(heading+180))),
                            (int)(origin_y + radius * cos(toRadians(heading+120+180))),
                            (int)(origin_y + radius * cos(toRadians(heading+240+180)))};
        g.drawPolygon(triangle_x, triangle_y, 3);

        int []triangle_x2 = {(int)(origin_x + (radius/2) * sin(toRadians(heading))),
                             (int)(origin_x + (radius/2) * sin(toRadians(heading+120))),
                             (int)(origin_x + (radius/2) * sin(toRadians(heading+240)))};
        int []triangle_y2 = {(int)(origin_y + (radius/2) * cos(toRadians(heading))),
                             (int)(origin_y + (radius/2) * cos(toRadians(heading+120))),
                             (int)(origin_y + (radius/2) * cos(toRadians(heading+240)))};

        g.drawPolygon(triangle_x2, triangle_y2, 3);
        return new int[][]{triangle_x, triangle_y};
    }
}
