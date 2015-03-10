import javafx.application.Application;
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
		
		
		//-----------------------------
		//Test Paddle
		int paddleW = 150;
		Paddle playerPaddle = new Paddle(1);
		//Rectangle paddle = new Rectangle(30, paddleW, Color.BLUE);
		Rectangle paddle = playerPaddle.getPaddle();
		gamePane.getChildren().add(paddle);
		
		movePaddleOnKeyPress(gameScene, paddle);
		
		/*
		primaryStage.setScene(gameScene);
		//primaryStage.setWidth(700);
		//primaryStage.setHeight(700);
		primaryStage.show();
		primaryStage.setResizable(false);
		*/
}
	
	//Method to test paddle movement
	private void movePaddleOnKeyPress(Scene scene, Rectangle rect) {
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	      public void handle(KeyEvent event) { //@Override
	    	  
	    	  int paddleVel = 5; //paddleVel = player.getPaddleSpeed();
	    	  switch (event.getCode()) {
	    		  case UP:   rect.setY(rect.getY() - paddleVel); break;
	    		  case DOWN: rect.setY(rect.getY() + paddleVel); break;
	    	  }
	    	  boolean minY = rect.getY() > rectW;
	    	  boolean maxY = rect.getY() <  paneWH - rectW - 150;
	    	  if (!minY)
	    		  rect.setY(rectW + 1);
	    	  if (!maxY)
	    		  rect.setY((paneWH - rectW - 150 ) - 1);
	    		  
	      }
	    });
	  }
	
	public Scene getGameScene() {
		return this.gameScene;
	}
}
