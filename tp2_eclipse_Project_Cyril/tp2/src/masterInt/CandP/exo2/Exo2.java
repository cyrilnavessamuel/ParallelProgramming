package masterInt.CandP.exo2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;

public class Exo2 {
	public static void main(String[] args) {

		final int READERS = 3;
		final int WRITERS = 3;
		Memory memory = new Memory();

		Semaphore inputS = new Semaphore(1);
		Semaphore outputS = new Semaphore(1);
		File file =null;
		FileInputStream in = null;
		
		/*int[] priorityTable = new int[3];
		for(int p : priorityTable)
			p=0;*/
		//System.out.println(new File(".").getAbsolutePath());
		
		try {
			file = new File("src\\masterInt\\CandP\\exo2\\input.txt");
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < READERS; i++) {
			new Reader(memory,outputS).start();
			Thread.currentThread().setPriority(i+1);
		}
		for (int i = 0; i < WRITERS; i++) {
			new Writer(memory,inputS,in).start();
			Thread.currentThread().setPriority(6+i);

		}

	}
}
