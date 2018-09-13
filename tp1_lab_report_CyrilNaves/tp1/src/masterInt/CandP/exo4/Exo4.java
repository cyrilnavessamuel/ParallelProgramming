package masterInt.CandP.exo4;

public class Exo4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Set up the table with random values
		int nb = 4; // number of tables
		int size = 10000;// Size of each table
		int[][] tab = new int[nb][size];
		for (int i = 0; i < nb; i++) {
			for (int j = 0; j < size; j++) {
				tab[i][j] = (int) (Math.random() * size);
			}
		}
		/*
		 * //Print the original tabs for(int i=0;i<nb;i++) { for(int j=0;j<size;j++)
		 * System.out.print(""+tab[i][j]); System.out.println("\n"); }
		 */

		// Create the bubble sorters
		BubbleSorter bubbleSorterfirst = new BubbleSorter(tab[0]);
		Thread firstthread = new Thread(bubbleSorterfirst);
		BubbleSorter bubbleSortersecond = new BubbleSorter(tab[1]);
		Thread secondthread = new Thread(bubbleSortersecond);
		BubbleSorter bubbleSorterThird = new BubbleSorter(tab[2]);
		Thread thirdthread = new Thread(bubbleSorterThird);
		BubbleSorter bubbleSorterfourth = new BubbleSorter(tab[3]);
		Thread fourththread = new Thread(bubbleSorterfourth);
		// Get the current time
		long start_time = System.currentTimeMillis();
		// Do bubble sorting
		firstthread.start();
		secondthread.start();
		thirdthread.start();
		fourththread.start();
		// Waiting for the Bubble sorters
		try {
			firstthread.join();
			secondthread.join();
			thirdthread.join();
			fourththread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Do the fusion
		// Wait for the fusion to complete

		Fusion fusion = new Fusion(tab);
		Thread threadfusion = new Thread(fusion);
		threadfusion.start();
		try {
			threadfusion.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int[] mergedTab = fusion.getResult();
		// Print the total execution time
		System.out.println("Total time=" + (System.currentTimeMillis() - start_time) + "ms");
		// Print result
		for (int ij = 0; ij < mergedTab.length; ij++) {
			System.out.println("" + mergedTab[ij]);
		}

	}

}
