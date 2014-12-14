import java.util.ArrayList;
import java.util.Collection;

public class TestResults {
	int test;
	int lag;
	long time;
	ArrayList<TimePoint> path;
	
	// Circle
	float cx = 568;
	float cy = 600;
	float r = 395;
	
	public double accuracy() {
		ArrayList<Double> distances = new ArrayList<Double>();
		for (TimePoint point : path) {
			distances.add(distanceToCircle(point.x, point.y));
		}
		return averageOfList(distances);
	}
	
	private double distanceToCircle(float x, float y) {
		return Math.abs(Math.sqrt((x - cx)*(x - cx) + (y - cy)*(y - cy))-r);
	}
	
	private double averageOfList(Collection<Double> values) {
		Double sum = 0.0;
		for (Double value : values) {
			sum += value;
		}
		return sum.doubleValue() / values.size();
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "TestResults [test="
				+ test
				+ ", lag="
				+ lag
				+ ", time="
				+ time
				+ ", path="
				+ (path != null ? path
						.subList(0, Math.min(path.size(), maxLen)) : null)
				+ "]";
	}
}
