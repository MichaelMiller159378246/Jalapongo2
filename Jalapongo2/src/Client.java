/*import*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class Client extends Thread{
	private BufferedReader in;
	private PrintWriter out;
	private static int port;
	private static String ipAddress;
	private static String name;
	TextField textfield = new TextField();
	TextArea textarea = new TextArea();

	/*Construct client*/
	public Client(int portN, String IP,String uName) {
		port = portN;
		ipAddress = IP;
		name = uName;
	}
	
	//connect to server and enter processing loop
	public void connect(){
		Socket socket; // Initializes the socket
		try {
			socket = new Socket(ipAddress,port); // Creates the socket
			in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Creates the BufferedReader
			out = new PrintWriter(socket.getOutputStream(),true); // Creates the PrintWriter 
			
			//Submit name and get verification that it is accepted
			while(true){
				String line = in.readLine(); // Reads the message sent from the host
				if(line.startsWith("SUBMITNAME")){ // If the message sent from the host starts with SUBMITNAME proceed
					out.println(name); // Sends the clients name to the host
				}
				else if (line.startsWith("NAMEACCEPTED")){ // TODO Do we need this
					//textfield.setDisable(false);
					//textarea.setDisable(false);
				}
				else if (line.startsWith("MESSAGE ")){ // If the message sent from the host starts with MESSAGE proceed
					String namesL = line.toString().substring(line.indexOf("["), line.length()); // Creates a string of names from the list sent by the host
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.nameLRQ.setText(namesL); //Updates the clients GUI
						}
					});
				}
			}//end while loop
		} catch (UnknownHostException e) { // if an UnknownHostException was caught proceed
			System.out.println("There was an error connecting to the host"); // displays an error
		} catch (IOException e) { // if an IOException was caught proceed
			System.out.println("Error connecting to the host"); // Displays an error message
		}
	}//end run

	// Runs the Thread
	public void run(){
		connect(); // Calls the connect mehtod
	}
}//end Client