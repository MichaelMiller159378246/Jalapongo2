import javafx.scene.shape.*;

/**
 * @author Mike
 * @version 1.0
 * @created 02-Mar-2015 3:19:46 PM
 */
public class PowerUps {
	
	private Circle appearance;
	private int power;
	private Rectangle powerUp;
	private int type;

	public PowerUps(){
		powerUp = new Rectangle();
			powerUp.setX((int)Math.random()*600 + 50); //Upper Left Corner
			powerUp.setY((int)Math.random()*600 + 50); //Upper Left Corner
			powerUp.setWidth(20);
			powerUp.setHeight(20);
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

	public void addLives(){ //add 1 life to player

	}

	public void shield(){ // protect against one goal

	}

	public void largePaddle(){ //increase paddle length

	}

	public void smallPaddle(){ //decrease paddle length

	}

	public void speedAdd(){ //set ball speed higher

	}

	public void speedSub(){ //set ball speed lower

	}

	public void stall(){ //set paddle speed = 0
		
	}
}//end Power-Ups
