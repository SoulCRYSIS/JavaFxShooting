package application;

import java.util.ArrayList;

import Exception.CantLoadGraphicException;
import entity.base.Boss;
import entity.base.Entity;
import entity.base.Monster;
import entity.base.RotateType;
import entity.player.Player;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import logic.GameController;
import logic.Hitbox;
import logic.PathList;

public class DrawUtil {
	private static GraphicsContext gc;
	private static Font font_timer;
	private static Font font_bossname;
	private static Font font_text;
	private static Image background;
	private static Image healthbar_front;
	private static Image healthbar_back;
	private static Image hp_icon;
	private static Image immune_ring;
	private static ColorAdjust colorAdjust;
	public static ArrayList<Image> EntityGraphics;

	public static void loadGraphics(GraphicsContext gc_temp) throws CantLoadGraphicException {
		font_timer = Font.loadFont("file:font/ARCADECLASSIC.ttf", 50);
		font_bossname = Font.loadFont("file:font/Minecraft.ttf", 20);
		font_text = Font.loadFont("file:font/Warden Regular.otf", 100);
		gc = gc_temp;
		gc.setTextAlign(TextAlignment.CENTER);
		background = new Image("Background.jpg");
		healthbar_front = new Image("healthbar_front.png");
		healthbar_back = new Image("healthbar_back.png");
		hp_icon = new Image("heart.png");
		immune_ring = new Image("immune_ring.gif");
		EntityGraphics = new ArrayList<Image>();
		colorAdjust = new ColorAdjust();
		for (String path : PathList.ENTITY) {
			Image img;
			try {
				img = new Image(path);
			} catch (Exception e) {
				throw new CantLoadGraphicException(path);
			}
			EntityGraphics.add(img);
			GameController.hitboxes.add(new Hitbox(img, path.contains("bullet")));
		}
	}

	public static void clearCanvas() {
		gc.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
	}

	public static void drawBackground(GraphicsContext bg) {
		bg.drawImage(background, 0, 0);
	}

	public static void drawFade(Monster mon) {
		gc.save();
		colorAdjust.setBrightness(mon.getSpawningTime() / 60.0);
		gc.setEffect(colorAdjust);
		drawMonster(mon);
		gc.restore();
	}

	public static void drawMonster(Monster mon) {
		if (mon.getRotateType() == RotateType.ROTATE) {
			DrawUtil.drawRotate(mon);
		} else if (mon.getRotateType() == RotateType.FLIP) {
			DrawUtil.drawFlip(mon);
		} else {
			DrawUtil.drawStationary(mon);
		}
	}

	public static void drawBossSpawnArea(Boss boss) {
		gc.setFill(Color.WHITE);
		gc.setFont(font_bossname);
		gc.fillText("..." + boss.getName() + "...", Main.WIDTH / 2, 600);
		gc.setFill(Color.rgb(255, 0, 0, 0.4));
		gc.fillRoundRect(boss.getLeft(), boss.getTop(), boss.getRight() - boss.getLeft(),
				boss.getBottom() - boss.getTop(), 10, 10);
	}
	
	public static void drawPause() {
		gc.setFill(Color.rgb(0, 0, 0, 0.5));
		gc.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		gc.setFill(Color.WHITE);
		gc.setFont(font_text);
		gc.fillText("PAUSE", Main.WIDTH/2, Main.HEIGHT/2);
	}

	public static void drawStage(int stage) {
		gc.setFill(Color.WHITE);
		gc.setFont(font_bossname);
		gc.fillText("STAGE " + stage, Main.WIDTH / 2, 640);
	}

	public static void drawHealthBar(Boss boss, int count) {
		gc.drawImage(healthbar_back, (Main.WIDTH - healthbar_back.getWidth()) / 2, healthbar_back.getHeight() * count);
		gc.setFill(Color.GREEN);
		gc.fillRect(32 + (Main.WIDTH - healthbar_back.getWidth()) / 2, 22 + healthbar_back.getHeight() * count,
				460 * boss.getHealthPercent(), 22);
		gc.drawImage(healthbar_front, (Main.WIDTH - healthbar_back.getWidth()) / 2, healthbar_back.getHeight() * count);
		gc.setFill(Color.WHITE);
		gc.setFont(font_bossname);
		gc.fillText(boss.getName(), Main.WIDTH / 2, 40 + healthbar_back.getHeight() * count);

	}

