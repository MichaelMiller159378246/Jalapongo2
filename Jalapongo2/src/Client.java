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
					String namesL = line.toString().substring(line.indexOf("[") + 1, line.length() - 1); // Creates a string of names from the list sent by the host
					String[] namesA = namesL.split(", ");
					int x = namesL.length() - namesL.replace(",", "").length() + 1;
					Platform.runLater(new Runnable() { // Runs when it gets the chance
						public void run() { //Runs
							GameGUI.nameLRQ.setText(namesL); //Updates the clients GUI

							System.out.println(name);
							GameGUI.namesGPRQ.getChildren().clear();
							for(int i = 0; i < x; i++){
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
								if(nameL.toString().substring(nameL.toString().indexOf("'") + 1, nameL.toString().length() - 1).equals(name)){
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