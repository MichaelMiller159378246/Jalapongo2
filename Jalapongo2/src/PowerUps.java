import java.util.Collection;
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * @author Mike
 * @version 1.0
 * @created 02-Mar-2015 3:19:46 PM
 */
/*  flip => 1
 *  multiBall => 2
 *  addLives => 3
 *  shield => 4
 *  largePaddle => 5
 *  smallPaddle => 6
 *  slowSpeed => 7
 *  addSpeed => 8
 *  stall => 9
 */
public class PowerUps {
	private Rectangle powerUp;
	private int type;
	private Ball triggerBall;
	private static int newBallCounter = 1;
	private int size = 40;

	private Random generator = new Random(System.currentTimeMillis());
	
	public PowerUps(){
		powerUp = new Rectangle(size, size);
			powerUp.setX(generator.nextDouble()*500+100); //Upper Left Corner
			powerUp.setY(generator.nextDouble()*500+100); //Upper Left Corner
			type = (int)(Math.random()*9+1);
			
			switch (type){
			case 1:
				powerUp.setFill(Color.RED); //flip
				break;
			case 2:
				powerUp.setFill(Color.BROWN); //multiball
				break;
			case 3:
				powerUp.setFill(Color.ORANGE); //addlives
				break;
			case 4:
				powerUp.setFill(Color.PURPLE); //shield
				break;
			case 5:
				powerUp.setFill(Color.PINK); //largePaddle
				break;
			case 6:
				powerUp.setFill(Color.YELLOW); //smallPaddle
				break;
			case 7:
				powerUp.setFill(Color.BLUE); //slow
				break;
			case 8:
				powerUp.setFill(Color.GREEN); //fast
				break;
			case 9:
				powerUp.setFill(Color.CYAN); //stall
				break;
			}
	}
	
	public void movePowerUpOffPane(Ball ball){
		this.getPowerUp().setX(-size);
		this.getPowerUp().setY(-size);
		setTriggerBall(ball);
		GameScreen.powerUpHit(this);
		GameScreen.gamePane.getChildren().remove(this); 
	}
	
	public int getSize(){
		return this.size;
	}
	
	public int getType(){
		return type;
	}
	
	public Rectangle getPowerUp(){
		return powerUp;
	}
	
	public void resetNewBallCounter(){
		newBallCounter = 1;
	}
	
	public Ball getTriggerBall(){
		return this.triggerBall;
	}
	
	public void setTriggerBall(Ball triggerBall){
		this.triggerBall = triggerBall;
	}

	public static void flip(Ball ball){ //flip player controls 
		System.out.println("flipped controls");
		Paddle paddle = ball.getPaddleLastHit();
		if (paddle.getControls() != 1){
			paddle.setControls(1);
		}else{
			paddle.setControls(2);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void multiBall(){ //Add 2 additional balls
		System.out.println("multi balls");
		if(newBallCounter%8 == 0){
            try {
            	GameScreen.gamePane.getChildren().addAll((Collection<? extends Node>) (GameScreen.ball1 = new Ball()));
			} catch (Exception e){}
		}
		if(newBallCounter%8 == 1){
	        try {
            	GameScreen.gamePane.getChildren().addAll((Collection<? extends Node>) (GameScreen.ball2 = new Ball()).getBall());
	        } catch (Exception e){}
		}
		if(newBallCounter%8 == 2){
            try {
            	GameScreen.gamePane.getChildren().addAll((Collection<? extends Node>) (GameScreen.ball3 = new Ball()).getBall());
			} catch (Exception e){}
		}
		if(newBallCounter%8 == 3){
	        try {
            	GameScreen.gamePane.getChildren().addAll((Collection<? extends Node>) (GameScreen.ball4 = new Ball()).getBall());
			} catch (Exception e){}
		}
		if(newBallCounter%8 == 4){
	        try {
            	GameScreen.gamePane.getChildren().addAll((Collection<? extends Node>) (GameScreen.ball5 = new Ball()).getBall());
			} catch (Exception e){}
		}
		if(newBallCounter%8 == 5){
	        try {
            	GameScreen.gamePane.getChildren().addAll((Collection<? extends Node>) (GameScreen.ball6 = new Ball()).getBall());
			} catch (Exception e){}
		}
		if(newBallCounter%8 == 6){
	        try {
            	GameScreen.gamePane.getChildren().addAll((Collection<? extends Node>) (GameScreen.ball7 = new Ball()).getBall());
			} catch (Exception e){}
		}
		if(newBallCounter%8 == 7){
	        try {
            	GameScreen.gamePane.getChildren().addAll((Collection<? extends Node>) (GameScreen.mainBall = new Ball()).getBall());
			} catch (Exception e){}
		}
		newBallCounter++;
	}
	
	public static void addLives(Ball ball){ //add 1 life to player
		System.out.println("lives Added");
		Paddle paddle = ball.getPaddleLastHit();
		paddle.addLife();
	}
	
	public static void shield(Ball ball){ // protect against one goal
		System.out.println("SHIELD! JK");
		Paddle paddle = ball.getPaddleLastHit();
	}

	public static void largePaddle(Ball ball){ //increase paddle length
		System.out.println("larger paddle");
		Paddle paddle = ball.getPaddleLastHit();
		if(paddle.getPos() == 1 || paddle.getPos() == 3){ //left
			paddle.getPaddle().setHeight(300);
		}
		else{											 //bottom
			paddle.getPaddle().setWidth(300);
		}
		GameScreen.gamePane.getChildren().add(paddle.getPaddle());
	}

	public static void smallPaddle(Ball ball){ //decrease paddle length
		System.out.println("smaller paddle");
		Paddle paddle = ball.getPaddleLastHit();
		if(paddle.getPos() == 1 || paddle.getPos() == 3){ //left
			paddle.getPaddle().setHeight(75);
		}
		else{ 											 //bottom
			paddle.getPaddle().setWidth(75);
		}
		GameScreen.gamePane.getChildren().add(paddle.getPaddle());
		}

	public static void addSpeed(Ball ball){ //set ball speed higher
		ball.setXSpeed((int)ball.getXSpeed() + 2);
		ball.setYSpeed((int)ball.getYSpeed() + 2);
		System.out.println("faster ball");
	}

	public static void subSpeed(Ball ball){ //set ball speed lower
		ball.setXSpeed((int)ball.getXSpeed() - 2);
		ball.setYSpeed((int)ball.getYSpeed() - 1);
		System.out.println("slower ball");
	}

	public static void stall(Ball ball){ //set paddle speed = 0
		System.out.println("stall controls");
		Paddle paddle = ball.getPaddleLastHit();
		if (paddle.getControls() != 0){
			paddle.setControls(0);
		}
		//try {Thread.sleep(4000);}
		//catch (InterruptedException e) {}
		//paddle.setControls(1);
	}
}//end Power-Ups
