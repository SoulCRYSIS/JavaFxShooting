package entity.monster;

import java.util.function.Supplier;

import application.Main;
import entity.base.Boss;
import entity.base.Monster;
import entity.base.RotateType;
import logic.GameController;

public class Boss_Brain extends Boss {
	private Supplier<Monster> minionBuilder;
	private int time;
	private boolean direction;

	public Boss_Brain(int ID, double HP, double speed, Supplier<Monster> minionBuilder) {
		super(ID, HP, speed, "Brain of Cthulhu");
		this.minionBuilder = minionBuilder;
		this.rotateType = RotateType.STATIONARY;
		direction = ((int) (Math.random() * 2)) == 0;
	}
	
	@Override
	public void update() {
		updateAngle();
		move();
		countdown();
	}
	
	public int random(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

	public void countdown() {
		time++;
		if (time == 240) {
			Monster minion = minionBuilder.get();
			minion.setX(random(1,Main.WIDTH/2));
			minion.setY(random(1,Main.HEIGHT/2));
			GameController.addMonster(minion);

			/*
			 * minion = minionBuilder.get(); minion.setX(random(1,Main.WIDTH/2));
			 * minion.setY(random(Main.HEIGHT/2, Main.HEIGHT-1));
			 * GameController.addMonster(minion);
			 */
			
			minion = minionBuilder.get();
			minion.setX(random(Main.WIDTH/2, Main.WIDTH-1));
			minion.setY(random(1, Main.HEIGHT));
			GameController.addMonster(minion);
			
			/*
			 * minion = minionBuilder.get(); minion.setX(random(Main.WIDTH/2,
			 * Main.WIDTH-1)); minion.setY(random(Main.HEIGHT/2, Main.HEIGHT-1));
			 * GameController.addMonster(minion);
			 */
			time = 0;
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
