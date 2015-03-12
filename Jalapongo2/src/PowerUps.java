import java.util.Random;

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
 *  addSpeed => 7
 *  subSpeed => 8
 *  stall => 9
 */
public class PowerUps {
	private Rectangle powerUp;
	private int type;

	private Random generator = new Random(System.currentTimeMillis());
	
	public PowerUps(){
		powerUp = new Rectangle(20,20);
			powerUp.setX(generator.nextDouble()*400+100); //Upper Left Corner
			powerUp.setY(generator.nextDouble()*400+100); //Upper Left Corner
			type = (int)(Math.random()*9+1);
			if(type == 1){
				powerUp.setFill(Color.RED); //flip
			}
			if(type == 2){
				powerUp.setFill(Color.BROWN); //multiball
			}
			if(type == 3){
				powerUp.setFill(Color.ORANGE); //addlives
			}
			if(type == 4){
				powerUp.setFill(Color.PURPLE); //shield
			}
			if(type == 5){
				powerUp.setFill(Color.PINK); //largePaddle
			}
			if(type == 6){
				powerUp.setFill(Color.YELLOW); //smallPaddle
			}
			if(type == 7){
				powerUp.setFill(Color.BLUE); //fast
			}
			if(type == 8){
				powerUp.setFill(Color.GREEN); //slow
			}	
			if(type == 9){
				powerUp.setFill(Color.CYAN); //stall
			}
	}
	
	public int getType(){
		return type;
	}
	
	public Rectangle getPowerUp(){
		return powerUp;
	}

	public void flipX(Ball ball){ //flip player controls 
		Paddle paddle = ball.getPaddleLastHit();
		if (paddle.getControls() != 1){
			paddle.setControls(1);
		}else{
			paddle.setControls(2);
		}
	}
	
	/*
	public void multiBall(){ //Add 3 - 8 balls
		int numBall = (int)(Math.random()*8 + 3);
		int[] balls;
		for(i = 0; i <= numball; i++){
			Ball balls[i] = new Ball();
		}
	}
	*/
	/*
	public void addLives(Ball ball){ //add 1 life to player
		Player player = GameScreen.getPlayer(ball.getPaddleLastHit());
		player.addLife();
	}
	*/
	public void shield(Ball ball){ // protect against one goal
		Paddle paddle = ball.getPaddleLastHit();
		
	}

	public void largePaddle(Ball ball){ //increase paddle length
		Paddle paddle = ball.getPaddleLastHit();
		paddle.setLength(300);
		//wait 10 seconds
		paddle.setLength(150);
	}

	public void smallPaddle(Ball ball){ //decrease paddle length
		Paddle paddle = ball.getPaddleLastHit();
		paddle.setLength(75);
		//wait 10 seconds
		paddle.setLength(150);
	}

	public void speedAdd(Ball ball){ //set ball speed higher
		ball.setXSpeed((int)ball.getXSpeed() + 2);
		ball.setYSpeed((int)ball.getYSpeed() + 2);
	}

	public void speedSub(Ball ball){ //set ball speed lower
		ball.setXSpeed((int)ball.getXSpeed() - 2);
		ball.setYSpeed((int)ball.getYSpeed() - 2);
	}

	public void stall(Ball ball){ //set paddle speed = 0
		Paddle paddle = ball.getPaddleLastHit();
		if (paddle.getControls() !=0){
			paddle.setControls(0);
		}
		// wait 2 seconds
		paddle.setControls(1);
	}
}//end Power-Ups
