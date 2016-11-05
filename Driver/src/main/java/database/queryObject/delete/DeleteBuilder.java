package database.queryObject.delete;

import database.InMemoryDatabase;
import database.queryObject.From;
import database.queryObject.IQueryObject;
import database.queryObject.selectBuild.SelectBuilder;


public class DeleteBuilder extends SelectBuilder{
	
	public DeleteBuilder(InMemoryDatabase db) {
		super(db);
	}
	
	@Override
	public IQueryObject build() {
		
		Delete delete = new Delete(getDb(), getRoot().getSource());
		delete.from = new From(getRoot().getFrom());
		
		IQueryObject ob = getRoot().build();
		
		delete.where = ob.getWhere();
		
		return delete;
	}
	
}