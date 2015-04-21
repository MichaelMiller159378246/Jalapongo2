

// Imports
import java.io.BufferedReader; // Imports BufferedReader
import java.io.IOException; // Imports IOException
import java.io.InputStreamReader; // Imports InputStreamReader
import java.io.PrintWriter; // Imports PrintWriter
import java.net.ServerSocket; // Imports ServerSocket
import java.net.Socket; // Imports Socket
import java.util.HashSet; // Import HashSet

import javafx.application.Platform; // Imports platform
import javafx.beans.value.ChangeListener; // imports ChangeListener
import javafx.beans.value.ObservableValue; // Imports ObservableValue
import javafx.event.EventHandler; // Imports EventHandler
import javafx.geometry.Insets; // Imports Insets
import javafx.scene.control.Button; //Imports Button
import javafx.scene.control.Label; // Imports label
import javafx.scene.input.MouseEvent; // Imports MouseEvent
import javafx.scene.paint.Color; // Imports Color


// Host class is used to launch the server so others can connect
/**
 * @author Mike
 *
 */
public class Host extends Thread{

	private static ServerSocket listener; // Creates a ServerSocket Listener
	private static int port; // Creates an integer for the port for the server to listen on
	private static HashSet<String> names = new HashSet<String>(); // Creates a HashSet for the players names
	private static String namesB = new String(); // Creates a string to hold all player names
	private static String hostN; // Creates a string for the hosts name
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>(); // Creates a writer to communicate with the Client 
	private static int k = 1; // Creates a counting value
	private static String output; // Creates an output string variable
	private static int readyState1 = 0; // Creates an integer to indicate if the player is ready
	private static int readyState2 = 0; // Creates an integer to indicate if the player is ready
	private static int readyState3 = 0; // Creates an integer to indicate if the player is ready
	private static int readyState4 = 0; // Creates an integer to indicate if the player is ready
	private static String[] namesA = {null, null, null, null}; // Creates a private string array to hold all the names
	private static int refresh = 1;



	/**
	 * @returns sum of players ready
	 */
	public static int getPlayerCount(){
		return readyState1 + readyState2 + readyState3 + readyState4; // returns the sum of all the players ready
	}

