package database.queryObject.postorder;

import java.util.Iterator;

import database.queryObject.WhereElement;

public interface PostOrderBinaryTreeIterator extends Iterator<WhereElement> { 
	  /** Returns the next integer a in the post-order traversal of the given binary tree.
	   * For example, given a binary tree below,
	   *       4
	   *      / \
	   *     2   6
	   *    / \ / \
	   *   1  3 5  7
	   * the outputs will be 1, 3, 2, 5, 7, 6, 4. 
	   */ 
	  public WhereElement next(); 

	  /** Return true if traversal has not finished; otherwise, return false. */ 
	  public boolean hasNext();
}
