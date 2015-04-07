// Imports
import java.io.BufferedReader; // Imports BufferedReader
import java.io.IOException; // Imports IOException
import java.io.InputStreamReader; // Imports InputStreamReader
import java.io.PrintWriter; // Imports PrintWriter
import java.net.ServerSocket; // Imports ServerSocket
import java.net.Socket; // Imports Socket
import java.util.Arrays;
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
	private static String namesB = new String();
	private static String hostN;
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>(); // Creates a writer to communicate with the Client
	public static int k = 0;
	public static String output;
	private static int readyState1 = 0;
	private static int readyState2 = 0;
	private static int readyState3 = 0;
	private static int readyState4 = 0;


	public static int getPlayerCount(){
		return readyState1 + readyState2 + readyState3 + readyState4;
	}
	
	// Constructor
	public Host(int port, String name){				
		Host.port = port; // Sets the global port to the inputed port
		names.add(name);
		namesB = name;
		hostN = name;

		Label nameL1 = new Label(hostN);
		GameGUI.readyCB1.selectedProperty().addListener(new ChangeListener<Boolean>(){

			public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
				if (t1) {
					GameGUI.circle1.setFill(Color.LIME);
					output = "1A";
					readyState1 = 1;

				} else {
					GameGUI.circle1.setFill(Color.DARKGREEN);
					output = "1B";
					readyState1 = 0;
				}
				for (PrintWriter writer : writers){ // Adds writer to the types of writes
					//writer.println(out); // Takes the message from the client and displays it on the screen
					writer.println( output);
				}
			}

		});
		GameGUI.nameBP1.setLeft(nameL1);
		GameGUI.nameBP1.setRight(GameGUI.circle1);
		if(nameL1.toString().substring(nameL1.toString().indexOf("'") + 1, nameL1.toString().length() - 1).equals(hostN)){
			GameGUI.nameBP1.setCenter(GameGUI.readyCB1);
		}
		GameGUI.nameBP1.setPrefWidth(700 / 2);
		nameL1.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
				"-fx-font-weight: bold;" +  // Sets the font to bold
				"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
		//		"-fx-border-color: Black"); // Sets the border to black
		GameGUI.nameBP1.setStyle("-fx-border-color: Black");
		GameGUI.nameBP1.setPadding(new Insets(10));
		
		GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0);
		
		
		
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
							namesB = namesB + ", " + name;
							break; // Breaks out of the while loop
						}
					}
				}

				String namesL = names.toString(); // Converts the names in the hash string to a string
				Platform.runLater(new Runnable() { // Runs when it gets the chance
					public void run() { //Runs
						GameGUI.nameLRQ.setText(namesL); //Updates the gui
						int x = namesB.length() - namesB.replace(",", "").length() + 1;
						//System.out.println(names.size());
						//String[] namesA = names.toArray(new String[names.size()]);
						String[] namesA = namesB.split(", ");
						GameGUI.namesGPRQ.getChildren().clear();
						System.out.println(k);
						for(int i = k; i < x; i++){
							/*
							CheckBox readyCB = new CheckBox();
							BorderPane nameBP = new BorderPane();
							Label nameL = new Label(namesA[names.size()-(i+1)]);
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
							 */



							System.out.println(Arrays.toString(namesA));



							switch (i + 1){
							case 1:
								Label nameL1 = new Label(namesA[0]);
								GameGUI.readyCB1.selectedProperty().addListener(new ChangeListener<Boolean>(){

									public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
										if (t1) {
											GameGUI.circle1.setFill(Color.LIME);
											output = "1A";
											readyState1 = 1;

										} else {
											GameGUI.circle1.setFill(Color.DARKGREEN);
											output = "1B";
											readyState1 = 0;
										}
										for (PrintWriter writer : writers){ // Adds writer to the types of writes
											//writer.println(out); // Takes the message from the client and displays it on the screen
											writer.println( output);
										}
									}

								});
								GameGUI.nameBP1.setLeft(nameL1);
								GameGUI.nameBP1.setRight(GameGUI.circle1);
								System.out.println(namesA[0]);
								if(nameL1.toString().substring(nameL1.toString().indexOf("'") + 1, nameL1.toString().length() - 1).equals(hostN)){
									GameGUI.nameBP1.setCenter(GameGUI.readyCB1);
								}
								GameGUI.nameBP1.setPrefWidth(700 / 2);
								nameL1.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
										"-fx-font-weight: bold;" +  // Sets the font to bold
										"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
								//		"-fx-border-color: Black"); // Sets the border to black
								GameGUI.nameBP1.setStyle("-fx-border-color: Black");
								GameGUI.nameBP1.setPadding(new Insets(10));
								k++;
								break;
							case 2:
								Label nameL2 = new Label(namesA[1]);
								GameGUI.readyCB2.selectedProperty().addListener(new ChangeListener<Boolean>(){

									public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
										if (t1) {
											GameGUI.circle2.setFill(Color.LIME);

										} else {
											GameGUI.circle2.setFill(Color.DARKGREEN);
										}
									}

								});
								GameGUI.nameBP2.setLeft(nameL2);
								GameGUI.nameBP2.setRight(GameGUI.circle2);
								if(nameL2.toString().substring(nameL2.toString().indexOf("'") + 1, nameL2.toString().length() - 1).equals(hostN)){
									GameGUI.nameBP2.setCenter(GameGUI.readyCB2);
								}
								GameGUI.nameBP2.setPrefWidth(700 / 2);
								nameL2.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
										"-fx-font-weight: bold;" +  // Sets the font to bold
										"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
								//		"-fx-border-color: Black"); // Sets the border to black
								GameGUI.nameBP2.setStyle("-fx-border-color: Black");
								GameGUI.nameBP2.setPadding(new Insets(10));
								k++;
								break;
							case 3:
								Label nameL3 = new Label(namesA[2]);
								GameGUI.readyCB3.selectedProperty().addListener(new ChangeListener<Boolean>(){

									public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
										if (t1) {
											GameGUI.circle3.setFill(Color.LIME);

										} else {
											GameGUI.circle3.setFill(Color.DARKGREEN);
										}
									}

								});
								GameGUI.nameBP3.setLeft(nameL3);
								GameGUI.nameBP3.setRight(GameGUI.circle3);
								if(nameL3.toString().substring(nameL3.toString().indexOf("'") + 1, nameL3.toString().length() - 1).equals(hostN)){
									GameGUI.nameBP3.setCenter(GameGUI.readyCB3);
								}
								GameGUI.nameBP3.setPrefWidth(700 / 2);
								nameL3.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
										"-fx-font-weight: bold;" +  // Sets the font to bold
										"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
								//		"-fx-border-color: Black"); // Sets the border to black
								GameGUI.nameBP3.setStyle("-fx-border-color: Black");
								GameGUI.nameBP3.setPadding(new Insets(10));
								k++;
								break;
							case 4:
								Label nameL4 = new Label(namesA[3]);
								GameGUI.readyCB4.selectedProperty().addListener(new ChangeListener<Boolean>(){

									public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
										if (t1) {
											GameGUI.circle4.setFill(Color.LIME);

										} else {
											GameGUI.circle4.setFill(Color.DARKGREEN);
										}
									}

								});
								GameGUI.nameBP4.setLeft(nameL4);
								GameGUI.nameBP4.setRight(GameGUI.circle4);
								if(nameL4.toString().substring(nameL4.toString().indexOf("'") + 1, nameL4.toString().length() - 1).equals(hostN)){
									GameGUI.nameBP4.setCenter(GameGUI.readyCB4);
								}
								GameGUI.nameBP4.setPrefWidth(700 / 2);
								nameL4.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
										"-fx-font-weight: bold;" +  // Sets the font to bold
										"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
								//		"-fx-border-color: Black"); // Sets the border to black
								GameGUI.nameBP4.setStyle("-fx-border-color: Black");
								GameGUI.nameBP4.setPadding(new Insets(10));
								k++;
								break;
							}





							/*
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
							 */
						}
						switch(x){
						case 2:
							GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0);
							GameGUI.namesGPRQ.add(GameGUI.nameBP2, 0, 1);
							break;
						case 3:
							GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0);
							GameGUI.namesGPRQ.add(GameGUI.nameBP2, 0, 1);
							GameGUI.namesGPRQ.add(GameGUI.nameBP3, 0, 2);
							break;
						case 4:
							GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0);
							GameGUI.namesGPRQ.add(GameGUI.nameBP2, 0, 1);
							GameGUI.namesGPRQ.add(GameGUI.nameBP3, 0, 2);
							GameGUI.namesGPRQ.add(GameGUI.nameBP4, 0, 3);
							break;
						}
					}
				});

				String readyS = readyState1 + "," + readyState2 + "," + readyState3 + "," + readyState4;
				out.println("NAMEACCEPTED :" + readyS); // Sets the message to send to the client to "NAMEACCEPTED"
				writers.add(out); // Tells the client that the name was accepted
				for (PrintWriter writer : writers){ // Adds writer to the types of writes
					//writer.println("MESSAGE " + name + ": " + names.toString()); // Takes the message from the client and displays it on the screen
					writer.println("MESSAGE " + name + ":" + namesB);
				}

				// Listens for the client and displays messages
				while(true){
					String input = in.readLine(); // Reads the info sent by the client
					if (input == null){ // If the client did not send any info proceed
						return; // Restart the while loop
					}
					if(input.equals("2A")){
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle2.setFill(Color.LIME);
								//GameGUI.readyCB2.setSelected(true);
								//out.println("2A");
								//System.out.println(Arrays.toString(writers.toArray()));
							}
						});
						output = "2A";
						readyState2 = 1;
					}
					else if(input.equals("2B")){
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle2.setFill(Color.DARKGREEN);
								//out.println("2B");
							}
						});	
						output = "2B";
						readyState2 = 0;
					}
					else if(input.equals("3A")){
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle3.setFill(Color.LIME);
							}
						});	
						output = "3A";
						readyState3 = 1;
					}
					else if(input.equals("3B")){
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle3.setFill(Color.DARKGREEN);
							}
						});	
						output = "3B";
						readyState3 = 0;
					}
					else if(input.equals("4A")){
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle4.setFill(Color.LIME);
							}
						});	
						output = "4A";
						readyState4 = 1;
					}
					else if(input.equals("4B")){
						Platform.runLater(new Runnable() { // Runs when it gets the chance
							public void run() { //Runs
								GameGUI.circle4.setFill(Color.DARKGREEN);
							}
						});	
						output = "4B";
						readyState4 = 0;
					}
					
					for (PrintWriter writer : writers){ // Adds writer to the types of writes
						//writer.println(out); // Takes the message from the client and displays it on the screen
						writer.println( output);
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