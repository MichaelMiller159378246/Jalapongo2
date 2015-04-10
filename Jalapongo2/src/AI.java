//This Class is for the computer player


public class AI extends Player{

	public AI(int pos) {
		super(pos);
	}
	
	public void moveAI(Ball ball) {
		if (paddle.getLives() > 0 && paddle.getControls() != 0) {
		
		if (this.pos == 1 || this.pos == 3) {
			if ((ball.getYLoc() - this.getPaddle().getLength()/2) > this.getLoc())
				paddle.paddleMove(1);
			else
				paddle.paddleMove(-1);
		}
		else {
			if ((ball.getXLoc() - this.getPaddle().getLength()/2) > this.getLoc())
				paddle.paddleMove(1);
			else
				paddle.paddleMove(-1);
		}
	}
		
	}

}
