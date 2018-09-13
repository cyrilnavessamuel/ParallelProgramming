package masterInt.CandP.exo1;

public class Exo1 {

	public static void main(String[] args) {
		Philosopher[] philosophers = new Philosopher[5];
		Fork[] forks = new Fork[5];

		System.out.println("begin initializing the resources and users...");

		for (int i = 0; i < 5; i++) {

			forks[i] = new Fork(i);
		}
		
		for (int i = 0; i < 4; i++) {
			philosophers[i] = new Philosopher(i, forks[i], forks[i + 1]);
		}
		philosophers[4] = new Philosopher(4, forks[0], forks[4]);
		
		Thread[] threads = new Thread[10];
		for (int i = 0; i < 5; i++) {
			threads[i] = new Thread(philosophers[i]);
			threads[i].start();
		}
	}

}
