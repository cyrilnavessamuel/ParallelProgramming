package masterInt.CandP.exo4;

//This class implements the bubble sorting algorithm in a thread
public class BubbleSorter implements Runnable {
	private int[] tab;

	public BubbleSorter(int[] _tab) {
		super();
		this.tab = _tab;
	}


	// Run the bubble sort algorithm on tab.
	@Override
	public void run() {
		long start_time = System.currentTimeMillis();
		bubble_srt();
		System.out.println("Bubble Sorting time=" + (System.currentTimeMillis()-start_time) + " ms");
	}

	// Here is the bubble sort algorithm on tab
	public void bubble_srt() {
		int t, n = tab.length;
		for (int i = 0; i < n; i++)
			for (int j = 1; j < (n - i); j++)
				if (tab[j - 1] > tab[j]) {
					t = tab[j - 1];
					tab[j - 1] = tab[j];
					tab[j] = t;
				}
	}

	// This main aims at trying the bubble sorting algorithm and compare the
	// sequential sorting with the parallel sorting

	public static void main(String[] args) {
		// Set up the table with random values
		int size = 40000;
		int[] tab = new int[size];
		for (int i = 0; i < size; i++) {
			tab[i] = (int) (Math.random() * size);
		}
		// Do the bubble sort ...
		long start_time = System.currentTimeMillis();
		int t, n = tab.length;
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {
				if (tab[j - 1] > tab[j]) {
					t = tab[j - 1];
					tab[j - 1] = tab[j];
					tab[j] = t;
				}
			}
		}
		System.out.println("Bubble Sorting time=" + (System.currentTimeMillis() - start_time) + " ms");
	}

}
