 // Imports
import java.net.URL;

import javafx.application.Application; // Imports Application
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler; // Imports EventHandlers
import javafx.geometry.Insets; // Imports Insets
import javafx.geometry.Pos; // Imports POS
import javafx.scene.Scene; // Imports scene
import javafx.scene.control.Button; // Imports Buttons
import javafx.scene.control.CheckBox; // Imports CheckBox
import javafx.scene.control.ComboBox; // Imports ComboBox
import javafx.scene.control.Label; // Imports Label
import javafx.scene.control.TextField; // Imports TextField
import javafx.scene.layout.BorderPane; // Imports BorderPane
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox; // Imports HBox
import javafx.scene.layout.VBox; // Imports VBox
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font; // Imports Font
import javafx.scene.text.Text; // Imports Text
import javafx.stage.Stage; // Imports Stage
import javafx.stage.WindowEvent;


public class GameGUI extends Application {

	private static Stage globalS;
	public static GameScreen globalG;
	private static Scene sbScene;
	private static int sceneWH = 700; // Int used to control screen size
	public static Label nameLRQ; // Public label so the host class can edit the data
	public static GridPane namesGPRQ; // Public HBox so the host class can edit the data
	public static Circle circle1 = new Circle(25);
	public static Circle circle2 = new Circle(25);
	public static Circle circle3 = new Circle(25);
	public static Circle circle4 = new Circle(25);
	public static CheckBox readyCB1 = new CheckBox();
	public static CheckBox readyCB2 = new CheckBox();
	public static CheckBox readyCB3 = new CheckBox();
	public static CheckBox readyCB4 = new CheckBox();
	public static BorderPane nameBP1 = new BorderPane();
	public static BorderPane nameBP2 = new BorderPane();
	public static BorderPane nameBP3 = new BorderPane();
	public static BorderPane nameBP4 = new BorderPane();

	public static Button startRQB = new Button("Start"); // Creates a start button
	//hosting options
	public static CheckBox fastCB;
	public static CheckBox shieldCB;
	public static CheckBox livesCB;
	public static CheckBox bigCB;
	public static CheckBox slowCB;
	public static CheckBox smallCB;
	public static CheckBox flipCB;
	public static CheckBox stallCB;
	public static CheckBox multiCB;
	public static CheckBox musicCB;
	
	//For junit tests
	public static Button optionsHO;
	public static Button playBSM;
	public static Button hostBCS;

	public static ComboBox<Integer> livesCoB;
	public static ComboBox AICB = new ComboBox<Integer>();
	
	private Thread clientThread;
	
	/**
	 * Constructor
	 */
	public GameGUI(){
		
	}
	
	/**
	 * @param primaryStage - Used to set the stage of the program
	 * This just displays the GUI
	 */
	public GameGUI(Stage primaryStage){
		try {
			start(primaryStage);
		} catch (Exception e) {
			System.out.println("Error Creating Stage");
		}
	}
	
