import javafx.application.Application;
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

public class GameScreen extends Application{

	
	public void start(Stage primaryStage) throws Exception {
		
		// Start Screen GUI Menu
		// Construct GUI Opjects
		int paneWH = 700;
		Pane gamePane = new Pane();
		VBox gameVB = new VBox();
		Scene gameScene = new Scene(gamePane, paneWH, paneWH);
		
		//Corner Bumpers
		int rectW = 100;
		int rectH = 30;
		
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
		//Paddles
		Paddle testPaddle = new Paddle(1);

		Rectangle paddle = new Rectangle(30, 80, Color.BLUE);
		gamePane.getChildren().add(paddle);
		paddle.relocate(0, paneWH/2-40);		
		
		primaryStage.setScene(gameScene);
		//primaryStage.setWidth(700);
		//primaryStage.setHeight(700);
		primaryStage.show();
		primaryStage.setResizable(false);
		
}

	public static void main(String[] args) {
		GameScreen.launch(args);
	} 
	
}
