package masterInt.CandP.exo4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * This is a multi-threaded server, the main thread is responsible for monitoring client request
 */
public class MultithreadedServer 
{

    // port number to listen on
    private final int port;

    public MultithreadedServer(int port) 
    {
        this.port = port;
    }

	public void execute() 
    {
        //...
		System.out.println("begin server initializing...");
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("server initialize successfully...");
		int i=0;
		while(true)
		{
			//wait for the client request, and delegate it to a clientManager thread
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread t = new Thread(new ClientManager(socket,i));
			t.start();
			System.out.println("Client "+ i++ + " connected.");
		}
    }

    public static void main(String[] args) 
    {
        if (args.length != 1) {
            System.err.println("usage: java "
                    + MultithreadedServer.class.getCanonicalName()
                    + " serverPort");
            System.exit(1);
        }

        // On unix systems you can check that the server is running
        // by executing the following command:
        // lsof -Pi | grep 9999
        try {
            new MultithreadedServer(Integer.parseInt(args[0])).execute();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid port number: "
                    + args[0]);
        }
    }

}
