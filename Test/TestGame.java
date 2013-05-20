import static org.junit.Assert.*;

import org.junit.Test;


public class TestGame {

	@Test
	public void testGame(){
		GameGUI gGui = new GameGUI();
		String playerName = "Tester";
		Game g = new Game(gGui, playerName);
		long seed = g.getSeed();
		System.out.println(g.equals(new Game(gGui, playerName, seed)));
		assertEquals(new Game(gGui, playerName, seed), g);
	}

}
