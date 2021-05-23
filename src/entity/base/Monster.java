package entity.base;

import logic.GameController;

public abstract class Monster extends Entity implements Updatable {
	private static final int HP_SCALE = 12000;
	protected int spawningTime;
	protected double HP;
	protected double speed;
	protected RotateType rotateType;

	public Monster(int ID, double HP, double speed) {
		super(ID);
		this.spawningTime = 60;
		this.speed = speed;
		this.HP = HP + HP * GameController.getElapsedTime() / HP_SCALE;
	}

	public void updateAngle() {
		angle = Math.atan2(GameController.getPlayer().getCenterY() - getCenterY(),
				GameController.getPlayer().getCenterX() - getCenterX());
	}

	public double getHP() {
		return HP;
	}

	public boolean decreaseHP(double dmg) {
		this.HP -= dmg;
		return HP <= 0;
	}

	public void spawningTest() {
		if (spawningTime > 0) {
			spawningTime--;
		} else {
			update();
		}
	}
	
	public int getSpawningTime() {
		return spawningTime;
	}
	
	public RotateType getRotateType() {
		return rotateType;
	}
	
}
