import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.math3.stat.inference.TestUtils;

public class Main {

	public static void main(String[] args) throws IOException, ParseError {

		String[] participants = {
		/* ENTER FILENAMES HERE */
		};

		int trials = participants.length * 5;

		double[] time0 = new double[trials];
		double[] time50 = new double[trials];
		double[] time100 = new double[trials];
		double[] time250 = new double[trials];
		double[] time500 = new double[trials];

		int time0i, time50i, time100i, time250i, time500i;
		time0i = time50i = time100i = time250i = time500i = 0;

		double[] accuracy0 = new double[trials];
		double[] accuracy50 = new double[trials];
		double[] accuracy100 = new double[trials];
		double[] accuracy250 = new double[trials];
		double[] accuracy500 = new double[trials];

		int accuracy0i, accuracy50i, accuracy100i, accuracy250i, accuracy500i;
		accuracy0i = accuracy50i = accuracy100i = accuracy250i = accuracy500i = 0;

		for (String file : participants) {
			Processor processor = new Processor(file);
			for (TestResults test : processor.tests) {
				if (test.test > 0) {
					switch (test.lag) {
					case 0:
						time0[time0i++] = (double) test.time;
						accuracy0[accuracy0i++] = test.accuracy();
						break;
					case 50:
						time50[time50i++] = (double) test.time;
						accuracy50[accuracy50i++] = test.accuracy();
						break;
					case 100:
						time100[time100i++] = (double) test.time;
						accuracy100[accuracy100i++] = test.accuracy();
						break;
					case 250:
						time250[time250i++] = (double) test.time;
						accuracy250[accuracy250i++] = test.accuracy();
						break;
					case 500:
						time500[time500i++] = (double) test.time;
						accuracy500[accuracy500i++] = test.accuracy();
						break;
					}
				}
			}
		}

		ArrayList<double[]> times = new ArrayList<double[]>();
		times.add(time0);
		times.add(time50);
		times.add(time100);
		times.add(time250);
		times.add(time500);

		ArrayList<double[]> accuracies = new ArrayList<double[]>();
		accuracies.add(accuracy0);
		accuracies.add(accuracy50);
		accuracies.add(accuracy100);
		accuracies.add(accuracy250);
		accuracies.add(accuracy500);

		double timeFStatistic = TestUtils.oneWayAnovaFValue(times);
		double timePValue = TestUtils.oneWayAnovaPValue(times);

		double accuracyFStatistic = TestUtils.oneWayAnovaFValue(accuracies);
		double accuracyPValue = TestUtils.oneWayAnovaPValue(accuracies);

		System.out.println("TIME:");
		System.out.println("F = " + timeFStatistic);
		System.out.println("p = " + timePValue);

		System.out.println();

		System.out.println("ACCURACY:");
		System.out.println("F = " + accuracyFStatistic);
		System.out.println("p = " + accuracyPValue);

	}

}
