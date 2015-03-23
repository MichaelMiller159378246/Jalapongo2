import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class GameScreen {

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
	// Construct GUI Opjects
	Pane gamePane = new Pane();
	Scene gameScene = new Scene(gamePane, paneWH, paneWH);
	
	//Power Ups
	PowerUps powerUp1, powerUp2, powerUp3, powerUp4, powerUp5;
	
	//Ball
	Ball gameBall = new Ball();
	
	//Players
	AI player1 = new AI(1);
	Player player2 = new Player(2);
	AI player3 = new AI(3);
	AI player4 = new AI(4);
	
	//Paddles
	Paddle paddle1 = player1.getPaddle();
	Paddle paddle2 = player2.getPaddle();
	Paddle paddle3 = player3.getPaddle();
	Paddle paddle4 = player4.getPaddle();	

	
	public GameScreen() {
		
		//Make the Rectangles that make up the corners of the game screen
		Rectangle rect1 = new Rectangle(rectW, rectH); //650,0
		Rectangle rect2 = new Rectangle(rectW, rectH); //10, 0
		Rectangle rect3 = new Rectangle(rectH, rectW); //0, 10 
		Rectangle rect4 = new Rectangle(rectH, rectW); //0, 650
		Rectangle rect5 = new Rectangle(rectW, rectH); //10, 650
		Rectangle rect6 = new Rectangle(rectW, rectH); //650, 690
		Rectangle rect7 = new Rectangle(rectH, rectW); //690, 650
		Rectangle rect8 = new Rectangle(rectH, rectW); //690, 10
		
		//Get the rectangles and add them to the screen
		gamePane.getChildren().addAll(rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8);
		
		
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
									  paddle3.getPaddle(), paddle4.getPaddle(), gameBall.getBall());
		
		//Moves the paddles, can only used one at a time
		//movePaddleOnKeyPress(gameScene, player1 paddle1);
		movePaddleOnKeyPress(gameScene, player2, paddle2);
		//movePaddleOnKeyPress(gameScene, player3, paddle3);
		//movePaddleOnKeyPress(gameScene, player4, paddle4);
		
		
		
}
	private void generatePowerUp(){
		    			if(i%5 == 0){
				            try {
				            	gamePane.getChildren().remove(powerUp1.getPowerUp());
							} catch (Exception e){}
				            powerUp1 = new PowerUps();
		    				gamePane.getChildren().add(powerUp1.getPowerUp());
		    			}
		    			if(i%5 == 1){
					        try {
					            gamePane.getChildren().remove(powerUp2.getPowerUp());
					        } catch (Exception e){}
		    				powerUp2 = new PowerUps();
		    				gamePane.getChildren().add(powerUp2.getPowerUp());
		    			}
		    			if(i%5 == 2){
				            try {
				            	gamePane.getChildren().remove(powerUp3.getPowerUp());
							} catch (Exception e){}
		    				powerUp3 = new PowerUps();
		    				gamePane.getChildren().add(powerUp3.getPowerUp());
		    			}
		    			if(i%5 == 3){
					        try {
					          	gamePane.getChildren().remove(powerUp4.getPowerUp());
							} catch (Exception e){}
		    				powerUp4 = new PowerUps();
		    				gamePane.getChildren().add(powerUp4.getPowerUp());
		    			}
		    			if(i%5 == 4){
					        try {
					            gamePane.getChildren().remove(powerUp5.getPowerUp());
							} catch (Exception e){}
		    				powerUp5 = new PowerUps();
		    				gamePane.getChildren().add(powerUp5.getPowerUp());
		    			}
	}
	
	private void checkBallHitPowerUp(){
		
	}
	
	//Method to test paddle movement
	private void movePaddleOnKeyPress(Scene scene, Player player, Paddle paddle) {
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	      @SuppressWarnings("incomplete-switch")
		public void handle(KeyEvent event) { //@Override
	    	  

	    	  //Sets up the paddle movement for players 1 and 3 on the sides
	    if(paddle.getControls() == 1){
	    	  if (player.getLives() > 0) {
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
	    	  if (player.getLives() > 0) {
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
	    	  if (player.getLives() > 0) {
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

		int x = gameBall.getXLoc();
		int y = gameBall.getYLoc();
		
		//Reverse direction if the ball hits a corner
		if (x > (paneWH - rectH - 20) 
				&& (y > (paneWH - rectW - 20) || y < rectW ) 
				&& gameBall.getXSpeed() > 0) //right side
			gameBall.reverseX();
		if (x < rectH
				&& (y > (paneWH - rectW - 20) || y < rectW ) 
				&& gameBall.getXSpeed() < 0) //left side
			gameBall.reverseX();
		if (y > (paneWH - rectH - 20)
				&& (x > (paneWH - rectW - 20) || x < rectW )
				&& gameBall.getYSpeed() > 0) //bottom
			gameBall.reverseY();
		if (y < rectH
				&& (x > (paneWH - rectW - 20) || x < rectW )
				&& gameBall.getYSpeed() < 0) //top
			gameBall.reverseY();
		
		//Life Lost if Ball Passes Player's Paddle and hits their wall
		if (x < 0 && gameBall.getXSpeed() < 0) {
			player1.scoredOn();
			System.out.println("Player 1 Was Scored On");
			playerOut(player1);
			gameBall.restart();
			Thread.sleep(200);
		}
		if (y > (paneWH - 20) && gameBall.getYSpeed() > 0) {
			player2.scoredOn();
			System.out.println("Player 2 Was Scored On");
			playerOut(player2);
			gameBall.restart();
			Thread.sleep(200);
		}
		if (x > (paneWH - 20) && gameBall.getXSpeed() > 0) {
			player3.scoredOn();
			System.out.println("Player 3 Was Scored On");
			playerOut(player3);
			gameBall.restart();
			Thread.sleep(200);
		}
		if (y < 0 && gameBall.getYSpeed() < 0) {
			player4.scoredOn();
			System.out.println("Player 4 Was Scored On");
			playerOut(player4);
			gameBall.restart();
			Thread.sleep(200);
		}
		
		//Paddle Hits
		checkCollisionWith1();
		checkCollisionWith2();
		checkCollisionWith3();
		checkCollisionWith4();
		checkCollisionWithPowerUp();
	}
	
	
	// ------The following check the collision with the players paddles--------------
	
	//If ball collides with paddle1, reverse x direction

	private void checkCollisionWith1() {
		if ( (gameBall.getYLoc() + 20 > paddle1.getPaddle().getY())
				&& (gameBall.getYLoc() < paddle1.getPaddle().getY() + paddle1.getLength()) )
			if ( gameBall.getXLoc() < (rectH) &&
					(gameBall.getXSpeed() < 0) ) {
				gameBall.reverseX();
				System.out.println("1 hit");
			}
	}
	
	//If ball collides with paddle2, reverse y direction
	private void checkCollisionWith2() {
		if ( (gameBall.getXLoc() + 20 > paddle2.getPaddle().getX()) 
				&& (gameBall.getXLoc() < paddle2.getPaddle().getX() + paddle2.getLength()) )
			if ( (gameBall.getYLoc() > (paneWH - (20 + rectH))) &&
					(gameBall.getYSpeed() > 0) ) {
				gameBall.reverseY();
				System.out.println("2 hit");
			}
	}
	
	//If ball collides with paddle3, reverse x direction
	private void checkCollisionWith3() {
		if ( (gameBall.getYLoc() + 20 > paddle3.getPaddle().getY()) 
				&& (gameBall.getYLoc() < paddle3.getPaddle().getY() + paddle3.getLength()) )
			if ( gameBall.getXLoc() > (paneWH - (20 + rectH)) &&
					(gameBall.getXSpeed() > 0) ) {
				gameBall.reverseX();
				System.out.println("3 hit");
			}
	}
	
	//If ball collides with paddle4, reverse y direction
	private void checkCollisionWith4() {
		if ( (gameBall.getXLoc() + 20 > paddle4.getPaddle().getX())
				&& (gameBall.getXLoc() < paddle4.getPaddle().getX() + paddle4.getLength()) )
			if ( gameBall.getYLoc() < (rectH) &&
					(gameBall.getYSpeed() < 0) ) {
				gameBall.reverseY();
				System.out.println("4 hit");
			}
	}
	
	private void checkCollisionWithPowerUp() {
        try {
			if (   gameBall.getXLoc() >= powerUp1.getPowerUp().getX() && gameBall.getXLoc() <= powerUp1.getPowerUp().getX() + 20 
				&& gameBall.getYLoc() >= powerUp1.getPowerUp().getY() && gameBall.getYLoc() <= powerUp1.getPowerUp().getY() + 20
				
				|| gameBall.getXLoc() + 20 >= powerUp1.getPowerUp().getX() && gameBall.getXLoc() + 20 <= powerUp1.getPowerUp().getX() + 20 
				&& gameBall.getYLoc() + 20 >= powerUp1.getPowerUp().getY() && gameBall.getYLoc() + 20 <= powerUp1.getPowerUp().getY() + 20)
				{System.out.println("power up 1 hit"); gamePane.getChildren().remove(powerUp1.getPowerUp());}	
			
			if (   gameBall.getXLoc() >= powerUp2.getPowerUp().getX() && gameBall.getXLoc() <= powerUp2.getPowerUp().getX() + 20
				&& gameBall.getYLoc() >= powerUp2.getPowerUp().getY() && gameBall.getYLoc() <= powerUp2.getPowerUp().getY() + 20
				
				|| gameBall.getXLoc() + 20 >= powerUp2.getPowerUp().getX() && gameBall.getXLoc() + 20 <= powerUp2.getPowerUp().getX() + 20
				&& gameBall.getYLoc() + 20 >= powerUp2.getPowerUp().getY() && gameBall.getYLoc() + 20 <= powerUp2.getPowerUp().getY() + 20)
				{System.out.println("power up 2 hit"); gamePane.getChildren().remove(powerUp2.getPowerUp());}
			
			if (   gameBall.getXLoc() >= powerUp3.getPowerUp().getX() && gameBall.getXLoc() <= powerUp3.getPowerUp().getX() + 20
				&& gameBall.getYLoc() >= powerUp3.getPowerUp().getY() && gameBall.getYLoc() <= powerUp3.getPowerUp().getY() + 20
				
				|| gameBall.getXLoc() + 20 >= powerUp3.getPowerUp().getX() && gameBall.getXLoc() + 20 <= powerUp3.getPowerUp().getX() + 20
				&& gameBall.getYLoc() + 20 >= powerUp3.getPowerUp().getY() && gameBall.getYLoc() + 20 <= powerUp3.getPowerUp().getY() + 20)
				{System.out.println("power up 3 hit"); gamePane.getChildren().remove(powerUp3.getPowerUp());}
			
			if (   gameBall.getXLoc() >= powerUp4.getPowerUp().getX() && gameBall.getXLoc() <= powerUp4.getPowerUp().getX() + 20
				&& gameBall.getYLoc() >= powerUp4.getPowerUp().getY() && gameBall.getYLoc() <= powerUp4.getPowerUp().getY() + 20
				
				|| gameBall.getXLoc() + 20 >= powerUp4.getPowerUp().getX() && gameBall.getXLoc() + 20 <= powerUp4.getPowerUp().getX() + 20
				&& gameBall.getYLoc() + 20 >= powerUp4.getPowerUp().getY() && gameBall.getYLoc() + 20 <= powerUp4.getPowerUp().getY() + 20)
				{System.out.println("power up 4 hit"); gamePane.getChildren().remove(powerUp4.getPowerUp());}	
			
			if (   gameBall.getXLoc() >= powerUp5.getPowerUp().getX() && gameBall.getXLoc() <= powerUp5.getPowerUp().getX() + 20
				&& gameBall.getYLoc() >= powerUp5.getPowerUp().getY() && gameBall.getYLoc() <= powerUp5.getPowerUp().getY() + 20
				
				|| gameBall.getXLoc() + 20 >= powerUp5.getPowerUp().getX() && gameBall.getXLoc() + 20 <= powerUp5.getPowerUp().getX() + 20
				&& gameBall.getYLoc() + 20 >= powerUp5.getPowerUp().getY() && gameBall.getYLoc() + 20 <= powerUp5.getPowerUp().getY() + 20)
				{System.out.println("power up 5 hit"); gamePane.getChildren().remove(powerUp5.getPowerUp());}
		} catch (Exception e){}
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
			          gameBall.moveBall();
			          try {
						checkReverse();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			          player1.moveAI(gameBall);
			          player3.moveAI(gameBall);
			          player4.moveAI(gameBall);
			          if (count%50 == 1){
			        	  generatePowerUp();
			        	  i++;
			          }
			          count++;
			        }
			      });
			      Thread.sleep(100);
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
		if (player.getLives() < 1) {
			player.getPaddle().setLength(paneWH);
			if (player.getPos() == 1 || player.getPos() == 3)
				player.getPaddle().getPaddle().setY(0);
			else
				player.getPaddle().getPaddle().setX(0);
			System.out.println("Player " + player.getPos() + " is out");
		}
	}
	
	public Player getPlayer(Paddle paddle){
		int pos = paddle.getPos();
		if(pos == 1){
			return player1;
		}if(pos == 2){
			return player2;
		}if(pos == 3){
			return player3;
		}else{
			return player4;
		}
	}
	
	public Scene getGameScene() {
		return this.gameScene;
	}
}
