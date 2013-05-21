import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class ItemTest {
//this.a = new Adventurer(playername, this.arrayDungeon[posDungeon]);
//	String name, int modLife, int modStr, int modDef
	
	Potion potion = new Potion();
	
	@Test
	public void testItem() {
				System.out.println("Potion name is "+ potion);
		
	}

	@Test
	public void testGetModDef() {
		assertEquals(0,potion.getModDef());
	}

	@Test
	public void testGetModStr() {
		assertEquals(0,potion.getModStr());
	}

	@Test
	public void testGetModLife() {
		assertEquals(50,potion.getModLife());
	}

	@Test
	public void testGetRepresentation() {
		System.out.println("Representation" + potion.getRepresentation());
		assertEquals('!',potion.getRepresentation());
	}

	@Test
	public void testGetName() {
		System.out.println("Potion: "+potion);
		assertEquals("Health Potion ",potion.getName());
		
	}

}
