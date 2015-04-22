//Imports
import java.io.BufferedReader; // Imports the BufferedReader
import java.io.InputStreamReader; // Imports the InputStreamReader
import java.io.PrintWriter; // Inputs the PrintWriter
import java.net.Socket; // Inputs the Socket
import java.net.UnknownHostException; // Inputs the UnknownHostException
import java.io.IOException; // Imports the IOException

import javafx.application.Platform; // Imports Platform
import javafx.beans.value.ChangeListener; // Imports ChangeListener
import javafx.beans.value.ObservableValue; // Imports ObservableValue
import javafx.geometry.Insets; // Imports Insets
import javafx.scene.control.Label; // Imports Label
import javafx.scene.paint.Color; // Imports Color

/**
 * @author Mike, Leslie
 *
 */
public class Client extends Thread{
	private BufferedReader in; //Creates a BufferedReader to get info from the host
	public static PrintWriter out; // Creates a printWriter to send messages to the host
	private static int port; // Creates an global integer for the port
	private static String ipAddress; // Creates a global string for the IP address
	private static String name; // Creates a global string for the name
	private int k = 0; // Creates a global integer to keep track of position
	public String[] names = {null, null, null, null}; // Creates a global string array to keep track of names
	public static String output = null;
	
	/*Construct client*/
	/**
	 * @param portN - Port to connect to, number can be between 1 and 5 digits long
	 * @param IP - IP address to connect to
	 * @param uName - String for the users name
	 */
	public Client(int portN, String IP,String uName) {
		port = portN; // Sets the global port number to the inputed number
		ipAddress = IP; // Sets the global IP address to the inputed IP address
		name = uName; // Sets the global name to the inputed name
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
				else if (line.startsWith("NAMEACCEPTED ")){ // If the host sends "NAMEACCEPTED"
					String lights = line.substring(line.indexOf(":") + 1, line.length()); // Reads the message from the host
					String[] lightsA = lights.split(","); // Creates an array from the string

					// Turn on the respective ready lights
					if(lightsA[0].equals("1")){ // If player 1 is ready
						GameGUI.circle1.setFill(Color.LIME); // Set the light to LIME
					}
					if(lightsA[1].equals("1")){ // If player 2 is ready
						GameGUI.circle2.setFill(Color.LIME); // Set the light to LIME
					}
					if(lightsA[2].equals("1")){ // If player 3 is ready
						GameGUI.circle3.setFill(Color.LIME); // Set the light to LIME
					}
					if(lightsA[3].equals("1")){ // If player 4 is ready
						GameGUI.circle4.setFill(Color.LIME); // Set the light to LIME
					}
				}
				else if (line.startsWith("MESSAGE ")){ // If the message sent from the host starts with MESSAGE proceed
					String namesL = line.substring(line.indexOf(":") + 1, line.length()); // Creates a string of all the names from the host
					String[] namesA = namesL.split(", "); // Creates an array from the name string
					int x = namesL.length() - namesL.replace(",", "").length() + 1; // Creates an integer to determine how many players are connected
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.namesGPRQ.getChildren().clear(); //Clears the Grid Pane
							for(int i = k; i < x; i++){
								switch(i + 1){
								case 1:
									names[0] = namesA[0]; // Updates the name list
									Label nameL1 = new Label(namesA[0]); // Adds the hosts name to the first label
									GameGUI.nameBP1.setLeft(nameL1); // Sets the label to the left side of the border pane
									GameGUI.nameBP1.setRight(GameGUI.circle1); // Sets the circle to the right side of the border pane
									GameGUI.nameBP1.setPrefWidth(700 / 2); // Set the preferred width
									nameL1.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
											"-fx-font-weight: bold;" +  // Sets the font to bold
											"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
									GameGUI.nameBP1.setStyle("-fx-border-color: Black"); // Set the border to black
									GameGUI.nameBP1.setPadding(new Insets(10)); // Adds padding to the border pane
									k++; // Adds 1 to k
									break;
								case 2:
									names[1] = namesA[1]; // Updates the name list
									Label nameL2 = new Label(namesA[1]); // Adds the second players name to the label
									GameGUI.nameBP2.setLeft(nameL2); // Set the label to the left of the border pane
									GameGUI.nameBP2.setRight(GameGUI.circle2); // Set the circle to the right of the border pane
									if(nameL2.toString().substring(nameL2.toString().indexOf("'") + 1, nameL2.toString().length() - 1).equals(name)){ //If the name of the client is equal to the current name on the list 
										GameGUI.nameBP2.setCenter(GameGUI.readyCB2); // Set the check box to the center of the border pane
										GameGUI.readyCB2.selectedProperty().addListener(new ChangeListener<Boolean>(){ // Adds a listener to the check box

											public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
												if (t1) { // If the user pressed the check box
													out.println("2A"); // Output "2A"

												} else { // If the user unchecks the check box
													out.println("2B"); // Output "2B"
												}
											}

										});
									}
									GameGUI.nameBP2.setPrefWidth(700 / 2); // Set the preferred width
									nameL2.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
											"-fx-font-weight: bold;" +  // Sets the font to bold
											"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
									GameGUI.nameBP2.setStyle("-fx-border-color: Black"); // set the border to black
									GameGUI.nameBP2.setPadding(new Insets(10)); // set the padding
									k++; // add 1 to k
									break;
								case 3:
									names[2] = namesA[2]; // Updates the name list
									Label nameL3 = new Label(namesA[2]); // Adds the third name to the label
									GameGUI.readyCB3.selectedProperty().addListener(new ChangeListener<Boolean>(){ // Add an action listener to the check box

										public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
											if (t1) { // If the user checks the check box
												out.println("3A"); // sends a message to the host
											} else { // If the user unchecks the check box
												out.println("3B"); // sends a message to the host
											}
										}

									});
									GameGUI.nameBP3.setLeft(nameL3); // Adds the label to the left side of the border pane
									GameGUI.nameBP3.setRight(GameGUI.circle3); // Adds the circle to the right side of the border pane
									if(nameL3.toString().substring(nameL3.toString().indexOf("'") + 1, nameL3.toString().length() - 1).equals(name)){ // If the clients name equal the current name
										GameGUI.nameBP3.setCenter(GameGUI.readyCB3); // add the check box
										GameGUI.readyCB3.selectedProperty().addListener(new ChangeListener<Boolean>(){ // Add an action listener to the check box

											public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
												if (t1) { // If the user checks the check box
													out.println("3A"); // sends a message to the host
												} else { // If the user unchecks the check box
													out.println("3B"); // sends a message to the host
												}
											}

										});
									}
									GameGUI.nameBP3.setPrefWidth(700 / 2); // Set the preferred width
									nameL3.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
											"-fx-font-weight: bold;" +  // Sets the font to bold
											"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
									GameGUI.nameBP3.setStyle("-fx-border-color: Black"); // Set the border to black
									GameGUI.nameBP3.setPadding(new Insets(10)); // Adds padding to the border pane
									k++; // Adds 1 to the value of k
									break;
								case 4:
									names[3] = namesA[3]; // Updates the name list
									Label nameL4 = new Label(namesA[3]); // Adds the last players name to the label =
									GameGUI.readyCB4.selectedProperty().addListener(new ChangeListener<Boolean>(){ // Adds a listener to the check box

										public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
											if (t1) { // If the user checks the check box
												out.println("4A"); // Sends a message to the host
											} else { // If the user unchecks the check box
												out.println("4B"); // Sends a message to the host
											}
										}
									});
									
