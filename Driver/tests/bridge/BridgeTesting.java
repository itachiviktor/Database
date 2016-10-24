package bridge;

import org.junit.Test;

import database.InMemoryDatabase;
import database.LoadedDatabase;
import driver.Driver;
import junit.framework.TestCase;

public class BridgeTesting extends TestCase {
	InMemoryDatabase db;
	Driver driver;
	
	protected void setUp() throws Exception {
		db = new InMemoryDatabase("Game");
		driver = new Driver(db);
	}

	protected void tearDown() throws Exception {
		
	}
	
	@Test
	public void testBridge(){
		assertEquals(db.classDefinitionsString(), driver.classDefinitionsString());
		assertEquals(db.mapInstancesString(), driver.mapInstancesString());
	}

}
