import static org.junit.Assert.*;

import org.junit.Test;


public class BoxTest {

	@Test
	public void testIsWalkable() {
		Box testBox = new Box('#', false, false);
		assertEquals("Checks if the returned value for walkable is correct", false, testBox.isWalkable());
	}

	@Test
	public void testSetWalkable() {
		Box testBox = new Box('#', false, false);
		testBox.setWalkable(true);
		assertEquals("Checks if the value for walkable has changed", true, testBox.isWalkable());
	}
	@Test
	public void testIsVisiable(){
		Box testBox = new Box();
		assertEquals("check if the value for visiable has changed", false, testBox.isVisible());
	}
	@Test
	public void testSetVisiable(){
		Box testBox = new Box();
		testBox.setVisible(true);
		assertEquals("Check if the value for visiable has changed", true, testBox.isVisible());
	}
	@Test
	public void testIsWalked(){
		Box testBox = new Box();
		assertEquals("check if the value for walked has changed", false, testBox.isWalkable());
}
	@Test
	public void testSetWalked(){
	Box testBox= new Box();
	testBox.setWalked(true);
	assertEquals("check", true, testBox.isWalked());
	}
	
	@Test
	public void testGetActor(){
		
	}
}



