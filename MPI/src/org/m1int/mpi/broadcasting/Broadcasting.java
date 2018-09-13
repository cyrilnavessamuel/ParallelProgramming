package org.m1int.mpi.broadcasting;

import fr.unice.mpi4lectures.MPIMessage;
import fr.unice.mpi4lectures.MPIProcess;
import java.util.*;

/**
 * 
 * In Broadcasting Implementation a simple producer consumer scenario is
 * replicated with one of the processes sending the message and the other one
 * receiving it from the corresponding sender.
 *
 */
public class Broadcasting extends MPIProcess {

	static String message;

	public static void main(String[] args) {

		System.out.println("Enter message");
		message = new Scanner(System.in).nextLine();
		MPIProcess.createProcessors(Broadcasting.class, 4);
	}

	/**
	 * 
	 */
	@Override
	public void main() {

		int rank = Rank();
		int size = Size();
		Barrier(0, 1, 2, 3);
		if (rank == 0) {
			for (int processor = 1; processor < size; processor++) {
				byte[] bytesBroadCast = message.getBytes();
				Send(bytesBroadCast, bytesBroadCast.length, MPIMessage.TYPE.CHAR, processor, "");
				System.out.println("broadcast message sent to processor " + processor);
			}
		}

		else {
			byte[] receivedMessage = message.getBytes();
			Recv(receivedMessage, receivedMessage.length, MPIMessage.TYPE.CHAR, 0, "");
			System.out.println("Processor " + rank + " received " + new String(receivedMessage));
		}

	}

}
