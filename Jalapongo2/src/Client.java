/*import*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class Client{
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
		
		/*
		//Layout message transmission GUI
		Stage stage = new Stage();
		Scene scene = new Scene(new Group(),200,200);
		textfield.setDisable(true);
		textarea.setDisable(true);
		stage.setScene(scene);
		stage.show();
		
		//Add listener
		textfield.setOnKeyTyped(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent ke){
				textfield.appendText("");
			}
		});
		*/
		
		try {
			run();
		} catch (IOException e) {
			System.out.println("Error connecting to user");
		}
	}
	
	
	private String getName(){
		return name;
	}
	
	//connect to server and enter processing loop
	private void run() throws IOException{
		
		//Make connection and initialize streams
		Socket socket = new Socket(ipAddress,port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(),true);
		
		System.out.println("test");
		//Submit name and get verification that it is accepted
		while(true){
			String line = in.readLine();
			if(line.startsWith("SUBMITNAME")){
				out.println(getName());
			}
			else if (line.startsWith("NAMEACCEPTED")){
				textfield.setDisable(false);
				textarea.setDisable(false);
			}
			else if (line.startsWith("MESSAGE")){
				textarea.appendText(line.substring(8) + '\n');
			}
		}//end while loop
	}//end run
		
	//run the client
	public static void main(String[] args) throws Exception{
		Client user = new Client(port,ipAddress,name);
		user.run();
	}//end main
	
}//end Client