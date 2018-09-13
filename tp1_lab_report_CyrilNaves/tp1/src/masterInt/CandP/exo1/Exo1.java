package masterInt.CandP.exo1;

public class Exo1 extends Thread {

	String message;

	/**
	 * Constructor
	 * 
	 * @param _message
	 *            The message to print
	 */
	public Exo1(String inpmessage) {
		message = inpmessage;
	}

	@Override
	public void run() {
		// Print the message 10000 times
		for (int i = 0; i < 10000; i++) {
			System.out.println(message + "no:" + (i + 1));
		}
	}

	/**
	 * Main method to print concurrent messages through two different threads
	 * @param args
	 */
	public static void main(String[] args) {
		Exo1 exo1 = new Exo1("Thread1Printing");
		exo1.start();
		Exo1 exo2 = new Exo1("Thread2Printing");
		exo2.start();
		for (int i = 0; i < 10000; i++) {
			System.out.println("main" + "no:" + (i + 1));
		}

	}

}
