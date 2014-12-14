import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, ParseError {
		Processor processor = new Processor(args[0]);

		for (TestResults test : processor.tests) {
			System.out.println(test);
		}
	}

}
