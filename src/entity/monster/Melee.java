package entity.monster;

import entity.base.Monster;
import entity.base.RotateType;

public class Melee extends Monster {
	private double backup_speed;

	public Melee(int ID, double HP, double speed) {
		super(ID, HP, speed);
		this.rotateType = RotateType.FLIP;
		this.backup_speed = speed;
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

	public void stop() {
		new Thread(() -> {
			speed = 0;
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println(e);
			}
			speed = backup_speed;
		}).start();
	}
}