	/**
	 * @author Nick, Dalton, Mike, Jon, Leslie
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * Starts the code
	 */
	@SuppressWarnings("unchecked")
	public void start(Stage primaryStage) throws Exception {

		//Indicator lights for if user is ready
		//Starts Dark green if user is not ready
		circle1.setFill(Color.DARKGREEN);
		circle1.setStroke(Color.BLACK);
		circle1.setStrokeWidth(2);
		circle2.setFill(Color.DARKGREEN);
		circle2.setStroke(Color.BLACK);
		circle2.setStrokeWidth(2);
		circle3.setFill(Color.DARKGREEN);
		circle3.setStroke(Color.BLACK);
		circle3.setStrokeWidth(2);
		circle4.setFill(Color.DARKGREEN);
		circle4.setStroke(Color.BLACK);
		circle4.setStrokeWidth(2);

		//*******************************************************************************
		// Start Screen GUI Menu

		// Construct GUI Objects
		BorderPane startSMBP = new BorderPane(); //Creates a BorderPane
		VBox buttonSMVB = new VBox(); // Creates a VBox to house the buttons
		
		// Controls
		Button playBSM = new Button("Play"); // Creates a play button
			playBSM.setMaxWidth(300);
			playBSM.setFont(new Font(24));
		Button optionsBSM = new Button("Options"); // creates a options button
			optionsBSM.setMaxWidth(300);
			optionsBSM.setFont(new Font(24));
		Button quitBSM = new Button("Quit"); // Creates a quit button
			quitBSM.setMaxWidth(300);
			quitBSM.setFont(new Font(24));
		
		
		//Text
		Text title = new Text();
		title.setFont(new Font(50));
		title.setText("Jalapo\u00F1go"); //This weird code makes the spanish n
		title.setFill(Color.GREEN);
		
		
		//Alters VBox properties
		buttonSMVB.getChildren().addAll(title,playBSM, optionsBSM, quitBSM); //Adds the items to the VBox
		buttonSMVB.setSpacing(50); // Set the spacing between the items to 50
		buttonSMVB.setAlignment(Pos.CENTER); // Sets the alignment of the VBox to the center
		
		startSMBP.setCenter(buttonSMVB); // Adds the VBox to the center of the BorderPane
		Scene startMScene = new Scene(startSMBP, sceneWH,sceneWH); // Creates the scene

		//*******************************************************************************	
		
		//Music
		String song = "pongSong.mp3";

		//Add song to GameScreen
		URL resource = getClass().getResource(song);
		Media media = new Media(resource.toString());
		MediaPlayer songPlayer = new MediaPlayer(media);
		songPlayer.setCycleCount(MediaPlayer.INDEFINITE);


		//*******************************************************************************		
		//Options Menu
		BorderPane optionsBP = new BorderPane();
		VBox optionsVBox = new VBox();
		Scene optionsScene = new Scene(optionsBP,sceneWH,sceneWH);

		//Create checkbox for music
		musicCB = new CheckBox("Music");

		//Create buttons
		Button backBOS = new Button("Back");
			backBOS.setFont(new Font(24));
			backBOS.setMaxWidth(100);
		//Add checkboxes to VBox
		optionsVBox.getChildren().addAll(musicCB,backBOS);
		optionsVBox.setAlignment(Pos.CENTER);
		optionsVBox.setSpacing(50);

		//Preset check box for music as unchecked
		musicCB.setIndeterminate(false);
		musicCB.setSelected(false);

		//Set VBox in BorderPane
		optionsBP.setCenter(optionsVBox);

		//*******************************************************************************

		// Choice Menu GUI
		//construct GUI objects
		BorderPane choiceBP = new BorderPane();
		VBox choiceVBox = new VBox();
		Scene choiceScene = new Scene(choiceBP, sceneWH,sceneWH);

		//Buttons

		Button playBCS = new Button("Join");
			playBCS.setMaxWidth(300);
			playBCS.setFont(new Font(24));
		Button hostBCS = new Button("Host");
		 	hostBCS.setMaxWidth(300);
			hostBCS.setFont(new Font(24));
		Button backBCS = new Button("Back");
			backBCS.setMaxWidth(300);
			backBCS.setFont(new Font(24));
		Button helpBCS = new Button("Help");
			helpBCS.setMaxWidth(300);
			helpBCS.setFont(new Font(24));

		//Set Join/Host buttons in center of VBox
		choiceVBox.getChildren().addAll(playBCS, hostBCS,helpBCS,backBCS);
		choiceVBox.setSpacing(50);
		choiceVBox.setAlignment(Pos.CENTER);

		//Add VBox to BorderPane
		choiceBP.setCenter(choiceVBox);
		
		//*******************************************************************************		
		//Help Menu
		//create GUI objects
				BorderPane bpHM = new BorderPane();
				BorderPane bpHowToPlayHM = new BorderPane();
				BorderPane bpJoinGameHM = new BorderPane();
				BorderPane bpHostGameHM = new BorderPane();
				
				VBox initialHMVB = new VBox();
				VBox howToPlayHMVB = new VBox();
				VBox joinGameHMVB = new VBox();
				VBox hostGameHMVB = new VBox();
				
			//create buttons for initial help screen
			Button howToPlayHMB = new Button("How to Play");
				howToPlayHMB.setMaxWidth(300);
				howToPlayHMB.setFont(new Font(24));
			Button joinGameHMB = new Button("Join a Game");
				joinGameHMB.setMaxWidth(300);
				joinGameHMB.setFont(new Font(24));
			Button hostGameHMB = new Button("Host a Game");
				hostGameHMB.setMaxWidth(300);
				hostGameHMB.setFont(new Font(24));
			Button backIHMB = new Button("Back");
				backIHMB.setMaxWidth(300);
				backIHMB.setFont(new Font(24));
			Button backHTPHMB = new Button("Back");
				backHTPHMB.setMaxWidth(300);
				backHTPHMB.setFont(new Font(24));
			Button backJGHMB = new Button("Back");
				backJGHMB.setMaxWidth(300);
				backJGHMB.setFont(new Font(24));
			Button backHGHMB = new Button("Back");
				backHGHMB.setMaxWidth(300);
				backHGHMB.setFont(new Font(24));

				
				//add buttons for initial help screen to VBox
				initialHMVB.getChildren().addAll(howToPlayHMB,joinGameHMB,hostGameHMB,backIHMB);
				initialHMVB.setAlignment(Pos.CENTER);
				initialHMVB.setSpacing(30);
				
				//add VBox to initial border pane
				bpHM.setCenter(initialHMVB);
				
				//Make text about how to play
				Text howToPlayTitleText = new Text("How To Play:");
				howToPlayTitleText.setFont(new Font(30));
				howToPlayTitleText.setUnderline(true);
				
				Text howToPlayInfoText = new Text("The objective of Jalapo\u00F1go is to be the last player with lives remaining.\n\n"
						+ "Every player begins the game with five lives.\n\n"
						+ "To avoid losing lives you must navigate your paddle so that the ball does not hit the wall behind "
						+ "your paddle.\n\n"
						+ "When a player reaches 0 lives, their paddle will take up their side of the screen "
						+ "and they will be out.\n\n");
				
				Text controlsTitleText = new Text("Controls:");
				controlsTitleText.setFont(new Font(20));
				controlsTitleText.setUnderline(true);
				
				Text controlsInfoText = new Text("To control your paddle use the left and right arrow keys.\n\n"
						+ "Controls may be reversed if you hit a reverse controls power-up.\n\n"
						+ "If controls are reversed you must use the left arrow key to move right, and the right arrow key to move left.\n\n"
						+ "Reversed controls will remain until the power-up is disabled\n\n");
				
				Text powerupsTitleText = new Text("Power-ups:");
				powerupsTitleText.setFont(new Font(20));
				powerupsTitleText.setUnderline(true);
				
				Text powerupsInfoText = new Text("A power-up can be identified by its color.\n"
						+ "Power-ups are applied to the player who hit the ball last before collision with the power-up.\n"
						+ "Flip: changes controls - color = RED\n"
						+ "Add Lives: adds a live to the player - color = ORANGE\n"
						+ "Shield: adds a shield behind player - color = PURPLE\n"
						+ "Large Paddle: makes paddle larger - color =  PINK\n"
						+ "Small Paddle makes paddle smaller - color = YELLOW\n"
						+ "Slow Ball: makes ball move slower - color = BLUE\n"
						+ "Fast Ball: makes ball move faster - color = GREEN\n"
						+ "Stall: prevents paddle movement for a certain amount of time - color = CYAN \n"
						+ "Multi-Ball: adds a random number of balls to game - color = BROWN \n");
				
				
				//add text for how to play help screen to VBox
				howToPlayHMVB.getChildren().addAll(howToPlayTitleText,howToPlayInfoText,controlsTitleText,controlsInfoText,powerupsTitleText,powerupsInfoText,backHTPHMB);
				howToPlayHMVB.setAlignment(Pos.CENTER);
				
				//add VBox to how to play border pane
				bpHowToPlayHM.setCenter(howToPlayHMVB);
				
				//Make text about how to join a game
				Text joinGameTitleText = new Text("Join a Game:");
				joinGameTitleText.setFont(new Font(30));
				joinGameTitleText.setUnderline(true);
				
				Text joinGameInfoText = new Text("To join a game selected by another user requires the port number"
						+ "and the IP address of the host's server.\n\n"
						+ "Once these have been obtained from the host enter the values into their respective fields.\n\n"
						+ "Enter a username of your choice (up to 10 characters).\n\n"
						+ "Clicking on join game will take you to the ready screen.\n\n"
						+ "There you will see a list of the users connected to the server.\n\n"
						+ "Toggle the checkbox next to the username you entered when you are ready to play.\n\n"
						+ "The indicator light next to your username will change colors.\n\n"
						+ "The host will be notified when all players are ready to begin.\n\n"
						+ "The game will start when the host selects start game.\n\n"
						+ "Exiting the screen at any time during this process will disconnect you from the server.\n\n");
				
				//add items to join game VBox
				joinGameHMVB.getChildren().addAll(joinGameTitleText,joinGameInfoText,backJGHMB);
				joinGameHMVB.setAlignment(Pos.CENTER);
				
				//add join game VBox to border pane
				bpJoinGameHM.setCenter(joinGameHMVB);
				
				//Make text about how to host a game
				Text hostGameTitleText = new Text("Host a Game:");
				hostGameTitleText.setFont(new Font(30));
				hostGameTitleText.setUnderline(true);
				
				Text hostGameInfoText = new Text("To host a game first enter your desired username (up to 10 characters).\n\n"
						+ "Next either select or randomize a 4 digit port number.\n\n"
						+ "Make note of the port number as this is how players will connect to your game.\n\n"
						+ "Select the desired number of AI's (computer players)\n\n"
						+ "Note: if you want to play a single player game of Jalapongo select 3 AI's.\n\n"
						+ "Otherwise, select the number of AI's based on the number of players expected to join your server.\n\n");
				
				Text hostOptionsTitleText = new Text("Host Options:");
				hostOptionsTitleText.setFont(new Font(20));
				hostOptionsTitleText.setUnderline(true);
				
				Text hostOptionsInfoText = new Text("As a host you can adjust game parameters in the options menu\n\n"
						+ "Changing the number of lives will adjust how many lives each player starts with.\n\n"
						+ "Toggling a power-up on or off will determine whether that power-up is generated.\n\n");
				
				//add items to host game VBox
				hostGameHMVB.getChildren().addAll(hostGameTitleText,hostGameInfoText,hostOptionsTitleText,hostOptionsInfoText,backHGHMB);
				hostGameHMVB.setAlignment(Pos.CENTER);
				
				//add host game VBox to border pane
				bpHostGameHM.setCenter(hostGameHMVB);
				
				//create scenes
				Scene sceneIHM = new Scene(bpHM, sceneWH,sceneWH);
				Scene sceneHTPHM = new Scene(bpHowToPlayHM,sceneWH,sceneWH);
				Scene sceneJGHM = new Scene(bpJoinGameHM,sceneWH,sceneWH);
				Scene sceneHGHM = new Scene(bpHostGameHM,sceneWH,sceneWH);

		
		//*******************************************************************************	
		
		//Host Menu GUI
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
		
		nameTF.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	            if (nameTF.getText().length() > 10) {
	                String s = nameTF.getText().substring(0, 10);
	                nameTF.setText(s);
	            }
	        }
	    });
		
		TextField portTFHS = new TextField("7777");
		// Make Buttons	
		AICB.getItems().addAll(0, 1, 2, 3);
		AICB.setValue(3);
		Button backHO = new Button("Back");
			backHO.setFont(new Font(24));
			backHO.setMaxWidth(150);
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

		vbHOS.getChildren().addAll(hbName, hbPort, hbAI, optionsHO, startHostingHO,backHO);
		vbHOS.setAlignment(Pos.CENTER);
		vbHOS.setSpacing(50);

		bpHOS.setCenter(vbHOS);
		
		Scene sceneHOS = new Scene(bpHOS, sceneWH,sceneWH);

		//*******************************************************************************
		//Host Options GUI
		BorderPane bpGOS = new BorderPane();
		VBox powerUpVB = new VBox();
		HBox liveshb = new HBox();
		HBox bottomhb = new HBox();

		Text powerUpsText = new Text("Power Ups:                               "
				+ "                            ");
		powerUpsText.setFont(new Font(24));
		fastCB = new CheckBox("Fast Ball       ");
		shieldCB = new CheckBox("Shield           ");
		livesCB = new CheckBox("Extra Lives    "); 
		bigCB = new CheckBox("Big Paddle    ");
		slowCB = new CheckBox("Slow Ball      ");
		smallCB = new CheckBox("Small Paddle ");
		flipCB = new CheckBox("Flip Controls ");
		stallCB = new CheckBox("Stall Controls");
		multiCB = new CheckBox("Multi Ball      ");

		Text livesText = new Text("# of Lives:        ");
		livesText.setFont(new Font(24));
		livesCoB = new ComboBox<Integer>();
		livesCoB.getItems().addAll(1, 5, 10, 25);
		livesCoB.setValue(5);

		Button okaybt = new Button("Okay");
		okaybt.setFont(new Font(24));	

		fastCB.setSelected(true);
		shieldCB.setSelected(true);
		livesCB.setSelected(true);
		bigCB.setSelected(true);
		slowCB.setSelected(true);
		smallCB.setSelected(true);
		flipCB.setSelected(true);
		stallCB.setSelected(true);
		multiCB.setSelected(true);

		liveshb.getChildren().addAll(livesText, livesCoB);
		liveshb.setAlignment(Pos.CENTER);

		bottomhb.getChildren().addAll(okaybt);
		bottomhb.setAlignment(Pos.CENTER);
		bottomhb.setSpacing(25);

		powerUpVB.getChildren().addAll(liveshb, powerUpsText, fastCB, slowCB, shieldCB,
				livesCB, bigCB, smallCB, flipCB, stallCB, multiCB, bottomhb);
		powerUpVB.setAlignment(Pos.CENTER);		
		powerUpVB.setSpacing(15);

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

		TextField nameTFJS = new TextField("");
		
		nameTFJS.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	            if (nameTFJS.getText().length() > 10) {
	                String s = nameTFJS.getText().substring(0, 10);
	                nameTFJS.setText(s);
	            }
	        }
	    });

		
		
		TextField portTFJS= new TextField("");
		TextField ipTF = new TextField("");				

		// Make Buttons	
		Button backJS = new Button("Back");
			backJS.setFont(new Font(24));
			backJS.setMaxWidth(150);
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

		vbJS.getChildren().addAll(hbNameJS, hbPortJS,hbIP,joinGameJS,backJS);
		vbJS.setAlignment(Pos.CENTER);
		vbJS.setSpacing(50);

		jsBP.setCenter(vbJS);
	
		Scene jsScene = new Scene(jsBP, sceneWH,sceneWH);
		//*******************************************************************************		
		//Ready Screen GUI

		//Construct GUI objects
		BorderPane readyQBP = new BorderPane(); // Creates a BorderPane
		BorderPane lowerRQBP = new BorderPane(); // Creates a BorderPane

		namesGPRQ = new GridPane();
		namesGPRQ.setPadding(new Insets(10));
		namesGPRQ.setVgap(20);
		namesGPRQ.setPrefWidth(sceneWH / 2);
