//Nick Added This for the quiz
//Changed the package
import java.util.HashSet;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/**
 * @author Leslie
 * @version 1.0
 * @created 02-Mar-2015 3:19:44 PM
 */

// This is for the quiz Michael Miller
public class GameGUI extends Application {

	/*
	private Button applyBOM;
	private Button backBCS;
	private Button backBOM;
	private Button backBUQ;
	private Button connectBCS;
	private Button helpBHS;
	private TextField helpTfHM;
	private Button hostBCS;
	private Label ipLCS;
	private Label ipLHS;
	private TextField ipTfCS;
	private TextField ipTfHS;
	private CheckBox musicCbOM;
	private Label nameLUQ;
	private Button okBSS;
	private Button optionsBSM;
	private Button playBCS;
	private Button playBSM;
	private Button playBUQ;
	private Label portLCS;
	private Label portLHS;
	private TextField portTfCS;
	private TextField portTfHS;
	private Button quitBSM;
	private CheckBox readyCbuQ;
	private Circle readyCUQ;
	private CheckBox soundCbOM;
	private TextField summaryTfSS;
	public Ball m_Ball;
	public Paddle m_Paddle;
	*/
	
	private int sceneWH = 700;
	private static HashSet<String> names = new HashSet<String>();
	private static Label nameLRQ;
	private int port;
	
