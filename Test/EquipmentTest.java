import static org.junit.Assert.*;

import org.junit.Test;


public class EquipmentTest {

	Equipment equipment=new Equipment(null, 0, 0);
	Equipment equipment1=new Equipment(null, 50, 0);
	Equipment equipment2=new Equipment(null, 0, 50);
	@Test
	public void testEquipment() {
		System.out.println("Equipment is: "+equipment);
		assertEquals("",0,0);
	}
	
	@Test
	public void testToString() {
		System.out.println("Name is:"+equipment.getName());
		assertEquals("Unknow item", equipment.getName());
		
		assertNotNull("Not Null",equipment.toString());
		assertNotNull("Not Null",equipment1.toString());
		assertNotNull("Not Null",equipment2.toString());
		
		System.out.println("Str is: "+equipment.getModStr());
		assertEquals(0,equipment.getModStr());
		
		System.out.println("Def is: "+equipment.getModDef());
		assertEquals(0,equipment.getModDef());
	}


}
