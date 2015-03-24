import java.util.Random;

import javafx.scene.paint.Color;
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
	private Paddle paddleLastHit;
	private int xSpeed;
	private int ySpeed;
	private int size = 20;

	public Ball(){
		ball = new Rectangle(size, size);
		ball.setX(setStartLoc());
		ball.setY(setStartLoc());
		randomizeDirection();
	}

	public int setStartLoc(){
		return (int)(Math.random()*301 + 200);
	}
	
	public int setStartSpeed(){
		return (int)(Math.random()*3 + 5);
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

	public void moveBall(){
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
	
	//Jon's add-ons
	public Rectangle getBall() {
		return ball;
	}
	
	public void reverseX() {
		xSpeed = -xSpeed;
	}
	
	public void reverseY() {
		ySpeed = -ySpeed;
	}
	
	public void restart() {
		ball.setX(setStartLoc());
		ball.setY(setStartLoc());
		ball.setWidth(size);
		ball.setHeight(size);
		xSpeed = setStartSpeed();
		ySpeed = setStartSpeed();
		randomizeDirection();
	}
	
	private void randomizeDirection() {
		Random random = new Random();
		boolean randX = random.nextBoolean();
		boolean randY = random.nextBoolean();
		
		xSpeed = setStartSpeed();
		ySpeed = setStartSpeed();
		
		//Prevent ball from moving in constant diagonal
				while (Math.abs(xSpeed) - Math.abs(ySpeed) < 2) {
					if (Math.abs(xSpeed) > Math.abs(ySpeed))
						ySpeed--;
					else
						xSpeed--;
				}
		
		//Make x-direction random
		if (randX)
			xSpeed = -xSpeed;
		//make y-direction random
		if (randY)
			ySpeed = -ySpeed;
	}

	public int getSize() {
		return this.size;
	}
}//end Ball
