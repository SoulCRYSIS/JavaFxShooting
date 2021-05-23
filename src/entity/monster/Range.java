package entity.monster;

import java.util.function.Supplier;

import entity.base.Bullet;
import entity.base.Monster;
import entity.base.RotateType;
import logic.GameController;

public class Range extends Monster {
	private Supplier<Bullet> bulletBuilder;
	private int cooldown;
	private int time;
	private boolean direction;

	public Range(int ID, double HP, double speed, int cooldown, Supplier<Bullet> bulletBuilder) {
		super(ID, HP, speed);
		this.rotateType = RotateType.FLIP;
		this.cooldown = cooldown;
		this.bulletBuilder = bulletBuilder;
		direction = ((int) (Math.random() * 2)) == 0;
	}
	
	@Override
	public void update() {
		updateAngle();
		move();
		countdown();
	}
	
	private void move() {
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

	private void countdown() {
		if (time == cooldown) {
			Bullet bullet = bulletBuilder.get();
			bullet.setSpawnCenter(this);
			bullet.setAngle(angle);
			GameController.addBullet_enemy(bullet);
			time = 0;
		} else {
			time++;
		}
	}
}