									GameGUI.nameBP4.setLeft(nameL4); // Set the name to the left side of the border pane
									GameGUI.nameBP4.setRight(GameGUI.circle4); // Set the circle to the right side of the border pane
									if(nameL4.toString().substring(nameL4.toString().indexOf("'") + 1, nameL4.toString().length() - 1).equals(name)){ //If the clients name is equal to the current name 
										GameGUI.nameBP4.setCenter(GameGUI.readyCB4); // Add teh check box to the center of the border pane
										GameGUI.readyCB4.selectedProperty().addListener(new ChangeListener<Boolean>(){ // Adds a listener to the check box

											public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
												if (t1) { // If the user checks the check box
													out.println("4A"); // Sends a message to the host
												} else { // If the user unchecks the check box
													out.println("4B"); // Sends a message to the host
												}
											}
										});
									}
									GameGUI.nameBP4.setPrefWidth(700 / 2); // set the preferred width
									nameL4.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
											"-fx-font-weight: bold;" +  // Sets the font to bold
											"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
									GameGUI.nameBP4.setStyle("-fx-border-color: Black"); // adds a Black border to the border pane
									GameGUI.nameBP4.setPadding(new Insets(10)); // Adds padding to the border pane
									k++; // Adds one to the value of k
									break;
								}
							}
							// Adds the BorderPanes based off of the amount of players
							switch(x){
							case 2:
								GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0); // Adds player 1
								GameGUI.namesGPRQ.add(GameGUI.nameBP2, 0, 1); // Adds player 2
								break;
							case 3:
								GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0); // Adds player 1
								GameGUI.namesGPRQ.add(GameGUI.nameBP2, 0, 1); // Adds player 2
								GameGUI.namesGPRQ.add(GameGUI.nameBP3, 0, 2); // Adds player 3
								break;
							case 4:
								GameGUI.namesGPRQ.add(GameGUI.nameBP1, 0, 0); // Adds player 1
								GameGUI.namesGPRQ.add(GameGUI.nameBP2, 0, 1); // Adds player 2
								GameGUI.namesGPRQ.add(GameGUI.nameBP3, 0, 2); // Adds player 3
								GameGUI.namesGPRQ.add(GameGUI.nameBP4, 0, 3); // Adds player 4
								break;
							}
						}
					});
				}
				else if(line.startsWith("1A")){ // If the host sent "1A"
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle1.setFill(Color.LIME); // Change the first circle to LIME
						}
					});
				}
				else if(line.startsWith("1B")){ // If the host sent "1B"
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle1.setFill(Color.DARKGREEN); // Change the first circle to DARKGREEN
						}
					});
				}
				else if(line.startsWith("2A")){ // If the host sent "2A"
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle2.setFill(Color.LIME); // Change the second circle to LIME
						}
					});
				}
				else if(line.startsWith("2B")){ // If the host sent "2B"
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle2.setFill(Color.DARKGREEN); // Change the second circle to DARKGREEN
						}
					});
				}
				else if(line.startsWith("3A")){ // If the host sent "3A"
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle3.setFill(Color.LIME); // Change the third circle to LIME
						}
					});
				}
				else if(line.startsWith("3B")){ // If the host sent "3B"
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle3.setFill(Color.DARKGREEN); // Change the third circle to DARKGREEN
						}
					});
				}
				else if(line.startsWith("4A")){ // If the host sent "4A"
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle4.setFill(Color.LIME); // Change the fourth circle to LIME
						}
					});
				}
				else if(line.startsWith("4B")){ // If the host sent "4B"
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle4.setFill(Color.DARKGREEN); // Change the fourth circle to DARKGEEN
						}
					});
				}
				else if(line.startsWith("CHANGE")){ // If the host sent "Change"
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							String input =  line.substring(line.indexOf(":") + 1, line.length());
							System.out.println(line.toString());
							String[] ball = input.split(",");
							System.out.println(ball[1]);
							GameGUI.gameStart(names[0], names[1], names[2], names[3], k); // Calls the gameStart method from GameGUI
							GameGUI.globalG.setEverything(Integer.parseInt(ball[0]), Integer.parseInt(ball[1]), Double.parseDouble(ball[2]), Double.parseDouble(ball[3]), Double.parseDouble(ball[4]), Double.parseDouble(ball[5]), Double.parseDouble(ball[6]), Double.parseDouble(ball[7]), Double.parseDouble(ball[8]), Double.parseDouble(ball[9]), Integer.parseInt(ball[10]), Integer.parseInt(ball[11]), Integer.parseInt(ball[12]), Integer.parseInt(ball[13]), Double.parseDouble(ball[14]), Double.parseDouble(ball[15]), Double.parseDouble(ball[16]), Double.parseDouble(ball[17]), Double.parseDouble(ball[18]), Double.parseDouble(ball[19]), Integer.parseInt(ball[20]), Double.parseDouble(ball[21]), Double.parseDouble(ball[22]), Integer.parseInt(ball[23]), Double.parseDouble(ball[24]), Double.parseDouble(ball[25]), Integer.parseInt(ball[26]), Double.parseDouble(ball[27]), Double.parseDouble(ball[28]), Integer.parseInt(ball[29]), Double.parseDouble(ball[30]), Double.parseDouble(ball[31]), Integer.parseInt(ball[32]), Double.parseDouble(ball[33]), Double.parseDouble(ball[34]), Double.parseDouble(ball[35]), Double.parseDouble(ball[36]), Double.parseDouble(ball[37]), Double.parseDouble(ball[38]), Double.parseDouble(ball[39]), Double.parseDouble(ball[40]), Double.parseDouble(ball[41]), Double.parseDouble(ball[42]), Double.parseDouble(ball[43]), Double.parseDouble(ball[44]), Double.parseDouble(ball[45]), Double.parseDouble(ball[46]), Double.parseDouble(ball[47]), Double.parseDouble(ball[48]), Double.parseDouble(ball[49]), Double.parseDouble(ball[50]), Double.parseDouble(ball[51]), Double.parseDouble(ball[52]), Double.parseDouble(ball[53]), Double.parseDouble(ball[54]), Double.parseDouble(ball[55]), Double.parseDouble(ball[56]), Double.parseDouble(ball[57]), Double.parseDouble(ball[58]));
							out.println("OK");
						}
					});
				}
				else if(line.startsWith("REFRESH")){
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
					String input =  line.substring(line.indexOf(":") + 1, line.length());
					//System.out.println(line.toString());
					String[] ball = input.split(",");
					GameGUI.globalG.setEverything(Integer.parseInt(ball[0]), Integer.parseInt(ball[1]), Double.parseDouble(ball[2]), Double.parseDouble(ball[3]), Double.parseDouble(ball[4]), Double.parseDouble(ball[5]), Double.parseDouble(ball[6]), Double.parseDouble(ball[7]), Double.parseDouble(ball[8]), Double.parseDouble(ball[9]), Integer.parseInt(ball[10]), Integer.parseInt(ball[11]), Integer.parseInt(ball[12]), Integer.parseInt(ball[13]), Double.parseDouble(ball[14]), Double.parseDouble(ball[15]), Double.parseDouble(ball[16]), Double.parseDouble(ball[17]), Double.parseDouble(ball[18]), Double.parseDouble(ball[19]), Integer.parseInt(ball[20]), Double.parseDouble(ball[21]), Double.parseDouble(ball[22]), Integer.parseInt(ball[23]), Double.parseDouble(ball[24]), Double.parseDouble(ball[25]), Integer.parseInt(ball[26]), Double.parseDouble(ball[27]), Double.parseDouble(ball[28]), Integer.parseInt(ball[29]), Double.parseDouble(ball[30]), Double.parseDouble(ball[31]), Integer.parseInt(ball[32]), Double.parseDouble(ball[33]), Double.parseDouble(ball[34]), Double.parseDouble(ball[35]), Double.parseDouble(ball[36]), Double.parseDouble(ball[37]), Double.parseDouble(ball[38]), Double.parseDouble(ball[39]), Double.parseDouble(ball[40]), Double.parseDouble(ball[41]), Double.parseDouble(ball[42]), Double.parseDouble(ball[43]), Double.parseDouble(ball[44]), Double.parseDouble(ball[45]), Double.parseDouble(ball[46]), Double.parseDouble(ball[47]), Double.parseDouble(ball[48]), Double.parseDouble(ball[49]), Double.parseDouble(ball[50]), Double.parseDouble(ball[51]), Double.parseDouble(ball[52]), Double.parseDouble(ball[53]), Double.parseDouble(ball[54]), Double.parseDouble(ball[55]), Double.parseDouble(ball[56]), Double.parseDouble(ball[57]), Double.parseDouble(ball[58]));
					out.println("OK" + " " + name);
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
		connect(); // Calls the connect method
	}
}//end Client