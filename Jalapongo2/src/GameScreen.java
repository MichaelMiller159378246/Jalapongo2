import java.net.URL;
import java.time.LocalTime;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * @author Nick, Dalton, Jon, Mike, Leslie
 *
 */
public class GameScreen {

	private int numberPlayers = 1;

	public static int paddle2Move = 0;

	//Player times
	public String player1Time;
	public String player2Time;
	public String player3Time;
	public String player4Time;

	//Stage
	private Stage primaryStage;
	private Scene summaryScene;

	//Game Timer
	int startTime = LocalTime.now().toSecondOfDay();

	//Corner Bumpers
	static int rectW = 60;
	static int rectH = 30;
	//Pane Size
	static int paneWH = 700;

	static int min = rectW;
	static int max = paneWH - rectW;

	static int count = 2; //counter for power up timer when they appear
	static int i = 1; //rotate through 5 power ups on screen at a time

	// Start Screen GUI Menu
	// Construct GUI Objects
	static Pane gamePane = new Pane();
	static Scene gameScene = new Scene(gamePane, paneWH, paneWH);

	//Power Ups
	static PowerUps powerUp1 = new PowerUps(), powerUp2 = new PowerUps(), powerUp3 = new PowerUps(), powerUp4 = new PowerUps(), powerUp5 = new PowerUps();

	//Ball
	static Ball mainBall = new Ball(8); //main ball id # is 8
	static Ball ball1 = new Ball(), ball2 = new Ball(), ball3 = new Ball(), ball4 = new Ball(), ball5 = new Ball(), ball6 = new Ball(), ball7 = new Ball();
	static int newBallCounter = 1;

	//Sound Effects
	String loseSound = "Lose.wav";
	String PowerUpSound = "PowerUp.wav";
	URL loseResource = getClass().getResource(loseSound);
	URL puResource = getClass().getResource(PowerUpSound);

	Media loseMedia = new Media(loseResource.toString());
	Media puMedia = new Media(puResource.toString());


	//Players
	AI player1 = new AI(1);
	Player player2 = new Player(2);
	AI player3 = new AI(3);
	AI player4 = new AI(4);

	//Paddles
	public Paddle paddle1 = player1.getPaddle();
	public Paddle paddle2 = player2.getPaddle();
	public Paddle paddle3 = player3.getPaddle();
	public Paddle paddle4 = player4.getPaddle();

	// Player Names
	private String player1Name;
	private String player2Name;
	private String player3Name;
	private String player4Name;

	//Lives Display
	Text player1L;
	Text player2L;
	Text player3L;
	Text player4L;	

	//Make the Rectangles that make up the corners of the game screen
	Rectangle rect1 = new Rectangle(rectW, rectH); //650,0
	Rectangle rect2 = new Rectangle(rectW, rectH); //10, 0
	Rectangle rect3 = new Rectangle(rectH, rectW); //0, 10 
	Rectangle rect4 = new Rectangle(rectH, rectW); //0, 650
	Rectangle rect5 = new Rectangle(rectW, rectH); //10, 650
	Rectangle rect6 = new Rectangle(rectW, rectH); //650, 690
	Rectangle rect7 = new Rectangle(rectH, rectW); //690, 650
	Rectangle rect8 = new Rectangle(rectH, rectW); //690, 10

	//create shields 
	static Rectangle shield1 = new Rectangle(10, paneWH), shield2 = new Rectangle(paneWH, 10), shield3 = new Rectangle(10, paneWH), shield4 = new Rectangle(paneWH, 10);

	public GameScreen(){

	}
	/**
	 * @author Nick, Dalton, Jon, Mike, Leslie
	 * @param name1
	 * @param name2
	 * @param name3
	 * @param name4
	 * @param numberPlayers
	 */
	public GameScreen(String name1, String name2, String name3, String name4, int numberPlayers) {

		shield1.setX(-900);
		shield1.setY(-900);
		shield2.setX(-900);
		shield2.setY(-900);
		shield3.setX(-900);
		shield3.setY(-900);
		shield4.setX(-900);
		shield4.setY(-900);
		shield1.setFill(Color.CYAN);
		shield2.setFill(Color.CYAN);
		shield3.setFill(Color.CYAN);
		shield4.setFill(Color.CYAN);
		gamePane.getChildren().add(shield1);
		gamePane.getChildren().add(shield2);
		gamePane.getChildren().add(shield3);
		gamePane.getChildren().add(shield4);
		gamePane.getChildren().add(ball1.getBall());
		gamePane.getChildren().add(ball2.getBall());
		gamePane.getChildren().add(ball3.getBall());
		gamePane.getChildren().add(ball4.getBall());
		gamePane.getChildren().add(ball5.getBall());
		gamePane.getChildren().add(ball6.getBall());
		gamePane.getChildren().add(ball7.getBall());

		this.numberPlayers = numberPlayers;

		if(name2 == null){
			name2 = "AI";
		}

		if(name3 == null){
			name3 = "AI";
		}

		if(name4 == null){
			name4 = "AI";
		}

		player1Name = name1;
		player2Name = name2;
		player3Name = name3;
		player4Name = name4;

		//Labels for when the game has started
		player1L = new Text(player3Name + ": " + paddle2.getLives() + " Lives");
		player2L = new Text(player1Name + ": " + paddle2.getLives() + " Lives");
		player3L = new Text(player4Name + ": " + paddle2.getLives() + " Lives");
		player4L = new Text(player2Name + ": " + paddle2.getLives() + " Lives");


		//Get the rectangles and add them to the screen
		gamePane.getChildren().addAll(player1L, player2L, player3L, player4L);
		gamePane.getChildren().addAll(rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8);
		gamePane.getChildren().add(powerUp1.getPowerUp());


		//Add Lives and player name Text
		player1L.setX(-10); 			player1L.setY(paneWH/2);
		player1L.setRotate(90);
		player1L.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		player1L.setFill(Color.BLUE);
		player1L.setId("fancytext");


		player2L.setX(paneWH/2-75); 	player2L.setY(paneWH-50);
		player2L.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		player2L.setFill(Color.GREEN);
		player2L.setId("fancytext");

		player3L.setX(paneWH-110); 	player3L.setY(paneWH/2);
		player3L.setRotate(270);
		player3L.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		player3L.setFill(Color.RED);
		player3L.setId("fancytext");

		player4L.setX(paneWH/2-75); 	player4L.setY(50);
		player4L.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		player4L.setFill(Color.YELLOW);
		player4L.setId("fancytext");

		//Set the location of all the rectangles to their respective corners
		rect1.setX(paneWH-rectW); 	rect1.setY(0);
		rect2.setX(0); 				rect2.setY(0);
		rect3.setX(0); 				rect3.setY(0);
		rect4.setX(0); 				rect4.setY(paneWH-rectW);
		rect5.setX(0); 				rect5.setY(paneWH-rectH);
		rect6.setX(paneWH-rectW); 	rect6.setY(paneWH-rectH);
		rect7.setX(paneWH-rectH); 	rect7.setY(paneWH-rectW);
		rect8.setX(paneWH-rectH); 	rect8.setY(0);

		//----------------------------
		//Add shapes		
		gamePane.getChildren().addAll(paddle1.getPaddle(), paddle2.getPaddle(),
				paddle3.getPaddle(), paddle4.getPaddle(), mainBall.getBall());


		//Moves the paddles, can only used one at a time
		movePaddleOnKeyPress(gameScene, player2, paddle2);
		
	}
	
	/**
	 * @author Dalton L'Heureux
	 * @param ball
	 * 
	 * This method is used to check if a ball has hit an active shield. The Method
	 * only check collisions with shields 2 and 3. If a ball hits a shield the direction of the ball is reversed and the
	 * shield is deactivated (moved off screen) 
	 */
	public void checkCollisionWithShield23(Ball ball){
		if (ball.getXLoc() + ball.getSize() > shield3.getX() &&
				ball.getXLoc() + ball.getSize() < shield3.getX() + shield3.getWidth()){
			ball.reverseX();
			shield3.setX(-900);
			shield3.setY(-900);
		}

		if ((ball.getYLoc() + ball.getSize() > shield2.getY()) &&
				ball.getYLoc() + ball.getSize() < shield2.getY() + shield2.getHeight()){
			ball.reverseY();
			shield2.setX(-900);
			shield2.setY(-900);
		}
	}

