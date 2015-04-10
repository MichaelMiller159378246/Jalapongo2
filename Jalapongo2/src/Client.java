/*import*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Client extends Thread{
	private BufferedReader in;
	private PrintWriter out;
	private static int port;
	private static String ipAddress;
	public static String name;
	TextField textfield = new TextField();
	TextArea textarea = new TextArea();
	public Circle circle1 = new Circle(25);
	public Circle circle2 = new Circle(25);
	public Circle circle3 = new Circle(25);
	public Circle circle4 = new Circle(25);
	public CheckBox readyCB1 = new CheckBox();
	public CheckBox readyCB2 = new CheckBox();
	public CheckBox readyCB3 = new CheckBox();
	public CheckBox readyCB4 = new CheckBox();
	public static BorderPane nameBP1 = new BorderPane();
	public static BorderPane nameBP2 = new BorderPane();
	public static BorderPane nameBP3 = new BorderPane();
	public static BorderPane nameBP4 = new BorderPane();
	public int pos = 2;
	public int k = 0;
	public String[] names;

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
				System.out.println(line);
				if(line.startsWith("SUBMITNAME")){ // If the message sent from the host starts with SUBMITNAME proceed
					out.println(name); // Sends the clients name to the host
				}
				else if (line.startsWith("NAMEACCEPTED ")){ // TODO Do we need this
					//textfield.setDisable(false);
					//textarea.setDisable(false);

					String lights = line.substring(line.indexOf(":") + 1, line.length());
					String[] lightsA = lights.split(",");
					System.out.println(lightsA);

					if(lightsA[0].equals("1")){
						GameGUI.circle1.setFill(Color.LIME);
					}
					if(lightsA[1].equals("1")){
						GameGUI.circle2.setFill(Color.LIME);
					}
					if(lightsA[2].equals("1")){
						GameGUI.circle3.setFill(Color.LIME);
					}
					if(lightsA[3].equals("1")){
						GameGUI.circle4.setFill(Color.LIME);
					}


				}
				else if (line.startsWith("MESSAGE ")){ // If the message sent from the host starts with MESSAGE proceed
					//String namesL = line.toString().substring(line.indexOf("[") + 1, line.length() - 1); // Creates a string of names from the list sent by the host
					System.out.println(line);
					String namesL = line.substring(line.indexOf(":") + 1, line.length());
					String[] namesA = namesL.split(", ");
					int x = namesL.length() - namesL.replace(",", "").length() + 1;
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.nameLRQ.setText(namesL); //Updates the clients GUI
							GameGUI.namesGPRQ.getChildren().clear();

							System.out.println(namesL);

							for(int i = k; i < x; i++){
								switch(i + 1){
								case 1:
									Label nameL1 = new Label(namesA[0]);
									circle1.setFill(Color.DARKGREEN);
									readyCB1.selectedProperty().addListener(new ChangeListener<Boolean>(){

										public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
											if (t1) {
												circle1.setFill(Color.LIME);

											} else {
												circle1.setFill(Color.DARKGREEN);
											}
										}

									});
									circle1.setStroke(Color.BLACK);
									circle1.setStrokeWidth(2);
									nameBP1.setLeft(nameL1);
									nameBP1.setRight(GameGUI.circle1);
									if(nameL1.toString().substring(nameL1.toString().indexOf("'") + 1, nameL1.toString().length() - 1).equals(name)){
										nameBP1.setCenter(readyCB1);
									}
									nameBP1.setPrefWidth(700 / 2);
									nameL1.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
											"-fx-font-weight: bold;" +  // Sets the font to bold
											"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
									//		"-fx-border-color: Black"); // Sets the border to black
									nameBP1.setStyle("-fx-border-color: Black");
									nameBP1.setPadding(new Insets(10));
									k++;
									break;
								case 2:
									Label nameL2 = new Label(namesA[1]);
									circle2.setFill(Color.DARKGREEN);
									readyCB2.selectedProperty().addListener(new ChangeListener<Boolean>(){

										public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
											if (t1) {
												circle2.setFill(Color.LIME);
												out.println("2A");

											} else {
												circle2.setFill(Color.DARKGREEN);
												out.println("2B");
											}
										}

									});
									circle2.setStroke(Color.BLACK);
									circle2.setStrokeWidth(2);
									nameBP2.setLeft(nameL2);
									nameBP2.setRight(GameGUI.circle2);
									if(nameL2.toString().substring(nameL2.toString().indexOf("'") + 1, nameL2.toString().length() - 1).equals(name)){
										nameBP2.setCenter(readyCB2);
									}
									nameBP2.setPrefWidth(700 / 2);
									nameL2.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
											"-fx-font-weight: bold;" +  // Sets the font to bold
											"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
									//		"-fx-border-color: Black"); // Sets the border to black
									nameBP2.setStyle("-fx-border-color: Black");
									nameBP2.setPadding(new Insets(10));
									k++;
									break;
								case 3:
									Label nameL3 = new Label(namesA[2]);
									circle3.setFill(Color.DARKGREEN);
									readyCB3.selectedProperty().addListener(new ChangeListener<Boolean>(){

										public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
											if (t1) {
												circle3.setFill(Color.LIME);
												out.println("3A");

											} else {
												circle3.setFill(Color.DARKGREEN);
												out.println("3B");
											}
										}

									});
									circle3.setStroke(Color.BLACK);
									circle3.setStrokeWidth(2);
									nameBP3.setLeft(nameL3);
									nameBP3.setRight(GameGUI.circle3);
									if(nameL3.toString().substring(nameL3.toString().indexOf("'") + 1, nameL3.toString().length() - 1).equals(name)){
										nameBP3.setCenter(readyCB3);
									}
									nameBP3.setPrefWidth(700 / 2);
									nameL3.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
											"-fx-font-weight: bold;" +  // Sets the font to bold
											"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
									//		"-fx-border-color: Black"); // Sets the border to black
									nameBP3.setStyle("-fx-border-color: Black");
									nameBP3.setPadding(new Insets(10));
									k++;
									break;
								case 4:
									Label nameL4 = new Label(namesA[3]);
									circle4.setFill(Color.DARKGREEN);
									readyCB4.selectedProperty().addListener(new ChangeListener<Boolean>(){

										public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
											if (t1) {
												//circle4.setFill(Color.LIME);
												out.println("4A");

											} else {
												//circle4.setFill(Color.DARKGREEN);
												out.println("4B");
											}
										}

									});
									circle4.setStroke(Color.BLACK);
									circle4.setStrokeWidth(2);
									nameBP4.setLeft(nameL4);
									nameBP4.setRight(GameGUI.circle4);
									if(nameL4.toString().substring(nameL4.toString().indexOf("'") + 1, nameL4.toString().length() - 1).equals(name)){
										nameBP4.setCenter(readyCB4);
									}
									nameBP4.setPrefWidth(700 / 2);
									nameL4.setStyle("-fx-font-size: 22px;" + // Sets the font size of the label to 22 pixels
											"-fx-font-weight: bold;" +  // Sets the font to bold
											"-fx-text-stroke: 5;");  // Sets the stroke of the font to 5
									//		"-fx-border-color: Black"); // Sets the border to black
									nameBP4.setStyle("-fx-border-color: Black");
									nameBP4.setPadding(new Insets(10));
									k++;
									break;
								}
							}
							switch(x){
							case 2:
								GameGUI.namesGPRQ.add(nameBP1, 0, 0);
								GameGUI.namesGPRQ.add(nameBP2, 0, 1);
								break;
							case 3:
								GameGUI.namesGPRQ.add(nameBP1, 0, 0);
								GameGUI.namesGPRQ.add(nameBP2, 0, 1);
								GameGUI.namesGPRQ.add(nameBP3, 0, 2);
								break;
							case 4:
								GameGUI.namesGPRQ.add(nameBP1, 0, 0);
								GameGUI.namesGPRQ.add(nameBP2, 0, 1);
								GameGUI.namesGPRQ.add(nameBP3, 0, 2);
								GameGUI.namesGPRQ.add(nameBP4, 0, 3);
								break;
							}
							names = namesA;
						}
					});
				}
				else if(line.startsWith("1A")){
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle1.setFill(Color.LIME);
							System.out.println(line);
						}
					});
				}
				else if(line.startsWith("1B")){
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle1.setFill(Color.DARKGREEN);
						}
					});
				}
				else if(line.startsWith("2A")){
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle2.setFill(Color.LIME);
							System.out.println(line.toString());
						}
					});
				}
				else if(line.startsWith("2B")){
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle2.setFill(Color.DARKGREEN);
							System.out.println(line.toString());
						}
					});
				}
				else if(line.startsWith("3A")){
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle3.setFill(Color.LIME);
						}
					});
				}
				else if(line.startsWith("3B")){
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle3.setFill(Color.DARKGREEN);
						}
					});
				}
				else if(line.startsWith("4A")){
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle4.setFill(Color.LIME);
						}
					});
				}
				else if(line.startsWith("4B")){
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.circle4.setFill(Color.DARKGREEN);
						}
					});
				}
				else if(line.startsWith("CHANGE")){
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.gameStart();
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