//import
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{
	private static Socket socket;
	public static int port;
	private BufferedReader in;
	private PrintWriter out;
	
	//Construct client 
	public Client(String ipAddress) throws Exception{
		//Setup networking
		socket = new Socket(ipAddress,port);
	}
	
}