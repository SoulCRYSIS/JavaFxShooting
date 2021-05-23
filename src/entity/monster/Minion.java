package entity.monster;

import entity.base.Monster;
import entity.base.RotateType;

public class Minion extends Monster {
	public Minion(int ID, double HP, double speed) {
		super(ID, HP, speed);
		this.rotateType = RotateType.FLIP;
	}
	
	@Override
	public void update() {
		updateAngle();
		move();
	}
	
	private void move() {
		x += Math.cos(angle) * speed;
		y += Math.sin(angle) * speed;
	}
	
}
