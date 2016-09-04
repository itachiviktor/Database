package database.queryObject;

import java.util.List;
import java.util.Stack;

import database.LoadedDatabase;
import datastructure.Instance;
import datastructure.TileMap;

public class QueryObjectTest {
	public static void main(String[] args) {
		LoadedDatabase db = new LoadedDatabase();
		
		Postorder order = new Postorder();
		
		
		Select select = new Select(db, "mine");
		From from = new From("azeroth");
		
		for(int i=0;i<db.getMap().size();i++){
			System.out.println(db.getMap().get(i));
		}
		
		
		
		
		
		
		
		
		/*Operand op1 = new Operand("mine.zindex",true);
		Operand op2 = new Operand("0",false);
		Operators oper1 = Operators.EQ;
		
		Operand op3 = new Operand("mine.zlayer",true);
		Operand op4 = new Operand("0",false);
		Operators oper2 = Operators.EQ;
		
		Operand op5 = new Operand("mine", true);
		Operand op6 = new Operand("Number", false);
		Operators oper3 = Operators.LT;
		
		Operand op7 = new Operand("mine", true);
		Operand op8 = new Operand("Mine", false);
		Operators oper4 = Operators.IS;
		
		
		WhereLetter letter1 = new WhereLetter(op1, op2, oper1);
		
		WhereLetter letter2 = new WhereLetter(op3, op4, oper2);
		
		WhereLetter letter3 = new WhereLetter(op5, op6, oper3);
		
		WhereLetter letter4 = new WhereLetter(op7, op8, oper4);
		
		WhereNode node = new WhereNode(letter1, letter2);
		node.setOperator(Operators.AND);
		
		
		WhereNode other = new WhereNode(letter3, letter4);
		other.setOperator(Operators.OR);
		
		WhereNode rootos = new WhereNode(node, other);
		rootos.setOperator(Operators.AND);*/
		
		Select sel = new Select(db, "mine");
		From fr = new From("azeroth");
		Operand op3 = new Operand("mine.id",true);
		Operand op4 = new Operand("1",false);
		Operators oper2 = Operators.EQ;
		WhereLetter let = new WhereLetter(op3, op4, oper2);
		
		Where wh = new Where();
		wh.setRoot(let);
		
		sel.setFrom(fr);
		sel.setWhere(wh);
		
		
		
		Operand op1 = new Operand("mine.id",true);
		Operand op2 = new Operand(sel,false);
		Operators oper1 = Operators.GT;
		
		
		WhereLetter letter1 = new WhereLetter(op1, op2, oper1);
		
		
		
		Where where = new Where();
		where.setRoot(letter1);
		
		select.setWhere(where);
		select.setFrom(from);
		
		List<Instance> ini = select.execute();
		//List<Instance> ini = where.execute(db.getMap().getMap());
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		for(int i=0;i<ini.size();i++){
			System.out.println(ini.get(i));
		}
		
		//letter.setCheckInstance(db.getMap().getMap().get(4));
		
		
		
		
		/*List<WhereElement> res = order.postorderTraversal(node);
		
		
		
		Stack<Boolean> resHeap = new Stack<Boolean>();
		
		for(int i=0;i<res.size();i++){
			if(res.get(i) instanceof WhereLetter){
				heapLoad(resHeap, res.get(i).execute());
			}else{
				heapLoad(resHeap, (WhereNode)res.get(i));
			}
		}*/
		
		/*System.out.println("eredmény");
		for(int i=0;i<resHeap.size();i++){
			System.out.println(" " + resHeap.peek());
		}*/
		
	}
	
	public static void heapLoad(Stack<Boolean> heap, boolean value){
		heap.push(value);
	}
	
	public static void heapLoad(Stack<Boolean> heap, WhereNode node){
		boolean left = heap.pop();
		boolean right = heap.pop();
		
		heap.push(node.execute(left, right));
	}
}
