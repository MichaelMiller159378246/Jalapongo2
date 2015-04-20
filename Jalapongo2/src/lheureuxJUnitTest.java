import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class lheureuxJUnitTest {
	
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
		
		
		
	}
}