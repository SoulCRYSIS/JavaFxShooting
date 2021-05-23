package entity.monster;

import java.util.function.Supplier;

import entity.base.Boss;
import entity.base.Bullet;
import entity.base.RotateType;
import logic.GameController;

public class Boss_Mage extends Boss {
	private Supplier<Bullet> bulletBuilder;
	private int time;
	private boolean direction;

	public Boss_Mage(int ID, double HP, double speed, Supplier<Bullet> bulletBuilder) {
		super(ID, HP, speed, "Dark Mage");
		this.bulletBuilder = bulletBuilder;
		this.rotateType = RotateType.FLIP;
		direction = ((int) Math.random() * 2) == 0;
	}
	
	@Override
	public void update() {
		updateAngle();
		move();
		countdown();
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

	public void countdown() {
		time++;
		if (time == 240) {
			Bullet bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle(Math.PI / 3);
			GameController.addBullet_enemy(bullet);

			bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle((Math.PI / 3) * 2);
			GameController.addBullet_enemy(bullet);

			bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle(Math.PI);
			GameController.addBullet_enemy(bullet);

			bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle(Math.PI / 3 + Math.PI);
			GameController.addBullet_enemy(bullet);

			bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle((Math.PI / 3) * 2 + Math.PI);
			GameController.addBullet_enemy(bullet);

			bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle(Math.PI * 2);
			GameController.addBullet_enemy(bullet);
			time = 0;
		} else if (time == 120) {
			Bullet bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle(Math.PI / 4);
			GameController.addBullet_enemy(bullet);

			bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle(3 * Math.PI / 4);
			GameController.addBullet_enemy(bullet);

			bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle(5 * Math.PI / 4);
			GameController.addBullet_enemy(bullet);

			bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle(7 * Math.PI / 4);
			GameController.addBullet_enemy(bullet);
		}

	}

}
