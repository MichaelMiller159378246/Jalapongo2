/**
 * @author Mike
 * @version 1.0
 * @created 02-Mar-2015 3:19:45 PM
 */
public class Player{

	private String name;
	protected Paddle paddle;
	protected int pos; //added

	public Player(int position){
		paddle = new Paddle(position);
		this.pos = position;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	//added
	public Paddle getPaddle() {
		return paddle;
	}
	
	//added
	public int getLoc() {
		if (pos == 1 || pos == 3)
			return (int)paddle.getPaddle().getY();
		else
			return (int)paddle.getPaddle().getX();
	}
	
	//added
	public int getPos() {
		return pos;
	}
	
	
}//end Player