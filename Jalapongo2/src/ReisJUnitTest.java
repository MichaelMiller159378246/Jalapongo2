import static org.junit.Assert.*;
import javafx.application.Platform;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ReisJUnitTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Beginning tests");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Ending tests");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("Starting a test");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Ending a test");
	}

	@Test
	//test user story 
	public void test1() {
		
	}
}
