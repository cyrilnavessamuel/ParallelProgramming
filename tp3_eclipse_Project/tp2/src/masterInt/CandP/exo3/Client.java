package masterInt.CandP.exo3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * This is a client thread, the user can send message to server through this client.
 */
public class Client {

	private final String host;

	private final int port;

	public Client(String serverHost, int serverPort) {
		this.host = serverHost;
		this.port = serverPort;
	}

	public void execute() {
		// initialize the resources
		Socket socket = null;
		try {
			socket = new Socket(this.host, this.port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line;
		BufferedReader reader = null;
		PrintWriter printer = null;
		BufferedReader socketReader = null;

		try {
			System.out.println("Client built up...");
			reader = new BufferedReader(new InputStreamReader(System.in));
			printer = new PrintWriter(socket.getOutputStream());
			socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("reader or printer initialize failed.");
		}
		while (true) {
			try {
				//wait for the user input from console and send it to server
				line = reader.readLine();
				printer.println(line);
				printer.flush();
				System.out.println("Client: message has been sent.");
				//wait for the server ack
				System.out
						.println(String.format("Client: receive server acknowledgement <%s>", socketReader.readLine()));
				
				if (line.equals("stop")) {
					break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//if the client send a stop message, release the resources
		try {
			reader.close();
			printer.close();
			socketReader.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("client closed...");
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("usage: java " + Client.class.getCanonicalName() + " serverHost serverPort");
			System.exit(1);
		}

		try {
			new Client(args[0], Integer.parseInt(args[1])).execute();
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid port number: " + args[0]);
		}
	}

}
