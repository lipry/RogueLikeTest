import java.io.Serializable;
import java.util.Random;

/**
 * @author Callum D E Smith // Darren E Pollard
 * @version 0.1
 */
@SuppressWarnings("serial")
public class Monster extends Actor implements Serializable {

	private String name;
	private static final Item[] DROP = { 
			new Equipment("Rusty Sword             ", 2, 0),
			new Equipment("Fine Sword              ", 4, 0),
			new Equipment("Rusty Armor             ", 0, 2),
			new Equipment("Fine Armor              ", 0, 4),
			new Equipment("Orichalcum Sword        ", 5, 0),
			new Equipment("Sharp Blade             ", 7, 0),
			new Equipment("Adamantium Claymore     ", 10, 0),
			new Equipment("Destroyer               ", 13, 0),
			new Equipment("Reinforced Leather Armor", 0, 6),
			new Equipment("Full Plate Armor        ", 0, 7),
			new Equipment("Mithril Hauberk         ", 0, 11),
			new Equipment("Dragon's Plate Armor    ", 0, 15) };

	/**
	 * 
	 * @param name
	 *            the name of the monster
	 * @param lvl
	 *            the level of the monster
	 * @param dungeon
	 *            the dungeon in which the monster is created
	 * 
	 *            Constructor for objects of class Monster. It creates a Monster
	 *            with statistics assigned by the level of the Monster
	 */
	public Monster(String name, int lvl, Dungeon dungeon) {
		super(35 + lvl * 15, 3 + lvl * 2, 2 + lvl * 2, lvl, lvl * 7, '&',
				dungeon);

		if (name != null) {
			this.name = name;
		} else {
			this.name = "Unknow monster";
		}

	}

	/**
	 * 
	 * @param name
	 *            the name of the monster
	 * @param hp
	 *            the life points of the monster
	 * @param str
	 *            the strength of the monster
	 * @param def
	 *            the defence of the monster
	 * @param lvl
	 *            the level of the monster
	 * @param exp
	 *            the experience the monster gives when it dies
	 * @param rep
	 *            the representation of the monster on the screen
	 * @param dungeon
	 *            the dungeon in which the monster is created
	 */
	public Monster(String name, int hp, int str, int def, int lvl, int exp,
			char rep, Dungeon dungeon) {
		super(hp, str, def, lvl, exp, rep, dungeon);
		if (name != null) {
			this.name = name;
		} else {
			this.name = "Unknow monster";
		}

	}

	/**
	 * @return the name of the monster
	 */
	public String getName() {
		return name;
	}

	/**
	 * Chooses semi-randomly, with different probabilities, the item dropped by
	 * the monster.
	 */

	public void dropLoot() {
		Random rnd = new Random();
		int i = rnd.nextInt(100);
		if (i >= 26 && i < 56)
			getDungeon().getBoxes()[getPosX()][getPosY()].setItem(new Potion());
		if (i >= 56 && i < 71) {
			i = rnd.nextInt(2);
			getDungeon().getBoxes()[getPosX()][getPosY()].setItem(DROP[i]);
		}
		if (i >= 71 && i < 86) {
			i = rnd.nextInt(2) + 2;
			getDungeon().getBoxes()[getPosX()][getPosY()].setItem(DROP[i]);
		}
		if (i >= 86 && i < 88) {
			i = rnd.nextInt(4) + 4;
			getDungeon().getBoxes()[getPosX()][getPosY()].setItem(DROP[i]);
		}
		if (i >= 88 && i < 90) {
			i = rnd.nextInt(4) + 8;
			getDungeon().getBoxes()[getPosX()][getPosY()].setItem(DROP[i]);
		}
		if (i >= 90 && i < 95)
			getDungeon().getBoxes()[getPosX()][getPosY()]
					.setItem(new Miscellaneous("Strength Potion         ", 3, 0));
		if (i >= 95 && i < 100)
			getDungeon().getBoxes()[getPosX()][getPosY()]
					.setItem(new Miscellaneous("Defence Potion           ", 0, 3));
	}

	/**
	 * 
	 * @param item
	 *            the item dropped by the monster
	 * 
	 */
	public void dropLoot(Item item) {
		if (item == null) {
			item = new Potion();
		}
		getDungeon().getBoxes()[getPosX()][getPosY()].setItem(item);
	}

	/**
	 * Checks if the Adventurer is within a 3 boxes range from the Adventurer,
	 * if so the method calls moveToPlayer method, otherwise calls moveRandom
	 * method. If the Monster is near the Adventurer it attacks him.
	 */

