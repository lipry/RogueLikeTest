import java.io.Serializable;

/**
 * 
 */

/**
 * @author Francesco Merati
 * @author Marco Taddei
 * @author Vittorio Toma
 * 
 */
public class Miscellaneous extends Item implements Serializable {

	
		//CONSTRUCTORS
	/**
	 * Creates miscellaneous item (quest item/keys/strength-defence potions)
	 * 
	 * @param name
	 *            the name of the miscellaneous item
	 * @param modLife
	 *            assigned by default to 0
	 * @param modStr
	 *            if the item is a strength potion it represents the amount of
	 *            strength increased by the potion
	 * @param modDef
	 *            if the item is a defence potion it represents the amount of
	 *            defence increased by the potion
	 */
	public Miscellaneous(String name, int modStr, int modDef) {
		super(name, 0, modStr, modDef);
	}

		//METHODS
	
	/**
	 * @return a String representation of a Miscellaneous Object.
	 */
	public String toString() {
		if (this.getModStr() == 0 && this.getModDef() == 0) //If the miscellaneous item is not a potion.
			return super.toString();
		else													// then the item is definitely a strength or defence potion.
			return this.getModStr() > 0 ? this.getName() + " (Str+ "
					+ this.getModStr() + ")" : this.getName() + " (Def+ "
					+ this.getModDef() + ")";   
	}

}
