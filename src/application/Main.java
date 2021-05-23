package application;

import entity.player.Player;
import entity.player.Rifle;
import entity.player.Shotgun;
import entity.player.Smg;
import entity.player.Weapon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.GameController;

public class Main extends Application {
	public static final int WIDTH = 1366;
	public static final int HEIGHT = 768;
	private static Pane currentPane;
	private static Scene scene;
	
	@Override
	public void start(Stage stage) {
		scene = new Scene(MainMenu());
		stage.setScene(scene);
		stage.setTitle("Dungeon Survival");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent e) {
	            Platform.exit();
	            System.exit(0);
            }
        });
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void openBuffPane() {
		GameController.pauseGame();
		currentPane.getChildren().add(new BuffPane());
	}

	public static void closeBuffPane() {
		currentPane.getChildren().remove(currentPane.getChildren().size() - 1);
		GameController.continueGame();
	}

	public static void changeScene(Pane pane) {
		currentPane = pane;
		scene.setRoot(pane);
	}

	public static void addEventListener() {
		Player p = GameController.getPlayer();
		scene.setOnKeyPressed((event) -> {
			KeyCode keycode = event.getCode();
			switch (keycode) {
			case A:
				p.setLeft(1);
				break;
			case D:
				p.setRight(1);
				break;
			case W:
				p.setUp(1);
				break;
			case S:
				p.setDown(1);
				break;
			default:
				break;
			}
		});
		scene.setOnKeyReleased((event) -> {
			KeyCode keycode = event.getCode();
			switch (keycode) {
			case A:
				p.setLeft(0);
				break;
			case D:
				p.setRight(0);
				break;
			case W:
				p.setUp(0);
				break;
			case S:
				p.setDown(0);
				break;
			case ESCAPE:
				GameController.switchState();
				break;
			default:
				break;
			}
		});
		scene.setOnMousePressed((event) -> {
			p.setPressing(true);
		});
		scene.setOnMouseReleased((event) -> {
			p.setPressing(false);
		});
		scene.setOnMouseMoved((event) -> {
			p.updateCursor(event.getX(), event.getY());
		});
		scene.setOnMouseDragged((event) -> {
			p.updateCursor(event.getX(), event.getY());
		});
	}
	
	public static Pane MainMenu() {
		Pane pane = new Pane();
		Font font_button = Font.loadFont(ClassLoader.getSystemResourceAsStream("Warden Regular.otf"), 30);
		Font font_title = Font.loadFont(ClassLoader.getSystemResourceAsStream("Warden Regular.otf"), 100);

		Canvas canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(new Image("Main_Background.jpg"), 0, 0);
		gc.setFill(Color.WHITE);
		gc.setFont(font_title);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText("DUNGEON SURVIVAL", Main.WIDTH / 2, 150);

		Button playBtn = new Button("Play");
		playBtn.setPrefHeight(35);
		playBtn.setPrefWidth(140);
		playBtn.setFont(font_button);
		playBtn.setStyle("-fx-background-color: #E53D1F;-fx-background-radius: 10;-fx-text-fill:#ffffff");
		playBtn.setLayoutX(Main.WIDTH / 2 - 70);
		playBtn.setLayoutY(350);

		Button exitBtn = new Button("Exit");
		exitBtn.setPrefHeight(35);
		exitBtn.setPrefWidth(140);
		exitBtn.setFont(font_button);
		exitBtn.setStyle("-fx-background-color: #E53D1F;-fx-background-radius: 10;-fx-text-fill:#ffffff");
		exitBtn.setLayoutX(Main.WIDTH / 2 - 70);
		exitBtn.setLayoutY(450);

		playBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.changeScene(SelectWeapon());
			}
		});

		exitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Platform.exit();
				System.exit(0);
			}
		});
		pane.getChildren().add(canvas);
		pane.getChildren().add(playBtn);
		pane.getChildren().add(exitBtn);
		return pane;
	}
	
	public static Pane SelectWeapon() {
		Pane pane = new Pane();
		Font font_text = Font.loadFont(ClassLoader.getSystemResourceAsStream("Warden Regular.otf"), 30);
		Font font_title = Font.loadFont(ClassLoader.getSystemResourceAsStream("Warden Regular.otf"), 60);

		Canvas canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(new Image("Main_Background.jpg"), 0, 0);
		gc.setFill(Color.WHITE);
		gc.setFont(font_title);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText("Select Weapon", Main.WIDTH / 2, 120);

		Image border = new Image("frame_weapon.png");
		gc.drawImage(border, 300, 200);
		gc.drawImage(border, 300, 350);
		gc.drawImage(border, 300, 500);

		gc.setFont(font_text);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.fillText("Rifle: slow rate of fire, but high damage", 400, 250);
		gc.fillText("Shotgun: shoot in group of 3", 400, 400);
		gc.fillText("SMG: high rate of fire, but low accuracy and damage", 400, 550);

		Button RiflePlayerBtn = new Button();
		RiflePlayerBtn.setPrefHeight(70);
		RiflePlayerBtn.setPrefWidth(70);
		RiflePlayerBtn.setLayoutX(300);
		RiflePlayerBtn.setLayoutY(200);
		RiflePlayerBtn.setStyle("-fx-background-color: transparent;");

		Button ShotgunPlayerBtn = new Button();
		ShotgunPlayerBtn.setPrefHeight(70);
		ShotgunPlayerBtn.setPrefWidth(70);
		ShotgunPlayerBtn.setLayoutX(300);
		ShotgunPlayerBtn.setLayoutY(350);
		ShotgunPlayerBtn.setStyle("-fx-background-color: transparent;");

		Button SmgPlayerBtn = new Button();
		SmgPlayerBtn.setPrefHeight(70);
		SmgPlayerBtn.setPrefWidth(70);
		SmgPlayerBtn.setLayoutX(300);
		SmgPlayerBtn.setLayoutY(500);
		SmgPlayerBtn.setStyle("-fx-background-color: transparent;");

		Button backBtn = new Button("Back");
		backBtn.setPrefHeight(35);
		backBtn.setPrefWidth(140);
		backBtn.setFont(font_text);
		backBtn.setStyle("-fx-background-color: #E53D1F;-fx-background-radius: 10;-fx-text-fill:#ffffff");

		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.changeScene(MainMenu());
			}
		});
		RiflePlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.changeScene(Game(new Rifle()));
			}
		});
		ShotgunPlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.changeScene(Game(new Shotgun()));
			}
		});
		SmgPlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.changeScene(Game(new Smg()));
			}
		});

		ImageView img_rifle = new ImageView("weapon_rifle.png");
		img_rifle.setX(305);
		img_rifle.setY(222);
		img_rifle.setRotate(-45);

		ImageView img_shotgun = new ImageView("weapon_shotgun.png");
		img_shotgun.setX(306);
		img_shotgun.setY(375);
		img_shotgun.setRotate(-45);

		ImageView img_smg = new ImageView("weapon_smg.png");
		img_smg.setX(308);
		img_smg.setY(518);
		img_smg.setRotate(-45);

		pane.getChildren().add(canvas);
		pane.getChildren().add(img_rifle);
		pane.getChildren().add(img_shotgun);
		pane.getChildren().add(img_smg);
		pane.getChildren().add(RiflePlayerBtn);
		pane.getChildren().add(ShotgunPlayerBtn);
		pane.getChildren().add(SmgPlayerBtn);
		pane.getChildren().add(backBtn);
		return pane;
	}
	
	public static Pane Game(Weapon weapon) {
		Pane pane = new Pane();
		Canvas canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
		Canvas background = new Canvas(Main.WIDTH, Main.HEIGHT);
		try {
			GameController.initGame(canvas.getGraphicsContext2D());
		} catch (Exception e) {
			System.out.println(e);
			Platform.exit();
			System.exit(0);
		}
		
		GameController.setPlayer(new Player(35, weapon));
		DrawUtil.drawBackground(background.getGraphicsContext2D());
		Main.addEventListener();
		pane.getChildren().add(background);
		pane.getChildren().add(canvas);
		return pane;
	}
	
	public static Pane Gameover() {
		Pane pane = new Pane();
		Font font_text = Font.loadFont(ClassLoader.getSystemResourceAsStream("Warden Regular.otf"), 30);
		Font font_title = Font.loadFont(ClassLoader.getSystemResourceAsStream("Warden Regular.otf"), 100);
		Canvas canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(new Image("Main_Background.jpg"), 0, 0);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(font_title);
		gc.fillText("Game Over", Main.WIDTH / 2, 100);
		gc.setFont(font_text);
		gc.fillText("Your Score", Main.WIDTH / 2, 200);
		gc.fillText("Stage " + GameController.getStage(), Main.WIDTH / 2, 250);
		gc.fillText("Time", Main.WIDTH / 2, 350);
		gc.fillText(GameController.getElapsedTime() / 6000 + " min", Main.WIDTH / 2, 400);
		gc.fillText((GameController.getElapsedTime() % 6000) / 100 + " sec", Main.WIDTH / 2, 450);
		gc.fillText(GameController.getElapsedTime() % 100 + "0 ms", Main.WIDTH / 2, 500);
		
		Button restartBtn = new Button("Restart");
		restartBtn.setPrefHeight(35);
		restartBtn.setPrefWidth(200);
		restartBtn.setFont(font_text);
		restartBtn.setStyle("-fx-background-color: #E53D1F;-fx-background-radius: 10;-fx-text-fill:#ffffff");
		restartBtn.setLayoutX(Main.WIDTH / 2 - 100);
		restartBtn.setLayoutY(600);
		restartBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.changeScene(SelectWeapon());
			}
		});
		
		pane.getChildren().add(canvas);
		pane.getChildren().add(restartBtn);
		return pane;
	}
	
	
}
