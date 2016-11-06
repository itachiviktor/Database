package database.queryObject.update;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import database.InMemoryDatabase;
import database.queryObject.IQueryObject;
import database.queryObject.Where;

/**
 * This class can prepare an Update QueryObject.
 * */
public class UpdateBuilder {
	private Update update;
	private InMemoryDatabase db;
	private Set set;
	private Move move;
	private List<Integer> moveValues;/*ezen pointra mozog(Point(10,10))*/
	
	private Where where;
	
	public UpdateBuilder(InMemoryDatabase db) {
		
		this.db = db;
		this.moveValues = new ArrayList<Integer>();
	}
	
	public IQueryObject build(){
		return null;
	}
	
}
