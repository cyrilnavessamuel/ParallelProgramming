package masterInt.CandP.exo2;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Semaphore;

public class Reader extends Thread {
	private static int readers = 0; // number of readers

	private int number;
	private Memory memory;
	Semaphore output;

	public Reader(Memory memory,Semaphore s) {
		this.memory = memory;
		this.number = Reader.readers++;
		this.output = s;
	}

	public void run() {
		while (true) {
			final int DELAY = 5000;
			try {
				Thread.sleep((int) (Math.random() * DELAY));
			} catch (InterruptedException e) {
			}
			
			try {
				this.output.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			byte[] bytes = this.memory.read(this.number);
			
			
			writeToOutput(bytes);
			this.output.release();
		}
	}

	public void writeToOutput(byte[] bytes) {

		System.out.println("Reader " + number + " write to output.");
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src\\\\masterInt\\\\CandP\\\\exo2\\\\output.txt", true)));
			out.write(new String(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
