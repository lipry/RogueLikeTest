/**
 * 
 * @author Francesco Merati
 * @author Marco Taddei
 * @author Vittorio Toma
 *
 */
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;


@SuppressWarnings("serial")
public class Adventurer extends Actor implements Serializable {
	
	
	
	private String name;
	private Vector<Item>healthPotion = new Vector<Item>();
	private Vector<Item>inventory = new Vector<Item>();
	public static int expToNextLevel=10;
	public static int maxLifePoints=100;
	private Equipment Armor = null, Weapon = null;
	private boolean key = false;

	
	
	/**
	 * 
	 * @param name the name of the Adventurer
	 * @param dungeon the Dungeon in which the Adventurer is created
	 * 
	 * Constructor that creates an Adventurer with a name, a dungeon
	 * and assigned statistics by default.
	 */
	public Adventurer(String name, Dungeon dungeon){
		
		super(100,10,8,1,0,'@', dungeon);
		this.name=name;
		dungeon.getBoxes()[getPosX()][getPosY()].setActor(null);
		this.setPosX(dungeon.getSpawnX());
		this.setPosY(dungeon.getSpawnY());
		dungeon.getBoxes()[getPosX()][getPosY()].setActor(this);
		
	}


	/**
	 * @return the name of the Adventurer
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	/**
	 * Set the key variable to true
	 */
	public void grabKey(){
		key = true;
	}
	
	/**
	 * 
	 * @return if the Adventurer has the key
	 */
	public boolean hasKey(){
		return key;
	}

	
	/**
	 * Checks if the adventurer has enough exp to level up 
	 */
	public static void checkExp(Adventurer adv){
		while(adv.getExp()>=expToNextLevel){
			maxLifePoints+=15;
			adv.levelUp();
			expToNextLevel*=1.5;
		}	
	}
	
	/**
	 * Levels up the adventurer adding random values to stats
	 */
	public void levelUp(){
			Random rnd = new Random();
			this.setLifePoints(maxLifePoints);
			this.setStrength(this.getStrength()+rnd.nextInt(5)+1);
			this.setDefence(this.getDefence()+rnd.nextInt(5)+1);
			this.setLevel(this.getLevel()+1);
			this.setExp(this.getExp()-expToNextLevel);
	}
	
	/**
	 * @param the Monster that gives experience to the Adventurer
	 * Gives the experience to Adventurer when fight is finished
	 */
	
	public void giveExp(Monster monster){
		if(monster.getLifePoints()<= 0){
			setExp(getExp()+monster.getExp());
		}
	}
	
	/**
	 * 
	 * @param item the item being picked up
	 * @return true if the item can be picked up, false otherwise
	 */
	public boolean pickUp(Item item){
		if(item instanceof Potion && healthPotion.size()<5){
			return healthPotion.add(item);
		}
		if((item instanceof Equipment || item instanceof Miscellaneous) && inventory.size()<8){
			return inventory.add(item);
		}
		return false;
	}
	
	/**
	 * 
	 * @param i is the position in the inventory of the item the user wants to throw away
	 * @return true if the item was removed successfully,false if not.
	 */
	
	public boolean throwAway(int i){
		if(i<1 || i>1 +inventory.size() || (i==1 && healthPotion.size()==0))
			return false;
		else{
			if(i==1)
				healthPotion.remove(0);
			else
				inventory.remove(i-2);
			return true;
		}
	}
	
	/**
	 * 	
	 * @return the string to print the invetory
	 */
	public String printInventory() {
		int cont = 2;
		String inv;
		inv = "EQUIPPED ITEMS\n\n";
		if (Armor != null) {
			inv = inv + "Armor: " + Armor.toString() + "\n";
		} else {
			inv = inv + "Armor: none\n";
		}
		if (Weapon != null) {
			inv = inv + "Weapon: " + Weapon.toString() + "\n\n";
		} else {
			inv = inv + "Weapon: none\n\n";
		}
		inv = inv + "INVENTORY ITEMS\n\n";
		inv = inv + healthPotion.size() + "x Health Potion(50)\t\t[1]\n";
		for (Item i : inventory) {
			inv = inv + "1x " + i.toString() + "\t[" + cont + "]\n";
			cont++;
		}
		return inv;
	}


    /**
     * 
     * @return the string to print the menu to destroy items
     */
    public String printDestroyMenu(){
        int cont = 2;
        String inv;
        inv = "EQUIPPED ITEMS\n\n";
        if(Armor != null) {
            inv = inv + "Armor: " + Armor.toString() + "\n";
        }
        else {
            inv = inv + "Armor: none\n";
        }
        if(Weapon != null) {
            inv = inv + "Weapon: " + Weapon.toString() + "\n\n";
        }
        else {
            inv = inv + "Weapon: none\n\n";
        }
        inv = inv + "SELECT ITEM TO DESTROY\n\n";
        inv = inv + healthPotion.size()+"x Health Potion(50)\t\t[1]\n";
        for(Item i:inventory){
            inv = inv + "1x "+i.toString()+"\t["+cont+"]\n";
            cont++;
        }
        return inv;
    }

