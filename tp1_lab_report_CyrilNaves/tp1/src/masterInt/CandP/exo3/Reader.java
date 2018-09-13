package masterInt.CandP.exo3;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class Reader implements Runnable {
	String lines = null;
	PipedOutputStream pipedOutputStream = null;

	public Reader(PipedOutputStream pipedOutputStream) {
		this.pipedOutputStream = pipedOutputStream;
	}

	// Read input stream when available
	// send it in the pipe
	public void run() {
		while (true) {
			System.out.println("Enter the stream");
			Scanner scanner = new Scanner(System.in);
			String total = "";
			String input = scanner.next();
			total += input;
			if (true) {

				try {
					pipedOutputStream.write(total.getBytes());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
	}
}

// }
