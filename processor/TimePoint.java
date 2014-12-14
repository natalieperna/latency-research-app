/**
 * Represents a drawing coordinate (x, y) and the time, t, that it was collected
 */
class TimePoint {
    float x, y; // Coordinate
    long t; // Time at that position

    TimePoint(float x, float y, long t) {
        this.x = x;
        this.y = y;
        this.t = t;
    }

	@Override
	public String toString() {
		return "TimePoint [x=" + x + ", y=" + y + ", t=" + t + "]";
	}
}
