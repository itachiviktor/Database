package database.queryObject.alter;

import database.InMemoryDatabase;
import database.queryObject.From;
import database.queryObject.Operand;
import database.queryObject.Operators;
import database.queryObject.Where;
import database.queryObject.WhereLetter;
import database.queryObject.delete.Delete;

public class AlterTest {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("db");
		
		for(int i=0;i<db.getClasses().size();i++){
			System.out.println(db.getClasses().get(i));
		}
		
		
		
		Delete delete = new Delete(db, "mine");
		From from = new From("azeroth");
		
		Operand op3 = new Operand("mine.id",true);
		Operand op4 = new Operand("7",false);
		Operators oper2 = Operators.EQ;
		WhereLetter let1 = new WhereLetter(op3, op4, oper2);
		
		Where wh = new Where();
		wh.setRoot(let1);
		
		delete.setFrom(from);
		delete.setWhere(wh);
		
		delete.execute();
		
		
		
		Alter alter = new Alter(db, "Mine");
		
		RenameClass alt = new RenameClass(db);
		alt.setRenameClassToThisName("Djkfldsgk");
		
		alter.setAlterType(alt);
		alter.execute();
		
		
		System.out.println("Módosítás után");
		System.out.println();
		
		for(int i=0;i<db.getClasses().size();i++){
			System.out.println(db.getClasses().get(i));
		}

	}

}