	public void start(Stage primaryStage) throws Exception {
		
		// Start Screen GUI Menu
		// Construct GUI Opjects
		BorderPane startSMBP = new BorderPane();
		VBox buttonSMVB = new VBox();
		Scene startMScene = new Scene(startSMBP, sceneWH,sceneWH);
		
		// Controls
		Button playBSM = new Button("Play");
		Button optionsBSM = new Button("Options");
		Button quitBSM = new Button("Quit");
		
		buttonSMVB.getChildren().addAll(playBSM, optionsBSM, quitBSM);
		buttonSMVB.setSpacing(50);
		buttonSMVB.setAlignment(Pos.CENTER);
		
		startSMBP.setCenter(buttonSMVB);
		
//*******************************************************************************
		
		//Options Menu
		BorderPane optionsBP = new BorderPane();
		VBox optionsVBox = new VBox();
		HBox optionsHBox = new HBox();
		Scene optionsScene = new Scene(optionsBP,sceneWH,sceneWH);
		
		//Create checkboxes
		CheckBox soundfxCB = new CheckBox("Sound Effects");
		CheckBox musicCB = new CheckBox("Music");
		
		//Create buttons
		Button backBOS = new Button("Back");
			   backBOS.setFont(new Font(25));
		//Add checkboxes to VBox
		optionsVBox.getChildren().addAll(musicCB,soundfxCB);
		optionsVBox.setAlignment(Pos.CENTER);
		optionsVBox.setSpacing(50);
		
		//Add button to VBox
		optionsHBox.getChildren().addAll(backBOS);
		optionsHBox.setAlignment(Pos.CENTER);
		
		//Set VBox in BorderPane
		optionsBP.setCenter(optionsVBox);
		
		//Set HBox in BorderPane
		optionsBP.setBottom(backBOS);
		
//*******************************************************************************
		
		// Choice Menu GUI
		//construct GUI objects
		BorderPane choiceBP = new BorderPane();
		VBox choiceVBox = new VBox();
		Scene choiceScene = new Scene(choiceBP, sceneWH,sceneWH);
		
		//Buttons
		Button playBCS = new Button("Join");
		Button hostBCS = new Button("Host");
		Button backBCS = new Button("Back");
		
		//Set Join/Host buttons in center of VBox
		choiceVBox.getChildren().addAll(playBCS, hostBCS);
		choiceVBox.setSpacing(50);
		choiceVBox.setAlignment(Pos.CENTER);
		
		//Add VBox to BorderPane
		choiceBP.setCenter(choiceVBox);
		//Put Back button at bottom left
		choiceBP.setBottom(backBCS);
		
//*******************************************************************************		
	
		//Host Menu GUI
		ObservableList AIoptions = FXCollections.observableArrayList("1", "2", "3"); // TODO add a 0 option in second sprint
		
		BorderPane bpHOS = new BorderPane();
		VBox vbHOS = new VBox();
		HBox hbName = new HBox();
		HBox hbPort = new HBox();
		HBox hbAI = new HBox();
	// Make Text 	
		Text nameText = new Text("Name: ");
		Text AIText = new Text("# of AI: ");
		Text portText = new Text("Port #: ");
		TextField nameTF = new TextField("Joey");
		TextField portTFHS = new TextField("7777");
	// Make Buttons	
		ComboBox AICB = new ComboBox(AIoptions);
		AICB.setValue("1");
		Button backHO = new Button("Back");
		Button optionsHO = new Button("Options");
			optionsHO.setFont(new Font(24));
			optionsHO.setMaxWidth(300);
		Button startHostingHO = new Button("Start Hosting");
			startHostingHO.setFont(new Font(24));
			startHostingHO.setMaxWidth(300);
		Button randomBHS = new Button ("Randomize");
			
		hbName.getChildren().addAll(nameText, nameTF);
		hbName.setAlignment(Pos.CENTER);
		hbName.setSpacing(75);
		
		hbPort.getChildren().addAll(portText, portTFHS,randomBHS);
		hbPort.setAlignment(Pos.CENTER);
		hbPort.setSpacing(75);
		
		hbAI.getChildren().addAll(AIText, AICB);
		hbAI.setAlignment(Pos.CENTER);
		hbAI.setSpacing(75);
		
		vbHOS.getChildren().addAll(hbName, hbPort, hbAI, optionsHO, startHostingHO);
		vbHOS.setAlignment(Pos.CENTER);
		vbHOS.setSpacing(50);
		
		bpHOS.setCenter(vbHOS);
		bpHOS.setBottom(backHO);
		
		Scene sceneHOS = new Scene(bpHOS, sceneWH,sceneWH);

//*******************************************************************************
		
ObservableList livesoptions = FXCollections.observableArrayList("5", "10", "25", "50");
		
		BorderPane bpGOS = new BorderPane();
		VBox powerUpVB = new VBox();
		HBox liveshb = new HBox();
		HBox bottomhb = new HBox();
		
		Text powerUpsText = new Text("Power Ups:                       "
				+ "                            ");
			powerUpsText.setFont(new Font(20));
		CheckBox fastSlowCB = new CheckBox("Fast/Slow");
		CheckBox sheildCB = new CheckBox("Sheild     ");
		CheckBox multiCB = new CheckBox("Multi Ball"); 
		CheckBox bigSmallCB = new CheckBox("Big/Small ");
		CheckBox magnetCB = new CheckBox("Magnet    ");
		CheckBox crazyCB = new CheckBox("CrazyBall ");
		
		Text livesText = new Text("# of Lives:        ");
			livesText.setFont(new Font(20));
		ComboBox livesCB = new ComboBox(livesoptions);
		livesCB.setValue("5");
		
		Button okaybt = new Button("Okay");
			okaybt.setFont(new Font(24));
			//okaybt.setMaxWidth(300);
		Button cancelbt = new Button("Cancel");
			cancelbt.setFont(new Font(24));
			//cancelbt.setMaxWidth(300);
		
		liveshb.getChildren().addAll(livesText, livesCB);
		liveshb.setAlignment(Pos.CENTER);
		
		bottomhb.getChildren().addAll(okaybt, cancelbt);
		bottomhb.setAlignment(Pos.CENTER);
		bottomhb.setSpacing(50);
		
		powerUpVB.getChildren().addAll(liveshb, powerUpsText, fastSlowCB, sheildCB,
				multiCB, bigSmallCB, magnetCB, crazyCB, bottomhb);
		powerUpVB.setAlignment(Pos.CENTER);		
		powerUpVB.setSpacing(35);
		
		bpGOS.setCenter(powerUpVB);
		
		Scene sceneGOS = new Scene(bpGOS, sceneWH,sceneWH);
		
//*******************************************************************************		
		//Join Screen GUI
		//create GUI objects
				BorderPane jsBP = new BorderPane();
				VBox vbJS = new VBox();
				HBox hbNameJS = new HBox();
				HBox hbPortJS = new HBox();
				HBox hbIP = new HBox();
			
			// Make text	
				Text nameTextJS = new Text("Name: ");
				Text portTextJS = new Text("Port #:");
				Text ipText = new Text("IP address:");
				
				TextField nameTFJS = new TextField(" ");
				TextField portTFJS= new TextField(" ");
				TextField ipTF = new TextField("");
				
			// Get values from text fields
				String name = nameTFJS.getText();
				String portS = portTFJS.getText();
				//Commented out so code will run
				//int port = Integer.parseInt(portS);
				
			//Call method to pass values to client class
				
				
			// Make Buttons	
				Button backJS = new Button("Back");
				Button joinGameJS = new Button("Join Game");
					joinGameJS.setFont(new Font(24));
					joinGameJS.setMaxWidth(300);
				
				hbNameJS.getChildren().addAll(nameTextJS, nameTFJS);
				hbNameJS.setAlignment(Pos.CENTER);
				hbNameJS.setSpacing(75);
				
				hbPortJS.getChildren().addAll(portTextJS, portTFJS);
				hbPortJS.setAlignment(Pos.CENTER);
				hbPortJS.setSpacing(10);
				
				hbIP.getChildren().addAll(ipText,ipTF);
				hbIP.setAlignment(Pos.CENTER);
				hbIP.setSpacing(75);
				
				vbJS.getChildren().addAll(hbNameJS, hbPortJS,hbIP,joinGameJS);
				vbJS.setAlignment(Pos.CENTER);
				vbJS.setSpacing(50);
				
				jsBP.setCenter(vbJS);
				jsBP.setBottom(backJS);
			
			
				Scene jsScene = new Scene(jsBP, sceneWH,sceneWH);
//*******************************************************************************			
				
				BorderPane readyQBP = new BorderPane();
				BorderPane lowerRQBP = new BorderPane();
				
				nameLRQ = new Label();
				
				Button startRQB = new Button("Start");
				Button disconnectRQB = new Button("Disconnect");
				
				lowerRQBP.setRight(startRQB);
				lowerRQBP.setLeft(disconnectRQB);
				lowerRQBP.setPadding(new Insets(10));
				
				readyQBP.setBottom(lowerRQBP);
				readyQBP.setRight(nameLRQ);
				
				Scene readyQS = new Scene(readyQBP, sceneWH,sceneWH);
				
		//*******************************************************************************
			
		
		quitBSM.setOnMouseClicked(e -> System.exit(0));
		playBSM.setOnMouseClicked(e -> primaryStage.setScene(choiceScene));
		optionsBSM.setOnMouseClicked(e -> primaryStage.setScene(optionsScene));
		
		backBOS.setOnMouseClicked(e -> primaryStage.setScene(startMScene));
		
		hostBCS.setOnMouseClicked(e -> primaryStage.setScene(sceneHOS));
		backBCS.setOnMouseClicked(e -> primaryStage.setScene(startMScene));
		playBCS.setOnMouseClicked(e -> primaryStage.setScene(jsScene));
		
		backJS.setOnMouseClicked(e -> primaryStage.setScene(choiceScene));
		//joinGameJS.setOnMouseClicked(e -> client(Integer.parseInt(portTFJS.getText()),ipTF.getText(),nameTFJS.getText()));
		joinGameJS.setOnMouseClicked(e -> readyScreen(primaryStage, readyQS, nameTFJS.getText(),Integer.parseInt(portTFJS.getText())));
		
		startHostingHO.setOnMouseClicked(e -> readyScreen(primaryStage, readyQS, nameTF.getText(), Integer.parseInt(portTFHS.getText())));
		optionsHO.setOnMouseClicked(e -> primaryStage.setScene(sceneGOS));
		backHO.setOnMouseClicked(e -> primaryStage.setScene(choiceScene));
		
		okaybt.setOnMouseClicked(e -> primaryStage.setScene(sceneHOS));
		
		disconnectRQB.setOnMouseClicked(e -> primaryStage.setScene(choiceScene));
		
		randomBHS.setOnMouseClicked( e -> {
			int portGen = (int)(Math.random()*9000)+1000;
			String parsePortGen = Integer.toString(portGen);
			portTFHS.setText(parsePortGen);
		});
		
		
		//Added by Jon on March 10
		//Launches the game screen from the "Start" button under:
		//Play -> Host -> Start Hosting -> Start
		GameScreen game = new GameScreen();
		startRQB.setOnMouseClicked(e -> primaryStage.setScene(game.getGameScene()));
		startRQB.setOnMousePressed(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent me) {
		        game.continuousUpdate();;
		    }
		});

		primaryStage.setScene(startMScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		
	}

	//method to transfer values to client class
	public void client(int portNumber, String IP, String uName){
		Client clientObject = new Client(portNumber,IP,uName);
	}
	
	public void readyScreen(Stage primaryStage, Scene scene, String hostName, int port){ //TODO improve this method for multiplayer
		names.add(hostName);
		String hostN = names.toString();
		nameLRQ.setText(hostN);
		Thread Host = new Thread(new Host(port));
		Host.start();
		primaryStage.setScene(scene);
	}
	
	public static void main(String[] args){
		GameGUI.launch(args);
	}
	
	/*
	public GameGUI() {

	}

	public void finalize() throws Throwable {

	}

	 
	public changeScene(Scene){

	}
	 
	public launchServer(int port){

	}

	public main(){

	}

	public run(){

	}


	public server(int port){

	}
	
	
	*/
	
	
}//end Game GUI
// Added for testing
