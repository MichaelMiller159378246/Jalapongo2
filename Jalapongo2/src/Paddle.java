import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

//Added by Leslie
/**
 * @author Nick
 * @version 1.0
 * @created 02-Mar-2015 3:19:45 PM
 */
public class Paddle {

	private Rectangle paddle;
	private int lives = 5;//(int) GameGUI.livesCoB.getValue(); //required on paddle for in order to add lives... impossable to getplayer based on paddle last hit
	private int PaddleLocX;
	private int PaddleLocY;
	private int pos;
	private int speed = 5;
	private int control = 1; //1=normal, 2=flipped
	private int paddle12W = 30;
	private int paddle12H = 150;

	//Sounds
	String scoreSound = "Score.wav";
	URL scoreResource = getClass().getResource(scoreSound);
	Media scoreMedia = new Media(scoreResource.toString());
	
	public Paddle(int pos){
		if(pos == 1){ //left
			PaddleLocX = 0;
			paddle = new Rectangle(PaddleLocX,325,paddle12W,paddle12H); //Rectangle(x, y, width, height)
			this.pos = pos;
			paddle.setFill(Color.BLUE);
		}
		if(pos == 2){ //bottom
			PaddleLocY = 670;
			paddle = new Rectangle(325,PaddleLocY,paddle12H,paddle12W);
			this.pos = pos;
			paddle.setFill(Color.GREEN);
		}
		if(pos == 3){ //right
			PaddleLocX = 670;
			paddle = new Rectangle(PaddleLocX,325,paddle12W,paddle12H);
			this.pos = pos;
			paddle.setFill(Color.RED);
		}
		if(pos == 4){ //top
			PaddleLocY = 0;
			paddle = new Rectangle(325,PaddleLocY,paddle12H,paddle12W);
			this.pos = pos;
			paddle.setFill(Color.YELLOW);
		}	
	}
	
	public void setLives(int lives){
		this.lives = lives;
	}
	
	public int getLives(){
		return lives;
	}
	
	public void scoredOn(){
		lives -= 1;
		MediaPlayer scorePlayer = new MediaPlayer(scoreMedia);
		scorePlayer.play();
	}
	
	public void addLife(){
		lives += 1;
	}
	
	public void setLength(int x){
		if(pos == 1 || pos == 3){
			this.paddle12H = x;
		}else{
			this.paddle12W = x;
		}
	}
	
	public int getLength(){
		//if(pos == 1 || pos == 3){ //TODO do we need this?
		//	return paddle12H;
		//}else{
		//	return paddle12W;
		//}
		return paddle12H;
	}
	
	public void setControls(int x){
		this.control = x;
	}
	
	public int getControls(){
		return this.control;
	}

	public void paddleMove(int direction){

		int min = GameScreen.min();
		int max;
		
		if(pos == 1 || pos == 3) {
			max = GameScreen.max() - (int)paddle.getHeight();
			paddle.setY(paddle.getY() + direction*speed);
			if (paddle.getY() > max)
				paddle.setY(max);
			else if (paddle.getY() < min)
				paddle.setY(min);
		}
		else {
			max = GameScreen.max() - (int)paddle.getWidth();
			paddle.setX(paddle.getX() + direction*speed);
			if (paddle.getX() > max)
				paddle.setX(max);
			else if (paddle.getX() < min)
				paddle.setX(min);
			
		}
		
	}
	
	public int getPos() {
		return pos;
	}
	
	public Rectangle getPaddle() {
		return paddle;
	}
}//end Paddle
