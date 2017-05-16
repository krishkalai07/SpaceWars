/**
 * AUTHOR:  Krish Kalai
 * Date:    5/5/17
 * VERSION: 1
 */
public class Coordinate2D {
    private int latitude;
    private int longitude;

    Coordinate2D(int lat, int lon) {
        if (lat < 0 || lon < 0) {
        //  throw new ValueBeyondRange();
        }
        this.latitude = lat;
        this.longitude = lon;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
}
