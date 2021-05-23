package entity.player;

import entity.base.Bullet;
import logic.GameController;

public class Shotgun extends Weapon {
	public Shotgun() {
		// Change template here
		super(33, 1, 35);
	}
	@Override
	public void shoot() {
		// Change template here
		Bullet bullet = new Bullet(31, 15, bounceTime);
		bullet.setSpawnCenter(GameController.getPlayer());
		bullet.setAngle(GameController.getPlayer().getAngle());
		bullet.setDmg(atkDmg);
		GameController.addBullet_player(bullet);
		
		bullet = new Bullet(31, 15, bounceTime);
		bullet.setSpawnCenter(GameController.getPlayer());
		bullet.setAngle(GameController.getPlayer().getAngle() - Math.PI / 12);
		bullet.setDmg(atkDmg);
		GameController.addBullet_player(bullet);
		
		bullet = new Bullet(31, 15, bounceTime);
		bullet.setSpawnCenter(GameController.getPlayer());
		bullet.setAngle(GameController.getPlayer().getAngle() + Math.PI / 12);
		bullet.setDmg(atkDmg);
		GameController.addBullet_player(bullet);
	}
}
