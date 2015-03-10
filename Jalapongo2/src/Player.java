import java.io.BufferedReader; // Imports BufferedReader
import java.io.IOException; // Imports IOException
import java.io.InputStreamReader; // Imports InputStreamReader
import java.io.PrintWriter; // Imports PrintWriter
import java.net.ServerSocket; // Imports ServerSocket
import java.net.Socket; // Imports Socket
import java.util.HashSet; // Import HashSet

/**
 * @author Mike
 * @version 1.0
 * @created 02-Mar-2015 3:19:45 PM
 */
public class Player{

	private int lives;
	private String name;
	protected Paddle player;
	private Socket socket;
	protected int position; //added
	public static String serverAddress;
	public static int port;

	
	public Player(String serverAddress) throws Exception{
		//setup networking
		Player.serverAddress = serverAddress;
		socket = new Socket(serverAddress, port);
	}
	


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
		lives =- 1;
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
	
	
}//end Player