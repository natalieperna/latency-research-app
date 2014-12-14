import java.util.ArrayList;

public class TestResults {
	int test;
	int lag;
	long time;
	ArrayList<TimePoint> path;

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
