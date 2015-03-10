import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameScreen {

	//Corner Bumpers
	int rectW = 100;
	int rectH = 30;
	//Pane Size
	int paneWH = 700;
	
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
	    	  
	    	  
	    	  if ((paddle.getPos() == 1) || (paddle.getPos() == 3)) {
	    		boolean minY = paddle.getPaddle().getY() >= rectW;
	    	  	boolean maxY = paddle.getPaddle().getY() <=  paneWH - rectW - 150;
	    	  	switch (event.getCode()) {
	    	  		case UP:  	if (minY) paddle.paddleMove(-1);
	    		  				else paddle.paddleMove(1); break;
	    	  		case DOWN: 	if(maxY) paddle.paddleMove(1); 
	    		  			 	else paddle.paddleMove(-1); break;
	    	  	}
	    	  }//if
	    	  
	    	  else {
	    		  boolean minX = paddle.getPaddle().getX() >= rectW;
		    	  boolean maxX = paddle.getPaddle().getX() <=  paneWH - rectW - 150;
		    	  switch (event.getCode()) {
		    		  case LEFT:  if (minX) paddle.paddleMove(-1);
		    		  			  else paddle.paddleMove(1); break;
		    		  case RIGHT: if(maxX) paddle.paddleMove(1); 
		    		  			  else paddle.paddleMove(-1); break;
		    	  }
	    	  }//else  
	      }
	    });
	  }
	
	private void checkReverse() {
		if ((gameBall.getXLoc() > 700) || (gameBall.getXLoc() < 0))
			gameBall.reverseX();
		if ((gameBall.getYLoc()> 700) || (gameBall.getYLoc() < 0))
			gameBall.reverseY();
		
		checkCollisionWith2();
	}
	
	private void checkCollisionWith2() {
		if ((gameBall.getXLoc() - 20) > player2.getLoc() &&
				gameBall.getXLoc() < (player2.getLoc() + paddle2.getLength()))
			if ( (gameBall.getYLoc() > (paneWH - (20 + 30))) &&
					(gameBall.getYSpeed() > 0) ) {
				gameBall.reverseY();
				System.out.println("Col");
			}
	}
	
	public void continuousUpdate() {
		Task task = new Task<Void>() {
			  @Override
			  public Void call() throws Exception {
			    while (true) {
			      Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
			          gameBall.moveBall();
			          checkReverse();
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
	
	public Scene getGameScene() {
		return this.gameScene;
	}
}
