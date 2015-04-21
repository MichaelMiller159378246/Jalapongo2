import java.net.URL;
import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;

/**
 * @author Dalton L'Heureux
 * @version 1.0
 * @created 02-Mar-2015 3:19:43 PM
 */
public class Ball {
	//Jon commented on ball 
	private Rectangle ball;
	private Paddle paddleLastHit;
	private int xSpeed;
	private int ySpeed;
	private int id;
	private int size = 20;

	//Sounds
	String bounce = "Bounce.wav";
	URL resource = getClass().getResource(bounce);
	Media media = new Media(resource.toString());



	public Ball(int id){
		ball = new Rectangle(size, size);
		this.id = id;
		ball.setX(setStartLoc());
		ball.setY(setStartLoc());
		randomizeDirection();
	}

	public Ball(){
		ball = new Rectangle(size, size);
		ball.setX(-9999);
		ball.setY(-9999);
		id = -1;
	}

	public int getID(){
		return this.id;
	}

	public int setStartLoc(){
		return (int)(Math.random()*50 + 325);
	}

	public int setStartSpeed(){
		return (int)(Math.random()*2 + 5);
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
		if(!(getID() < 0)){
			ball.setX(ball.getX() + xSpeed);
			if(xSpeed == 0){
				xSpeed = xSpeed - 2;
			}
			ball.setY(ball.getY() + ySpeed);	
			if(ySpeed == 0){
				ySpeed = ySpeed + 2;
			}
		}
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
		MediaPlayer bouncePlayer = new MediaPlayer(media);	
		bouncePlayer.play();

	}

	public void reverseY() {
		ySpeed = -ySpeed;
		MediaPlayer bouncePlayer = new MediaPlayer(media);	
		bouncePlayer.play();

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
