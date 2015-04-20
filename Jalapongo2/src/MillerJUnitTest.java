import static org.junit.Assert.*;
import javafx.application.Platform;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class MillerJUnitTest {

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
	public void test() {
		assertTrue(GameGUI.musicCB.isSelected());
	}
}
