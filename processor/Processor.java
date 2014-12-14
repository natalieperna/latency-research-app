import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Processor {

	BufferedReader file;
	ArrayList<TestResults> tests;
	TestResults test;
	Matcher matcher;

	static Pattern testPattern = Pattern.compile("Test #(\\d+)");
	static Pattern lagPattern = Pattern.compile("Lag: (\\d+)ms");
	static Pattern timePattern = Pattern.compile("Time: (\\d+)ms");
	static Pattern pointPattern = Pattern
			.compile("T\\d+ L\\d+ \\(([\\d\\.]+), ([\\d\\.]+)\\) @ (\\d+)");

	static int[] lags = { 0, 50, 100, 250, 500 };

	Processor(String filename) throws IOException, ParseError {
		tests = new ArrayList<TestResults>();

		file = new BufferedReader(new FileReader(filename));

		skipHeader();

		// Iterate through practice and each of the tests
		for (int i = 0; i <= 25; i++) {
			test = new TestResults();

			setTestNumber(i);
			setLag();
			setTime();
			setPath();

			tests.add(test);
		}

		file.close();
	}

	private void skipHeader() throws IOException {
		for (int i = 0; i < 6; i++)
			file.readLine();
	}

	private String readAndMatch(Pattern pattern) throws ParseError {
		try {
			matcher = pattern.matcher(file.readLine());
			matcher.find();
			return matcher.group(1);
		} catch (IOException | IllegalStateException e) {
			throw new ParseError();
		}
	}

	private void setTestNumber(int expected) throws ParseError {
		try {
			test.test = Integer.parseInt(readAndMatch(testPattern));
		} catch (NumberFormatException e) {
			throw new ParseError();
		}
		if (test.test != expected)
			throw new ParseError();
	}

	private void setLag() throws ParseError {
		try {
			test.lag = Integer.parseInt(readAndMatch(lagPattern));
		} catch (NumberFormatException e) {
			throw new ParseError();
		}
		if (Arrays.binarySearch(lags, test.lag) == -1)
			throw new ParseError();
	}

	private void setTime() throws ParseError {
		try {
			test.time = Integer.parseInt(readAndMatch(timePattern));
		} catch (NumberFormatException e) {
			throw new ParseError();
		}
	}

	private void setPath() throws ParseError {
		test.path = new ArrayList<TimePoint>();

		try {
			// Check header
			String line = file.readLine();
			if (!line.equals("Path:"))
				throw new ParseError();

			// Iterate through all points
			line = file.readLine();
			while (!line.isEmpty()) {
				matcher = pointPattern.matcher(line);
				matcher.find();

				TimePoint point = new TimePoint(Float.parseFloat(matcher
						.group(1)), Float.parseFloat(matcher.group(2)),
						Long.parseLong(matcher.group(3)));

				test.path.add(point);

				line = file.readLine();
			}

			// Remove duplicate endpoint
			test.path.remove(test.path.size() - 1);

		} catch (IOException | IllegalStateException e) {
			throw new ParseError();
		}
	}

}