import java.io.Serializable;

/**
 * @author Marco Taddei
 * @author Francesco Merati
 * @author Vittorio Toma
 * 
 */
public class Equipment extends Item implements Serializable {

	
			//CONSTRUCTORS
	/**
	 * Creates a piece of equipment (weapon/armor) which boosts the adventurer's
	 * strength or defence
	 * 
	 * @param name
	 *            the name of the equipment
	 * @param modLife
	 *            assigned by default to 0
	 * @param modStr
	 *            the amount of strength increased by the weapon
	 * @param modDef
	 *            the amount of defence increased by the armor
	 */
	public Equipment(String name, int modStr, int modDef) {
		super(name, 0, modStr, modDef);
		this.representation = '*';

	}

			//METHODS
	
	/**
	 * @return a String representation of a Equipment Object.
	 */
	
	public String toString() {
		return this.getModStr() > 0 ? this.getName() + " (Str+ "
				+ this.getModStr() + ")" : this.getName() + " (Def+ "
				+ this.getModDef() + ")";

	}
	
	 //Method added to testing code
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Equipment)) return false;
		Equipment e = (Equipment) o;
		return this.toString().equals(e.toString());
	}

}
