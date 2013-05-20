import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class BossTest {

	Boss badBoy;
	Dungeon du;
	Adventurer adv;
	@Before
	public void init(){
		du= new Dungeon(1234);
		badBoy = new Boss("Bad",22,du);
		adv=new Adventurer("Jimmi",du);
	}

	@Test
	public void toStringTest() {
		String expected=badBoy.getName()+" - HP: " + badBoy.getLifePoints() + " - Str: "
				+ badBoy.getStrength() + " - Def: " + badBoy.getDefence() + " - Lvl: "
				+ badBoy.getLevel();
		assertEquals(expected, badBoy.toString());
	}
	
	@Test
	public void dropLootTest() {
		badBoy.dropLoot();
	}
	
	
	
	@Test
	public void checkRangeTest() {
		badBoy.checkRange(adv);	
	
		adv.setPosX(0);
		badBoy.setPosX(0);
		adv.setPosY(0);
		badBoy.setPosY(1);
		
		badBoy.checkRange(adv);	
		
		adv.setLifePoints(0);
		badBoy.checkRange(adv);	
		
		
		adv.setPosX(0);
		badBoy.setPosX(3);
		adv.setPosY(0);
		badBoy.setPosY(1);
		
		badBoy.checkRange(adv);	
		
	}
	
	
	
	
	
	
}
	


