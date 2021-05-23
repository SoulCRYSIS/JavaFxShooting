package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

import entity.base.Boss;
import entity.base.Bullet;
import entity.base.Monster;
import entity.monster.*;

public class EntityData {
	// Argument(x, y, ID, HP, Speed, Cooldown, SummonType)
	public static final Supplier<Bullet> BULLET_1 = () -> {return new Bullet(24, 5, 0);};
	public static final Supplier<Bullet> BULLET_2 = () -> {return new Bullet(25, 7, 0);};
	public static final Supplier<Bullet> BULLET_3 = () -> {return new Bullet(26, 10, 1);};
	public static final Supplier<Bullet> BULLET_LASER = () -> {return new Bullet(27, 15, 0);};
	public static final Supplier<Bullet> BULLET_MAGE = () -> {return new Bullet(28, 8, 1);};
	public static final Supplier<Bullet> BULLET_ALIEN = () -> {return new Bullet(29, 10, 0);};
	public static final Supplier<Bullet> BULLET_ALIEN_HUGE = () -> {return new Bullet(30, 10, 5);};
	public static final Supplier<Monster> MELEE_LV1 = () -> {return new Melee(0, 2, 1);};
	public static final Supplier<Monster> MELEE_LV2 = () -> {return new Melee(1, 3.5, 2);};
	public static final Supplier<Monster> MELEE_LV3 = () -> {return new Melee(2, 5, 1.5);};
	public static final Supplier<Monster> SPEED_LV1 = () -> {return new Speed(3, 1, 1.5, 160);};
	public static final Supplier<Monster> SPEED_LV2 = () -> {return new Speed(4, 2, 2.5, 120);};
	public static final Supplier<Monster> SPEED_LV3 = () -> {return new Speed(5, 3, 3.5, 80);};
	public static final Supplier<Monster> RANGE_LV1 = () -> {return new Range(6, 1, 1, 160, BULLET_1);};
	public static final Supplier<Monster> RANGE_LV2 = () -> {return new Range(7, 2, 1.5, 120, BULLET_2);};
	public static final Supplier<Monster> RANGE_LV3 = () -> {return new Range(8, 3, 2, 80, BULLET_3);};
	public static final Supplier<Monster> RANGE_ALIEN = () -> {return new Range(23, 2, 0.5, 60, BULLET_LASER);};
	public static final Supplier<Monster> MINION_LV1 = () -> {return new Minion(9, 1, 2);};
	public static final Supplier<Monster> MINION_LV2 = () -> {return new Minion(10, 1, 3);};
	public static final Supplier<Monster> MINION_LV3 = () -> {return new Minion(11, 2, 3);};
	public static final Supplier<Monster> MINION_BRAIN = () -> {return new Minion(18, 1, 1.5);};
	public static final Supplier<Monster> MINION_QUEENBEE = () -> {return new Minion(21, 0.5, 4);};
	public static final Supplier<Monster> SUMMONER_LV1 = () -> {return new Summoner(12, 1, 0.5, 200, MINION_LV1);};
	public static final Supplier<Monster> SUMMONER_LV2 = () -> {return new Summoner(13, 2, 0.75, 160, MINION_LV2);};
	public static final Supplier<Monster> SUMMONER_LV3 = () -> {return new Summoner(14, 3, 1, 120, MINION_LV3);};
	
	public static final Supplier<Boss> BOSS_MAGE = () -> {return new Boss_Mage(16, 20, 2, BULLET_MAGE);};
    public static final Supplier<Boss> BOSS_BRAIN = () -> {return new Boss_Brain(17, 15, 1, MINION_BRAIN);};
    public static final Supplier<Boss> BOSS_EYE = () -> {return new Boss_Eye(15, 18, 2, BULLET_LASER);};
    public static final Supplier<Boss> BOSS_QUEENBEE = () -> {return new Boss_QueenBee(19, 20, 2, MINION_QUEENBEE);};
    public static final Supplier<Boss> BOSS_ALIEN = () -> {return new Boss_Alien(22, 25, 2, BULLET_ALIEN, BULLET_ALIEN_HUGE, RANGE_ALIEN);};
	public static final ArrayList<Supplier<Boss>> BOSS = new ArrayList<Supplier<Boss>>(Arrays.asList(BOSS_MAGE, BOSS_BRAIN, BOSS_EYE, BOSS_QUEENBEE, BOSS_ALIEN));
	
	public static final ArrayList<Supplier<Monster>> LV1 = new ArrayList<Supplier<Monster>>(Arrays.asList(MELEE_LV1, SPEED_LV1, RANGE_LV1, SUMMONER_LV1));
	public static final ArrayList<Supplier<Monster>> LV2 = new ArrayList<Supplier<Monster>>(Arrays.asList(MELEE_LV2, SPEED_LV2, RANGE_LV2, SUMMONER_LV2));
	public static final ArrayList<Supplier<Monster>> LV3 = new ArrayList<Supplier<Monster>>(Arrays.asList(MELEE_LV3, SPEED_LV3, RANGE_LV3, SUMMONER_LV3));
	public static final ArrayList<ArrayList<Supplier<Monster>>> MONSTER = new ArrayList<ArrayList<Supplier<Monster>>>(Arrays.asList(LV1, LV2, LV3));
}
