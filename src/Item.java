import java.io.Serializable;

/**
 * 
 * @author Francesco Merati
 * @author Marco Taddei
 * @author Vittorio Toma
 * 
 */

public abstract class Item implements Serializable {

		//FIELDS
	
	private String name;
	char representation = '!';
	private int modLife, modStr, modDef;

	
		//CONSTRUCTORS
	
	/**
	 * 
	 * @param name
	 *            the name of the item
	 * @param modLife
	 *            the amount of life restored by the item
	 * @param modStr
	 *            the amount of strength increased by the item
	 * @param modDef
	 *            the amount of defence increased by the item
	 */

		
	public Item(String name, int modLife, int modStr, int modDef) {
		if (name == null){name = "Unknow item";}
		this.name = name;
		this.modLife = modLife;
		this.modStr = modStr;
		this.modDef = modDef;
	}

		//METHODS
	
	/**
	 * @return the defence's modifier
	 */
	public int getModDef() {
		return modDef;
	}

	/**
	 * @return the strength's modifier
	 */
	public int getModStr() {
		return modStr;
	}

	/**
	 * @return the life's modifier
	 */
	public int getModLife() {
		return modLife;
	}

	/**
	 * @return the representation of the item
	 */
	public char getRepresentation() {
		return representation;
	}

	/**
	 * @return the name of the item
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the String representation of Item Object.
	 */
	public String toString() {
		return name;
	}

}
