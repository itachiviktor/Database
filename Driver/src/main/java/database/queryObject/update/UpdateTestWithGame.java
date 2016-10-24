package database.queryObject.update;

import java.awt.Point;
import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.Operand;
import database.queryObject.Operators;
import database.queryObject.Where;
import database.queryObject.WhereLetter;
import datastructure.Instance;

public class UpdateTestWithGame {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("Final");
		
		Update update = new Update("og", db);
		
		/*Set set = new Set();
		set.setAttribute("location.x");
		set.setValue(30);
		set.setAttribute("location.y");
		set.setValue(333);*/
		
		Move move = new Move();
		move.setNewLocationPoint(new Point(100, 100));
		
		/*updatenél nincs mine.id csak id , majd a mine. -ot manuálisan kell odaraknom a buildelőben.*/
		Operand op3 = new Operand("mine.id",true);
		Operand op4 = new Operand("216",false);
		Operators oper2 = Operators.EQ;
		WhereLetter let1 = new WhereLetter(op3, op4, oper2);
		
		Where wh = new Where();
		wh.setRoot(let1);
		
		update.db = db;
		update.setMove(move);
		update.where = wh;
		
		List<Instance> res = update.execute();
		
		System.out.println();
		System.out.println();
		for(int i=0;i<db.getMapByName("og").size();i++){
			System.out.println(db.getMapByName("og").get(i));
		}

	}

}
