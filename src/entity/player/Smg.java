package entity.player;

import entity.base.Bullet;
import logic.GameController;

public class Smg extends Weapon {
	public Smg() {
		// Change template here
		super(32, 1, 14);
	}

	@Override
	public void shoot() {
		// Change template here
		Bullet bullet = new Bullet(31, 20, bounceTime);
		bullet.setSpawnCenter(GameController.getPlayer());
		bullet.setAngle(GameController.getPlayer().getAngle() + Math.random() * Math.PI / 9 - Math.PI / 18);
		bullet.setDmg(atkDmg);
		GameController.addBullet_player(bullet);
	}
}