	public void checkRange(Adventurer adv) {
		if (adv != null) {
			if (adv.getPosX() - this.getPosX() < -3
					|| adv.getPosX() - this.getPosX() > 3
					|| adv.getPosY() - this.getPosY() < -3
					|| adv.getPosY() - this.getPosY() > 3)
				moveRandom();
			else {
				if (adv.getPosX() - this.getPosX() >= -1
						&& adv.getPosX() - this.getPosX() <= 1
						&& adv.getPosY() - this.getPosY() >= -1
						&& adv.getPosY() - this.getPosY() <= 1) {
					hit(adv);
					if (adv.getLifePoints() <= 0)
						Game.playerDied();
				} else
					moveToPlayer(adv);
			}
		}
	}

	/**
	 * Set the monster to disappear if it's life point is lesser than 1
	 */
	public void isDead() {
		if (this instanceof Boss) {
			Sound.setCurrentMusic(Sound.back);
		}
		getDungeon().getBoxes()[getPosX()][getPosY()].setActor(null);

	}

	/**
	 * Moves toward the player, if it is possible, the Monster object.
	 */

	public boolean moveToPlayer(Adventurer adv) {
		if (adv != null) {
			if (adv.getPosX() > getPosX()) {
				if (getDungeon().getBoxes()[getPosX() + 1][getPosY()] != null
						&& getDungeon().getBoxes()[getPosX() + 1][getPosY()]
								.isWalkable()) {
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(null);
					setPosX(getPosX() + 1);
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(this);
					return true;
				}
			}
			if (adv.getPosX() < getPosX()) {
				if (getDungeon().getBoxes()[getPosX() - 1][getPosY()] != null
						&& getDungeon().getBoxes()[getPosX() - 1][getPosY()]
								.isWalkable()) {
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(null);
					setPosX(getPosX() - 1);
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(this);
					return true;
				}
			}
			if (adv.getPosY() > getPosY()) {
				if (getDungeon().getBoxes()[getPosX()][getPosY() + 1] != null
						&& getDungeon().getBoxes()[getPosX()][getPosY() + 1]
								.isWalkable()) {
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(null);
					setPosY(getPosY() + 1);
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(this);
					return true;
				}
			}
			if (adv.getPosY() < getPosY()) {
				if (getDungeon().getBoxes()[getPosX()][getPosY() - 1] != null
						&& getDungeon().getBoxes()[getPosX()][getPosY() - 1]
								.isWalkable()) {
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(null);
					setPosY(getPosY() - 1);
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(this);
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * Moves randomly, if it is possible, the Monster object.
	 */

	public boolean moveRandom() {
		Random rnd = new Random();
		boolean loop = true;
		int i;
		while (loop) {
			i = rnd.nextInt(4);
			switch (i) {
			case 0:
				if ((getDungeon().getBoxes()[getPosX() + 1][getPosY()] != null)
						&& getDungeon().getBoxes()[getPosX() + 1][getPosY()]
								.isWalkable()) {
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(null);
					setPosX(getPosX() + 1);
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(this);
					loop = false;
				}
				break;
			case 1:
				if ((getDungeon().getBoxes()[getPosX() - 1][getPosY()] != null)
						&& getDungeon().getBoxes()[getPosX() - 1][getPosY()]
								.isWalkable()) {
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(null);
					setPosX(getPosX() - 1);
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(this);
					loop = false;
				}
				break;
			case 2:
				if ((getDungeon().getBoxes()[getPosX()][getPosY() + 1] != null)
						&& getDungeon().getBoxes()[getPosX()][getPosY() + 1]
								.isWalkable()) {
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(null);
					setPosY(getPosY() + 1);
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(this);
					loop = false;
				}
				break;
			case 3:
				if ((getDungeon().getBoxes()[getPosX()][getPosY() - 1] != null)
						&& getDungeon().getBoxes()[getPosX()][getPosY() - 1]
								.isWalkable()) {
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(null);
					setPosY(getPosY() - 1);
					getDungeon().getBoxes()[getPosX()][getPosY()]
							.setActor(this);
					loop = false;
				}
			default:
				loop = false;
				break;
			}
		}
		return !loop;
	}

	public String toString() {
		return name + " - HP: " + getLifePoints() + " - Str: " + getStrength()
				+ " - Def: " + getDefence() + " - Lvl: " + getLevel();
	}
	
	//Method added to testing code
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Monster)) return false;
		
		Monster m = (Monster)o;
		return this.name.equals(m.getName());
	}

}
