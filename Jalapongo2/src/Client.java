/*import*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;


public class Client{
	private BufferedReader in;
	private PrintWriter out;
	private static int port;
	private static String ipAddress;
	private static String name;
	
	/*Construct client*/
	public Client(int portN, String IP,String uName) {
		port = portN;
		ipAddress = IP;
		name = uName;
	}
	
	private String getName(){
		return name;
	}
	
	/*connect to server and enter processing loop*/
	private void run() throws IOException{
		
		//Make connection and initialize streams
		Socket socket = new Socket(ipAddress,port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(),true);
		
		
		/*Submit name and get verification that it is accepted*/
		while(true){
			String line = in.readLine();
			if(line.startsWith("SUBMITNAME")){
				out.println(getName());
			}
			else if (line.startsWith("NAMEACCEPTED")){
				
			}
		}//end while loop
	}//end run
	
	/*run the client*/
	public static void main(String[] args) throws Exception{
		Client user = new Client(port,ipAddress,name);
		user.run();
		
	}
}//end Client