import java.io.Serializable;
import java.util.Random;

/**
 * @author Francesco Merati
 * @author Marco Taddei
 * @author Vittorio Toma
 * 
 */
public class Boss extends Monster implements Serializable {

	/**
	 * A fixed list of possible drops of a Boss object.
	 */

	private static final Item[] BOSS_DROP = {
			new Equipment("Reinforced Leather Armor", 0, 6),
			new Equipment("Obsidian Studding       ", 15, 0),
			new Equipment("Duralumin Warhammer     ", 18, 0),
			new Equipment("Extairen                ", 24, 0), 
			new Equipment("Kasreyon                ", 30, 0),
			new Equipment("Elven Leissanga         ", 0, 20),
			new Equipment("Glacial Cuirass         ", 0, 24),
			new Equipment("Divine Protection       ", 0, 29),
			new Equipment("Tilkal Armor            ", 0, 35) };

	/**
	 * 
	 * @param name
	 *            the name of the Boss monster
	 * @param lvl
	 *            the level of the Boss monster
	 * @param dungeon
	 *            the dungeon in which the Boss monster is created
	 */
	public Boss(String name, int lvl, Dungeon dungeon) {
		super(name, (35 + lvl * 10) * 2, 5 + lvl * 4, 5 + lvl * 3, lvl,
				lvl * 20, 'B', dungeon);
	}

	/**
	 * This method chooses randomly the drop of the Boss monster
	 */

	public void dropLoot() {
		Random rnd = new Random();
		int i = rnd.nextInt(8);
		getDungeon().getBoxes()[getPosX()][getPosY()].setItem(BOSS_DROP[i]);

	}

	/**
	 * Checks if the Adventurer is within a 5 boxes range from the Adventurer,
	 * if so the method calls moveToPlayer method, otherwise calls moveRandom
	 * method. If the Monster is near the Adventurer it attacks him. This
	 * override Monster checkRange method.
	 */
	public void checkRange(Adventurer adv) {
		if (adv.getPosX() - this.getPosX() < -5
				|| adv.getPosX() - this.getPosX() > 5
				|| adv.getPosY() - this.getPosY() < -5
				|| adv.getPosY() - this.getPosY() > 5)
			moveRandom();
		else {
			if (adv.getPosX() - this.getPosX() >= -1
					&& adv.getPosX() - this.getPosX() <= 1
					&& adv.getPosY() - this.getPosY() >= -1
					&& adv.getPosY() - this.getPosY() <= 1){
				hit(adv);
				if (adv.getLifePoints() <= 0) 
					Game.playerDied();
			} else
				moveToPlayer(adv);
		}
	}

	public String toString() {
		return getName() + " - HP: " + getLifePoints() + " - Str: "
				+ getStrength() + " - Def: " + getDefence() + " - Lvl: "
				+ getLevel();
	}

}
