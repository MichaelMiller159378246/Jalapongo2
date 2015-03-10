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
		
		rect1.relocate(paneWH-rectW,0);
		rect2.relocate(0, 0);
		rect3.relocate(0, 0);
		rect4.relocate(0, paneWH-rectW);
		rect5.relocate(0, paneWH-rectH);
		rect6.relocate(paneWH-rectW, paneWH-rectH);
		rect7.relocate(paneWH-rectH, paneWH-rectW);
		rect8.relocate(paneWH-rectH, 0);
		
		
		//-----------------------------
		//Test Paddle
		int paddleW = 150;
		Rectangle paddle = new Rectangle(30, paddleW, Color.BLUE);
		gamePane.getChildren().add(paddle);
		paddle.relocate(0, (paneWH/2 - paddleW/2));		
		
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
	    		  case UP:   rect.setY(rect.getY() - paddleVel); System.out.println(rect.getY());break;
	    		  case DOWN: rect.setY(rect.getY() + paddleVel); break;
	    	  }
	    	  boolean minY = rect.getY() > -(paneWH/2 - 1.5*rectW);
	    	  boolean maxY = rect.getY() <  (paneWH/2 - 1.5*rectW);
	    	  if (!minY)
	    		  rect.setY(-(paneWH/2 - 1.5*rectW) + 1);
	    	  if (!maxY)
	    		  rect.setY((paneWH/2 - 1.5*rectW) -1);
	    		  
	      }
	    });
	  }
	
	public Scene getGameScene() {
		return this.gameScene;
	}
}