    public String getEquipment() {
        String equipped;
        if (Armor != null) {
            equipped = "Armor: " + Armor.toString() + "   ";
        }
        else {
            equipped = "Armor: none   ";
        }
        if (Weapon != null) {
            equipped = equipped + "Weapon: " + Weapon.toString() + "   ";
        }
        else {
            equipped = equipped + "Weapon: none   ";
        }
        return equipped;
    }
	
	/**
	 * 
	 * @param i is the position in the inventory of the potion that the user wants to use.
	 * @return true if the potion is used, false if else.
	 */
	
	public boolean usePotion(int i){
		if(i<1 || i>1+inventory.size() || (i==1 && healthPotion.size()==0)){
			return false;
		}
		else{			
			if(i==1 && this.getLifePoints()+50 <= maxLifePoints){
				this.setLifePoints(getLifePoints()+50);
				healthPotion.remove(healthPotion.size()-1);
				return true;
			}
			else if(i==1 && this.getLifePoints()+50 >= maxLifePoints){
				this.setLifePoints(maxLifePoints);
				healthPotion.remove(healthPotion.size()-1);
				return true;
			}
			if(inventory.get(i-2) instanceof Miscellaneous && (inventory.get(i-2).getModDef() > 0)|| inventory.get(i-2) instanceof Miscellaneous && (inventory.get(i-2).getModStr() > 0)){
					this.setDefence(getDefence()+inventory.get(i-2).getModDef());
					this.setStrength(getStrength()+inventory.get(i-2).getModStr());
					inventory.remove(i-2);
					return true;
			}
			return false;
		}
	}
	/**
	 * 
	 * @param i is the position(in the inventory) of the objects that has to be equipped
	 * @return true if the item has been equipped, false if the operation was not successful.
	 */
	
	public boolean equip(int i){ 
		Equipment change;
		if(i<1 || i>1+inventory.size()|| !(inventory.get(i-2) instanceof Equipment)){
			return false;
		}
		else{
			if(inventory.get(i-2).getModDef()>0){   
				if(Armor == null){
					Armor = (Equipment)inventory.get(i-2);
					this.setDefence(this.getDefence()+inventory.get(i-2).getModDef());
					inventory.remove(i-2);
				}
				else{
					this.setDefence(this.getDefence()-Armor.getModDef());
					change = Armor;
					Armor = (Equipment)inventory.get(i-2);
					this.setDefence(this.getDefence()+inventory.get(i-2).getModDef());
					inventory.remove(i-2);
					inventory.add(change);
				}
				
			}
			else{
				if(Weapon == null){
					Weapon = (Equipment)inventory.get(i-2);
					this.setStrength(this.getStrength()+inventory.get(i-2).getModStr());
					inventory.remove(i-2);
				}
				else{
					this.setStrength(this.getStrength()-Weapon.getModStr());
					change = Weapon;
					Weapon = (Equipment)inventory.get(i-2);
					this.setStrength(this.getStrength()+inventory.get(i-2).getModStr());
					inventory.remove(i-2);
					inventory.add(change);
				}
			}
			return true;
				
		}
	}
	
	/**
	 * 
	 * @param un must be "Weapon" to unequip the weapon , "Armor" to unequip the armor. 
	 * @return true if the operation was successful, false if not.
	 */
	
