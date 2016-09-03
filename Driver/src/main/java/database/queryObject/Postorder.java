package database.queryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import database.queryObject.postorder.PostorderTraveler;

public class Postorder {
	
	public static List<WhereElement> postorderTraversal(WhereElement root){
		PostorderTraveler traveler = new PostorderTraveler();
		return traveler.postorderTraversal(root);
	}
	
	/*public static List<WhereElement> postorderTraversal(WhereElement root) {
	    List<WhereElement> res = new ArrayList<WhereElement>();
	 
	    
	    WhereNode copy = (WhereNode)root;
	    try {
			copy = (WhereNode)copy.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	    
	    if(root==null) {
	        return res;
	    }
	 
	    Stack<WhereElement> stack = new Stack<WhereElement>();
	    stack.push(copy);
	 
	    while(!stack.isEmpty()) {
	    	
	        WhereElement temp = stack.peek();
	        if(temp.getLeftChild() == null && temp.getRightChild() == null) {
	            WhereElement pop = stack.pop();
	            res.add(pop);
	        }
	        else {
	            if(temp.getRightChild() != null) {
	                stack.push(temp.getRightChild());
	                temp.setRightChild(null);
	            }
	 
	            if(temp.getLeftChild() != null) {
	                stack.push(temp.getLeftChild());
	                temp.setLeftChild(null);
	            }
	        }
	    }
	    return res;
	}*/
}
