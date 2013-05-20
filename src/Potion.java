import java.io.Serializable;

/**
 * 
 * @author Francesco Merati
 * @author Marco Taddei
 * @author Vittorio Toma
 * 
 */

public class Potion extends Item implements Serializable {

	/**
	 * Creates an health potion that restores a given amount of life
	 * 
	 * @param name
	 *            it is assigned by default to "Health Potion"
	 * @param modLife
	 *            the amount of life restored by the potion, assigned by default
	 *            to 50
	 * @param modStr
	 *            it is assigned by default to 0
	 * @param modDef
	 *            it is assigned by default to 0
	 */

	// CONSTRUCTORS

	public Potion() {
		super("Health Potion ", 50, 0, 0);
	}

	// METHODS

	/**
	 * @return a String representation of a Potion Object
	 */
	public String toString() {
		return super.toString() + "(" + this.getModLife() + ")";
	}

}
