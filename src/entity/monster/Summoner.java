package entity.monster;

import java.util.function.Supplier;

import entity.base.Monster;
import entity.base.RotateType;
import logic.GameController;

public class Summoner extends Monster {
	private Supplier<Monster> minionBuilder;
	private int cooldown;
	private int time;
	private boolean direction;
	
	public Summoner(int ID, double HP, double speed, int cooldown, Supplier<Monster> minionBuilder) {
		super(ID, HP, speed);
		this.rotateType = RotateType.FLIP;
		this.cooldown = cooldown;
		this.minionBuilder = minionBuilder;
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
	
	public void countdown() {
		if (time == cooldown) {
			Monster minion = minionBuilder.get();
			minion.setSpawnCenter(this);
			GameController.addMonster(minion);
			time = 0;
		} else {
			time++;
		}
	}
}
