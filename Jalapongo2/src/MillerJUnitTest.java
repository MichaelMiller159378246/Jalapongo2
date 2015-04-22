import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javafx.application.Application;
import javafx.stage.Stage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MillerJUnitTest extends Application{

	// Used this web page as a guide
	// http://stackoverflow.com/questions/18429422/basic-junit-test-for-javafx-8

	// Before the entire class
	@BeforeClass
	public static void initJFX() {
		String[] args = null; // Sets the String[] args to null

		Thread t = new Thread("JavaFX Init Thread") { // Creates a new thread
			@Override
			public void run() { // Run method within thread
				main(args);// Calls the main function of this class
			}
		};
		t.start(); // Starts the thread
		try {
			Thread.sleep(10000); // Thread sleeps for 10 seconds allowing the host to change the music
		} catch (InterruptedException e) {
			System.out.println("There was an error trying to sleep"); // Displays Error
		}
	}

	// After the class is done
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass:Exiting unit tests...\n");
	}

	// Before Each Test
	@Before
	public void setUp() throws Exception {
		System.out.println("setUp:Starting a test\n");
	}

	// After Each Test
	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown:Test complete\n\n");
	}

	// Tests to see if the music is selected
	@Test
	public void musicTestTrue() throws InterruptedException {
		assertTrue(GameGUI.musicCB.isSelected()); // Tests to see the value of the music check box
	}

	// Tests to see if the music is not selected
	@Test
	public void musicTestFalse() throws InterruptedException {
		assertFalse(GameGUI.musicCB.isSelected()); // Tests to see the value of the music check box
	}

	public static void main(String[] args){
		MillerJUnitTest.launch(args);// Launches the class basically calling the start method
	}

	@Override
	public void start(Stage arg0) throws Exception {
		new GameGUI(arg0); // Launches the GUI
	}


}
