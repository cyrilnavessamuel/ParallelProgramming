package org.m1int.mpi.bubblesorter;

import fr.unice.mpi4lectures.MPIProcess;
import fr.unice.mpi4lectures.MPIMessage;

public class BubbleSort_MPI extends MPIProcess {

	/*
	 * In the bubble sort implementation, 4 objects of BubbleSorter and handing it
	 * over to 4 different processors to do sorting.5 MPI processors are created
	 * through main method which receive message, perform sorting and then send
	 * messages to each of the other processor Finally processor 5 performs the
	 * fusion of the sorted elements.
	 * 
	 */
	static BubbleSorter bubbleSorter1;
	static BubbleSorter bubbleSorter2;
	static BubbleSorter bubbleSorter3;
	static BubbleSorter bubbleSorter4;
	static byte counter = 0;

	/**
	 * The implementation of this method is the code the processor will execute.
	 */
	@Override
	public void main() {
		// get the rank of this processor
		int rank = Rank();
		System.out.println("Starting processor with rank " + rank);

		// this is the rendez-vous point for all processors
		Barrier(0, 1, 2, 3, 4);

		// get the number of processor in the MPI system
		// remember you created 4 processors...
		int size = Size();

		// if this processor is the first one
		if (rank == 0) {
			bubbleSorter1.bubble_srt();
			counter++;
			Send(new byte[] { counter }, 1, MPIMessage.TYPE.CHAR, 4, "");
		} else if (rank == 1) {
			bubbleSorter2.bubble_srt();
			counter++;
			Send(new byte[] { counter }, 1, MPIMessage.TYPE.CHAR, 4, "");
		} else if (rank == 2) {
			bubbleSorter3.bubble_srt();
			counter++;
			Send(new byte[] { counter }, 1, MPIMessage.TYPE.CHAR, 4, "");
		} else if (rank == 3) {
			bubbleSorter4.bubble_srt();
			counter++;
			Send(new byte[] { counter }, 1, MPIMessage.TYPE.CHAR, 4, "");
		}

		else if (rank == 4) {

			byte[] counterArray = new byte[1];
			Recv(counterArray, 1, MPIMessage.TYPE.CHAR, 0, "");
			System.out.println("Processor " + counterArray[0] + " finished its task");
			Recv(counterArray, 1, MPIMessage.TYPE.CHAR, 1, "");
			System.out.println("Processor " + counterArray[0] + " finished its task");
			Recv(counterArray, 1, MPIMessage.TYPE.CHAR, 2, "");
			System.out.println("Processor " + counterArray[0] + " finished its task");
			Recv(counterArray, 1, MPIMessage.TYPE.CHAR, 3, "");
			System.out.println("Processor " + counterArray[0] + " finished its task");

			System.out.println("All worker processors finished tasks. Now lets do fusion");

			int[][] resultTab = new int[4][size];
			// get the sorted tabs from each sorter
			resultTab[0] = bubbleSorter1.getTab();
			resultTab[1] = bubbleSorter2.getTab();
			resultTab[2] = bubbleSorter3.getTab();
			resultTab[3] = bubbleSorter4.getTab();
			Fusion fusion = new Fusion(resultTab);
			// do the fusion

			// get the merged sorted array from fusion object
			int[] fusedResult = fusion.fusion();
			System.out.println("fusion is done");

			// print 10 elements of of resultab
			for (int j = 0; j < 10; j++)
				System.out.print("   " + fusedResult[j]);

		}
	}

	/**
	 * Main method where 5 MPI Processors are created
	 * 
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
		// Create the bubble sorters

		bubbleSorter1 = new BubbleSorter(tab[0]);
		bubbleSorter2 = new BubbleSorter(tab[1]);
		bubbleSorter3 = new BubbleSorter(tab[2]);
		bubbleSorter4 = new BubbleSorter(tab[3]);

		// create creating 5 processors
		MPIProcess.createProcessors(BubbleSort_MPI.class, 5);
	}

}
