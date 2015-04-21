import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * @author Dalton L'Heureux
 * @version 1.0
 * @created 02-Mar-2015 3:19:46 PM
 * 
 * The PowerUps class is used to create power up objects. Each power up is created with a random type in a random location.
 * The possible power up types are as follows:
 * 
 * power up type => power up type ID #
 *  flip => 1
 *  addLives => 2
 *  shield => 3
 *  largePaddle => 4
 *  smallPaddle => 5
 *  slowSpeed => 6
 *  addSpeed => 7
 *  stall => 8
 *  multiBall => 9
 *  
 *  This class also contains methods to be called when a power up of a given type is activated.
 */

public class PowerUps {
	private Rectangle powerUp;
	private int type;
	private Ball triggerBall;
	private int size = 40;
			
	private Random generator = new Random(System.currentTimeMillis());
	
	public void setPowerUp(){
		powerUp.setX(generator.nextDouble()*500+100); //Upper Left Corner
		powerUp.setY(generator.nextDouble()*500+100); //Upper Left Corner
	}
	
	public void setType(int tempType){
		type = tempType;
		switch (type){
		case 1:
				powerUp.setFill(Color.RED); //flip
				break;
		case 2:
				powerUp.setFill(Color.ORANGE); //addlives
				break;
		case 3:
				powerUp.setFill(Color.PURPLE); //shield
				break;
		case 4:
				powerUp.setFill(Color.PINK); //largePaddle
				break;
		case 5:
				powerUp.setFill(Color.YELLOW); //smallPaddle
				break;
		case 6:
				powerUp.setFill(Color.BLUE); //slow
				break;
		case 7:
				powerUp.setFill(Color.GREEN); //fast
				break;
		case 8:
				powerUp.setFill(Color.CYAN); //stall
				break;
		case 9:
				powerUp.setFill(Color.BROWN); //multiball
				break;
	}
	}
	
	public PowerUps(){
		int flag = 1;
		powerUp = new Rectangle(size, size);
			powerUp.setX(9999); //Upper Left Corner
			powerUp.setY(9999); //Upper Left Corner
		if(GameGUI.flipCB.isSelected()||GameGUI.livesCB.isSelected()||GameGUI.shieldCB.isSelected()||GameGUI.bigCB.isSelected()||
				GameGUI.smallCB.isSelected()||GameGUI.fastCB.isSelected()||GameGUI.slowCB.isSelected()||GameGUI.stallCB.isSelected()||
				GameGUI.multiCB.isSelected()){	
			while(flag == 1){	
				type = (int)(Math.random()*9+1);
				switch (type){
					case 1:
						if(GameGUI.flipCB.isSelected()){
							powerUp.setFill(Color.RED); //flip
							flag = 0;
							break;
						}else{break;}
					case 2:
						if(GameGUI.livesCB.isSelected()){
							powerUp.setFill(Color.ORANGE); //addlives
							flag = 0;
							break;
						}else{break;}
					case 3:
						if(GameGUI.shieldCB.isSelected()){
							powerUp.setFill(Color.PURPLE); //shield
							flag = 0;
							break;
						}else{break;}
					case 4:
						if(GameGUI.bigCB.isSelected()){
							powerUp.setFill(Color.PINK); //largePaddle
							flag = 0;
							break;
						}else{break;}
					case 5:
						if(GameGUI.smallCB.isSelected()){
							powerUp.setFill(Color.YELLOW); //smallPaddle
							flag = 0;
							break;
						}else{break;}
					case 6:
						if(GameGUI.slowCB.isSelected()){
							powerUp.setFill(Color.BLUE); //slow
							flag = 0;
							break;
						}else{break;}
					case 7:
						if(GameGUI.fastCB.isSelected()){
							powerUp.setFill(Color.GREEN); //fast
							flag = 0;
							break;
						}else{break;}
					case 8:
						if(GameGUI.stallCB.isSelected()){
							powerUp.setFill(Color.CYAN); //stall
							flag = 0;
							break;
						}else{break;}
					case 9:
					    if(GameGUI.multiCB.isSelected()){
							powerUp.setFill(Color.BROWN); //multiball
							flag = 0;
							break;
						}else{break;}
					}
			}
		}else{
			//Don't make any power ups
		}
	}
	