	/**
	 * @author Dalton L'Heureux
	 * @param ball
	 * 
	 * This method is used to check if a ball has hit an active shield. The Method
	 * only check collisions with shields 1 and 4. If a ball hits a shield the direction of the ball is reversed and the
	 * shield is deactivated (moved off screen) 
	 */
	public void checkCollisionWithShield14(Ball ball){
		if (ball.getXLoc() < shield1.getX() + shield1.getWidth() && 
				ball.getXLoc() > shield1.getX()){
			ball.reverseX();
			shield1.setX(-900);
			shield1.setY(-900);
		}

		if (ball.getYLoc() < shield4.getY() + shield4.getHeight() && 
				ball.getYLoc() > shield4.getY()){
			ball.reverseY();
			shield4.setX(-900);
			shield4.setY(-900);
		}
	}

	/**
	 * @author Dalton L'Heureux
	 * @param paddle
	 * 
	 * This method is called by the shield power up to activate a shield for the player who hit the ball last.
	 * All shields are created at the start of program launch so the method only needs to move the shields into 
	 * the correct position.
	 */
	public static void createShield(Paddle paddle){
		if(paddle.getPos() == 1){
			shield1.setX(0);
			shield1.setY(0);
		}
		if(paddle.getPos() == 2){
			shield2.setX(0);
			shield2.setY(690);
		}
		if(paddle.getPos() == 3){
			shield3.setX(690);
			shield3.setY(0);
		}
		if(paddle.getPos() == 4){
			shield4.setX(0);
			shield4.setY(0);
		}
	}

	/**
	 * @author Dalton L'Heureux
	 * 
	 * This method is called by the multiball powerup and is used to add a ball to the current game. The new ball
	 * acts the same as the main ball except it is not reset when it scores on a player and the AI do not follow the ball.
	 * There is a limit of 8 active balls at any given time.
	 */
	@SuppressWarnings("unchecked")
	public static void addBall(){
		if(newBallCounter%8 == 0){
			try {
				gamePane.getChildren().removeAll(ball1.getBall());
			} catch (Exception e){}
			ball1 = new Ball(0);
			gamePane.getChildren().addAll(ball1.getBall());
		}
		if(newBallCounter%8 == 1){
			try {
				gamePane.getChildren().removeAll(ball2.getBall());
			} catch (Exception e){}
			ball2 = new Ball(1);
			gamePane.getChildren().addAll(ball2.getBall());
		}
		if(newBallCounter%8 == 2){
			try {
				gamePane.getChildren().removeAll(ball3.getBall());
			} catch (Exception e){}
			ball3 = new Ball(2);
			gamePane.getChildren().addAll(ball3.getBall());
		}
		if(newBallCounter%8 == 3){
			try {
				gamePane.getChildren().removeAll(ball4.getBall());
			} catch (Exception e){}
			ball4 = new Ball(3);
			gamePane.getChildren().addAll(ball4.getBall());
		}
		if(newBallCounter%8 == 4){
			try {
				gamePane.getChildren().removeAll(ball5.getBall());
			} catch (Exception e){}
			ball5 = new Ball(4);
			gamePane.getChildren().addAll(ball5.getBall());
		}
		if(newBallCounter%8 == 5){
			try {
				gamePane.getChildren().removeAll(ball6.getBall());
			} catch (Exception e){}
			ball6 = new Ball(5);
			gamePane.getChildren().addAll(ball6.getBall());
		}
		if(newBallCounter%8 == 6){
			try {
				gamePane.getChildren().removeAll(ball7.getBall());
			} catch (Exception e){}
			ball7 = new Ball(6);
			gamePane.getChildren().addAll(ball7.getBall());
		}
		if(newBallCounter%8 == 7){
			try {
				gamePane.getChildren().removeAll(mainBall.getBall());
			} catch (Exception e){}
			mainBall = new Ball(8);
			gamePane.getChildren().addAll(mainBall.getBall());
		}
		newBallCounter++;
	}

	/**
	 * @author Dalton L'Heureux
	 * 
	 * This method creates a power up and adds it to the game screen. There is a limit of 5 active power ups at any
	 * given time. The type of power up created is determined within the constructor of the power up itself.
	 * */
	private void generatePowerUp(){
		if(GameGUI.flipCB.isSelected()||GameGUI.livesCB.isSelected()||GameGUI.shieldCB.isSelected()||GameGUI.bigCB.isSelected()||
				GameGUI.smallCB.isSelected()||GameGUI.fastCB.isSelected()||GameGUI.slowCB.isSelected()||GameGUI.stallCB.isSelected()||
				GameGUI.multiCB.isSelected()){
			if(i%5 == 0){
				try {
					gamePane.getChildren().removeAll(powerUp1.getPowerUp());
				} catch (Exception e){}
				powerUp1 = new PowerUps();
				powerUp1.setPowerUp();
				gamePane.getChildren().add(powerUp1.getPowerUp());
			}
			if(i%5 == 1){
				try {
					gamePane.getChildren().removeAll(powerUp2.getPowerUp());
				} catch (Exception e){}
				powerUp2 = new PowerUps();
				powerUp2.setPowerUp();
				gamePane.getChildren().add(powerUp2.getPowerUp());
			}
			if(i%5 == 2){
				try {
					gamePane.getChildren().removeAll(powerUp3.getPowerUp());
				} catch (Exception e){}
				powerUp3 = new PowerUps();
				powerUp3.setPowerUp();
				gamePane.getChildren().add(powerUp3.getPowerUp());
			}
			if(i%5 == 3){
				try {
					gamePane.getChildren().removeAll(powerUp4.getPowerUp());
				} catch (Exception e){}
				powerUp4 = new PowerUps();
				powerUp4.setPowerUp();
				gamePane.getChildren().add(powerUp4.getPowerUp());
			}
			if(i%5 == 4){
				try {
					gamePane.getChildren().removeAll(powerUp5.getPowerUp());
				} catch (Exception e){}
				powerUp5 = new PowerUps();
				powerUp5.setPowerUp();
				gamePane.getChildren().add(powerUp5.getPowerUp());
			}
		}
	}

	public void playSound(){
		MediaPlayer puPlayer = new MediaPlayer(puMedia);
		puPlayer.play();
	}

	/**
	 * @author Dalton L'Heureux
	 * @param powerup
	 * 
	 * This method is called when a ball hits a power up and is used to call the correct power up method
	 * based on the power up's type:
	 * powerup => type ID#
	 * 	 flip => 1
		 addLives => 2
		 shield => 3
		 largePaddle => 4
		 smallPaddle => 5
		 slowSpeed => 6
		 addSpeed => 7
		 stall => 8
		 multiBall => 9
	 */
	public static void powerUpHit(PowerUps powerUp){
		int type = powerUp.getType();

		switch (type){
		case 1: 
			PowerUps.flip(powerUp.getTriggerBall());
			break;
		case 2: 
			PowerUps.addLives(powerUp.getTriggerBall());
			break;
		case 3: 
			PowerUps.shield(powerUp.getTriggerBall());
			break; 
		case 4: 
			PowerUps.largePaddle(powerUp.getTriggerBall());
			break;
		case 5: 
			PowerUps.smallPaddle(powerUp.getTriggerBall());
			break;
		case 6: 
			setMinBallSpeed(-1);
			PowerUps.subSpeed(powerUp.getTriggerBall());
			break;
		case 7: 
			setMinBallSpeed(1);
			PowerUps.addSpeed(powerUp.getTriggerBall());
			break;
		case 8: 
			PowerUps.stall(powerUp.getTriggerBall());
			break;
		case 9: 
			PowerUps.multiBall();
			break;
		}			
	}

