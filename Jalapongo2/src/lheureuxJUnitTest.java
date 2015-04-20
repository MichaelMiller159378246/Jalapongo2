import static org.junit.Assert.*;
import javafx.scene.control.CheckBox;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class lheureuxJUnitTest extends GameGUI{
	
	static int i = 1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Starting Tests");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\nDone all Tests\n");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("\nStarting Test : " + i);
		
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("\nDone With Test : " + i);
		i++;
	}

	@Test
	public void powerUpSelectionTest() {
		//user story 2 toggling power ups ????????
		GameGUI game = new GameGUI();
		game.playBSM.fire();
		game.hostBCS.fire();
		game.optionsHO.fire();
		if(fastCB.isSelected()){
			System.out.println("fast ball selected on start");
		}else{
			fail("fast ball isnt slected on start");
		}
		game.fastCB.fire();
		if(fastCB.isSelected()){
			fail("fast ball is selected after first click");
		}else{
			System.out.println("test passed");
		}
		
		/*fastCB = new CheckBox("Fast Ball       ");
		shieldCB = new CheckBox("Shield           ");
		livesCB = new CheckBox("Extra Lives    "); 
		bigCB = new CheckBox("Big Paddle    ");
		slowCB = new CheckBox("Slow Ball      ");
		smallCB = new CheckBox("Small Paddle ");
		flipCB = new CheckBox("Flip Controls ");
		stallCB = new CheckBox("Stall Controls");
		multiCB = new CheckBox("Multi Ball      ");*/
		
	}
}