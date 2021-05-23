package entity.base;

import application.Main;

public class Bullet extends Entity implements Updatable {
	private double speedX;
	private double speedY;
	private int bounceLeft;
	private double dmg;
	private double speed;
//	private ImageView iv_img;
//	private Image img;

	public Bullet(int ID, double speed, int bounceLeft) {
		super(ID);
		this.speed = speed;
		this.bounceLeft = bounceLeft;
	}

	@Override
	public void update() {
		x += speedX;
		y += speedY;

	}

	public boolean bounce() {
		if (bounceLeft == 0) {
			return false;
		} else {
			bounceLeft--;
			if (getLeft() <= 0 || getRight() >= Main.WIDTH) {
				angle = Math.PI - angle;
				speedX = -speedX;
			} else if (getTop() <= 0 || getBottom() >= Main.HEIGHT) {
				angle = -angle;
				speedY = -speedY;
			}
			return true;
		}
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}

	public double getDmg() {
		return dmg;
	}

	public void setAngle(double angle) {
		this.angle = angle;
		this.speedX = (double) Math.cos(angle) * speed;
		this.speedY = (double) Math.sin(angle) * speed;
	}

}
