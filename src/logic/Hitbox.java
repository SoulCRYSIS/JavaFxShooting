package logic;

import javafx.scene.image.Image;

public class Hitbox {
	private static final double HITBOX_BORDER = 0.15;
	private double left;
	private double right;
	private double top;
	private double bottom;
	private double centerX;
	private double centerY;
	private double width;
	private double height;
	
	public Hitbox(Image img, boolean is_bullet) {
		width = img.getWidth();
		height = img.getHeight();
		if (is_bullet) {
			width = Math.min(width, height);
			height = width;
			left = width;
			top = height;
			right = width;
			bottom = height;
			centerX = width / 2;
			centerY = height / 2;
		} else {
			left = width * HITBOX_BORDER;
			top = height * HITBOX_BORDER;
			right = width * (1 - HITBOX_BORDER);
			bottom = height * (1 - HITBOX_BORDER);
			centerX = width / 2;
			centerY = height / 2;
		}
	}

	public double getLeft() {
		return left;
	}

	public double getRight() {
		return right;
	}

	public double getTop() {
		return top;
	}

	public double getBottom() {
		return bottom;
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
}
