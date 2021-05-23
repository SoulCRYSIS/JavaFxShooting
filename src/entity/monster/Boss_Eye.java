package entity.monster;

import java.util.function.Supplier;

import application.DrawUtil;
import entity.base.Boss;
import entity.base.Bullet;
import entity.base.RotateType;
import logic.GameController;

public class Boss_Eye extends Boss {
	private static final double DASH_SPEED_COEFFICIENT = 10;
	private Supplier<Bullet> bulletBuilder;
	private int time;
	private int chargeTime;
	private boolean direction;

	public Boss_Eye(int ID, double HP, double speed, Supplier<Bullet> bulletBuilder) {
		super(ID, HP, speed, "Eye of Cthulhu");
		this.bulletBuilder = bulletBuilder;
		this.direction = ((int) (Math.random() * 2)) == 0;
		this.rotateType = RotateType.ROTATE;
	}

	@Override
	public void update() {
		if (time == 120) {
			if (chargeTime == 60 || chargeTime == 120 || chargeTime == 180) {
				x += Math.cos(angle) * speed * DASH_SPEED_COEFFICIENT;
				y += Math.sin(angle) * speed * DASH_SPEED_COEFFICIENT;
				if (GameController.OutOfScene(this)) {
					chargeTime++;
					updateAngle();
				}
			} else if (chargeTime > 180) {
				time++;
				chargeTime = 0;
			} else {
				if (GameController.HitBorder(this)) {
					x += Math.cos(angle) * speed;
					y += Math.sin(angle) * speed;
				}
				chargeTime++;
				DrawUtil.drawDashPath(this);
			}

		} else if (time == 180 || time == 190 || time == 200 || time == 240 || time == 250 || time == 260 || time == 300
				|| time == 310 || time == 320) {
			Bullet bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle(angle);
			GameController.addBullet_enemy(bullet);
			time++;
		} else if (time > 320) {
			time = 0;
		} else {
			updateAngle();
			if (GameController.HitBorder(this)) {
				x += Math.cos(angle) * speed;
				y += Math.sin(angle) * speed;
			} else {
				move();
			}
			time++;
		}
	}

	public void move() {
		if (direction) {
			x += Math.cos(angle + Math.PI / 2) * speed;
			y += Math.sin(angle + Math.PI / 2) * speed;
		} else {
			x += Math.cos(angle - Math.PI / 2) * speed;
			y += Math.sin(angle - Math.PI / 2) * speed;
		}

		if (GameController.HitBorder(this)) {
			direction = !direction;
		}
	}

}
