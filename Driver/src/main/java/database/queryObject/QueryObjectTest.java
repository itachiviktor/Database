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
		
		
		Select belso = new Select(db, "mine");
		belso.where.op = ">";
		belso.where.jobb = "3";
		
		Select select = new Select(db, "mine.x");
		select.where.op = "<";
		select.where.jobb = "5";
		select.from.select = belso;
		
		Select attros = new Select(db, "mine.stone.location.x");
		
		
		TileMap l = select.execute();
		
		/*for(int i=0;i<l.size();i++){
			System.out.println(l.get(i));
		}*/
		
		WhereNode ten = new WhereNode();
		WhereNode twelve = new WhereNode();
		WhereNode thirty = new WhereNode();
		WhereNode fourthy = new WhereNode();
		WhereNode fifthy = new WhereNode();
		
		WhereLetter one = new WhereLetter();
		WhereLetter two = new WhereLetter();
		WhereLetter three = new WhereLetter();
		WhereLetter four = new WhereLetter();
		WhereLetter five = new WhereLetter();
		WhereLetter six = new WhereLetter();
		
		one.ret = false;
		one.id = 1;
		
		two.ret = true;
		two.id = 2;
		
		three.ret = false;
		three.id = 3;
		
		four.ret = true;
		four.id = 4;
		
		five.ret = false;
		five.id = 5;
		
		six.ret = true;
		six.id = 6;
		
		ten.leftChild = twelve;
		ten.rightChild = fifthy;
		ten.id = 10;
		ten.operator = Operators.OR;
		
		twelve.leftChild = thirty;
		twelve.rightChild = fourthy;
		twelve.id = 20;
		twelve.operator = Operators.OR;
		
		thirty.leftChild = one;
		thirty.rightChild = two;
		thirty.id = 30;
		thirty.operator = Operators.AND;
		
		fourthy.leftChild = three;
		fourthy.rightChild = four;
		fourthy.id = 40;
		fourthy.operator = Operators.OR;
		
		fifthy.leftChild = five;
		fifthy.rightChild = six;
		fifthy.id = 50;
		fifthy.operator = Operators.AND;
		
		List<WhereElement> res = order.postorderTraversal(ten);
		
		/*for(int i=0;i<res.size();i++){
			System.out.println(res.get(i).toString());
		}*/
		
		Stack<Boolean> resHeap = new Stack<Boolean>();
		
		for(int i=0;i<res.size();i++){
			if(res.get(i) instanceof WhereLetter){
				heapLoad(resHeap, res.get(i).execute());
			}else{
				heapLoad(resHeap, (WhereNode)res.get(i));
			}
		}
		
		System.out.println("eredmény");
		for(int i=0;i<resHeap.size();i++){
			System.out.println(" " + resHeap.peek());
		}
		
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
