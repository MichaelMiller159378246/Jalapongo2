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
		//Test Paddles
		int paddleW = 150;
		
		Paddle playerPaddle1 = new Paddle(1);
		Rectangle paddle1 = playerPaddle1.getPaddle();
		
		Paddle playerPaddle2 = new Paddle(2);
		Rectangle paddle2 = playerPaddle2.getPaddle();
		
		Paddle playerPaddle3 = new Paddle(3);
		Rectangle paddle3 = playerPaddle3.getPaddle();
		
		Paddle playerPaddle4 = new Paddle(4);
		Rectangle paddle4 = playerPaddle4.getPaddle();
		gamePane.getChildren().addAll(paddle1, paddle2, paddle3, paddle4);
		
		//Moves the paddles, can only used one at a time
		//movePaddleOnKeyPress(gameScene, playerPaddle1);
		movePaddleOnKeyPress(gameScene, playerPaddle2);
		//movePaddleOnKeyPress(gameScene, playerPaddle3);
		//movePaddleOnKeyPress(gameScene, playerPaddle4);
		
		//Test Ball--------------------------------
		gamePane.getChildren().add(gameBall.getBall());
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
	
	public void continuousUpdate() {
		Task task = new Task<Void>() {
			  @Override
			  public Void call() throws Exception {
			    while (true) {
			      Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
			          gameBall.moveBall();
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
	
	public Scene getGameScene() {
		return this.gameScene;
	}

}
