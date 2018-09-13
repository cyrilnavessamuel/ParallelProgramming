package org.m1int.mpi.matrixmultiplication;

import fr.unice.mpi4lectures.MPIMessage;
import fr.unice.mpi4lectures.MPIProcess;
import java.util.*;

/**
 * 
 * MatrixMultilplication Class consists of processes equal to the no of rows of
 * final matrix computed. Finally the resultant matrix is displayed after each
 * of the sub processes are completed In this case we handle [3 *4 ] [4*3]
 * matrix which results in [3*3] matrix where by the the process for row 1, 2,3
 * are computed and finally displayed in the result with the sending messages
 * all received from the previous processes.
 * 
 *
 */
public class MatrixMutiplication extends MPIProcess {

	static int M = 3, N = 4, P = 3;
	static int[][] matrix1, matrix2, finalMatrix;
	static int processors;

	public static void main(String[] args) {

		matrix1 = new int[M][N];
		matrix2 = new int[N][P];
		Scanner input = new Scanner(System.in);
		finalMatrix = new int[M][P];
		System.out.println("Input of Matrix 1 with:" + M + " rows and " + N + " columns");
		for (int matrix1Row = 0; matrix1Row < M; matrix1Row++) {
			for (int matrix1Col = 0; matrix1Col < N; matrix1Col++) {
				System.out.println("MATRIX 1:: Enter the values for ROW " + matrix1Row + " COLUMN " + matrix1Col);
				matrix1[matrix1Row][matrix1Col] = Integer.parseInt(input.nextLine());
			}
		}

		System.out.println("Input of MATRIX 2 with" + N + " rows " + P + " columns");
		for (int matrix2Row = 0; matrix2Row < N; matrix2Row++) {
			for (int matrix2Col = 0; matrix2Col < P; matrix2Col++) {
				System.out.println("MATRIX 2 :: Enter the values for ROW " + matrix2Row + " COLUMN " + matrix2Col);
				matrix2[matrix2Row][matrix2Col] = Integer.parseInt(input.nextLine());
			}
		}

		processors = M + 1;
		MPIProcess.createProcessors(MatrixMutiplication.class, processors);
	}

	/**
	 * 
	 * @param rowOfFirstMatrix
	 * @param rowNumOfResultantMatrix
	 */
	static void multiply(int[] rowOfFirstMatrix, int rowNumOfResultantMatrix) {

		int sum = 0;
		int columnCount = 0;

		for (int col = 0; col < 3; col++) {
			for (int row = 0; row < matrix2.length; row++)// row<2
			{
				sum = sum + matrix1[rowNumOfResultantMatrix][columnCount] * matrix2[row][col];
				columnCount++;
			}
			finalMatrix[rowNumOfResultantMatrix][col] = sum;
			columnCount = 0;
			sum = 0;
		}

	}

	/**
	 * The implementation of this method is the code the processor will execute.
	 */
	@Override
	public void main() {
		// get the rank of this processor
		int rank = Rank();
		System.out.println("Starting processor with rank " + rank);

		// Creation of barrier Renedez-vous points
		int[] barriers = new int[processors];
		for (int i = 0; i < processors; i++) {
			barriers[i] = i;
		}
		Barrier(barriers);

		// if this processor is the first one
		if (rank == 0) // processor 0 is master processor
		{
			System.out.println("processor " + rank);

			// rowCount is also my processor count
			multiply(matrix1[0], 0);
			Send(new byte[] { (byte) (1) }, 1, MPIMessage.TYPE.CHAR, 3, "");
		}
		if (rank == 1) {
			System.out.println("processor " + rank);
			multiply(matrix1[1], 1);
			Send(new byte[] { (byte) (2) }, 1, MPIMessage.TYPE.CHAR, 3, "");
		}
		if (rank == 2) {
			System.out.println("processor " + rank);
			multiply(matrix1[2], 2);
			Send(new byte[] { (byte) (2) }, 1, MPIMessage.TYPE.CHAR, 3, "");
		}
		if (rank == 3) {
			byte[] buffer = new byte[1];
			Recv(buffer, 1, MPIMessage.TYPE.CHAR, 0, "");
			System.out.println("processor " + buffer[0] + " finished its task");
			Recv(buffer, 1, MPIMessage.TYPE.CHAR, 1, "");
			System.out.println("processor " + buffer[0] + " finished its task");
			Recv(buffer, 1, MPIMessage.TYPE.CHAR, 2, "");
			System.out.println("processor " + buffer[0] + " finished its task");
			System.out.println("Message received");
			System.out.println("Printing resultant matrix ");
			for (int row = 0; row < finalMatrix.length; row++) {
				for (int col = 0; col < finalMatrix[row].length; col++)

				{
					System.out.print(finalMatrix[row][col] + "\t");
				}
				System.out.println();
			}
		}

	}
}
