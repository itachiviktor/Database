package datastructure;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database.LoadedDatabase;

public class AttributeCheckerTest {
	
	private AttributeChecker checker;
	private LoadedDatabase db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		checker = new AttributeChecker();
		db = new LoadedDatabase();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals(true, checker.existAttribute(new StringPrimitiv("String", db.classes, db.map), "id"));
		assertEquals(true, checker.existAttribute(new StringPrimitiv("String", db.classes, db.map), "className"));
		assertEquals(true, checker.existAttribute(new StringPrimitiv("String", db.classes, db.map), "zindex"));
		assertEquals(true, checker.existAttribute(new StringPrimitiv("String", db.classes, db.map), "zlayer"));
		assertEquals(false, checker.existAttribute(new StringPrimitiv("String", db.classes, db.map), "alma"));
		assertEquals(true, checker.existAttribute(new Instance("Mine", db.classes, db.map), "stone"));
		assertEquals(true, checker.existAttribute(new Instance("Mine", db.classes, db.map), "id"));
		assertEquals(false, checker.existAttribute(new Instance("Mine", db.classes, db.map), "asd"));
	}

}
