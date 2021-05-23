package entity.monster;

import java.util.function.Supplier;

import application.Main;
import entity.base.Boss;
import entity.base.Bullet;
import entity.base.Monster;
import entity.base.RotateType;
import logic.GameController;

public class Boss_Alien extends Boss {
	private static final double DASH_SPEED_COEFFICIENT = 10;
	private Supplier<Bullet> bulletBuilder;
	private Supplier<Bullet> bulletHugeBuilder;
	private Supplier<Monster> minionBuilder;
	private int time;
	private int reloadTime;
	private boolean direction;

	public Boss_Alien(int ID, double HP, double speed, Supplier<Bullet> bulletBuilder, Supplier<Bullet> bulletHugeBuilder,
			Supplier<Monster> minionBuilder) {
		super(ID, HP, speed, "Martian");
		this.bulletBuilder = bulletBuilder;
		this.bulletHugeBuilder = bulletHugeBuilder;
		this.minionBuilder = minionBuilder;
		this.direction = ((int) (Math.random() * 2)) == 0;
		this.rotateType = RotateType.STATIONARY;
	}

	@Override
	public void update() {
		time++;
		if (time >= 60 && time <= 180) {
			reloadTime++;
			if (reloadTime == 10) {
				Bullet bullet = bulletBuilder.get();
				bullet.setSpawnCenter(this);
				bullet.setAngle((-Math.random() * Math.PI / 2) + (3 * Math.PI / 4));
				GameController.addBullet_enemy(bullet);
				reloadTime = 0;
			}
			move(speed * DASH_SPEED_COEFFICIENT);
		} else if (time == 190) {
			Bullet bullet = bulletHugeBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle((-Math.random() * Math.PI / 2) + (3 * Math.PI / 4));
			GameController.addBullet_enemy(bullet);
		} else if (time >= 240 && time <= 480) {
			reloadTime++;
			if (reloadTime == 30) {
				Monster minion = minionBuilder.get();
				if (((int) (Math.random() * 2)) == 0) {
					minion.setX(50);
				} else {
					minion.setX(Main.WIDTH - 50 - (minion.getCenterX() - minion.getX()) * 2);
				}
				minion.setY(Math.random() * 400 + 100);
				GameController.addMonster(minion);
				reloadTime = 0;
			}
			move(speed);
		} else if (time > 480) {
			time = 0;
		} else {
			move(speed);
		}
	}

	public void move(double speed) {
		if (direction) {
			x += speed;
		} else {
			x -= speed;
		}
		if (GameController.HitBorder(this)) {
			direction = !direction;
		}
	}

}