	//Method to test paddle movement
	private void movePaddleOnKeyPress(Scene scene, Player player, Paddle paddle) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			public void handle(KeyEvent event) { //@Override
				//Sets up the paddle movement for players 1 and 3 on the sides
				if(paddle.getControls() == 1){
					if (player.getPaddle().getLives() > 0) {
						if ((paddle.getPos() == 1) || (paddle.getPos() == 3)) {
							switch (event.getCode()) {
							//case UP:  	if (minY) paddle.paddleMove(-1);
							case UP:  	paddle.paddleMove(-1); break;
							case DOWN: 	paddle.paddleMove(1); break;
							}
						}

						//Sets up the paddle movement for players 2 and 4
						else {
							switch (event.getCode()) {
							case LEFT:  paddle.paddleMove(-1); break;
							case RIGHT: paddle.paddleMove(1); break;
							}
						}}  
				}
				if(paddle.getControls() == 2){
					if (player.getPaddle().getLives() > 0) {
						if ((paddle.getPos() == 1) || (paddle.getPos() == 3)) {
							switch (event.getCode()) {
							//case UP:  	if (minY) paddle.paddleMove(-1);
							case UP:  	paddle.paddleMove(1); break;
							case DOWN: 	paddle.paddleMove(-1); break;
							}
						}

						//Sets up the paddle movement for players 2 and 4
						else {
							switch (event.getCode()) {
							case LEFT:  paddle.paddleMove(1); break;
							case RIGHT: paddle.paddleMove(-1); break;
							}
						}}   
				}else{
					if (player.getPaddle().getLives() > 0) {
						if ((paddle.getPos() == 1) || (paddle.getPos() == 3)) {
							switch (event.getCode()) {
							//case UP:  	if (minY) paddle.paddleMove(-1);
							case UP:  	paddle.paddleMove(0); break;
							case DOWN: 	paddle.paddleMove(0); break;
							}
						}

						//Sets up the paddle movement for players 2 and 4
						else {
							switch (event.getCode()) {
							case LEFT:  paddle.paddleMove(0); break;
							case RIGHT: paddle.paddleMove(0); break;
							}
						}}   
				}
			}
		});
	}

	//Method to test paddle movement
	public void movePaddleOnKeyPress2(Scene scene, Player player, Paddle paddle) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			public void handle(KeyEvent event) { //@Override
				//Sets up the paddle movement for players 1 and 3 on the sides
				if(paddle.getControls() == 1){
					if (player.getPaddle().getLives() > 0) {
						if ((paddle.getPos() == 1) || (paddle.getPos() == 3)) {
							switch (event.getCode()) {
							case UP:  	paddle.paddleMove(-1); break;
							case DOWN: 	paddle.paddleMove(1); break;
							}
						}

						//Sets up the paddle movement for players 2 and 4
						else {
							switch (event.getCode()) {
							case LEFT: 	Client.output = "2L"; break;
							case RIGHT: Client.output = "2R"; break;
							}
						}}  
				}
				if(paddle.getControls() == 2){
					if (player.getPaddle().getLives() > 0) {
						if ((paddle.getPos() == 1) || (paddle.getPos() == 3)) {
							switch (event.getCode()) {
							case UP:  	paddle.paddleMove(1); break;
							case DOWN: 	paddle.paddleMove(-1); break;
							}
						}//if

						//Sets up the paddle movement for players 2 and 4
						else {
							switch (event.getCode()) {
							case LEFT: 	Client.output = "2L"; break;
							case RIGHT: Client.output = "2R"; break;
							}
						}}   
				}else{
					if (player.getPaddle().getLives() > 0) {
						if ((paddle.getPos() == 1) || (paddle.getPos() == 3)) {
							switch (event.getCode()) {
							//case UP:  	if (minY) paddle.paddleMove(-1);
							case UP:  	paddle.paddleMove(0); break;
							case DOWN: 	paddle.paddleMove(0); break;
							}
						}//if

						//Sets up the paddle movement for players 2 and 4
						else {
							switch (event.getCode()) {
							case LEFT: 	Client.output = "2L"; break;
							case RIGHT: Client.output = "2R"; break;
							}
						}}   
				}
			}
		});
	}


	//Changes the direction of the ball if there is contact

	private void checkReverse(Ball ball) throws InterruptedException {

		int x = ball.getXLoc();
		int y = ball.getYLoc();

		//Reverse direction if the ball hits a corner
		//Right Side
		if (x + 20 > (paneWH-rectH) && (ball.getXSpeed() > 0) &&
				(y < rect8.getHeight() || y > rect7.getY()))
			ball.reverseX();
		//Left Side
		if (x < rectH && (ball.getXSpeed() < 0) &&
				(y < rect3.getHeight() || y > rect4.getY()))
			ball.reverseX();
		//Bottom
		if (y + 20 > (paneWH-rectH) && (ball.getYSpeed() > 0) &&
				(x < rect5.getWidth() || x > rect6.getX()))
			ball.reverseY();
		//Top
		if (y < (rectH) && (ball.getYSpeed() < 0) &&
				(x < rect2.getWidth() || x > rect1.getX()))
			ball.reverseY();

		//Life Lost if Ball Passes Player's Paddle and hits their wall
		if (x < 0 && ball.getXSpeed() < 0) {
			player1.getPaddle().scoredOn();
			playerOut(player1);
			ball.setPaddleLastHit(null);
			minBallSpeed = 11;
			if(ball.getID() == 8){
				ball.restart();
			}else{
				ball.setXLoc(-9999);
				ball.setYLoc(-9999);
				ball.setXSpeed(0);
				ball.setYSpeed(0);
			}
			endGame();
		}
		if (y > (paneWH - 20) && ball.getYSpeed() > 0) {
			player2.getPaddle().scoredOn();
			playerOut(player2);
			ball.setPaddleLastHit(null);
			minBallSpeed = 11;
			if(ball.getID() == 8){
				ball.restart();
				//Thread.sleep(200);
			}else{
				ball.setXLoc(-9999);
				ball.setYLoc(-9999);
				ball.setXSpeed(0);
				ball.setYSpeed(0);
			}
			endGame();
		}
		if (x > (paneWH - 20) && ball.getXSpeed() > 0) {
			player3.getPaddle().scoredOn();
			playerOut(player3);
			ball.setPaddleLastHit(null);
			minBallSpeed = 11;
			if(ball.getID() == 8){
				ball.restart();
			}else{
				ball.setXLoc(-9999);
				ball.setYLoc(-9999);
				ball.setXSpeed(0);
				ball.setYSpeed(0);
			}
			endGame();
		}
		if (y < 0 && ball.getYSpeed() < 0) {
			player4.getPaddle().scoredOn();
			playerOut(player4);
			ball.setPaddleLastHit(null);
			minBallSpeed = 11;
			if(ball.getID() == 8){
				ball.restart();
			}else{
				ball.setXLoc(-9999);
				ball.setYLoc(-9999);
				ball.setXSpeed(0);
				ball.setYSpeed(0);
			}
			endGame();
		}

		//Paddle Hits
		try{checkCollisionWith1(mainBall);}catch(Exception e){}  
		try{checkCollisionWith1(ball1);}catch(Exception e){}  
		try{checkCollisionWith1(ball2);}catch(Exception e){}  
		try{checkCollisionWith1(ball3);}catch(Exception e){}  
		try{checkCollisionWith1(ball4);}catch(Exception e){}  
		try{checkCollisionWith1(ball5);}catch(Exception e){}  
		try{checkCollisionWith1(ball6);}catch(Exception e){}  
		try{checkCollisionWith1(ball7);}catch(Exception e){}  

		try{checkCollisionWith2(mainBall);}catch(Exception e){}  
		try{checkCollisionWith2(ball1);}catch(Exception e){}  
		try{checkCollisionWith2(ball2);}catch(Exception e){}  
		try{checkCollisionWith2(ball3);}catch(Exception e){}  
		try{checkCollisionWith2(ball4);}catch(Exception e){}  
		try{checkCollisionWith2(ball5);}catch(Exception e){}  
		try{checkCollisionWith2(ball6);}catch(Exception e){}  
		try{checkCollisionWith2(ball7);}catch(Exception e){}  

		try{checkCollisionWith3(mainBall);}catch(Exception e){}  
		try{checkCollisionWith3(ball1);}catch(Exception e){}  
		try{checkCollisionWith3(ball2);}catch(Exception e){}  
		try{checkCollisionWith3(ball3);}catch(Exception e){}  
		try{checkCollisionWith3(ball4);}catch(Exception e){}  
		try{checkCollisionWith3(ball5);}catch(Exception e){}  
		try{checkCollisionWith3(ball6);}catch(Exception e){}  
		try{checkCollisionWith3(ball7);}catch(Exception e){}  

		try{checkCollisionWith4(mainBall);}catch(Exception e){}  
		try{checkCollisionWith4(ball1);}catch(Exception e){}  
		try{checkCollisionWith4(ball2);}catch(Exception e){}  
		try{checkCollisionWith4(ball3);}catch(Exception e){}  
		try{checkCollisionWith4(ball4);}catch(Exception e){}  
		try{checkCollisionWith4(ball5);}catch(Exception e){}  
		try{checkCollisionWith4(ball6);}catch(Exception e){}  
		try{checkCollisionWith4(ball7);}catch(Exception e){}  
	}

	// ------The following check the collision with the players paddles--------------

	//If ball collides with paddle1, reverse x direction

	/**
	 * @author Nick Seydel
	 * @return Returns the winner
	 */
	public int checkWin(){
		if(paddle1.getLives() < 1 && paddle2.getLives() < 1 && paddle3.getLives() < 1){
			//player 4 wins 
			return 2;
		}
		else if(paddle1.getLives() < 1 && paddle2.getLives() < 1 && paddle4.getLives() < 1){
			//player 3 wins 
			return 4;
		}
		else if(paddle1.getLives() < 1 && paddle3.getLives() < 1 && paddle4.getLives() < 1){
			//player 2 wins 
			return 1;
		}
		else if(paddle2.getLives() < 1 && paddle3.getLives() < 1 && paddle4.getLives() < 1){
			//player 1 wins 
			return 3;
		}
		else return 0;

	}

	private void checkCollisionWith1(Ball ball) {
		if ( (ball.getYLoc() + ball.getSize() > paddle1.getPaddle().getY())
				&& (ball.getYLoc() < paddle1.getPaddle().getY() + paddle1.getPaddle().getHeight()) )
			if ( ball.getXLoc() < (paddle1.getPaddle().getWidth()) &&
					(ball.getXSpeed() < 0) ) {
				ball.reverseX();
				ball.setPaddleLastHit(paddle1);

				if(ball.getYSpeed() <= 0){
					if((ball.getYLoc() + ball.getSize() > paddle1.getPaddle().getY() + paddle1.getPaddle().getHeight()/2) 
							&& (ball.getYLoc() < paddle1.getPaddle().getY() + paddle1.getPaddle().getHeight()) ){
						ball.reverseY();
						ball.setXSpeed(ball.getXSpeed() - 1);
					}
				}
				if(ball.getYSpeed() >= 0){
					if((ball.getYLoc() + ball.getSize() < paddle1.getPaddle().getY() + paddle1.getPaddle().getHeight()/2) 
							&& (ball.getYLoc() > paddle1.getPaddle().getY()) ){
						ball.reverseY();
						ball.setXSpeed(ball.getXSpeed() - 1);
					}
				}
			}
	}

	//If ball collides with paddle2, reverse y direction
	private void checkCollisionWith2(Ball ball) {
		if ( (ball.getXLoc() + ball.getSize() > paddle2.getPaddle().getX()) 
				&& (ball.getXLoc() < paddle2.getPaddle().getX() + paddle2.getPaddle().getWidth()) )
			if ( (ball.getYLoc() > (paneWH - (ball.getSize() + paddle2.getPaddle().getHeight()))) &&
					(ball.getYSpeed() > 0) ) {
				ball.reverseY();
				ball.setPaddleLastHit(paddle2);
				if(ball.getXSpeed() <= 0){
					if((ball.getXLoc() + ball.getSize() > paddle2.getPaddle().getX() + paddle2.getPaddle().getWidth()/2) 
							&& (ball.getXLoc() < paddle2.getPaddle().getX() + paddle2.getPaddle().getWidth()) ){
						ball.reverseX();
						ball.setYSpeed(ball.getXSpeed() - 1);
					}
				}
				if(ball.getXSpeed() >= 0){
					if((ball.getXLoc() + ball.getSize() < paddle2.getPaddle().getX() + paddle2.getPaddle().getWidth()/2) 
							&& (ball.getXLoc() > paddle2.getPaddle().getX()) ){
						ball.reverseX();
						ball.setYSpeed(ball.getXSpeed() - 1);
					}
				}
			}
	}

	//If ball collides with paddle3, reverse x direction
	private void checkCollisionWith3(Ball ball) {
		if ( (ball.getYLoc() + ball.getSize() > paddle3.getPaddle().getY()) 
				&& (ball.getYLoc() < paddle3.getPaddle().getY() + paddle3.getPaddle().getHeight()) )
			if ( ball.getXLoc() > (paneWH - (ball.getSize() + paddle3.getPaddle().getWidth())) &&
					(ball.getXSpeed() > 0) ) {
				ball.reverseX();
				ball.setPaddleLastHit(paddle3);
				if(ball.getYSpeed() <= 0){
					if((ball.getYLoc() + ball.getSize() > paddle3.getPaddle().getY() + paddle3.getPaddle().getHeight()/2) 
							&& (ball.getYLoc() < paddle3.getPaddle().getY() + paddle3.getPaddle().getHeight()) ){
						ball.reverseY();
						ball.setXSpeed(ball.getXSpeed() + 1);
					}
				}
				if(ball.getYSpeed() >= 0){
					if((ball.getYLoc() + ball.getSize() < paddle3.getPaddle().getY() + paddle3.getPaddle().getHeight()/2) 
							&& (ball.getYLoc() > paddle3.getPaddle().getY()) ){
						ball.reverseY();
						ball.setXSpeed(ball.getXSpeed() + 1);
					}
				}
			}
	}

	//If ball collides with paddle4, reverse y direction
	private void checkCollisionWith4(Ball ball) {
		if ( (ball.getXLoc() + ball.getSize() > paddle4.getPaddle().getX())
				&& (ball.getXLoc() < paddle4.getPaddle().getX() + paddle4.getPaddle().getWidth()) )
			if ( ball.getYLoc() < (paddle4.getPaddle().getHeight()) &&
					(ball.getYSpeed() < 0) ) {
				ball.reverseY();
				ball.setPaddleLastHit(paddle4);
				if(ball.getXSpeed() <= 0){
					if((ball.getXLoc() + ball.getSize() > paddle4.getPaddle().getX() + paddle4.getPaddle().getWidth()/2) 
							&& (ball.getXLoc() < paddle4.getPaddle().getX() + paddle4.getPaddle().getWidth()) ){
						ball.reverseX();

						if (Math.abs(ball.getYSpeed()) > 2)
							ball.setYSpeed(ball.getYSpeed() + 1);
					}
				}
				if(ball.getXSpeed() >= 0){
					if((ball.getXLoc() + ball.getSize() < paddle4.getPaddle().getX() + paddle4.getPaddle().getWidth()/2) 
							&& (ball.getXLoc() > paddle4.getPaddle().getX()) ){
						ball.reverseX();

						ball.setYSpeed(ball.getYSpeed() + 1);
					}
				}
			}
	}

	/**
	 * @author Jonathan Hemingway
	 * @param Signed integer indicating increase or decrease
	 * 
	 * This method is used to manage the speed of the ball to keep the game more interesting.
	 * It prevents the ball from moving too slowly
	 */
	
	private static int minBallSpeed = 11;
	
	public static void setMinBallSpeed(int signedInt) {
		if (signedInt < 0)
			minBallSpeed -= 1;
		else
			minBallSpeed += 3;
	}
	
	private void checkBallSpeed() {
		int xSpeed = mainBall.getXSpeed();
		int ySpeed = mainBall.getYSpeed();
		int speedTotal = Math.abs(xSpeed) + Math.abs(ySpeed);

		if (speedTotal < minBallSpeed) {
			if (ySpeed < 0)
				mainBall.setYSpeed(ySpeed - 1);
			else
				mainBall.setYSpeed(ySpeed + 1);

			if (xSpeed < 0)
				mainBall.setXSpeed(xSpeed - 1);
			else
				mainBall.setXSpeed(xSpeed + 1);
		}
	}

	/**
	 * @author Dalton L'Heureux
	 * @param ball
	 * 
	 * This method is used to check if a ball hit a power up. if a power up is hit the move power up off screen method is called.
	 */
	private void checkBallHitPowerUp(Ball ball){
		try {
			if (   ball.getXLoc() >= powerUp1.getPowerUp().getX() && ball.getXLoc() <= powerUp1.getPowerUp().getX() + powerUp1.getSize() 
					&& ball.getYLoc() >= powerUp1.getPowerUp().getY() && ball.getYLoc() <= powerUp1.getPowerUp().getY() + powerUp1.getSize()

					|| ball.getXLoc() + ball.getSize() >= powerUp1.getPowerUp().getX() && ball.getXLoc() + ball.getSize() <= powerUp1.getPowerUp().getX() + powerUp1.getSize() 
					&& ball.getYLoc() + ball.getSize() >= powerUp1.getPowerUp().getY() && ball.getYLoc() + ball.getSize() <= powerUp1.getPowerUp().getY() + powerUp1.getSize()

					|| ball.getXLoc() + ball.getSize() >= powerUp1.getPowerUp().getX() && ball.getXLoc() + ball.getSize() <= powerUp1.getPowerUp().getX() + powerUp1.getSize() 
					&& ball.getYLoc()                  >= powerUp1.getPowerUp().getY() && ball.getYLoc()                  <= powerUp1.getPowerUp().getY() + powerUp1.getSize()

					|| ball.getXLoc()                  >= powerUp1.getPowerUp().getX() && ball.getXLoc()                  <= powerUp1.getPowerUp().getX() + powerUp1.getSize() 
					&& ball.getYLoc() + ball.getSize() >= powerUp1.getPowerUp().getY() && ball.getYLoc() + ball.getSize() <= powerUp1.getPowerUp().getY() + powerUp1.getSize()
					){powerUp1.movePowerUpOffPane(ball);
					MediaPlayer puPlayer = new MediaPlayer(puMedia);
					puPlayer.play();}	

			if (   ball.getXLoc() >= powerUp2.getPowerUp().getX() && ball.getXLoc() <= powerUp2.getPowerUp().getX() + powerUp2.getSize()
					&& ball.getYLoc() >= powerUp2.getPowerUp().getY() && ball.getYLoc() <= powerUp2.getPowerUp().getY() + powerUp2.getSize()

					|| ball.getXLoc() + ball.getSize() >= powerUp2.getPowerUp().getX() && ball.getXLoc() + ball.getSize() <= powerUp2.getPowerUp().getX() + powerUp1.getSize()
					&& ball.getYLoc() + ball.getSize() >= powerUp2.getPowerUp().getY() && ball.getYLoc() + ball.getSize() <= powerUp2.getPowerUp().getY() + powerUp1.getSize()

					|| ball.getXLoc() + ball.getSize() >= powerUp2.getPowerUp().getX() && ball.getXLoc() + ball.getSize() <= powerUp2.getPowerUp().getX() + powerUp2.getSize() 
					&& ball.getYLoc()                  >= powerUp2.getPowerUp().getY() && ball.getYLoc()                  <= powerUp2.getPowerUp().getY() + powerUp2.getSize()

					|| ball.getXLoc()                  >= powerUp2.getPowerUp().getX() && ball.getXLoc()                  <= powerUp2.getPowerUp().getX() + powerUp2.getSize() 
					&& ball.getYLoc() + ball.getSize() >= powerUp2.getPowerUp().getY() && ball.getYLoc() + ball.getSize() <= powerUp2.getPowerUp().getY() + powerUp2.getSize()
					){powerUp2.movePowerUpOffPane(ball);
					MediaPlayer puPlayer = new MediaPlayer(puMedia);
					puPlayer.play();}

			if (   ball.getXLoc() >= powerUp3.getPowerUp().getX() && ball.getXLoc() <= powerUp3.getPowerUp().getX() + powerUp3.getSize()
					&& ball.getYLoc() >= powerUp3.getPowerUp().getY() && ball.getYLoc() <= powerUp3.getPowerUp().getY() + powerUp3.getSize()

					|| ball.getXLoc() + ball.getSize() >= powerUp3.getPowerUp().getX() && ball.getXLoc() + ball.getSize() <= powerUp3.getPowerUp().getX() + powerUp1.getSize()
					&& ball.getYLoc() + ball.getSize() >= powerUp3.getPowerUp().getY() && ball.getYLoc() + ball.getSize() <= powerUp3.getPowerUp().getY() + powerUp1.getSize()

					|| ball.getXLoc() + ball.getSize() >= powerUp3.getPowerUp().getX() && ball.getXLoc() + ball.getSize() <= powerUp3.getPowerUp().getX() + powerUp3.getSize() 
					&& ball.getYLoc()                  >= powerUp3.getPowerUp().getY() && ball.getYLoc()                  <= powerUp3.getPowerUp().getY() + powerUp3.getSize()

					|| ball.getXLoc()                  >= powerUp3.getPowerUp().getX() && ball.getXLoc()                  <= powerUp3.getPowerUp().getX() + powerUp3.getSize() 
					&& ball.getYLoc() + ball.getSize() >= powerUp3.getPowerUp().getY() && ball.getYLoc() + ball.getSize() <= powerUp3.getPowerUp().getY() + powerUp3.getSize()
					){powerUp3.movePowerUpOffPane(ball);
					MediaPlayer puPlayer = new MediaPlayer(puMedia);
					puPlayer.play();}

			if (   ball.getXLoc() >= powerUp4.getPowerUp().getX() && ball.getXLoc() <= powerUp4.getPowerUp().getX() + powerUp4.getSize()
					&& ball.getYLoc() >= powerUp4.getPowerUp().getY() && ball.getYLoc() <= powerUp4.getPowerUp().getY() + powerUp4.getSize()

					|| ball.getXLoc() + ball.getSize() >= powerUp4.getPowerUp().getX() && ball.getXLoc() + ball.getSize() <= powerUp4.getPowerUp().getX() + powerUp1.getSize()
					&& ball.getYLoc() + ball.getSize() >= powerUp4.getPowerUp().getY() && ball.getYLoc() + ball.getSize() <= powerUp4.getPowerUp().getY() + powerUp1.getSize()

					|| ball.getXLoc() + ball.getSize() >= powerUp4.getPowerUp().getX() && ball.getXLoc() + ball.getSize() <= powerUp4.getPowerUp().getX() + powerUp4.getSize() 
					&& ball.getYLoc()                  >= powerUp4.getPowerUp().getY() && ball.getYLoc()                  <= powerUp4.getPowerUp().getY() + powerUp4.getSize()

					|| ball.getXLoc()                  >= powerUp4.getPowerUp().getX() && ball.getXLoc()                  <= powerUp4.getPowerUp().getX() + powerUp4.getSize() 
					&& ball.getYLoc() + ball.getSize() >= powerUp4.getPowerUp().getY() && ball.getYLoc() + ball.getSize() <= powerUp4.getPowerUp().getY() + powerUp4.getSize()
					){powerUp4.movePowerUpOffPane(ball);
					MediaPlayer puPlayer = new MediaPlayer(puMedia);
					puPlayer.play();}

			if (   ball.getXLoc() >= powerUp5.getPowerUp().getX() && ball.getXLoc() <= powerUp5.getPowerUp().getX() + powerUp5.getSize()
					&& ball.getYLoc() >= powerUp5.getPowerUp().getY() && ball.getYLoc() <= powerUp5.getPowerUp().getY() + powerUp5.getSize()

					|| ball.getXLoc() + ball.getSize() >= powerUp5.getPowerUp().getX() && ball.getXLoc() + ball.getSize() <= powerUp5.getPowerUp().getX() + powerUp1.getSize()
					&& ball.getYLoc() + ball.getSize() >= powerUp5.getPowerUp().getY() && ball.getYLoc() + ball.getSize() <= powerUp5.getPowerUp().getY() + powerUp1.getSize()

					|| ball.getXLoc() + ball.getSize() >= powerUp5.getPowerUp().getX() && ball.getXLoc() + ball.getSize() <= powerUp5.getPowerUp().getX() + powerUp5.getSize() 
					&& ball.getYLoc()                  >= powerUp5.getPowerUp().getY() && ball.getYLoc()                  <= powerUp5.getPowerUp().getY() + powerUp5.getSize()

					|| ball.getXLoc()                  >= powerUp5.getPowerUp().getX() && ball.getXLoc()                  <= powerUp5.getPowerUp().getX() + powerUp5.getSize() 
					&& ball.getYLoc() + ball.getSize() >= powerUp5.getPowerUp().getY() && ball.getYLoc() + ball.getSize() <= powerUp5.getPowerUp().getY() + powerUp5.getSize()
					){powerUp5.movePowerUpOffPane(ball);
					MediaPlayer puPlayer = new MediaPlayer(puMedia);
					puPlayer.play();}

		} catch (Exception e){}
	}

	/**
	 * @author Dalton L'Heureux
	 * 
	 * This method is used with the main thread to continuously check if any ball hit any active power up. 
	 */
	private void checkCollisionWithPowerUp() {
		checkBallHitPowerUp(mainBall);
		checkBallHitPowerUp(ball1);
		checkBallHitPowerUp(ball2);
		checkBallHitPowerUp(ball3);
		checkBallHitPowerUp(ball4);
		checkBallHitPowerUp(ball5);
		checkBallHitPowerUp(ball6);
		checkBallHitPowerUp(ball7);
	}

	/**
	 * @author Dalton L'Heureux
	 * 
	 * This method is used within the main thread to continuously check if any ball has hit any of the active shield. The Method
	 * only check collisions with shields 2 and 3. If a ball hits a shield the direction of the ball is reversed and the
	 * shield is deactivated (moved off screen) 
	 */
	private void checkCollisionWithShields(){
		try{checkCollisionWithShield23(mainBall);}catch(Exception e){}  
		try{checkCollisionWithShield23(ball1);}catch(Exception e){}
		try{checkCollisionWithShield23(ball2);}catch(Exception e){}
		try{checkCollisionWithShield23(ball3);}catch(Exception e){}
		try{checkCollisionWithShield23(ball4);}catch(Exception e){}
		try{checkCollisionWithShield23(ball5);}catch(Exception e){}
		try{checkCollisionWithShield23(ball6);}catch(Exception e){}
		try{checkCollisionWithShield23(ball7);}catch(Exception e){}

		try{checkCollisionWithShield14(mainBall);}catch(Exception e){}  
		try{checkCollisionWithShield14(ball1);}catch(Exception e){}
		try{checkCollisionWithShield14(ball2);}catch(Exception e){}
		try{checkCollisionWithShield14(ball3);}catch(Exception e){}
		try{checkCollisionWithShield14(ball4);}catch(Exception e){}
		try{checkCollisionWithShield14(ball5);}catch(Exception e){}
		try{checkCollisionWithShield14(ball6);}catch(Exception e){}
		try{checkCollisionWithShield14(ball7);}catch(Exception e){}
	}

	/**
	 * @author Dalton L'Heureux
	 * 
	 * This method is used within the main thread to continuously move all the active balls
	 */
	public void moveAllBalls(){
		try{
			mainBall.moveBall();	
		}catch(Exception e){}
		try{
			ball1.moveBall();
		}catch(Exception e){}
		try{
			ball2.moveBall();	
		}catch(Exception e){}
		try{
			ball3.moveBall();	
		}catch(Exception e){}
		try{
			ball4.moveBall();	
		}catch(Exception e){}
		try{
			ball5.moveBall();	
		}catch(Exception e){}
		try{
			ball6.moveBall();
		}catch(Exception e){}
		try{
			ball7.moveBall();	
		}catch(Exception e){}
	}

	//Continuously update the game screen to keep the ball moving
	//moves the ball and AI paddles

	private boolean running = true;
	public void continuousUpdate() {
		Task task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				while (running) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try{moveAllBalls();}catch(Exception e){}
							try{checkReverse(mainBall);}catch(InterruptedException e) {e.printStackTrace();}
							try{checkReverse(ball1);}catch(Exception e){}
							try{checkReverse(ball2);}catch(Exception e){}
							try{checkReverse(ball3);}catch(Exception e){}
							try{checkReverse(ball4);}catch(Exception e){}
							try{checkReverse(ball5);}catch(Exception e){}
							try{checkReverse(ball6);}catch(Exception e){}
							try{checkReverse(ball7);}catch(Exception e){}  
							try{checkCollisionWithPowerUp();}catch(Exception e){}
							try{checkCollisionWithShields();}catch(Exception e){}
							//TODO variable AI
							checkBallSpeed();
							player1.moveAI(mainBall);
							player3.moveAI(mainBall);
							player4.moveAI(mainBall);
							player1L.setText(player3Name + ": " + paddle1.getLives() + " Lives");
							player2L.setText(player1Name + ": " + paddle2.getLives() + " Lives");
							player3L.setText(player4Name + ": " + paddle3.getLives() + " Lives");
							player4L.setText(player2Name + ": " + paddle4.getLives() + " Lives");
							if (count%100 == 1){
								generatePowerUp();
								i++;
							}
							count++;
						}
					});
					Thread.sleep(50);
				}
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	//min paddle location
	static int min() { 
		return min;
	}

	//max paddle location
	static int max() { 
		return max;
	}

	/**
	 * @author Jonathan
	 * @param player
	 * 
	 * Performs the tasks which need to execute when a player gets out, including playing a
	 * sound, calculating the amount of time the player was in, removing the players paddle
	 * from the screen, and filling in the players side with a wall.
	 */
	//If a player runs out of lives, make their paddle fill the whole side
	private void playerOut(Player player) {
		if (player.getPaddle().getLives() < 1) {
			MediaPlayer losePlayer = new MediaPlayer(loseMedia);
			losePlayer.play();

			if (player.getPos() == 1) {
				gamePane.getChildren().remove(paddle1.getPaddle());
				paddle1.getPaddle().setX(-1000);
				paddle1.getPaddle().setY(-1000);
				rect3.setHeight(paneWH);
				int player1Lost = LocalTime.now().toSecondOfDay();
				int player1Elapsed = player1Lost - startTime;
				int player1Minutes = player1Elapsed / 60; //Minutes in game
				int player1Seconds = player1Elapsed % 60; //Seconds in game
				player3Time = new String(String.format("%s:%02d",player1Minutes, player1Seconds));
			}
			else if (player.getPos() == 3) {
				gamePane.getChildren().remove(paddle3.getPaddle());
				paddle3.getPaddle().setX(-1000);
				paddle3.getPaddle().setY(-1000);
				rect8.setHeight(paneWH);
				int player3Lost = LocalTime.now().toSecondOfDay();
				int player3Elapsed = player3Lost - startTime;
				int player3Minutes = player3Elapsed / 60; //Minutes in game
				int player3Seconds = player3Elapsed % 60; //Seconds in game
				player4Time = new String(String.format("%s:%02d",player3Minutes, player3Seconds));
			}
			else if (player.getPos() == 2) {
				gamePane.getChildren().remove(paddle2.getPaddle());
				paddle2.getPaddle().setX(-1000);
				paddle2.getPaddle().setY(-1000);
				rect5.setWidth(paneWH);
				int player2Lost = LocalTime.now().toSecondOfDay();
				int player2Elapsed = player2Lost - startTime;
				int player2Minutes = player2Elapsed / 60; //Minutes in game
				int player2Seconds = player2Elapsed % 60; //Seconds in game
				player1Time = new String(String.format("%s:%02d",player2Minutes, player2Seconds));
			}
			else {
				gamePane.getChildren().remove(paddle4.getPaddle());
				paddle4.getPaddle().setX(-1000);
				paddle4.getPaddle().setY(-1000);
				rect2.setWidth(paneWH);
				int player4Lost = LocalTime.now().toSecondOfDay();
				int player4Elapsed = player4Lost - startTime;
				int player4Minutes = player4Elapsed / 60; //Minutes in game
				int player4Seconds = player4Elapsed % 60; //Seconds in game
				player2Time = new String(String.format("%s:%02d",player4Minutes, player4Seconds));
			}
			System.out.println("Player " + player.getPos() + " is out");
		}
	}
	//End of game
	public void endGame() throws InterruptedException {
		int playersIn = 0;

		if (player1.getPaddle().getLives() > 0)
			playersIn++;
		if (player2.getPaddle().getLives() > 0)
			playersIn++;
		if (player3.getPaddle().getLives() > 0)
			playersIn++;
		if (player4.getPaddle().getLives() > 0)
			playersIn++;

		//Switch to summary screen
		//--Your Code Here--
		if (playersIn <= 1) {
			int endTime = LocalTime.now().toSecondOfDay();
			int secondsElapsed = endTime - startTime;
			int timeMinutes = secondsElapsed / 60; //Game time minutes
			int timeSeconds = secondsElapsed % 60; //Game time seconds
			System.out.printf("Player %d wins!%n",checkWin());
			primaryStage.setScene(summaryScene);
			new GameData(this, primaryStage);
			running = false;

			//Code to add game stats to summary screen
			//--Your Code Here--
		}
	}

	public Scene getGameScene(Stage primaryStage, Scene summaryScene) {
		this.primaryStage = primaryStage;
		this.summaryScene = summaryScene;
		return this.gameScene;
	}

	//get player 1 time
	public String getP1Time() {
		return player1Time;
	}

	//get player 2 time
	public String getP2Time() {
		return player2Time;
	}

	//get player 2 time
	public String getP3Time() {
		return player3Time;
	}

	//player 4 time
	public String getP4Time() {
		return player4Time;
	}
	
	//player 1 name
	public String getP1Name(){
		return player1Name;
	}
	
	//player 2 name
	public String getP2Name(){
		return player2Name;
	}
		
	//player 3 name
	public String getP3Name(){
		return player3Name;
	}	
	
	//player 4 name
	public String getP4Name(){
		return player4Name;
	}	

	public void Update() {
		Platform.runLater(new Runnable() { // Runs when it gets the chance
			public void run() { //Runs
				moveAllBalls();

				try{checkReverse(mainBall);}catch(InterruptedException e) {e.printStackTrace();}
				try{checkReverse(ball1);}catch(Exception e){}
				try{checkReverse(ball2);}catch(Exception e){}
				try{checkReverse(ball3);}catch(Exception e){}
				try{checkReverse(ball4);}catch(Exception e){}
				try{checkReverse(ball5);}catch(Exception e){}
				try{checkReverse(ball6);}catch(Exception e){}
				try{checkReverse(ball7);}catch(Exception e){}  
				try{checkCollisionWithPowerUp();}catch(Exception e){}
				try{checkCollisionWithShields();}catch(Exception e){}
				//TODO variable AI

				if(numberPlayers < 2){
					player4.moveAI(mainBall);
				}else if (paddle2Move == 1){
					paddle4.paddleMove(1);
				}else if (paddle2Move == -1){
					paddle4.paddleMove(-1);
				}
				else if (paddle2Move == 0){
					paddle4.paddleMove(0);
				}
				if(numberPlayers < 3){
					player1.moveAI(mainBall);
				}
				if(numberPlayers < 4){
					player3.moveAI(mainBall);
				}

				player1L.setText(player3Name + ": " + paddle1.getLives() + " Lives");
				player2L.setText(player1Name + ": " + paddle2.getLives() + " Lives");
				player3L.setText(player4Name + ": " + paddle3.getLives() + " Lives");
				player4L.setText(player2Name + ": " + paddle4.getLives() + " Lives");
				if (count%100 == 1){
					generatePowerUp();
					i++;
				}

				count++;
			}
		});
	}

	/**
	 * @return - Returns all the data needed to update the gui
	 */
	public String getEverything(){
		return mainBall.getXLoc() + "," + mainBall.getYLoc() + "," + paddle1.getPaddle().getX() + "," + paddle1.getPaddle().getY() + "," + paddle2.getPaddle().getX() + "," + paddle2.getPaddle().getY() + "," + paddle3.getPaddle().getX() + "," + paddle3.getPaddle().getY() + "," + paddle4.getPaddle().getX() + "," + paddle4.getPaddle().getY() + "," + paddle1.getLives() + "," + paddle2.getLives() + "," + paddle3.getLives() + "," + paddle4.getLives() + "," + rect5.getWidth() + "," + rect2.getWidth() + "," + rect3.getHeight() + "," + rect8.getHeight() + "," + powerUp1.getPowerUp().getX() + "," + powerUp1.getPowerUp().getY() + "," + powerUp1.getType() + "," + powerUp2.getPowerUp().getX() + "," + powerUp2.getPowerUp().getY() + "," + powerUp2.getType() + "," + powerUp3.getPowerUp().getX() + "," + powerUp3.getPowerUp().getY() + "," + powerUp3.getType() + "," + powerUp4.getPowerUp().getX() + "," + powerUp4.getPowerUp().getY() + "," + powerUp4.getType() + "," + powerUp5.getPowerUp().getX() + "," + powerUp5.getPowerUp().getY() + "," + powerUp5.getType() + "," + paddle1.getPaddle().getHeight() + "," + paddle2.getPaddle().getWidth() + "," + paddle3.getPaddle().getHeight() + "," + paddle4.getPaddle().getWidth() + "," + shield1.getX() + "," + shield1.getY() + "," + shield2.getX() + "," + shield2.getY() + "," + shield3.getX() + "," + shield3.getY() + "," + shield4.getX() + "," + shield4.getY() + "," + ball1.getXLoc() + "," + ball1.getYLoc() + "," + ball2.getXLoc() + "," + ball2.getYLoc() + "," + ball3.getXLoc() + "," + ball3.getYLoc() + "," + ball4.getXLoc() + "," + ball4.getYLoc() + "," + ball5.getXLoc() + "," + ball5.getYLoc() + "," + ball6.getXLoc() + "," + ball6.getYLoc() + "," + ball7.getXLoc() + "," + ball7.getYLoc(); 
	}

	/**
	 * @param mainBallX - X position for the main ball
	 * @param mainBallY - Y position for the main ball
	 * @param paddle1X - X position for the first paddle
	 * @param paddle1Y - Y position for the first paddle
	 * @param paddle2X - X position for the second paddle
	 * @param paddle2Y - Y position for the second paddle
	 * @param paddle3X - X position for the third paddle
	 * @param paddle3Y - Y position for the third paddle
	 * @param paddle4X - X position for the fourth paddle
	 * @param paddle4Y - Y position for the fourth paddle
	 * @param player1Lives - Number of player 1's lives
	 * @param player2Lives - Number of player 2's lives
	 * @param player3Lives - Number of player 3's lives
	 * @param player4Lives - Number of player 4's lives
	 * @param player1Rect - Length of rectangle
	 * @param player2Rect - Length of rectangle
	 * @param player3Rect - Length of rectangle
	 * @param player4Rect - Length of rectangle
	 * @param powerUp1X - X Location of the first power up
	 * @param powerUp1Y - Y Location of the first power up
	 * @param powerUp1Type - Integer to explain the type
	 * @param powerUp2X - X Location of the second power up
	 * @param powerUp2Y - Y Location of the second power up
	 * @param powerUp2Type - Integer to explain the type
	 * @param powerUp3X - X Location of the third power up
	 * @param powerUp3Y - Y Location of the third power up
	 * @param powerUp3Type - Integer to explain the type
	 * @param powerUp4X - X Location of the fourth power up
	 * @param powerUp4Y - Y Location of the fourth power up
	 * @param powerUp4Type - Integer to explain the type
	 * @param powerUp5X - X Location of the fifth power up
	 * @param powerUp5Y - Y Location of the fifth power up
	 * @param powerUp5Type - Integer to explain the type
	 * @param paddle1Height - Height of the paddle
	 * @param paddle2Width - Height of the paddle
	 * @param paddle3Height - Height of the paddle
	 * @param paddle4Width - Height of the paddle
	 * @param shield1X - X Location of the first shield
	 * @param shield1Y - Y location of the first shield
	 * @param shield2X - X Location of the second shield
	 * @param shield2Y - Y location of the second shield
	 * @param shield3X - X Location of the third shield
	 * @param shield3Y - Y location of the third shield
	 * @param shield4X - X Location of the fourth shield
	 * @param shield4Y - Y location of the fourth shield
	 * @param ball1X - X location of the first ball
	 * @param ball1Y - Y location of the first ball
	 * @param ball2X - X location of the second ball
	 * @param ball2Y - Y location of the second ball
	 * @param ball3X - X location of the third ball
	 * @param ball3Y - Y location of the third ball
	 * @param ball4X - X location of the fourth ball
	 * @param ball4Y - Y location of the fourth ball
	 * @param ball5X - X location of the fifth ball
	 * @param ball5Y - Y location of the fifth ball
	 * @param ball6X - X location of the sixth ball
	 * @param ball6Y - Y location of the sixth ball
	 * @param ball7X - X location of the seventh ball
	 * @param ball7Y - Y location of the seventh ball
	 */
	public void setEverything(int mainBallX, int mainBallY, double paddle1X, double paddle1Y, double paddle2X, double paddle2Y, double paddle3X, double paddle3Y, double paddle4X, double paddle4Y, int player1Lives, int player2Lives, int player3Lives, int player4Lives, Double player1Rect, Double player2Rect, Double player3Rect, Double player4Rect, Double powerUp1X, Double powerUp1Y, int powerUp1Type, Double powerUp2X, Double powerUp2Y, int powerUp2Type, Double powerUp3X, Double powerUp3Y, int powerUp3Type, Double powerUp4X, Double powerUp4Y, int powerUp4Type, Double powerUp5X, Double powerUp5Y, int powerUp5Type, double paddle1Height, double paddle2Width, double paddle3Height, double paddle4Width, double shield1X, double shield1Y, double shield2X, double shield2Y, double shield3X, double shield3Y, double shield4X, double shield4Y, double ball1X, double ball1Y, double ball2X, double ball2Y, double ball3X, double ball3Y, double ball4X, double ball4Y, double ball5X, double ball5Y, double ball6X, double ball6Y, double ball7X, double ball7Y){
		gamePane.getChildren().removeAll(powerUp1.getPowerUp());
		gamePane.getChildren().removeAll(powerUp2.getPowerUp());
		gamePane.getChildren().removeAll(powerUp3.getPowerUp());
		gamePane.getChildren().removeAll(powerUp4.getPowerUp());
		gamePane.getChildren().removeAll(powerUp5.getPowerUp());
		gamePane.getChildren().removeAll(ball1.getBall());
		gamePane.getChildren().removeAll(ball2.getBall());
		gamePane.getChildren().removeAll(ball3.getBall());
		gamePane.getChildren().removeAll(ball4.getBall());
		gamePane.getChildren().removeAll(ball5.getBall());
		gamePane.getChildren().removeAll(ball6.getBall());
		gamePane.getChildren().removeAll(ball7.getBall());
		gamePane.getChildren().removeAll(shield1);
		gamePane.getChildren().removeAll(shield2);
		gamePane.getChildren().removeAll(shield3);
		gamePane.getChildren().removeAll(shield4);
		mainBall.setXLoc(mainBallX);
		mainBall.setYLoc(mainBallY);
		paddle1.getPaddle().setX(paddle1X);
		paddle1.getPaddle().setY(paddle1Y);
		paddle2.getPaddle().setX(paddle2X);
		paddle2.getPaddle().setY(paddle2Y);
		paddle3.getPaddle().setX(paddle3X);
		paddle3.getPaddle().setY(paddle3Y);
		paddle4.getPaddle().setX(paddle4X);
		paddle4.getPaddle().setY(paddle4Y);
		player1L.setText(player3Name + ": " + player1Lives + " Lives");
		player2L.setText(player1Name + ": " + player2Lives + " Lives");
		player3L.setText(player4Name + ": " + player3Lives + " Lives");
		player4L.setText(player2Name + ": " + player4Lives + " Lives");
		rect5.setWidth(player1Rect);
		rect2.setWidth(player2Rect);
		rect3.setHeight(player3Rect);
		rect8.setHeight(player4Rect);
		powerUp1.setType(powerUp1Type);
		powerUp1.getPowerUp().setX(powerUp1X);
		powerUp1.getPowerUp().setY(powerUp1Y);
		powerUp2.setType(powerUp2Type);
		powerUp2.getPowerUp().setX(powerUp2X);
		powerUp2.getPowerUp().setY(powerUp2Y);
		powerUp3.setType(powerUp3Type);
		powerUp3.getPowerUp().setX(powerUp3X);
		powerUp3.getPowerUp().setY(powerUp3Y);
		powerUp4.setType(powerUp4Type);
		powerUp4.getPowerUp().setX(powerUp4X);
		powerUp4.getPowerUp().setY(powerUp4Y);
		powerUp5.setType(powerUp5Type);
		powerUp5.getPowerUp().setX(powerUp5X);
		powerUp5.getPowerUp().setY(powerUp5Y);
		paddle1.getPaddle().setHeight(paddle1Height);
		paddle2.getPaddle().setWidth(paddle2Width);
		paddle3.getPaddle().setHeight(paddle3Height);
		paddle4.getPaddle().setWidth(paddle4Width);
		shield1.setX(shield1X);
		shield1.setY(shield1Y);
		shield2.setX(shield2X);
		shield2.setY(shield2Y);
		shield3.setX(shield3X);
		shield3.setY(shield3Y);
		shield4.setX(shield4X);
		shield4.setY(shield4Y);
		ball1.getBall().setX(ball1X);
		ball1.getBall().setY(ball1Y);
		ball2.getBall().setX(ball2X);
		ball2.getBall().setY(ball2Y);
		ball3.getBall().setX(ball3X);
		ball3.getBall().setY(ball3Y);
		ball4.getBall().setX(ball4X);
		ball4.getBall().setY(ball4Y);
		ball5.getBall().setX(ball5X);
		ball5.getBall().setY(ball5Y);
		ball6.getBall().setX(ball6X);
		ball6.getBall().setY(ball6Y);
		ball7.getBall().setX(ball7X);
		ball7.getBall().setY(ball7Y);

		gamePane.getChildren().add(powerUp1.getPowerUp());
		gamePane.getChildren().add(powerUp2.getPowerUp());
		gamePane.getChildren().add(powerUp3.getPowerUp());
		gamePane.getChildren().add(powerUp4.getPowerUp());
		gamePane.getChildren().add(powerUp5.getPowerUp());
		gamePane.getChildren().addAll(shield1);
		gamePane.getChildren().addAll(shield2);
		gamePane.getChildren().addAll(shield3);
		gamePane.getChildren().addAll(shield4);
		gamePane.getChildren().addAll(ball1.getBall());
		gamePane.getChildren().addAll(ball2.getBall());
		gamePane.getChildren().addAll(ball3.getBall());
		gamePane.getChildren().addAll(ball4.getBall());
		gamePane.getChildren().addAll(ball5.getBall());
		gamePane.getChildren().addAll(ball6.getBall());
		gamePane.getChildren().addAll(ball7.getBall());		

	}
}