	// Constructor
	public Host(int port, String name, Button startB){			

		startB.setOnMousePressed(new EventHandler<MouseEvent>() { // When the user presses start the game continuously runs
			public void handle(MouseEvent me) { // Creates a handler
				if(Integer.parseInt(GameGUI.AICB.getValue().toString()) == 3){ // If the host is the only person playing
					GameGUI.gameStartSolo(namesA[0], namesA[1], namesA[2], namesA[3], getPlayerCount()); // Start the game
				} else if(getPlayerCount() == 4 - Integer.parseInt(GameGUI.AICB.getValue().toString())){ // If all users are ready
					GameGUI.gameStart(namesA[0], namesA[1], namesA[2], namesA[3], getPlayerCount());// Start the game
					output = "CHANGE" + ":" + GameGUI.globalG.getEverything(); // Change the output message to "CHANGE"
				}
				for (PrintWriter writer : writers){ // Adds writer to the types of writes
					writer.println( output); // Sends a message to all players
				}
			}
		});




		Host.port = port; // Sets the global port to the inputed port
		names.add(name); // add the inputed name to the names list
		namesB = name; // add the inputed name to the secondary name list
		hostN = name; // add the name to the host name variable
		namesA[0] = name;

		Label nameL1 = new Label(hostN); // Creates a new label for the host.
		GameGUI.readyCB1.selectedProperty().addListener(new ChangeListener<Boolean>(){ // Creates a listener for the host check box
			public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
				if (t1) { // If the boolean value is true
					GameGUI.circle1.setFill(Color.LIME); // Change the color of the first circle to LIME
					output = "1A"; // Change the output message to "1A"
					readyState1 = 1; // Change the ready state of 1 to 1
				} else { // If the check box is not selected
					GameGUI.circle1.setFill(Color.DARKGREEN); // Change the color of the first circle to DARKGREEN
					output = "1B"; // Change the output message to "1B"
					readyState1 = 0; // Change the ready state of 1 to 0
				}
				for (PrintWriter writer : writers){ // Adds writer to the types of writes
					writer.println( output); // Sends message to all players
				}
			}
		});
		GameGUI.nameBP1.setLeft(nameL1); // Adds the hosts name to the left side of the border pane
		GameGUI.nameBP1.setRight(GameGUI.circle1); // Adds the circle to the right side of the border pane
		GameGUI.nameBP1.setCenter(GameGUI.readyCB1); // Adds the check box to the center of the border pane
		GameGUI.nameBP1.setPrefWidth(GameGUI.getWidthHeight() / 2); // Sets the prefered width of the borderpane
		nameL1.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
				"-fx-font-weight: bold;" +  // Sets the font to bold
				"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
		GameGUI.nameBP1.setStyle("-fx-border-color: Black"); //Sets the border color to black
		GameGUI.nameBP1.setPadding(new Insets(10)); // Adds padding to the borderpane
		GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0); // Adds the borderpane to the first position in the GridPane



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
		private String name; // String for all the names
		private Socket socket; // Socket for the server
		private BufferedReader in; // BufferedReader to get messages from the client
		private PrintWriter out; // PrintWriter to send messages to the client(s)

		// Sets the global socket
		public Handler(Socket socket){
			this.socket = socket; //Sets the Handler socket to the given socket
		}

		// Constantly asks for user information
		public void run(){
			try{
				in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Creates a buffered reader based off the sockets input stream
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
							namesB = namesB + ", " + name;
							break; // Breaks out of the while loop
						}
					}
				}

				String namesL = names.toString(); // Converts the names in the hash string to a string
				Platform.runLater(new Runnable() { // Runs when it gets the chance
					public void run() { //Runs
						GameGUI.nameLRQ.setText(namesL); //Updates the GUI
						int x = namesB.length() - namesB.replace(",", "").length() + 1; // Calculates how many clients have connected to the host
						String[] namesList = namesB.split(", "); // Creates a array of all the names
						GameGUI.namesGPRQ.getChildren().clear(); // Clears the Gridpane
						for(int i = k; i < x; i++){
							// Switch for the current player
							switch (i + 1){
							case 2:
								namesA[1] = namesList[1]; // Updates the name list
								Label nameL2 = new Label(namesA[1]); // Creates a label and inserts the players name in it
								GameGUI.nameBP2.setLeft(nameL2); // Sets the name to the left side of the border pane
								GameGUI.nameBP2.setRight(GameGUI.circle2); // Sets the circle to the right side of the border pane
								GameGUI.nameBP2.setPrefWidth(GameGUI.getWidthHeight() / 2); // Sets the width
								nameL2.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
										"-fx-font-weight: bold;" +  // Sets the font to bold
										"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
								GameGUI.nameBP2.setStyle("-fx-border-color: Black"); // Sets the border color to black
								GameGUI.nameBP2.setPadding(new Insets(10)); // Adds padding to the border pane
								k++; // Adds 1 to the current value of k
								break;
							case 3:
								namesA[2] = namesList[2]; // Updates the name list
								Label nameL3 = new Label(namesA[2]); // Creates a label and inserts the players name in it
								GameGUI.nameBP3.setLeft(nameL3); // Sets the name to the left side of the border pane
								GameGUI.nameBP3.setRight(GameGUI.circle3); // Sets the circle to the right side of the border pane
								GameGUI.nameBP3.setPrefWidth(GameGUI.getWidthHeight() / 2); // Sets the width
								nameL3.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
										"-fx-font-weight: bold;" +  // Sets the font to bold
										"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
								GameGUI.nameBP3.setStyle("-fx-border-color: Black"); // Sets the border color to black
								GameGUI.nameBP3.setPadding(new Insets(10)); // Adds padding to the border pane
								k++; // Adds 1 to the current value of k
								break;
							case 4:
								namesA[3] = namesList[3]; // Updates the name list
								Label nameL4 = new Label(namesA[3]); // Creates a label and inserts the players name in it
								GameGUI.nameBP4.setLeft(nameL4); // Sets the name to the left side of the border pane
								GameGUI.nameBP4.setRight(GameGUI.circle4); // Sets the circle to the right side of the border pane
								GameGUI.nameBP4.setPrefWidth(GameGUI.getWidthHeight() / 2); // Sets the width
								nameL4.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
										"-fx-font-weight: bold;" +  // Sets the font to bold
										"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
								GameGUI.nameBP4.setStyle("-fx-border-color: Black"); // Sets the border color to black
								GameGUI.nameBP4.setPadding(new Insets(10)); // Adds padding to the border pane
								k++; // Adds 1 to the current value of k
								break;
							}
						}
						switch(x){
						case 2:
							GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0); // adds the border pane to the grid pane
							GameGUI.namesGPRQ.add(GameGUI.nameBP2, 0, 1); // adds the border pane to the grid pane
							break;
						case 3:
							GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0); // adds the border pane to the grid pane
							GameGUI.namesGPRQ.add(GameGUI.nameBP2, 0, 1); // adds the border pane to the grid pane
							GameGUI.namesGPRQ.add(GameGUI.nameBP3, 0, 2); // adds the border pane to the grid pane
							break;
						case 4:
							GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0); // adds the border pane to the grid pane
							GameGUI.namesGPRQ.add(GameGUI.nameBP2, 0, 1); // adds the border pane to the grid pane
							GameGUI.namesGPRQ.add(GameGUI.nameBP3, 0, 2); // adds the border pane to the grid pane
							GameGUI.namesGPRQ.add(GameGUI.nameBP4, 0, 3); // adds the border pane to the grid pane
							break;
						}
					}
				});

				String readyS = readyState1 + "," + readyState2 + "," + readyState3 + "," + readyState4; // Creates a string for all the ready states
				out.println("NAMEACCEPTED :" + readyS); // Sets the message to send to the client to "NAMEACCEPTED"
				writers.add(out); // Tells the client that the name was accepted
				for (PrintWriter writer : writers){ // Adds writer to the types of writes
					writer.println("MESSAGE " + name + ":" + namesB); // Sends the message to all clients
				}

				// Listens for the client and displays messages
				while(true){
					String input = in.readLine(); // Reads the info sent by the client
					if (input == null){ // If the client did not send any info proceed
						return; // Restart the while loop
					}
					if(input.equals("2A")){ // If the second user indicates that they are ready
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle2.setFill(Color.LIME); // Change the color of the circle to LIME
							}
						});
						output = "2A"; // Change the output to "2A"
						readyState2 = 1; // Change the ready state to 1
					}
					else if(input.equals("2B")){ // If the client indicated they are not ready
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle2.setFill(Color.DARKGREEN); // Change the color of the circle to DARKGREEN
							}
						});	
						output = "2B"; // Change the output to "2B"
						readyState2 = 0; // Change the ready state to 0
					}
					else if(input.equals("3A")){ // If the client indicates they are ready
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle3.setFill(Color.LIME); // Change the color of the circle to LIME
							}
						});	
						output = "3A"; // Change the output to "3A"
						readyState3 = 1; // Change the ready state to 1
					}
					else if(input.equals("3B")){ // If the client indicates they are not ready
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle3.setFill(Color.DARKGREEN); // Change the color of the circle to DarkGreen
							}
						});	
						output = "3B"; // Change the output to "3B"
						readyState3 = 0; // Change the ready state to 0
					}
					else if(input.equals("4A")){ // If the client indicates they are ready
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle4.setFill(Color.LIME); // Change the color of the circle to LIME
							}
						});	
						output = "4A"; // Change the output to "4A"
						readyState4 = 1; // Change the ready state to 1
					}
					else if(input.equals("4B")){ // If the client indicates they are not ready
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle4.setFill(Color.DARKGREEN); // Change the color of the circle to DARKGREEN
							}
						});	
						output = "4B"; // Change the output to "4B"
						readyState4 = 0; // Change the ready state to 0
					}
					else if(input.startsWith("OK")){
						System.out.println(refresh + " " + input.toString()  + " Out of " + (4 - Integer.parseInt(GameGUI.AICB.getValue().toString())));
						
						if(refresh < (4 - Integer.parseInt(GameGUI.AICB.getValue().toString()))){
							
							refresh++;
							output = null;
							
							if(refresh >= (4 - Integer.parseInt(GameGUI.AICB.getValue().toString()))){
								GameGUI.updateGame();
								Thread.sleep(40);
								
								refresh = 1;
								output = "REFRESH" + ":" + GameGUI.globalG.getEverything();
						}
					}
					else{
						GameGUI.updateGame();
						Thread.sleep(50);
						refresh = 1;
						output = "REFRESH" + ":" + GameGUI.globalG.getEverything();
					}
				}
				for (PrintWriter writer : writers){ // Adds writer to the types of writes
					writer.println( output); // Sends to all clients
				}
			}
		} catch (IOException e){
			System.out.println("ERROR 101: Error connecting with user"); // If there is an error connecting to the user display the error message 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}