	public boolean unequip(String un){
		if(inventory.size() == 8 || (Weapon == null && Armor == null)){
			return false;
		}
		else{
			if(un.equals("Armor") && Armor!= null){
				inventory.add(Armor);
				this.setDefence(this.getDefence()-Armor.getModDef());
				Armor= null;
				return true;
			}
			else if(un.equals("Weapon")&& Weapon != null){
				inventory.add(Weapon);
				this.setStrength(this.getStrength()-Weapon.getModStr());
				Weapon= null;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Moves(if possible) the Adventurer object in the box to the right
	 */
	public boolean moveRight(){
		Box[][] boxes = getDungeon().getBoxes();
		if(boxes[getPosX()+1][getPosY()] !=null){
			if(boxes[getPosX()+1][getPosY()].isWalkable()){
				boxes[getPosX()][getPosY()].setActor(null);
				setPosX(getPosX()+1);
				boxes[getPosX()][getPosY()].setActor(this);
				
				if (boxes[getPosX()][getPosY()].getRealRep() == '+'){
					Sound.door.play();
				}
				
				if(boxes[getPosX()][getPosY()].getItem()!=null)
					if(pickUp(boxes[getPosX()][getPosY()].getItem()))
						boxes[getPosX()][getPosY()].setItem(null);
				return true;
			}
			else{
				if(boxes[getPosX()+1][getPosY()].getActor()!=null){
					hit(boxes[getPosX()+1][getPosY()].getActor());
					return true;
				}
				return false;
			}
		}
		return false;
	}

	/**
	 * Moves(if possible) the Adventurer object in the box to the left
	 */
	
	public boolean moveLeft(){
		Box[][] boxes = getDungeon().getBoxes();
		if(boxes[getPosX()-1][getPosY()] !=null){
			if(boxes[getPosX()-1][getPosY()].isWalkable()){
				boxes[getPosX()][getPosY()].setActor(null);
				setPosX(getPosX()-1);
				boxes[getPosX()][getPosY()].setActor(this);
				
				if (boxes[getPosX()][getPosY()].getRealRep() == '+'){
					Sound.door.play();
				}
				
				if(boxes[getPosX()][getPosY()].getItem()!=null)
					if(pickUp(boxes[getPosX()][getPosY()].getItem()))
						boxes[getPosX()][getPosY()].setItem(null);
				return true;
			}
			else{
				if(boxes[getPosX()-1][getPosY()].getActor()!=null){
					hit(boxes[getPosX()-1][getPosY()].getActor());
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Moves(if possible) the Adventurer object in the box below. (if the box has a monster in it, the player will attack it).
	 */
	
	public boolean moveDown(){
		Box[][] boxes = getDungeon().getBoxes();
		if(boxes[getPosX()][getPosY()+1] !=null){ 
			if(boxes[getPosX()][getPosY()+1].isWalkable()){
				boxes[getPosX()][getPosY()].setActor(null);
				setPosY(getPosY()+1);
				boxes[getPosX()][getPosY()].setActor(this);
				
				if (boxes[getPosX()][getPosY()].getRealRep() == '+'){
					Sound.door.play();
				}
				
				if(boxes[getPosX()][getPosY()].getItem()!=null)
					if(pickUp(boxes[getPosX()][getPosY()].getItem()))
						boxes[getPosX()][getPosY()].setItem(null);
				return true;
			}
			else{
				if(boxes[getPosX()][getPosY()+1].getActor()!=null){
					hit(boxes[getPosX()][getPosY()+1].getActor());
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Moves(if possible) the Adventurer object in the upper box
	 */
	
	public boolean moveUp(){
		Box[][] boxes = getDungeon().getBoxes();
		if(boxes[getPosX()][getPosY()-1] != null){
			if(boxes[getPosX()][getPosY()-1].isWalkable()){
				boxes[getPosX()][getPosY()].setActor(null);
				setPosY(getPosY()-1);
				boxes[getPosX()][getPosY()].setActor(this);
				
				if (boxes[getPosX()][getPosY()].getRealRep() == '+'){
					Sound.door.play();
				}
				
				if(boxes[getPosX()][getPosY()].getItem()!=null)
					if(pickUp(boxes[getPosX()][getPosY()].getItem()))
						boxes[getPosX()][getPosY()].setItem(null);
				return true;
			}
			else{
				if(boxes[getPosX()][getPosY()-1].getActor()!=null){
					hit(boxes[getPosX()][getPosY()-1].getActor());
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	
	
	public boolean hit(Actor a){
		int i = new Random().nextInt(4);
		
		switch (i){
		case 0:
			Sound.hit1.play();
			//System.out.println("PLay 1");
			break;
		case 1:
			Sound.hit2.play();
			//System.out.println("PLay 2");
			break;
		case 2:
			Sound.hit3.play();
			//System.out.println("PLay 3");
			break;
		case 3:
			Sound.hit4.play();
			//System.out.println("PLay 4");
			break;
		}
		return super.hit(a);		
	}
	
	public boolean isDead(){
		if (this.getLifePoints() <= 0){
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * @return the Adventurer as a String
	 */
	
	public String toString(){
		return "Name: "+ name+ " - HP: "+getLifePoints()+"/"+maxLifePoints+" - Str: "+getStrength()+" - Def: "+getDefence()+" - Exp: "+getExp()+"/"+expToNextLevel+" - Lvl: "+getLevel();
	}
	
	//Method added to testing code
	public boolean getKey(){
		return this.key;
	}
	
	public Vector<Item> getHealthPotion(){
		return this.healthPotion;
	}
	
	public Vector<Item> getInventory(){
		return this.inventory; 
	}
	
	public Equipment getArmor(){
		return Armor; 
	}
	
	public Equipment getWeapon(){
		return this.Weapon; 
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Adventurer)) return false;
		Adventurer a = (Adventurer) o;
		return this.name.equals(a.getName())||this.expToNextLevel == a.expToNextLevel||this.maxLifePoints == a.maxLifePoints ||this.key == a.getKey() || this.healthPotion.equals(a.getHealthPotion())||this.inventory.equals(a.getInventory())||this.Armor.equals(a.getArmor())|| this.Weapon.equals(a.getWeapon());
	}
}
