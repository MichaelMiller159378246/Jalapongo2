import javafx.scene.shape.*;

//Added by Leslie
/**
 * @author Nick
 * @version 1.0
 * @created 02-Mar-2015 3:19:45 PM
 */
public class Paddle {

	private Rectangle Paddle;
	//private Line Paddle;
	private int PaddleLocX;
	private int PaddleLocY;
	private int pos;
	private int speed = 5;

	public Paddle(int pos){
		if(pos == 1){ //left
			PaddleLocX = 0;
			Paddle = new Rectangle(PaddleLocX,325,30,150); //Rectangle(x, y, width, height)
			this.pos = pos;
		}
		if(pos == 2){ //bottom
			PaddleLocY = 670;
			Paddle = new Rectangle(325,PaddleLocY,150,30);
			this.pos = pos;
		}
		if(pos == 3){ //right
			PaddleLocX = 670;
			Paddle = new Rectangle(PaddleLocX,325,150,30);
			this.pos = pos;
		}
		if(pos == 4){ //top
			PaddleLocY = 0;
			Paddle = new Rectangle(325,PaddleLocY,30,150);
			this.pos = pos;
		}	
	}

	public int getLength(){
		int length = 0;
		if(pos == 1 || pos == 3){
			length = (int)(Paddle.getWidth());
		}else{
			length = (int)(Paddle.getHeight());
		}
		return length;
	}

	public void setLength(int length){
		if(pos == 1 || pos == 3){
			Paddle.setWidth((double)length);
		}
		else{
			Paddle.setHeight((double)length);
		}
	}

	public void paddleMove(int direction){
		if(pos == 1 || pos == 3){
			Paddle.setY(Paddle.getY() + direction*speed);
		}
		else{
			Paddle.setX(Paddle.getX() + direction*speed);
		}
	}
	
	public Rectangle getPaddle() {
		return Paddle;
	}
}//end Paddle
