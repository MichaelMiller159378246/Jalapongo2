/**
 * @author Mike
 * @version 1.0
 * @created 02-Mar-2015 3:19:45 PM
 */
public class Player{

	private int lives = 5;
	private String name;
	protected Paddle player;
	protected int position; //added
	

	public Player(int position){
		player = new Paddle(position);
		this.position = position;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setLives(int lives){
		this.lives = lives;
	}
	
	public int getLives(){
		return lives;
	}
	
	public void scoredOn(){
		lives -= 1;
	}
	
	//added
	public Paddle getPaddle() {
		return player;
	}
	
	//added
	public int getLoc() {
		if (position == 1 || position == 3)
			return (int)player.getPaddle().getY();
		else
			return (int)player.getPaddle().getX();
	}
	
	//added
	public int getPos() {
		return position;
	}
	
	
}//end Player