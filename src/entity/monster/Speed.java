package entity.monster;

import application.DrawUtil;
import entity.base.Monster;
import entity.base.RotateType;
import logic.GameController;

public class Speed extends Monster {
	private static final double DASH_SPEED_COEFFICIENT = 3;
	private static final double CHARGE_SPEED_COEFFICIENT = 2;
	private int cooldown;
	private int time;
	private int chargeTime;

	public Speed(int ID, double HP, double speed, int cooldown) {
		super(ID, HP, speed);
		this.rotateType = RotateType.ROTATE;
		this.cooldown = cooldown;
	}

	@Override
	public void update() {
		if (time == cooldown) {
			if (chargeTime == (int) (cooldown / CHARGE_SPEED_COEFFICIENT)) {
				x += Math.cos(angle) * speed * DASH_SPEED_COEFFICIENT;
				y += Math.sin(angle) * speed * DASH_SPEED_COEFFICIENT;
				if (GameController.OutOfScene(this)) {
					time = 0;
					chargeTime = 0;
				}
			} else {
				chargeTime++;
				DrawUtil.drawDashPath(this);
			}

		} else {
			updateAngle();
			x += Math.cos(angle) * speed;
			y += Math.sin(angle) * speed;
			time++;
		}
	}

}
