package masterInt.CandP.exo2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Writer extends Thread {
	private static int writers = 0; // number of writers

	private int number;
	private Memory memory;
	Semaphore input;
	FileInputStream fis;

	public Writer(Memory memory,Semaphore s,FileInputStream fis) {
		this.memory = memory;
		this.number = Writer.writers++;
		this.input = s;
		this.fis= fis;
	}

	public void run() {
		while (true) {
			
			try {
				input.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			byte[] bytes = readFromInput();
			
			
			final int DELAY = 5000;
			try {
				Thread.sleep((int) (Math.random() * DELAY));
			} catch (InterruptedException e) {
			}
			
			if(bytes==null)
			{
				System.out.println("Writer " + number + "stop!");
				input.release();
				break;
			}
			this.memory.write(this.number,bytes);
			input.release();
		}
	}
	
	public byte[] readFromInput()
	{

		
        int length = 0;
        //int size=new Random(10).nextInt(1000);
        int size =100;
        byte[] bytes = new byte[size];
        System.out.println("Writer " + number + " read " +size + " byte from input");
        try {
			length = this.fis.read(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(length >0)
        {
        	//System.out.println("Writer " + number + "\r\n\r\n"+new String(bytes));
        	return bytes;
        }
        else
        {
        	System.out.println("Writer " + number + "empty input");
        	return null;
        }
	}
}
