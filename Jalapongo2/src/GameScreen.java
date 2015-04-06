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
import javafx.scene.text.Text;


public class GameScreen {
	
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
	static PowerUps powerUp1, powerUp2, powerUp3, powerUp4, powerUp5;
	
	//Ball
	static Ball mainBall = new Ball();
	static Ball ball1, ball2, ball3, ball4, ball5, ball6, ball7;
	
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
	
	//Lives Display
	Text player1L = new Text(paddle2.getLives() + " Lives");
	Text player2L = new Text(paddle2.getLives() + " Lives");
	Text player3L = new Text(paddle2.getLives() + " Lives");
	Text player4L = new Text(paddle2.getLives() + " Lives");
	
	
	//Make the Rectangles that make up the corners of the game screen
	Rectangle rect1 = new Rectangle(rectW, rectH); //650,0
	Rectangle rect2 = new Rectangle(rectW, rectH); //10, 0
	Rectangle rect3 = new Rectangle(rectH, rectW); //0, 10 
	Rectangle rect4 = new Rectangle(rectH, rectW); //0, 650
	Rectangle rect5 = new Rectangle(rectW, rectH); //10, 650
	Rectangle rect6 = new Rectangle(rectW, rectH); //650, 690
	Rectangle rect7 = new Rectangle(rectH, rectW); //690, 650
	Rectangle rect8 = new Rectangle(rectH, rectW); //690, 10
		
	public GameScreen() {
		
		//Get the rectangles and add them to the screen
		gamePane.getChildren().addAll(player1L, player2L, player3L, player4L);
		gamePane.getChildren().addAll(rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8);

		//Add Lives Text
		player1L.setX(50); 			player1L.setY(paneWH/2);
		player1L.setRotate(90);
		player2L.setX(paneWH/2); 	player2L.setY(paneWH-50);
		player3L.setX(paneWH-50); 	player3L.setY(paneWH/2);
		player3L.setRotate(270);
		player4L.setX(paneWH/2); 	player4L.setY(50);
		
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
		//movePaddleOnKeyPress(gameScene, player1, paddle1);
		movePaddleOnKeyPress(gameScene, player2, paddle2);
		//movePaddleOnKeyPress(gameScene, player3, paddle3);
		//movePaddleOnKeyPress(gameScene, player4, paddle4);		
}
	
	private void generatePowerUp(){
		if(GameGUI.flipCB.isSelected()||GameGUI.livesCB.isSelected()||GameGUI.sheildCB.isSelected()||GameGUI.bigCB.isSelected()||
				GameGUI.smallCB.isSelected()||GameGUI.fastCB.isSelected()||GameGUI.slowCB.isSelected()||GameGUI.stallCB.isSelected()){
		    			if(i%5 == 0){
				            try {
				            	gamePane.getChildren().removeAll(powerUp1.getPowerUp());
							} catch (Exception e){}
				            powerUp1 = new PowerUps();
		    				gamePane.getChildren().add(powerUp1.getPowerUp());
		    			}
		    			if(i%5 == 1){
					        try {
					            gamePane.getChildren().removeAll(powerUp2.getPowerUp());
					        } catch (Exception e){}
		    				powerUp2 = new PowerUps();
		    				gamePane.getChildren().add(powerUp2.getPowerUp());
		    			}
		    			if(i%5 == 2){
				            try {
				            	gamePane.getChildren().removeAll(powerUp3.getPowerUp());
							} catch (Exception e){}
		    				powerUp3 = new PowerUps();
		    				gamePane.getChildren().add(powerUp3.getPowerUp());
		    			}
		    			if(i%5 == 3){
					        try {
					          	gamePane.getChildren().removeAll(powerUp4.getPowerUp());
							} catch (Exception e){}
		    				powerUp4 = new PowerUps();
		    				gamePane.getChildren().add(powerUp4.getPowerUp());
		    			}
		    			if(i%5 == 4){
					        try {
					            gamePane.getChildren().removeAll(powerUp5.getPowerUp());
							} catch (Exception e){}
		    				powerUp5 = new PowerUps();
		    				gamePane.getChildren().add(powerUp5.getPowerUp());
		    			}
		}
	}
	
	public void playSound(){
		MediaPlayer puPlayer = new MediaPlayer(puMedia);
		puPlayer.play();
	}
	
