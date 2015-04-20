import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.stage.Stage;


public class MillerJUnitTest extends Application implements Runnable{
	
	
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
	public void musicTest() {
		// Was unable to get it to pass this part as the JUnit is unable to run while GUI is running
		assertTrue(GameGUI.musicCB.isSelected());
	}

	@Override
	public void start(Stage arg0) throws Exception {
		GameGUI game = new GameGUI();
		game.start(arg0);
	}

	@Override
	public void run() {
		GameGUI.launch();
	}
}
