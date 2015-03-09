import java.net.Socket;
/**
 * @author Mike
 * @version 1.0
 * @created 02-Mar-2015 3:19:45 PM
 */
public class Player{

	private int lives;
	private String name;
	private Paddle player;
	private String serverAddress;
	private static int port;
	private Socket socket;
	
	public Player(int pos){
		player = new Paddle(pos);
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
		lives =- 1;
	}
	
	public Player(String serverAddress) throws Exception{
		
		//setup networking
		socket = new Socket(serverAddress,port);
		
		
		
	}

}//end Player