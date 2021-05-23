package entity.base;

import logic.GameController;
import logic.Hitbox;

public class Entity {
	protected double x;
	protected double y;
	protected double angle;
	protected int ID;
	private Hitbox hitbox;
	
	public Entity(int ID) {
		this.ID = ID;
		this.hitbox = GameController.hitboxes.get(ID);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getLeft() {
		return x + hitbox.getLeft();
	}

	public double getRight() {
		return x + hitbox.getRight();
	}

	public double getTop() {
		return y + hitbox.getTop();
	}

	public double getBottom() {
		return y + hitbox.getBottom();
	}

	public double getCenterX() {
		return x + hitbox.getCenterX();
	}

	public double getCenterY() {
		return y + hitbox.getCenterY();
	}
	
	public double getAngle() {
		return angle;
	}

	public void setSpawnCenter(Entity e) {
		x = e.getCenterX() - hitbox.getCenterX();
		y = e.getCenterY() - hitbox.getCenterY();
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public int getID() {
		return ID;
	}

}
