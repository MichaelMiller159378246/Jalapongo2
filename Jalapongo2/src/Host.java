// Imports
import java.io.BufferedReader; // Imports BufferedReader
import java.io.IOException; // Imports IOException
import java.io.InputStreamReader; // Imports InputStreamReader
import java.io.PrintWriter; // Imports PrintWriter
import java.net.ServerSocket; // Imports ServerSocket
import java.net.Socket; // Imports Socket
import java.util.HashSet; // Import HashSet
import javafx.application.Platform; // Imports platform


// Host class is used to launch the server so others can connect
public class Host extends Thread{

	public static ServerSocket listener; // Creates a ServerSocket Listener
	public static int port; // Creates an integer for the port for the server to listen on
	private static HashSet<String> names = new HashSet<String>(); // Creates a HashSet for the players names
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>(); // Creates a writer to communicate with the Client


	// Constructor
	public Host(int port, String name){
		Host.port = port; // Sets the global port to the inputed port
		names.add(name);
	}

	// Method to launch the server
	public static void launchServer(int port){
		try {
			listener = new ServerSocket(port); // Try's to create a listener using the inputed port
			try{
				while(true){
					System.out.println("The server is now running"); // The listener was successfully created so the server should be running
					new Handler(listener.accept()).start(); // Waits for clients to connect
				}
			} catch (IOException e){
				System.out.println("The server has shut down"); // If the server closes then it has shut down

			} finally{
				listener.close(); // Closes the listener
			}
		} catch (IOException e) {
			System.out.println("ERROR 100: Error creating server socket!"); // If there was an error creating the listener then the message is produced
		}
	}

	//  Method used to broadcast to the clients
	private static class Handler extends Thread{
		// Declare Variables
		private String name;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		public Handler(Socket socket){
			this.socket = socket; //Sets the Handler socket to the given socket
		}

		// Constantly asks for user information
		public void run(){
			try{
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream())); // Creates a buffered reader based off the sockets input stream
				out = new PrintWriter(socket.getOutputStream(), true); // Creates a print writer based off the sockets output stream

				// Asks for user information
				while(true){
					out.println("SUBMITNAME"); // Asks the client to send the name of the client
					name = in.readLine(); // Takes the clients information
					if(name == null){ // If there was no data stored in the clients information proceed
						return; // Returns and starts the while loop over
					}
					synchronized (names) { // Keeps the names list synchronized
						if(!names.contains(name)){ // If the name received by the client does not already exist proceed
							names.add(name); // Add the name to the list of names
							break; // Breaks out of the while loop
						}
					}
				}

				String namesL = names.toString(); // Converts the names in the hash string to a string
				Platform.runLater(new Runnable() { // Runs when it gets the chance
					public void run() { //Runs
						GameGUI.nameLRQ.setText(namesL); //Updates the gui
					}
				});
				
				out.println("NAMEACCEPTED"); // Sets the message to send to the client to "NAMEACCEPTED"
				writers.add(out); // Tells the client that the name was accepted

				// Listens for the client and displays messages
				while(true){
					String input = in.readLine(); // Reads the info sent by the client
					if (input == null){ // If the client did not send any info proceed
						return; // Restart the while loop
					}
					for (PrintWriter writer : writers){ // Adds writer to the types of writes
						writer.println("MESSAGE " + name + ": " + input); // Takes the message from the client and displays it on the screen
					}
				}
			} catch (IOException e){
				System.out.println("ERROR 101: Error connecting with user"); // If there is an error connecting to the user display the error message 
			} finally { // If the user disconnects proceed
				if (name != null){ // If the name is not blank
					names.remove(name); // Remove the name from the list
				}
				if (out != null){ // If the output to the client is not blank
					writers.remove(out); // Remove the output
				}
				try{ // Try to close the server
					socket.close(); // Closes the server connection
				} catch(IOException e){
					System.out.println("ERROR 102: Error disconnecting user"); // If there is an error disconnecting the user
				}
			}
		}

	}

	// Runs the server
	public void run(){
		launchServer(port); // Starts the process
	}

	/*
	public void releasePowerUp(){

	}
	 */ //TODO use or not use

}