;

		// Creates controls
		nameLRQ = new Label(); //Creates a label;
		Button disconnectRQB = new Button("Disconnect"); // Creates a disconnect button
			
		// Alters BorderPane properties
		lowerRQBP.setRight(startRQB); // Sets the button to the right side of the BorderPane
		lowerRQBP.setLeft(disconnectRQB); // Sets the button to the left side of the BorderPane
		lowerRQBP.setPadding(new Insets(10)); // Adds some padding to the BorderPane

		// Alters BorderPane properties
		readyQBP.setBottom(lowerRQBP); // Sets the BorderPane to the bottom of the BorderPane 
		readyQBP.setRight(nameLRQ); // Sets the Label to the right of the BorderPane
		readyQBP.setCenter(namesGPRQ);

		Scene readyQS = new Scene(readyQBP, sceneWH,sceneWH); // Creates the scene

	
		//Causes the Red exit button to terminate the program
		//This Allows the port to close
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });


		//*******************************************************************************
		//Events
		//Start Menu Scene Events
		quitBSM.setOnMouseClicked(e -> System.exit(0)); // If the user presses quit the system closes
		playBSM.setOnMouseClicked(e -> primaryStage.setScene(choiceScene)); // If the user presses play the scene changes to the choice scene
		optionsBSM.setOnMouseClicked(e -> primaryStage.setScene(optionsScene)); // If the user presses options the scene changes to the options scene

		//Options Menu Scene Events
		backBOS.setOnMouseClicked(e -> primaryStage.setScene(startMScene)); // If the user presses back the scene changes to the start menu scene 
		//toggle music
		
		musicCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (musicCB.isSelected()==true)
					songPlayer.play(); 
				else
					songPlayer.stop();	
			}			
		});
		

		//Choice Menu Scene Events
		hostBCS.setOnMouseClicked(e -> primaryStage.setScene(sceneHOS)); // If the user presses host the scene changes to the host menu scene 
		playBCS.setOnMouseClicked(e -> primaryStage.setScene(jsScene)); // If the user presses join the scene changes to the join menu scene 
		backBCS.setOnMouseClicked(e -> primaryStage.setScene(startMScene)); // If the user presses back the scene changes to the start menu scene 
		helpBCS.setOnMouseClicked(e -> primaryStage.setScene(sceneIHM));//If the user press help the screen changes to the help menu scene
		
		//Help Menu Scene Events
		backIHMB.setOnMouseClicked(e -> primaryStage.setScene(choiceScene)); //on press takes user back to choice screen
		howToPlayHMB.setOnMouseClicked(e -> primaryStage.setScene(sceneHTPHM));//on press takes user to how to play help menu
		backHTPHMB.setOnMouseClicked(e -> primaryStage.setScene(sceneIHM));//on press takes user back to initial help menu
		joinGameHMB.setOnMouseClicked(e -> primaryStage.setScene(sceneJGHM));//on press takes user to join game help menu
		backJGHMB.setOnMouseClicked(e -> primaryStage.setScene(sceneIHM));//on press takes user back to initial help menu
		hostGameHMB.setOnMouseClicked(e -> primaryStage.setScene(sceneHGHM));//on press takes user to host game help menu
		backHGHMB.setOnMouseClicked(e -> primaryStage.setScene(sceneIHM));//on press takes user back to initial help menu
		
		//Join Menu Scene Events
		backJS.setOnMouseClicked(e -> primaryStage.setScene(choiceScene)); // If the user presses back the scene changes to the choice menu scene 
		joinGameJS.setOnMouseClicked(e -> client(primaryStage, readyQS,Integer.parseInt(portTFJS.getText()),ipTF.getText(),nameTFJS.getText(), startRQB)); // If the user presses join then the client method is called 
		//joinGameJS.setOnMouseClicked(e -> readyScreen(primaryStage, readyQS, nameTFJS.getText(),Integer.parseInt(portTFJS.getText()))); //TODO possibly delete this

		//Host Menu Events
		startHostingHO.setOnMouseClicked(e -> host(primaryStage, readyQS, nameTF.getText(), Integer.parseInt(portTFHS.getText()), startRQB)); // If the user presses start hosting then the host method is called 
		optionsHO.setOnMouseClicked(e -> primaryStage.setScene(sceneGOS)); // If the user presses options the scene changes to the host options menu scene 
		backHO.setOnMouseClicked(e -> primaryStage.setScene(choiceScene)); // If the user presses back the scene changes to the choice menu scene 
		randomBHS.setOnMouseClicked( e -> { // If the user presses random then a port number is randomly generated
			int portGen = (int)(Math.random()*9000)+1000; // Randomly generates a number
			String parsePortGen = Integer.toString(portGen); // Creates a string from the integer
			portTFHS.setText(parsePortGen); // Displays the generated number
		});

		//Host Options Menu Events
		okaybt.setOnMouseClicked(e -> primaryStage.setScene(sceneHOS)); // If the user presses okay the scene changes to the host menu scene 

		// Ready Screen Menu Events
		disconnectRQB.setOnMouseClicked(e -> primaryStage.setScene(choiceScene)); // If the user presses disconnect the scene changes to the choice menu scene 
		disconnectRQB.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent event) {
	        	clientThread.stop();
       }
		});
				
		//*******************************************************************************
		
		// Alter stage properties
		primaryStage.setScene(startMScene); //Sets the scene of the stage
		primaryStage.setResizable(false); // Prevents anyone from changing the stage size
		primaryStage.show(); // Shows the stage
		
		globalS = primaryStage;

	}

	
	/**
	 * @param name1 - String, transfers the players name
	 * @param name2 - String, transfers the players name
	 * @param name3 - String, transfers the players name
	 * @param name4 - String, transfers the players name
	 * Starts the game for multiplayer
	 */
	public static void gameStart(String name1, String name2, String name3, String name4, int numberPlayers){
		GameScreen game = new GameScreen(name1, name2, name3, name4, numberPlayers); // Creates a new game object
		globalG = game;
		globalS.setScene(game.getGameScene(globalS, sbScene)); // If the user presses start the scene changes to the scene gathered by the getGameScene method
	}
	
	/**
	 * @param name1 - Player 1's name
	 * @param name2 - Player 2's name 
	 * @param name3 - Player 3's name
	 * @param name4 - Player 4's name
	 * @param numberPlayers - Number of players to connect
	 * This method starts the game for single play use only
	 */
	public static void gameStartSolo(String name1, String name2, String name3, String name4, int numberPlayers){
		GameScreen game = new GameScreen(name1, name2, name3, name4, numberPlayers); // Creates a new game object
		globalS.setScene(game.getGameScene(globalS, sbScene)); // If the user presses start the scene changes to the scene gathered by the getGameScene method
		game.continuousUpdate();; // Calls the continuousUpdate method
	}
	
	/**
	 * Author Mike
	 * Updates the game in the gui
	 */
	public static void updateGame(){
		globalG.Update();
	}
		
	//method to transfer values to client class
	/**
	 * @param primaryStage - Stage used for the game
	 * @param scene - Scene used to transfer to
	 * @param portNumber - Port number to access
	 * @param IP - IP to connect to
	 * @param uName - Name of the User
	 * @param startB - Button to start the game
	 */
	public void client(Stage primaryStage, Scene scene, int portNumber, String IP, String uName, Button startB){
		clientThread = new Thread(new Client(portNumber,IP,uName));
		clientThread.start();
		startB.setVisible(false);
		primaryStage.setScene(scene);
	}

	// Method to launch the host class
	/**
	 * @param primaryStage - Stage used for the game
	 * @param scene - Scene used to transfer to
	 * @param hostName - Name of the host
	 * @param port - Port used for server
	 * @param startB - Button to start the game
	 */
	public void host(Stage primaryStage, Scene scene, String hostName, int port, Button startB){ //TODO improve this method for multiplayer
		nameLRQ.setText("[" + hostName + "]"); // Adds the hosts name to the label
		Thread Host = new Thread(new Host(port, hostName, startB)); // Creates a thread
		Host.start(); // Starts the thread

		primaryStage.setScene(scene); // Sets the scene to the ready screen
	}

	public static void main(String[] args){
		GameGUI.launch(args); // Launches the game
	}

	/**
	 * @return - Returns the scene width and height
	 */
	public static int getWidthHeight(){
		return sceneWH;
	}
	
}//end Game GUI
