//This Class is for the computer player


public class AI extends Player{

	public AI(int pos) {
		super(pos);
	}
	
	public void moveAI(Ball ball) {
		if (this.position == 1 || this.position == 3) {
			if ((ball.getYLoc() - this.getPaddle().getLength()/2) > this.getLoc())
				player.paddleMove(1);
			else
				player.paddleMove(-1);
		}
		else {
			if ((ball.getXLoc() - this.getPaddle().getLength()/2) > this.getLoc())
				player.paddleMove(1);
			else
				player.paddleMove(-1);
		}
	}

}
