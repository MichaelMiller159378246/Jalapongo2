
public class AI extends Player{

	public AI(int pos) {
		super(pos);
	}
	
	public void moveAI(Ball ball) {
		if (this.position == 1 || this.position == 3) {
			if ((ball.getYLoc() + 50) > this.getLoc())
				player.paddleMove(1);
			else
				player.paddleMove(-1);
		}
		else {
			if ((ball.getXLoc() + 50) > this.getLoc())
				player.paddleMove(1);
			else
				player.paddleMove(-1);
		}
	}

}