	public static void powerUpHit(PowerUps powerUp){
		int type = powerUp.getType();
		/*  flip => 1
		 *  addLives => 2
		 *  shield => 3
		 *  largePaddle => 4
		 *  smallPaddle => 5
		 *  slowSpeed => 6
		 *  addSpeed => 7
		 *  stall => 8
		 *  multiBall => 9
		 */
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
				PowerUps.subSpeed(powerUp.getTriggerBall());
				break;
			case 7: 
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
		    	  }//if
		    	  
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
		    	  }//if
		    	  
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
		    	  }//if
		    	  
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

	//Changes the direction of the ball if there is contact

	private void checkReverse() throws InterruptedException {

		int x = mainBall.getXLoc();
		int y = mainBall.getYLoc();
		
		//Reverse direction if the ball hits a corner
		//Right Side
		if (x + 20 > (paneWH-rectH) && (mainBall.getXSpeed() > 0) &&
				(y < rect8.getHeight() || y > rect7.getY()))
			mainBall.reverseX();
		//Left Side
		if (x < rectH && (mainBall.getXSpeed() < 0) &&
				(y < rect3.getHeight() || y > rect4.getY()))
			mainBall.reverseX();
		//Bottom
		if (y + 20 > (paneWH-rectH) && (mainBall.getYSpeed() > 0) &&
				(x < rect5.getWidth() || x > rect6.getX()))
			mainBall.reverseY();
		//Top
		if (y < (rectH) && (mainBall.getYSpeed() < 0) &&
				(x < rect2.getWidth() || x > rect1.getX()))
			mainBall.reverseY();
		
		//Life Lost if Ball Passes Player's Paddle and hits their wall
		if (x < 0 && mainBall.getXSpeed() < 0) {
			player1.getPaddle().scoredOn();
			System.out.println("Player 1 Was Scored On");
			//player1L.setText(paddle1.getLives() + " Lives");
			playerOut(player1);
			mainBall.setPaddleLastHit(null);
			mainBall.restart();
			Thread.sleep(200);
			endGame();
		}
		if (y > (paneWH - 20) && mainBall.getYSpeed() > 0) {
			player2.getPaddle().scoredOn();
			System.out.println("Player 2 Was Scored On");
			//player2L.setText(paddle2.getLives() + " Lives");
			playerOut(player2);
			mainBall.setPaddleLastHit(null);
			mainBall.restart();
			Thread.sleep(200);
			endGame();
		}
		if (x > (paneWH - 20) && mainBall.getXSpeed() > 0) {
			player3.getPaddle().scoredOn();
			System.out.println("Player 3 Was Scored On");
			//player3L.setText(paddle3.getLives() + " Lives");
			playerOut(player3);
			mainBall.setPaddleLastHit(null);
			mainBall.restart();
			Thread.sleep(200);
			endGame();
		}
		if (y < 0 && mainBall.getYSpeed() < 0) {
			player4.getPaddle().scoredOn();
			System.out.println("Player 4 Was Scored On");
			//player4L.setText(paddle4.getLives() + " Lives");
			playerOut(player4);
			mainBall.setPaddleLastHit(null);
			mainBall.restart();
			Thread.sleep(200);
			endGame();
		}

		//Paddle Hits
		checkCollisionWith1();
		checkCollisionWith2();
		checkCollisionWith3();
		checkCollisionWith4();
	}
	
	
	// ------The following check the collision with the players paddles--------------
	
	//If ball collides with paddle1, reverse x direction
	
	private int checkWin(){
		if(paddle1.getLives() < 1 && paddle2.getLives() < 1 && paddle3.getLives() < 1){
			//player 4 wins 
			return 4;
		}
		else if(paddle1.getLives() < 1 && paddle2.getLives() < 1 && paddle4.getLives() < 1){
			//player 3 wins 
			return 3;
		}
		else if(paddle1.getLives() < 1 && paddle3.getLives() < 1 && paddle4.getLives() < 1){
			//player 2 wins 
			return 2;
		}
		else if(paddle2.getLives() < 1 && paddle3.getLives() < 1 && paddle4.getLives() < 1){
			//player 1 wins 
			return 1;
		}
		else return 0;
		
	}

	private void checkCollisionWith1() {
		if ( (mainBall.getYLoc() + mainBall.getSize() > paddle1.getPaddle().getY())
				&& (mainBall.getYLoc() < paddle1.getPaddle().getY() + paddle1.getPaddle().getHeight()) )
			if ( mainBall.getXLoc() < (paddle1.getPaddle().getWidth()) &&
					(mainBall.getXSpeed() < 0) ) {
				mainBall.reverseX();
				mainBall.setPaddleLastHit(paddle1);
				//System.out.println("1 hit");
				if(mainBall.getYSpeed() <= 0){
					if((mainBall.getYLoc() + mainBall.getSize() > paddle1.getPaddle().getY() + paddle1.getPaddle().getHeight()/2) 
						&& (mainBall.getYLoc() < paddle1.getPaddle().getY() + paddle1.getPaddle().getHeight()) ){
					mainBall.reverseY();
					//System.out.println("2 reverse");
					mainBall.setXSpeed(mainBall.getXSpeed() - 1);
					}
				}
				if(mainBall.getYSpeed() >= 0){
					if((mainBall.getYLoc() + mainBall.getSize() < paddle1.getPaddle().getY() + paddle1.getPaddle().getHeight()/2) 
						&& (mainBall.getYLoc() > paddle1.getPaddle().getY()) ){
					mainBall.reverseY();
					//System.out.println("3 reverse");
					mainBall.setXSpeed(mainBall.getXSpeed() - 1);
					}
				}
			}
	}
	
	//If ball collides with paddle2, reverse y direction
	private void checkCollisionWith2() {
		if ( (mainBall.getXLoc() + mainBall.getSize() > paddle2.getPaddle().getX()) 
				&& (mainBall.getXLoc() < paddle2.getPaddle().getX() + paddle2.getPaddle().getWidth()) )
			if ( (mainBall.getYLoc() > (paneWH - (mainBall.getSize() + paddle2.getPaddle().getHeight()))) &&
					(mainBall.getYSpeed() > 0) ) {
				mainBall.reverseY();
				mainBall.setPaddleLastHit(paddle2);
				System.out.println("2 hit");
				if(mainBall.getXSpeed() <= 0){
					if((mainBall.getXLoc() + mainBall.getSize() > paddle2.getPaddle().getX() + paddle2.getPaddle().getWidth()/2) 
						&& (mainBall.getXLoc() < paddle2.getPaddle().getX() + paddle2.getPaddle().getWidth()) ){
					mainBall.reverseX();
					//System.out.println("2 reverse");
					mainBall.setYSpeed(mainBall.getYSpeed() - 1);
					}
				}
				if(mainBall.getXSpeed() >= 0){
					if((mainBall.getXLoc() + mainBall.getSize() < paddle2.getPaddle().getX() + paddle2.getPaddle().getWidth()/2) 
						&& (mainBall.getXLoc() > paddle2.getPaddle().getX()) ){
					mainBall.reverseX();
					//System.out.println("2 reverse");
					mainBall.setYSpeed(mainBall.getYSpeed() - 1);
					}
				}
			}
	}
	
	//If ball collides with paddle3, reverse x direction
	private void checkCollisionWith3() {
		if ( (mainBall.getYLoc() + mainBall.getSize() > paddle3.getPaddle().getY()) 
				&& (mainBall.getYLoc() < paddle3.getPaddle().getY() + paddle3.getPaddle().getHeight()) )
			if ( mainBall.getXLoc() > (paneWH - (mainBall.getSize() + paddle3.getPaddle().getWidth())) &&
					(mainBall.getXSpeed() > 0) ) {
				mainBall.reverseX();
				mainBall.setPaddleLastHit(paddle3);
				//System.out.println("3 hit");
				if(mainBall.getYSpeed() <= 0){
					if((mainBall.getYLoc() + mainBall.getSize() > paddle3.getPaddle().getY() + paddle3.getPaddle().getHeight()/2) 
						&& (mainBall.getYLoc() < paddle3.getPaddle().getY() + paddle3.getPaddle().getHeight()) ){
					mainBall.reverseY();
					//System.out.println("3 reverse");
					mainBall.setXSpeed(mainBall.getXSpeed() + 1);
					}
				}
				if(mainBall.getYSpeed() >= 0){
					if((mainBall.getYLoc() + mainBall.getSize() < paddle3.getPaddle().getY() + paddle3.getPaddle().getHeight()/2) 
						&& (mainBall.getYLoc() > paddle3.getPaddle().getY()) ){
					mainBall.reverseY();
					//System.out.println("3 reverse");
					mainBall.setXSpeed(mainBall.getXSpeed() + 1);
					}
				}
			}
	}
	
	//If ball collides with paddle4, reverse y direction
	private void checkCollisionWith4() {
		if ( (mainBall.getXLoc() + mainBall.getSize() > paddle4.getPaddle().getX())
				&& (mainBall.getXLoc() < paddle4.getPaddle().getX() + paddle4.getPaddle().getWidth()) )
			if ( mainBall.getYLoc() < (paddle2.getPaddle().getHeight()) &&
					(mainBall.getYSpeed() < 0) ) {
				mainBall.reverseY();
				mainBall.setPaddleLastHit(paddle4);
				//System.out.println("4 hit");
				if(mainBall.getXSpeed() <= 0){
					if((mainBall.getXLoc() + mainBall.getSize() > paddle4.getPaddle().getX() + paddle4.getPaddle().getWidth()/2) 
						&& (mainBall.getXLoc() < paddle4.getPaddle().getX() + paddle4.getPaddle().getWidth()) ){
					mainBall.reverseX();
					//System.out.println("4 reverse");
					if (Math.abs(mainBall.getYSpeed()) > 2)
					mainBall.setYSpeed(mainBall.getYSpeed() + 1);
					}
				}
				if(mainBall.getXSpeed() >= 0){
					if((mainBall.getXLoc() + mainBall.getSize() < paddle4.getPaddle().getX() + paddle4.getPaddle().getWidth()/2) 
						&& (mainBall.getXLoc() > paddle4.getPaddle().getX()) ){
					mainBall.reverseX();
					//System.out.println("4 reverse");
					mainBall.setYSpeed(mainBall.getYSpeed() + 1);
					}
				}
			}
	}
	
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
	
	//Continuously update the game screen to keep the ball moving

	//moves the ball and AI paddles

	public void continuousUpdate() {
		Task task = new Task<Void>() {
			  @Override
			  public Void call() throws Exception {
			    while (true) {
			      Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
			        	mainBall.moveBall();
			        try{ball1.moveBall();
			        	ball2.moveBall();
			        	ball3.moveBall();
			        	ball4.moveBall();
			        	ball5.moveBall();
			        	ball6.moveBall();
			        	ball7.moveBall();
			        }catch (Exception e) {}
			        try {
						checkReverse();
						checkCollisionWithPowerUp();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			          player1.moveAI(mainBall);
			          //player2.moveAI(mainBall);
			          player3.moveAI(mainBall);
			          player4.moveAI(mainBall);
			          player1L.setText(paddle1.getLives() + " Lives");
			          player2L.setText(paddle2.getLives() + " Lives");
			          player3L.setText(paddle3.getLives() + " Lives");
			          player4L.setText(paddle4.getLives() + " Lives");
			          if (count%100 == 1){
			        	  generatePowerUp();
			        	  i++;
			          }
			          count++;
			        }
			      });
			      Thread.sleep(50);
			    }
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
			}
			else if (player.getPos() == 3) {
				gamePane.getChildren().remove(paddle3.getPaddle());
				paddle3.getPaddle().setX(-1000);
				paddle3.getPaddle().setY(-1000);
				rect8.setHeight(paneWH);
				int player2Lost = LocalTime.now().toSecondOfDay();
				int player2Elapsed = player2Lost - startTime;
				int player2Minutes = player2Elapsed / 60; //Minutes in game
				int player2Seconds = player2Elapsed % 60; //Seconds in game
			}
			else if (player.getPos() == 2) {
				gamePane.getChildren().remove(paddle2.getPaddle());
				paddle2.getPaddle().setX(-1000);
				paddle2.getPaddle().setY(-1000);
				rect5.setWidth(paneWH);
				int player3Lost = LocalTime.now().toSecondOfDay();
				int player3Elapsed = player3Lost - startTime;
				int player3Minutes = player3Elapsed / 60; //Minutes in game
				int player3Seconds = player3Elapsed % 60; //Seconds in game
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
			Thread.sleep(10000);
			System.exit(0); //Need to change
		}
	}
	
	public Scene getGameScene() {
		return this.gameScene;
	}
}
