package entity.player;

import javafx.scene.media.AudioClip;

public abstract class Weapon {
	private static final double ATK_SPEED_COEFFICIENT = 0.5;
	private static final double ATK_DAMAGE_COEFFICIENT = 0.5;
	private int reloadTime;
	private int default_reloadTime;
	private int time;
	private int ID;
	private double atkSpeed;
	private double baseDmg;
	protected double atkDmg;
	protected int bounceTime;
	private static AudioClip shootSound = new AudioClip(ClassLoader.getSystemResource("gun_shoot.wav").toString());

	public abstract void shoot();

	public Weapon(int id, double baseDmg, int reloadTime) {
		this.ID = id;
		this.baseDmg = baseDmg;
		this.atkDmg = baseDmg;
		this.reloadTime = reloadTime;
		this.default_reloadTime = reloadTime;
		this.time = reloadTime;
		this.atkSpeed = 1;
		this.bounceTime = 0;
	}

	public void addAtkSpeed() {
		atkSpeed += ATK_SPEED_COEFFICIENT;
		this.reloadTime = (int) (default_reloadTime / atkSpeed);
	}

	public void addAtkDamage() {
		atkDmg += baseDmg * ATK_DAMAGE_COEFFICIENT;
	}

	public void addBounce() {
		this.bounceTime++;
	}
	
	public void update(boolean pressing) {
		if (time >= reloadTime && pressing) {
			shoot();
			shootSound.play();
			time = 0;
		} else if (time < reloadTime) {
			time++;
		}
	}

	public int getID() {
		return ID;
	}

}
