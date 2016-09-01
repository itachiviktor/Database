package database.queryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WhereBuilder {
	public List<WhereNode> nodes;
	public List<WhereLetter> letters;
	
	public WhereNode root;
	public WhereNode actualNode;
	
	public WhereBuilder() {
		this.nodes = new ArrayList<WhereNode>();
		this.letters = new ArrayList<WhereLetter>();
	}
	
	public void makeRoot(){
		root = new WhereNode(null);
		actualNode = root;
		nodes.add(root);
	}
	
	public void addLeftChild(){
		actualNode.leftChild = new WhereNode(actualNode);
		nodes.add((WhereNode)actualNode.leftChild);
	}
	
	public void addRightChild(){
		actualNode.rightChild = new WhereNode(actualNode);
		nodes.add((WhereNode)actualNode.rightChild);
	}
	
	public void addLeftChildLetter(){
		actualNode.leftChild = new WhereLetter();
		letters.add((WhereLetter)actualNode.leftChild);
	}
	
	public void addRightChildLetter(){
		actualNode.rightChild = new WhereLetter();
		letters.add((WhereLetter)actualNode.rightChild);
	}
	
	
	public void moveToParent(){
		actualNode = actualNode.parent;
	}
	
	
	
	
	
	public boolean build(){
		Postorder order = new Postorder();
		
		
		WhereNode ten = new WhereNode(null);
		WhereNode twelve = new WhereNode(null);
		WhereNode thirty = new WhereNode(null);
		WhereNode fourthy = new WhereNode(null);
		WhereNode fifthy = new WhereNode(null);
		
		WhereLetter one = new WhereLetter();
		WhereLetter two = new WhereLetter();
		WhereLetter three = new WhereLetter();
		WhereLetter four = new WhereLetter();
		WhereLetter five = new WhereLetter();
		WhereLetter six = new WhereLetter();
		
		one.ret = false;
		one.id = 1;
		
		two.ret = false;
		two.id = 2;
		
		three.ret = false;
		three.id = 3;
		
		four.ret = false;
		four.id = 4;
		
		five.ret = false;
		five.id = 5;
		
		six.ret = true;
		six.id = 6;
		
		ten.leftChild = twelve;
		ten.rightChild = fifthy;
		ten.id = 10;
		ten.operator = Operators.OR;
		ten.not = true;
		
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
	
		return resHeap.peek();
		/*for(int i=0;i<resHeap.size();i++){
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
