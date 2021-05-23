package entity.base;

import application.Main;

public abstract class Boss extends Monster {
	private double HP_max;
	private String name;

	public Boss(int ID, double HP, double speed, String name) {
		super(ID, HP, speed);
		this.spawningTime = 120;
		this.HP_max = this.HP;
		this.name = name;
		x = Main.WIDTH / 2 - getHitbox().getCenterX();
		y = 100;
	}

	public double getHealthPercent() {
		return HP / HP_max;
	}

	public String getName() {
		return name;
	}
}
