package entity.monster;

import java.util.function.Supplier;

import application.DrawUtil;
import entity.base.Boss;
import entity.base.Monster;
import entity.base.RotateType;
import logic.GameController;

public class Boss_QueenBee extends Boss {
	private static final double DASH_SPEED_COEFFICIENT = 10;
	private Supplier<Monster> minionBuilder;
	private int time;
	private int chargeTime;
	private boolean direction;

	public Boss_QueenBee(int ID, double HP, double speed, Supplier<Monster> minionBuilder) {
		super(ID, HP, speed, "Queen Bee");
		this.minionBuilder = minionBuilder;
		this.direction = ((int) (Math.random() * 2)) == 0;
		this.rotateType = RotateType.FLIP;
	}

	@Override
	public void update() {
		if (time == 120) {
			if (chargeTime == 60) {
				ID = 20;
				x += Math.cos(angle) * speed * DASH_SPEED_COEFFICIENT;
				y += Math.sin(angle) * speed * DASH_SPEED_COEFFICIENT;
				if (GameController.OutOfScene(this)) {
					chargeTime = 0;
					time++;
					ID = 19;
					updateAngle();
				}
			} else {
				if (GameController.HitBorder(this)) {
					x += Math.cos(angle) * speed;
					y += Math.sin(angle) * speed;
				}
				chargeTime++;
				DrawUtil.drawDashPath(this);
			}

		} else if (time == 180 || time == 240 || time == 300) {
			Monster minion = minionBuilder.get();
			minion.setSpawnCenter(this);
			GameController.addMonster(minion);
			time++;
		} else if (time > 300) {
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
