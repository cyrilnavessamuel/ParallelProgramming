package org.m1int.mpi.bubblesorter;

/**
 * 
 * Class Implementing basic bubble sorting algorithm
 *
 */
//This class implements the bubble sorting algorithm
public class BubbleSorter // ...
{
	private int[] tab;

	public BubbleSorter(int[] _tab) {
		super();
		this.tab = _tab;
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

	public int[] getTab() {
		return this.tab;
	}
}
