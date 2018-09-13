package org.m1int.mpi.ring;

import fr.unice.mpi4lectures.MPIMessage;
import fr.unice.mpi4lectures.MPIProcess;

public class Ring extends MPIProcess {
	/**
	 * This is entry point in the JVM.
	 */
	public static void main(String[] args)

	{
		// This creates 6 processors of type
		// create 6 instances of your processor
		MPIProcess.createProcessors(Ring.class, 6);
	}

	/**
	 * The implementation of this method is the code the processor will execute.
	 */
	@Override
	public void main() {
		// get the rank of this processor
		int rank = Rank();
		System.out.println("Starting processor with rank " + rank);

		// this is the rendez-vous point for all processors
		Barrier(0, 1, 2, 3, 4, 5);

		// get the number of processor in the MPI system
		// remember you created 6 processors...
		int size = Size();

		// if this processor is the first one
		if (rank == 0) {
			// it has to create the message
			// and send it to the first processor
			Send("catch me!".getBytes(), 9, MPIMessage.TYPE.CHAR, 1, "");

			byte[] buffer = new byte[9];
			Recv(buffer, 9, MPIMessage.TYPE.CHAR, size - 1, "");// blocked for responce from other
			System.out.println("message is back home '" + new String(buffer) + "'");
		} else {
			// this is not the first processor, whatever its rank
			// it waits for the message coming from the previous processos (rank
			// - 1)
			byte[] buffer = new byte[9];
			Recv(buffer, 9, MPIMessage.TYPE.CHAR, rank - 1, "");
			System.out.println("processor " + rank + " has just received the message '" + new String(buffer) + "'");

			// alter the text by replac a character at a random index by
			// underscore
			buffer[(int) (Math.random() * 9)] = '_';

			// and send the message to the next processor (rank + 1)
			System.out.println("processor " + rank + " forwards the message to " + ((rank + 1) % size));
			Send(buffer, 9, MPIMessage.TYPE.CHAR, (rank + 1) % size, "");
		}
	}
}
