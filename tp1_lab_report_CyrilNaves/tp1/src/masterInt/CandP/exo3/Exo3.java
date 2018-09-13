package masterInt.CandP.exo3;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.PipedInputStream;

public class Exo3 {
	public static void main(String[] args) {

		PipedOutputStream pipedOutputStream = new PipedOutputStream();
		PipedInputStream pipedInputStream = new PipedInputStream();
		try {
			pipedOutputStream.connect(pipedInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create the printer and the reader
		Reader reader = new Reader(pipedOutputStream);
		Printer printer = new Printer(pipedInputStream);
		// Start them
		Thread threadReader = new Thread(reader);
		Thread threadWriter = new Thread(printer);

		ExecutorService service = Executors.newFixedThreadPool(2);
		service.execute(threadReader);
		service.execute(threadWriter);
	}
}
