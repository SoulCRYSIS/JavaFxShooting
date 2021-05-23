package entity.player;

import entity.base.Bullet;
import logic.GameController;

public class Rifle extends Weapon {

	public Rifle() {
		//Change template here
		super(34, 2.5, 28);
		bounceTime = 1;
	}

	@Override
	public void shoot() {
		//Change template here
		Bullet bullet = new Bullet(31, 25, bounceTime);
		bullet.setSpawnCenter(GameController.getPlayer());
		bullet.setAngle(GameController.getPlayer().getAngle());
		bullet.setDmg(atkDmg);
		GameController.addBullet_player(bullet);
	}
}
