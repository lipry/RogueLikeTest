/**
 * This abstract class is intended to represent actors (Monsters, adventurer,...)
 * 
 * @author Francesco Merati
 * @author Marco Taddei
 * @author Vittorio Toma
 * @author Callum Smith
 * @author Darren Pollard
 *
 */
import java.io.Serializable;
import java.util.Random;

@SuppressWarnings("serial")
public abstract class Actor implements Serializable {

			//FIELDS

	
	
	private int lifePoints, strength, defence, level, exp;
	private char representation;
	private int posX, posY;
	private Dungeon dungeon;
	private boolean isAttacking;
	private Actor target;

			//CONSTRUCTORS
	/**
	 * Creates an Actor
	 */
	
	public Actor(int lp, int str, int def, int lvl, int xp, char rep,Dungeon dungeon) {
		if (lp < 1){lp = 100;}
		lifePoints = lp;
		
		if (str < 1){str = 10;}
		strength = str;
		
		if (lvl < 1){lvl = 1;}
		level = lvl;
		
		if (def <1){def = 10;}
		defence = def;
		
		isAttacking = false;
		target = null;
		
		if (xp <0){xp = 0;}
		exp = xp;
		
		representation = rep; // How the actor is shown in the dungeon: example "@"
		
		Random rnd = new Random();
		
		if (dungeon == null){
			dungeon = new Dungeon(rnd.nextLong());
		}
		this.dungeon = dungeon;
		
		do {
			posX = rnd.nextInt(Dungeon.SIZE_WIDTH);
			posY = rnd.nextInt(Dungeon.SIZE_HEIGHT);
		} while (dungeon.getBoxes()[posX][posY] == null
				|| !dungeon.getBoxes()[posX][posY].isWalkable());
		dungeon.getBoxes()[posX][posY].setActor(this);						//Sets the position of the actor in the dungeon randomly
	}
	
			//METHODS

	/**
	 * @return the life points of the Actor
	 */
	public int getLifePoints() {
		return lifePoints;
	}

	/**
	 * @param lifePoints
	 *            to set the Actor's life points
	 **/
	public void setLifePoints(int lifePoints) {
		if (lifePoints >= 0){
			this.lifePoints = lifePoints;
		}
	}

	/**
	 * @return the strength of the Actor
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * @param strength
	 *            to set the Actor's strength
	 */
	public void setStrength(int strength) {
		if (strength >0 ){
			this.strength = strength;
		}
	}

	/**
	 * @return the level of the Actor
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            to set the Actor's level
	 */
	public void setLevel(int level) {
		if (level >0){
			this.level = level;
		}
	}

	/**
	 * @return the defence of the Actor
	 */
	public int getDefence() {
		return defence;
	}

	/**
	 * @param defence
	 *            to set the Actor's defence
	 */
	public void setDefence(int defence) {
		if (defence >0){
			this.defence = defence;
		}
	}

	/**
	 * @return the experience of the Actor
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            to set the Actor's experience
	 */
	public void setExp(int exp) {
		if (exp > 0){
			this.exp = exp;
		}
	}

	/**
	 * @return the representation of the Actor
	 */
	public char getRepresentation() {
		return representation;
	}

	/**
	 * @param representation
	 *            to set the symbol that represents the Actor
	 */
	public void setRepresentation(char representation) {
		this.representation = representation;
	}

	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @param posX
	 *            the posX to set
	 */
	public void setPosX(int posX) {
		if (posX >= 0){
			this.posX = posX;
		}
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * @param posY
	 *            the posY to set
	 */
	public void setPosY(int posY) {
		if (posY >= 0){
			this.posY = posY;
		}
	}

	/**
	 * @return the dungeon
	 */
	public Dungeon getDungeon() {
		return dungeon;
	}

	/**
	 * @param dungeon
	 *            the dungeon to set
	 */
	public void setDungeon(Dungeon dungeon) {
		if (dungeon != null){
			this.dungeon = dungeon;
		}
	}

	/**
	 * @param actor
	 *            the actor being hit
	 * @return true if the actor is hit, false if not
	 */

	public boolean hit(Actor actor) {
		if (actor != null){
			// Sound
			if (actor instanceof Boss) {
				Sound.setCurrentMusic(Sound.boss);
			}
			Random rnd = new Random();							
			int attacker = rnd.nextInt(20) + 1 + strength;			//By comparing this two randomly generated we now if  
			int defender = rnd.nextInt(20) + 1 + actor.defence;		//the attacker is going to miss or not
			if (attacker > defender) {
				if (strength > actor.defence)
					actor.lifePoints -= strength - actor.defence;		
				else {
					actor.lifePoints -= 1;				//If the attacker hits , the hitten one loses at least 
					if (actor.lifePoints < 0)			//one life point.
						actor.lifePoints = 0; // modified
				}
				this.isAttacking = true;
				this.target = actor;
				return true;
			} else {
				this.isAttacking = false;
				this.target = null;
				return false;
			}
		}
		else return false;

	}
	
	/**
	 * @return a String representation of a Actor Object
	 */

	public String toString() {
		return "HP: " + lifePoints + " - Str: " + strength + " - Def: "
				+ defence + " - Lvl: " + level;
	}

	/**
	 * Returns if the actor actually attacked an other actor
	 * 
	 * @return Boolean, TRUE if the current actor is attacking another one
	 */
	public boolean getFightStatus() {
		return this.isAttacking;
	}

	/**
	 * Returns the current target of the actor
	 * 
	 * @return Actor, current target of the actor
	 */
	public Actor getTarget() {
		return this.target;
	}

}
