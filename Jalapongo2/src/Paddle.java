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
	//private Line Paddle;
	private int PaddleLocX;
	private int PaddleLocY;
	private int pos;
	private int speed = 8;

	public Paddle(int pos){
		if(pos == 1){ //left
			PaddleLocX = 0;
			paddle = new Rectangle(PaddleLocX,325,30,150); //Rectangle(x, y, width, height)
			this.pos = pos;
			paddle.setFill(Color.BLUE);
		}
		if(pos == 2){ //bottom
			PaddleLocY = 670;
			paddle = new Rectangle(325,PaddleLocY,150,30);
			this.pos = pos;
			paddle.setFill(Color.GREEN);
		}
		if(pos == 3){ //right
			PaddleLocX = 670;
			paddle = new Rectangle(PaddleLocX,325,30,150);
			this.pos = pos;
			paddle.setFill(Color.RED);
		}
		if(pos == 4){ //top
			PaddleLocY = 0;
			paddle = new Rectangle(325,PaddleLocY,150,30);
			this.pos = pos;
			paddle.setFill(Color.YELLOW);
		}	
	}

	public int getLength(){
		int length;
		if(pos == 1 || pos == 3){
			length = (int)(paddle.getHeight());
		}else{
			length = (int)(paddle.getWidth());
		}
		return length;
	}

	public void setLength(int length){
		if(pos == 1 || pos == 3){
			paddle.setWidth((double)length);
		}
		else{
			paddle.setHeight((double)length);
		}
	}

	public void paddleMove(int direction){
		if(pos == 1 || pos == 3){
			paddle.setY(paddle.getY() + direction*speed);
		}
		else{
			paddle.setX(paddle.getX() + direction*speed);
		}
	}
	
	public int getPos() {
		return pos;
	}
	
	public Rectangle getPaddle() {
		return paddle;
	}
}//end Paddle
