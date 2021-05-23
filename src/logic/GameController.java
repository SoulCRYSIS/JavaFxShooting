package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import Exception.CantLoadGraphicException;
import application.DrawUtil;
import application.Main;
import entity.base.Boss;
import entity.base.Bullet;
import entity.base.Entity;
import entity.base.Monster;
import entity.monster.Melee;
import entity.monster.Minion;
import entity.player.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;

public class GameController {
	public static ArrayList<Hitbox> hitboxes;
	private static Player player;
	private static ArrayList<Monster> monsters;
	private static ArrayList<Boss> bosses;
	private static ArrayList<Bullet> bullets_enemy;
	private static ArrayList<Bullet> bullets_player;
	private static Boss spawningBoss;
	private static int stage;
	private static long elapsedTime;
	private static final int STAGE_LENGTH = 6000;
	private static AnimationTimer MainProcess;
	private static Timer timer;
	private static boolean is_pause;
	private static AudioClip bossDeath = new AudioClip(ClassLoader.getSystemResource("boss_death.wav").toString());
	private static AudioClip backgroundMusic = new AudioClip(ClassLoader.getSystemResource("background_music.wav").toString());

	public static void initGame(GraphicsContext gc) throws CantLoadGraphicException {
		is_pause = false;
		hitboxes = new ArrayList<Hitbox>();
		try {
			DrawUtil.loadGraphics(gc);
		} catch (CantLoadGraphicException e) {
			throw e;
		}
		stage = 0;
		elapsedTime = 0;
		spawningBoss = null;
		monsters = new ArrayList<Monster>();
		bosses = new ArrayList<Boss>();
		bullets_enemy = new ArrayList<Bullet>();
		bullets_player = new ArrayList<Bullet>();

		// Monster test
//		for (Supplier<Monster> mon : EntityData.LV1) {
//			Monster monster = mon.get();
//			monster.setX(100);
//			monster.setY(100);
//			addEnemy(monster);
//		}
//
//		for (Supplier<Monster> mon : EntityData.LV2) {
//			Monster monster = mon.get();
//			monster.setX(100);
//			monster.setY(350);
//			addEnemy(monster);
//		}
//
//		for (Supplier<Monster> mon : EntityData.LV3) {
//			Monster monster = mon.get();
//			monster.setX(100);
//			monster.setY(600);
//			addEnemy(monster);
//		}
//		addEnemy(EntityData.BOSS_MAGE.get());
//		addEnemy(EntityData.BOSS_BRAIN.get());
//		addEnemy(EntityData.BOSS_EYE.get());
//		addEnemy(EntityData.BOSS_ALIEN.get());
//		addEnemy(EntityData.BOSS_QUEENBEE.get());

		initTimer();
		backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
		backgroundMusic.play();
		MainProcess = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				DrawUtil.clearCanvas();
				update();
				drawScene();

			}
		};
		MainProcess.start();
	}

	private static boolean isCollide(Entity e1, Entity e2) {
		return !(e1.getRight() < e2.getLeft() || e2.getRight() < e1.getLeft() || e1.getBottom() < e2.getTop()
				|| e2.getBottom() < e1.getTop());
	}

	public static boolean HitBorder(Entity e) {
		return (e.getLeft() < 0 || e.getRight() > Main.WIDTH || e.getTop() < 0 || e.getBottom() > Main.HEIGHT);
	}

	public static boolean OutOfScene(Entity e) {
		return (e.getCenterX() < 0 || e.getCenterX() > Main.WIDTH || e.getCenterY() < 0
				|| e.getCenterY() > Main.HEIGHT);
	}

	private static void drawScene() {
		// For draw game stage
		if (spawningBoss != null) {
			DrawUtil.drawBossSpawnArea(spawningBoss);
		}
		for (Monster mon : monsters) {
			if (mon.getSpawningTime() > 0) {
				DrawUtil.drawFade(mon);
			} else {
				DrawUtil.drawMonster(mon);
			}
//			DrawUtil.drawHitbox(mon);
		}
		DrawUtil.drawPlayer(player);
		for (Boss boss : bosses) {
			if (boss.getSpawningTime() > 0) {
				DrawUtil.drawFade(boss);
			} else {
				DrawUtil.drawMonster(boss);
			}
//			DrawUtil.drawHitbox(boss);
		}
		for (Bullet bullet : bullets_enemy) {
			DrawUtil.drawRotate(bullet);
			// DrawUtil.drawHitbox(bullet);
		}
		for (Bullet bullet : bullets_player) {
			DrawUtil.drawRotate(bullet);
			// DrawUtil.drawHitbox(bullet);
		}
		for (int i = bosses.size() - 1; i >= 0; i--) {
			DrawUtil.drawHealthBar(bosses.get(i), i);
		}
		DrawUtil.drawTimer();
		DrawUtil.drawStage(stage + 1);
		// DrawUtil.drawHitbox(player);
	}

	public static void update() {
		player.update();
		updateMonsters();
		updateBosses();
		updateBullet_enemy();
		updateBullet_Player();
		Spawner.spawn();
		int temp_stage = (int) (elapsedTime / STAGE_LENGTH);
		if (stage != temp_stage) {
			Spawner.spawnBoss(stage);
			stage++;
		}
	}

	public static void updateMonsters() {
		for (int i = monsters.size() - 1; i >= 0; i--) {
			Monster mon = monsters.get(i);
			mon.spawningTest();
			if (isCollide(mon, player)) {
				player.decreaseHP();
				if (mon instanceof Melee) {
					((Melee) mon).stop();
				} else if (mon instanceof Minion) {
					monsters.remove(i);
				}
			}
		}
	}

	public static void updateBosses() {
		for (int i = bosses.size() - 1; i >= 0; i--) {
			Boss boss = bosses.get(i);
			boss.spawningTest();
			if (isCollide(boss, player)) {
				player.decreaseHP();
			}
		}
	}

	public static void updateBullet_enemy() {
		for (int i = bullets_enemy.size() - 1; i >= 0; i--) {
			Bullet bullet = bullets_enemy.get(i);
			bullet.update();
			if (HitBorder(bullet) && !bullet.bounce()) {
				bullets_enemy.remove(i);
				continue;
			} else if (isCollide(player, bullet)) {
				player.decreaseHP();
				bullets_enemy.remove(i);
			}
		}
	}

	public static void updateBullet_Player() {
		outerloop: for (int i = bullets_player.size() - 1; i >= 0; i--) {
			Bullet bullet = bullets_player.get(i);
			bullet.update();
			if (OutOfScene(bullet) && !bullet.bounce()) {
				bullets_player.remove(i);
				continue;
			}
			for (int j = bosses.size() - 1; j >= 0; j--) {
				Boss boss = bosses.get(j);
				if (isCollide(boss, bullet)) {
					if (boss.decreaseHP(bullet.getDmg())) {
						bosses.remove(j);
						bossDeath.play();
						Main.openBuffPane();
					}
					bullets_player.remove(i);
					continue outerloop;
				}
			}
			for (int j = monsters.size() - 1; j >= 0; j--) {
				Monster mon = monsters.get(j);
				if (isCollide(mon, bullet)) {
					if (mon.decreaseHP(bullet.getDmg())) {
						monsters.remove(j);
					}
					bullets_player.remove(i);
					continue outerloop;
				}
			}
		}
	}

	private static void initTimer() {
		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				elapsedTime++;
			}
		});
		timer.start();
	}

	public static void setSpawningBoss(Boss boss) {
		spawningBoss = boss;
	}

	public static void setPlayer(Player player) {
		GameController.player = player;
	}

	public static Player getPlayer() {
		return player;
	}

	public static int getStage() {
		return stage;
	}

	public static long getElapsedTime() {
		return elapsedTime;
	}

	public static void addMonster(Monster mon) {
		monsters.add(mon);
	}

	public static void addBoss(Boss boss) {
		bosses.add(boss);
	}

	public static void addBullet_enemy(Bullet bullet) {
		bullets_enemy.add(bullet);
	}

	public static void addBullet_player(Bullet bullet) {
		bullets_player.add(bullet);
	}
	
	public static void switchState() {
		if (is_pause) {
			continueGame();
			is_pause = false;
		} else {
			pauseGame();
			DrawUtil.drawPause();
			is_pause = true;
		}
	}

	public static void pauseGame() {
		backgroundMusic.stop();
		MainProcess.stop();
		timer.stop();
	}

	public static void continueGame() {
		backgroundMusic.play();
		MainProcess.start();
		timer.start();
	}

	public static void gameEnd() {
		MainProcess.stop();
		timer.stop();
		Main.changeScene(Main.Gameover());
	}
}
