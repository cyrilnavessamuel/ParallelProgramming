package masterInt.CandP.exo2;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Memory {
	private int writers; // number of active readers

	private final int MEMORYSIZE = 1000;
	byte[] tab = new byte[MEMORYSIZE];

	private int writeIndex = 0;
	private int readIndex = 0;

	private Semaphore memoryS = new Semaphore(1);

	public Memory() {
		this.writers = 0;
		this.writeIndex = 0;
		this.readIndex = 0;
	}

	public synchronized byte[] read(int number) {
		while (this.writers != 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		int size =100;
		//int size = new Random(10).nextInt(1000);
		byte[] buffer = new byte[size];
		System.out.println("Reader " + number + " read " +size + " from memory");
		// read from memory

		int temp = readIndex;
		if (readIndex + 100 > writeIndex) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		readIndex = (readIndex + 100);

		for (int i = 0; i < 100; i++) {
			buffer[i] = tab[(temp++) % MEMORYSIZE];
		}

		this.notifyAll();
		return buffer;
	}

	public synchronized void write(int number, byte[] bytes) {
		synchronized (this) {
			this.writers++;
		}

		int i = 0;
		while (true) {
			if (i == bytes.length)
				break;

			while (writeIndex - MEMORYSIZE >= readIndex) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			tab[(writeIndex++) % MEMORYSIZE] = bytes[i++];
		}

		synchronized (this) {
			this.writers--;
			if (this.writers == 0) {
				this.notifyAll();
			}
		}
	}
}