import static org.junit.Assert.*;
import javafx.application.Platform;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class SeydelJUnitTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass: Preparing to run unit test(s)...\n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass:Exiting unit tests...\n");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("setUp:Starting a test\n");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown:Test complete\n\n");
	}

	@Test
	//	Test case 3 (host choose # of AIs)
	public void test() {
		GameGUI game = new GameGUI();
		game.playBSM.fire();
		game.hostBCS.fire();
		game.startHostingHO.fire();
		game.startRBQ.fire();
		assertEquals(AICB.getValue(), 3);
				
	}
	
	@Test
	//	Test case 6 (host choose # of AIs)
	public void test() {
		GameGUI game = new GameGUI();
		game.playBSM.fire();
		game.hostBCS.fire();
		game.startHostingHO.fire();
		game.readyCB1.fire();
		assertTrue(readyCB1.getFill()== LIME);
				
	}
		
}
