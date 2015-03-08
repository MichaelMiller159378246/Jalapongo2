import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


/**
 * @author Mike
 * @version 1.0
 * @created 02-Mar-2015 3:19:43 PM
 */
public class Ball {
//Jon commented on ball 
	private Rectangle ball;
	private Circle GUIBall;
	private Paddle paddleLastHit;
	private int xSpeed;
	private int ySpeed;
	private int size;

	public Ball(){
		ball = new Rectangle();
			ball.setX(setStartLoc());//Upper Left Corner
			ball.setY(setStartLoc());//Upper Left Corner
			ball.setWidth(size);
			ball.setHeight(size);
		xSpeed = setStartSpeed();
		ySpeed = setStartSpeed();
	}

	public int setStartLoc(){
		return (int)Math.ceil(Math.random()*300 + 200);
	}
	
	public int setStartSpeed(){
		return (int)Math.ceil(Math.random()*10 + 5);
	}

	public Paddle getPaddleLastHit(){
		return this.paddleLastHit;
	}

	public int getXLoc(){
		return (int)ball.getX();
	}

	public int getXSpeed(){
		return xSpeed;
	}

	public int getYLoc(){
		return (int)ball.getY();
	}

	public int getYSpeed(){
		return ySpeed;
	}

	public void moveBall(){//This needs Work
		ball.setX(ball.getX() + xSpeed);
		ball.setY(ball.getY() + ySpeed);
	}

	public void setPaddleLastHit(Paddle paddleLastHit){
		this.paddleLastHit = paddleLastHit;
	}

	public void setXLoc(int xLoc){
		ball.setX(xLoc);
	}

	public void setXSpeed(int xSpeed){
		this.xSpeed = xSpeed;
	}

	public void setYLoc(int yLoc){
		ball.setY(yLoc);
	}

	public void setYSpeed(int ySpeed){
		this.ySpeed = ySpeed;
	}
	
	public void setWidthAndHeight(int size){
		ball.setWidth(size);
		ball.setHeight(size);
		this.size = size;
	}
	
}//end Ball
