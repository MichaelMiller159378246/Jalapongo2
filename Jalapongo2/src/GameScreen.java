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
	
	
	// Start Screen GUI Menu
	// Construct GUI Opjects
	Pane gamePane = new Pane();
	Scene gameScene = new Scene(gamePane, paneWH, paneWH);
	
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
		
		Rectangle rect1 = new Rectangle(rectW, rectH); //650,0
		Rectangle rect2 = new Rectangle(rectW, rectH); //10, 0
		Rectangle rect3 = new Rectangle(rectH, rectW); //0, 10 
		Rectangle rect4 = new Rectangle(rectH, rectW); //0, 650
		Rectangle rect5 = new Rectangle(rectW, rectH); //10, 650
		Rectangle rect6 = new Rectangle(rectW, rectH); //650, 690
		Rectangle rect7 = new Rectangle(rectH, rectW); //690, 650
		Rectangle rect8 = new Rectangle(rectH, rectW); //690, 10
		
		gamePane.getChildren().addAll(rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8);
		
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
		//movePaddleOnKeyPress(gameScene, paddle1);
		movePaddleOnKeyPress(gameScene, paddle2);
		//movePaddleOnKeyPress(gameScene, paddle3);
		//movePaddleOnKeyPress(gameScene, paddle4);
		
}
	
	//Method to test paddle movement
	private void movePaddleOnKeyPress(Scene scene, Paddle paddle) {
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	      public void handle(KeyEvent event) { //@Override
	    	  
	    	  if (player2.getLives() > 0) {
	    	  
	    	  if ((paddle.getPos() == 1) || (paddle.getPos() == 3)) {
	    	  	switch (event.getCode()) {
	    	  		case UP:  	paddle.paddleMove(-1); break;
	    	  		case DOWN: 	paddle.paddleMove(1); break;
	    	  	}
	    	  }//if
	    	  
	    	  else {
		    	  switch (event.getCode()) {
		    		  case LEFT:  paddle.paddleMove(-1); break;
		    		  case RIGHT: paddle.paddleMove(1); break;
		    	  }
	    	  }//else  
	    	  
	    	  }
	      }
	    });
	  }
	
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
	}
	
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			          player1.moveAI(gameBall);
			          player3.moveAI(gameBall);
			          player4.moveAI(gameBall);
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
		if (player.getLives() < 1) {
			player.getPaddle().setLength(paneWH);
			if (player.getPos() == 1 || player.getPos() == 3)
				player.getPaddle().getPaddle().setY(0);
			else
				player.getPaddle().getPaddle().setX(0);
			System.out.println("Player " + player.getPos() + " is out");
		}
	}
	
	public Scene getGameScene() {
		return this.gameScene;
	}
}
