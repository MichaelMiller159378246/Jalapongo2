// Imports
import java.io.BufferedReader; // Imports BufferedReader
import java.io.IOException; // Imports IOException
import java.io.InputStreamReader; // Imports InputStreamReader
import java.io.PrintWriter; // Imports PrintWriter
import java.net.ServerSocket; // Imports ServerSocket
import java.net.Socket; // Imports Socket
import java.util.HashSet; // Import HashSet

import javafx.application.Platform; // Imports platform
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


// Host class is used to launch the server so others can connect
public class Host extends Thread{

	public static ServerSocket listener; // Creates a ServerSocket Listener
	public static int port; // Creates an integer for the port for the server to listen on
	private static HashSet<String> names = new HashSet<String>(); // Creates a HashSet for the players names
	private static String hostN;
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>(); // Creates a writer to communicate with the Client


	// Constructor
	public Host(int port, String name){
		Host.port = port; // Sets the global port to the inputed port
		names.add(name);
		hostN = name;
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

						System.out.println("Jello");
						String[] namesA = names.toArray(new String[names.size()]);
						GameGUI.namesGPRQ.getChildren().clear();
						for(int i = 0; i < names.size(); i++){
							CheckBox readyCB = new CheckBox();
							BorderPane nameBP = new BorderPane();
							Label nameL = new Label(namesA[i]);
							Circle circle = new Circle(25);
							circle.setFill(Color.DARKGREEN);
							readyCB.selectedProperty().addListener(new ChangeListener<Boolean>(){

								public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
									if (t1) {
										circle.setFill(Color.LIME);

									} else {
										circle.setFill(Color.DARKGREEN);
									}
								}

							});
							circle.setStroke(Color.BLACK);
							circle.setStrokeWidth(2);
							nameBP.setLeft(nameL);
							nameBP.setRight(circle);
							if(nameL.toString().substring(nameL.toString().indexOf("'") + 1, nameL.toString().length() - 1).equals(hostN)){
								nameBP.setCenter(readyCB);
							}
							nameBP.setPrefWidth(700 / 2);
							nameL.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
									"-fx-font-weight: bold;" +  // Sets the font to bold
									"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
							//		"-fx-border-color: Black"); // Sets the border to black
							nameBP.setStyle("-fx-border-color: Black");
							nameBP.setPadding(new Insets(10));
							GameGUI.namesGPRQ.add(nameBP, 0, i);

						}
					}
				});

				out.println("NAMEACCEPTED"); // Sets the message to send to the client to "NAMEACCEPTED"
				writers.add(out); // Tells the client that the name was accepted
				for (PrintWriter writer : writers){ // Adds writer to the types of writes
					writer.println("MESSAGE " + name + ": " + names.toString()); // Takes the message from the client and displays it on the screen
				}

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