import static org.junit.Assert.*;

import org.junit.Test;


public class MiscellaneousTest {
//String name, int modStr, int modDef
	
	
	@Test
	public void testOne() {
		Miscellaneous misc = new Miscellaneous(null,0,0);
		System.out.println("Name 1 is " + misc);
		}
	

	@Test
	public void testTwo() {
		Miscellaneous misc = new Miscellaneous("Misc2",50,50);
		System.out.println("Name 2 is " + misc);
		assertEquals("Misc2 (Str+ 50)",misc.toString());
	}
	
	@Test
	public void testThree() {
		Miscellaneous misc1 = new Miscellaneous("Misc2",0,50);
		System.out.println("Name 2 is " + misc1);
		assertNotNull("Misc1 is not null",misc1.toString());
	}
}
