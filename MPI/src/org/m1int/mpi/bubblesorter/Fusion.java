package org.m1int.mpi.bubblesorter;

//This class implements the fusion operation in a thread
public class Fusion {
	private int tab[][];

	public Fusion(int[][] _tab) {
		this.tab = _tab;
	}

	// Implement the fusion algorithm
	public int[] fusion() {
		int size = 0;
		int tabIndex;

		// Set up the size of the resulting merged table
		for (int i = 0; i < tab.length; i++)
			size += tab[i].length;

		int[] result = new int[size];

		// Set up the indices table
		int[] indices = new int[tab.length];

		for (int i = 0; i < tab.length; i++)
			indices[i] = 0;

		// Fill up the resulting merged table
		for (int i = 0; i < result.length; i++) {
			// Find the first table where there is still values to merge
			tabIndex = -1;
			for (int j = 0; j < tab.length; j++)
				if (indices[j] < tab[j].length) {
					tabIndex = j;
					break;
				}

			// Compare the value in the current table with the value in the next table
			for (int j = tabIndex + 1; j < tab.length; j++) {
				if ((indices[j] < tab[j].length) && (tab[tabIndex][indices[tabIndex]] > tab[j][indices[j]]))
					tabIndex = j;
			}

			// Add the correct value to the resulting merged table
			result[i] = tab[tabIndex][indices[tabIndex]];

			// Increase the index for the correct table
			indices[tabIndex]++;
		}

		return result;
	}
}