	public void movePowerUpOffPane(Ball ball){
		this.getPowerUp().setX(-size);
		this.getPowerUp().setY(-size);
		setTriggerBall(ball);
		GameScreen.powerUpHit(this);
		GameScreen.gamePane.getChildren().remove(this); 
	}
	
	public int getSize(){
		return this.size;
	}
	
	public int getType(){
		return type;
	}
	
	public Rectangle getPowerUp(){
		return powerUp;
	}
	
	public Ball getTriggerBall(){
		return this.triggerBall;
	}
	
	public void setTriggerBall(Ball triggerBall){
		this.triggerBall = triggerBall;
	}

	public static void flip(Ball ball){ //flip player controls 
		//System.out.println("flipped controls");
		Paddle paddle = ball.getPaddleLastHit();
		if (paddle.getControls() != 1){
			paddle.setControls(1);
		}else{
			paddle.setControls(2);
		}
	}

	public static void multiBall(){ //Add 2 additional balls
		//System.out.println("multi balls");
		GameScreen.addBall();
	}
	
	public static void addLives(Ball ball){ //add 1 life to player
		//System.out.println("lives Added");
		Paddle paddle = ball.getPaddleLastHit();
		paddle.addLife();
	}
	
	public static void shield(Ball ball){ // protect against one goal
		//System.out.println("SHIELD! JK");
		Paddle paddle = ball.getPaddleLastHit();
		GameScreen.createShield(paddle);
	}

	public static void largePaddle(Ball ball){ //increase paddle length
		//System.out.println("larger paddle");
		Paddle paddle = ball.getPaddleLastHit();
		if(paddle.getPos() == 1 || paddle.getPos() == 3){ //left
			paddle.getPaddle().setHeight(200);
		}
		else{											 //bottom
			paddle.getPaddle().setWidth(200);
		}
		GameScreen.gamePane.getChildren().add(paddle.getPaddle());
	}

	public static void smallPaddle(Ball ball){ //decrease paddle length
		//System.out.println("smaller paddle");
		Paddle paddle = ball.getPaddleLastHit();
		if(paddle.getPos() == 1 || paddle.getPos() == 3){ //left
			paddle.getPaddle().setHeight(75);
		}
		else{ 											 //bottom
			paddle.getPaddle().setWidth(75);
		}
		GameScreen.gamePane.getChildren().add(paddle.getPaddle());
		}

	public static void addSpeed(Ball ball){ //set ball speed higher
		if(ball.getXSpeed() >= 0){
			ball.setXSpeed((int)ball.getXSpeed() + 3);
		}else{
			ball.setXSpeed((int)ball.getXSpeed() - 3);
		}
		
		if(ball.getYSpeed() >= 0){
			ball.setYSpeed((int)ball.getYSpeed() + 3);
		}else{
			ball.setYSpeed((int)ball.getYSpeed() - 3);
		}
		//System.out.println("faster ball");
	}

	public static void subSpeed(Ball ball){ //set ball speed lower
		if(ball.getXSpeed() >= 0){
			ball.setXSpeed((int)ball.getXSpeed() - 3);
		}else{
			ball.setXSpeed((int)ball.getXSpeed() + 3);
		}
		
		if(ball.getYSpeed() >= 0){
			ball.setYSpeed((int)ball.getYSpeed() - 3);
		}else{
			ball.setYSpeed((int)ball.getYSpeed() + 3);
		}	
		//System.out.println("slower ball");
	}

	public static void stall(Ball ball){ //set paddle speed = 0
		//System.out.println("stall controls");
		Paddle paddle = ball.getPaddleLastHit();
		if (paddle.getControls() != 0){
			paddle.setControls(0);
		}
		
		//wait 4 seconds
		  ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				paddle.setControls(1);
			}
		  };
		//After 4 seconds, trigger action event to set controls to normal
		Timer timer = new Timer(4000, taskPerformer);
		timer.setRepeats(false);
		timer.start();
	}
	
}//end Power-Ups
