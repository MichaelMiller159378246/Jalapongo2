import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javafx.application.Application;
import javafx.stage.Stage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class lheureuxJUnitTest extends Application{
	
	static int i = 1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Starting Tests");
		
		String[] args = null; // Sets the String[] args to null

		Thread t = new Thread("JavaFX Init Thread") { // Creates a new thread
			@Override
			public void run() { // Run method within thread
				main(args);// Calls the main function of this class
			}
		};
		t.start(); // Starts the thread
		try {
			Thread.sleep(20000); // Thread sleeps for 20 seconds
		} catch (InterruptedException e) {
			System.out.println("There was an error trying to sleep"); // Displays Error
		}
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
	public void powerUpSlowBallTest() throws InterruptedException {
		//user story 2 toggling power ups
		assertTrue(GameGUI.slowCB.isSelected()); // do not touch slow ball toggle
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
	
	@Test
	public void powerUpMultiBallTest() throws InterruptedException {
		//user story 2 toggling power ups
		assertFalse(GameGUI.multiCB.isSelected()); // unselect multi ball toggle
	}
	
	public static void main(String[] args){
		lheureuxJUnitTest.launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		new GameGUI(arg0);
	}
}