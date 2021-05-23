package entity.player;

import application.Main;
import entity.base.Entity;
import entity.base.Updatable;
import logic.GameController;

public class Player extends Entity implements Updatable {
	private int left;
	private int right;
	private int up;
	private int down;
	private int HP;
	private Weapon weapon;
	private boolean pressing;
	private float speed;
	private boolean isImmune;

	public Player(int ID, Weapon weapon) {
		super(ID);
		x = Main.WIDTH / 2;
		y = Main.HEIGHT / 2;
		this.weapon = weapon;
		isImmune = false;
		speed = 5;
		HP = 20;
	}

	public void update() {
		move();
		weapon.update(pressing);
	}

	public void move() {
		int directionX = left * -1 + right;
		int directionY = up * -1 + down;
		if (directionX != 0 && directionY != 0) {
			x += directionX * speed / Math.sqrt(2);
			y += directionY * speed / Math.sqrt(2);
		} else {
			x += directionX * speed;
			y += directionY * speed;
		}
		if (GameController.HitBorder(this)) {
			if (directionX != 0 && directionY != 0) {
				x -= directionX * speed / Math.sqrt(2);
				y -= directionY * speed / Math.sqrt(2);
			} else {
				x -= directionX * speed;
				y -= directionY * speed;
			}
		}
	}

	public void decreaseHP() {
		if (!isImmune) {
			HP--;
			if (HP <= 0) {
				GameController.gameEnd();
			} else {
				new Thread(() -> {
					isImmune = true;
					try {
						Thread.sleep(3000);
					} catch (Exception e) {
						System.out.println(e);
					}
					isImmune = false;
				}).start();
			}
		}
		
	}

	public boolean getIsImmune() {
		return isImmune;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public void setPressing(boolean pressing) {
		this.pressing = pressing;
	}

	public Weapon getWeapon() {
		return weapon;
	}
	
	public int getHP() {
		return HP;
	}
	
	public void updateCursor(double cursorX, double cursorY) {
		angle = Math.atan2(cursorY - getCenterY(), cursorX - getCenterX());
	}
	
	public void addHP() {
		HP++;
	}
	
	public void addSpeed() {
		speed += 2;
	}
	
}
