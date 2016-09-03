package database.queryObject;

import database.InMemoryDatabase;
import datastructure.Instance;

public class OperandTest {

	public static void main(String[] args) {
		Operand op = new Operand("123",true);
		InMemoryDatabase db = new InMemoryDatabase("db");
		
		for(int i=0;i<db.getMapByName("azeroth").size();i++){
			System.out.println(db.getMapByName("azeroth").get(i));
		}
		
		
		if(op.getOperand(db.getMapByName("azeroth").get(2)) instanceof Boolean){
			boolean val = op.getOperand(db.getMapByName("azeroth").get(2));
			System.out.println(val);
		}else if(op.getOperand(db.getMapByName("azeroth").get(2)) instanceof Number){
			int val = op.getOperand(db.getMapByName("azeroth").get(2));
			System.out.println(val+10);
		}else if(op.getOperand(db.getMapByName("azeroth").get(7)) instanceof Instance){
			Instance val = op.getOperand(db.getMapByName("azeroth").get(7));
			System.out.println(val);
		}
	}

}