	public static void drawRotate(Entity e) {
		Rotate r = new Rotate(Math.toDegrees(e.getAngle()) - 90, e.getCenterX(), e.getCenterY());
		gc.save();
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		gc.drawImage(EntityGraphics.get(e.getID()), e.getX(), e.getY());
		gc.restore();
	}

	public static void drawFlip(Entity e) {
		Image img = EntityGraphics.get(e.getID());
		if (e.getAngle() < Math.PI / 2 && e.getAngle() > -Math.PI / 2) {
			gc.save();
			gc.translate(Main.WIDTH, 0);
			gc.scale(-1, 1);
			gc.drawImage(img, Main.WIDTH - e.getX() - e.getHitbox().getWidth(), e.getY());
			gc.restore();
		} else {
			gc.drawImage(img, e.getX(), e.getY());
		}
	}

	public static void drawStationary(Entity e) {
		gc.drawImage(EntityGraphics.get(e.getID()), e.getX(), e.getY());
	}

	public static void drawDashPath(Entity e) {
		Rotate r = new Rotate(Math.toDegrees(e.getAngle()), e.getCenterX() + Math.cos(e.getAngle()),
				e.getCenterY() + Math.sin(e.getAngle()));
		gc.save();
		gc.setFill(Color.rgb(255, 0, 0, 0.4));
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		gc.fillRect(e.getCenterX() - (e.getRight() - e.getLeft()) / 2,
				e.getCenterY() - (e.getRight() - e.getLeft()) / 2, Main.WIDTH, e.getRight() - e.getLeft());
		gc.restore();
	}

	public static void drawHitbox(Entity e) {
		// Hitbox test
		gc.strokeRect(e.getLeft(), e.getTop(), e.getRight() - e.getLeft(), e.getBottom() - e.getTop());
	}

	public static void drawPlayer(Player player) {
		Image player_img = EntityGraphics.get(player.getID());
		Image weapon_img = EntityGraphics.get(player.getWeapon().getID());
		ImageView iv_img = new ImageView(weapon_img);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);

		if (player.getAngle() > Math.PI / 2 || player.getAngle() < -Math.PI / 2) {
			iv_img.setRotate(-Math.toDegrees(player.getAngle()));
			weapon_img = iv_img.snapshot(params, null);
			gc.drawImage(EntityGraphics.get(player.getID()), player.getX(), player.getY());
			gc.drawImage(weapon_img, 0, 0, weapon_img.getWidth(), weapon_img.getHeight(),
					player.getCenterX() - weapon_img.getWidth() / 2, player.getCenterY() + weapon_img.getHeight() / 2,
					weapon_img.getWidth(), -weapon_img.getHeight());
		} else {
			iv_img.setRotate(Math.toDegrees(player.getAngle()));
			weapon_img = iv_img.snapshot(params, null);
			gc.drawImage(player_img, 0, 0, player_img.getWidth(), player_img.getHeight(),
					player_img.getWidth() + player.getX(), player.getY(), -player_img.getWidth(),
					player_img.getHeight());
			gc.drawImage(weapon_img, player.getCenterX() - weapon_img.getWidth() / 2,
					player.getCenterY() - weapon_img.getHeight() / 2);
		}

		for (int i = 0; i < player.getHP(); i++) {
			gc.drawImage(hp_icon, (Main.WIDTH - player.getHP() * hp_icon.getWidth()) / 2 + i * hp_icon.getWidth(), 700);
		}
		if (player.getIsImmune()) {
			gc.drawImage(immune_ring, player.getCenterX() - immune_ring.getWidth() / 2,
					player.getCenterY() - immune_ring.getHeight() / 2 - 20);
		}
	}

	public static void drawTimer() {
		gc.setFill(Color.WHITE);
		gc.setFont(font_timer);
		gc.fillText(String.format("%02d", GameController.getElapsedTime() % 100), Main.WIDTH / 2 + 80,
				Main.HEIGHT - 80);
		gc.fillText(String.format("%02d", (GameController.getElapsedTime() % 6000) / 100), Main.WIDTH / 2,
				Main.HEIGHT - 80);
		gc.fillText(String.format("%02d", GameController.getElapsedTime() / 6000), Main.WIDTH / 2 - 80,
				Main.HEIGHT - 80);
	}
}
