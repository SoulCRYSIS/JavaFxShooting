package logic;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

import application.Main;
import entity.base.Boss;
import entity.base.Monster;

public class Spawner {
	private static final Random RAND = new Random();
	private static final int MIN_DISTANT = 10000;
	private static final int SPAWN_RATE = 200;
	private static final int SPAWN_SCALE = 1000;
	private static final int SHIFT_AMOUNT = 1000;
	private static int shiftX;
	private static int time;
	private static int cooldown;

	public static double distantPlayer(Monster mon) {
		return Math.pow(mon.getCenterX() - GameController.getPlayer().getCenterX(), 2)
				+ Math.pow(mon.getCenterY() - GameController.getPlayer().getCenterY(), 2);
	}

	public static void spawn() {
		time++;
		if (time >= cooldown) {
			spawnMonster();
			cooldown = (int) (SPAWN_RATE / Math.log10((GameController.getElapsedTime() - shiftX) / SPAWN_SCALE + 10));
			time = 0;
		}
	}

	public static void spawnMonster() {
		WeightedRandom<ArrayList<Supplier<Monster>>> randomLevel = new WeightedRandom<ArrayList<Supplier<Monster>>>();
		// change level scale here
		randomLevel.addElement(EntityData.LV1, 1);
		randomLevel.addElement(EntityData.LV2, 0.1 * GameController.getElapsedTime() / 12000);
		randomLevel.addElement(EntityData.LV3, 0.01 * Math.pow(GameController.getElapsedTime() / 12000, 2));
		ArrayList<Supplier<Monster>> level = randomLevel.next();
		Monster mon = level.get(RAND.nextInt(4)).get();
		while (true) {
			mon.setX(RAND.nextDouble() * (Main.WIDTH - mon.getHitbox().getWidth()));
			mon.setY(RAND.nextDouble() * (Main.HEIGHT - mon.getHitbox().getHeight()));
			if (distantPlayer(mon) > MIN_DISTANT) {
				GameController.addMonster(mon);
				break;
			}
		}
		mon.updateAngle();
	}

	public static void spawnBoss(int stage) {
		new Thread(()->{
			Boss boss = EntityData.BOSS.get(stage % 5).get();
			boss.updateAngle();
			GameController.setSpawningBoss(boss);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			GameController.addBoss(boss);
			GameController.setSpawningBoss(null);
		}).start();
		shiftX += SHIFT_AMOUNT;
	}

}
