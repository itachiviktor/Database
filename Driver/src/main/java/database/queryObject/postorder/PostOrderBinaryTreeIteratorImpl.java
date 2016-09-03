package database.queryObject.postorder;

import java.util.NoSuchElementException;
import java.util.Stack;

import database.queryObject.WhereElement;


public class PostOrderBinaryTreeIteratorImpl implements PostOrderBinaryTreeIterator {  
	Stack<WhereElement> stack = new Stack<WhereElement>();  
	   
	/** Constructor */  
	public PostOrderBinaryTreeIteratorImpl(WhereElement root) {  
	  findNextLeaf(root);  
	}  
	
	/** find the first leaf in a tree rooted at cur and store intermediate nodes */  
	private void findNextLeaf(WhereElement cur) {  
		while (cur != null) {  
			stack.push(cur);  
			if (cur.getLeftChild() != null) {  
				cur = cur.getLeftChild();  
			} else {  
				cur = cur.getRightChild();  
			}  
	    }  
	}  
	   
	
	   
	/** {@inheritDoc} */  
	public boolean hasNext() {  
		return !stack.isEmpty();  
	}  
	   
	/** {@inheritDoc} */  
	public WhereElement next() {  
		if (!hasNext()) {  
	       throw new NoSuchElementException("All nodes have been visited!");  
	    }  
	   
	    WhereElement res = stack.pop();  
	    if (!stack.isEmpty()) {  
	    	WhereElement top = stack.peek();  
	       	if (res == top.getLeftChild()) {  
	       		findNextLeaf(top.getRightChild()); // find next leaf in right sub-tree 
	       	}  
	    }  
	   
	    return res;  
	}  
	   
	public void remove() {  
	     throw new UnsupportedOperationException("remove() is not supported.");  
	}
}