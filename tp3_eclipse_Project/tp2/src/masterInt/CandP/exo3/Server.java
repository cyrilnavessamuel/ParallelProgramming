package masterInt.CandP.exo3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * This a single thread server.
 * Question 2
 * 1)Connect the server by Telnet:
 *   Type the command : telnet 127.0.0.1 55555 in windows cmd
 * 2)Connect the server by browser:
 *   Open any browser and enter 127.0.0.1:55555 as address
 */
public class Server {

	// port number to listen on
	private final int port;

	public Server(int port) {
		this.port = port;
	}

	public void execute() {
		// ...
		System.out.println("begin server initializing...");
		ServerSocket serverSocket = null;
		Socket socket = null;

		
		//initialize the resources
		try {
			serverSocket = new ServerSocket(this.port);
			socket = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line;
		//the index is responsible for counting the number of messages
		int index = 0;

		//read inputstream from socket
		BufferedReader reader = null;
		//write to socket outputstream
		PrintWriter printer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printer = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("reader or printer initialize failed.");
		}
		System.out.println("server initialize successfully...");
		while (true) {
			try {
				//read message from client and return ack to client
				line = reader.readLine();
				System.out.println(String.format("Server: Message received from client: %s", line));
				printer.println(String.format("Message %s : %s", index++, line));
				printer.flush();
				if (line.equals("stop")) {
					//when receive a "stop" message, the server will release all the resources and shutdown
					printer.close();
					reader.close();
					socket.close();
					serverSocket.close();
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("server closed...");
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("usage: java " + Server.class.getCanonicalName() + " <serverPort>");
			System.exit(1);
		}

		// On unix systems you can check that the server is running
		// by executing the following command:
		// lsof -Pi | grep 9999
		try {
			new Server(Integer.parseInt(args[0])).execute();
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid port number: " + args[0]);
		}
	}

}
