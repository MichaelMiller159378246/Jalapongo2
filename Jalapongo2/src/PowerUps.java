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
		try {
		    Thread.sleep(2000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		paddle.setLength(150);
	}

	public void smallPaddle(Ball ball){ //decrease paddle length
		Paddle paddle = ball.getPaddleLastHit();
		paddle.setLength(75);
		try {
		    Thread.sleep(20000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		paddle.setLength(150);
	}

	public void speedAdd(){ //set ball speed higher

	}

	public void speedSub(){ //set ball speed lower

	}

	public void stall(Ball ball){ //set paddle speed = 0
		Paddle paddle = ball.getPaddleLastHit();
		if (paddle.getControls() !=0){
			paddle.setControls(0);
		}
		try {
		    Thread.sleep(2000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		paddle.setControls(1);
	}
}//end Power-Ups
