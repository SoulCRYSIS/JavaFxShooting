package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.GameController;
import javafx.scene.image.ImageView;

public class BuffPane extends Pane {
	public BuffPane() {
		Random rand = new Random();
		Canvas canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
		HBox hbox = new HBox();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.rgb(0, 0, 0, 0.5));
		gc.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		hbox.setSpacing(100);
		hbox.setPrefWidth(Main.WIDTH);
		hbox.setAlignment(Pos.CENTER);
		hbox.setLayoutY(250);

		ArrayList<Pane> paneList = initPaneList();
		Pane pane;
		pane = paneList.get(rand.nextInt(paneList.size()));
		paneList.remove(pane);
		hbox.getChildren().add(pane);

		pane = paneList.get(rand.nextInt(paneList.size()));
		paneList.remove(pane);
		hbox.getChildren().add(pane);

		pane = paneList.get(rand.nextInt(paneList.size()));
		paneList.remove(pane);
		hbox.getChildren().add(pane);
		
		getChildren().add(canvas);
		getChildren().add(hbox);
	}

	private ArrayList<Pane> initPaneList() {
		ArrayList<Pane> paneList = new ArrayList<Pane>();

		paneList.add(createPane(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				GameController.getPlayer().addHP();
				Main.closeBuffPane();
			}
		}, "Health Point", new Image("hp.png")));
		paneList.add(createPane(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				GameController.getPlayer().addSpeed();
				Main.closeBuffPane();
			}
		}, "Speed", new Image("speed.png")));
		paneList.add(createPane(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				GameController.getPlayer().getWeapon().addAtkDamage();
				Main.closeBuffPane();
			}
		}, "ATK Damage", new Image("atk_dmg.png")));
		paneList.add(createPane(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				GameController.getPlayer().getWeapon().addAtkSpeed();
				Main.closeBuffPane();
			}
		}, "ATK Speed", new Image("atk_speed.png")));
		paneList.add(createPane(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				GameController.getPlayer().getWeapon().addBounce();
				Main.closeBuffPane();
			}
		}, "Bullet Bounce", new Image("bounce.png")));
		return paneList;
	}

	private Pane createPane(EventHandler<ActionEvent> event, String text, Image img) {
		Pane pane;
		Button button;
		Label label;
		Image frame = new Image("frame_buff.png");
		ImageView iv_frame;
		ImageView iv_img;
		Font font = Font.loadFont(ClassLoader.getSystemResourceAsStream("Minecraft.ttf"), 30);

		pane = new Pane();
		button = new Button();
		iv_frame = new ImageView(frame);
		iv_img = new ImageView(img);
		button.setOnAction(event);
		button.setPrefSize(frame.getWidth(), frame.getHeight());
		button.setStyle("-fx-background-color: transparent;");
		label = new Label(text);
		label.setFont(font);
		label.setTextFill(Color.WHITE);
		label.setAlignment(Pos.CENTER);
		label.setPrefWidth(frame.getWidth());
		label.setLayoutY(frame.getHeight());
		pane.getChildren().add(iv_img);
		pane.getChildren().add(iv_frame);
		pane.getChildren().add(label);
		pane.getChildren().add(button);
		return pane;
	}
}
