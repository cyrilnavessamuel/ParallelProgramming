package masterInt.CandP.exo4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * This class is responsible for managing the connection between server and client
 */
public class ClientManager implements Runnable 
{

    private final Socket socket;
    private int id;

    
    //construct function, obtain the socket accepted by multithreadedserver
    public ClientManager(Socket socket,int id) 
    {
        this.socket = socket;
        this.id=id;
    }

    public void manage() throws IOException 
    {
        //...
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() 
    {
        //similar to a single thread server, processing the message sent from the connected client
    	String line;
		int index = 0;

		BufferedReader reader = null;
		PrintWriter printer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printer = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("reader or printer initialize failed.");
		}
    	while(true)
    	{
    		try {
    			line = reader.readLine();
    			System.out.println(String.format("Server: Message received from client %s : %s",this.id, line));
				printer.println(String.format("Message %s from client %s : %s", index++,this.id, line));
				printer.flush();
				if (line.equals("stop")) {
					printer.close();
					reader.close();
					socket.close();
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	System.out.println("client "+ this.id +" closed...");
    }

}
