package database.queryObject.postorder;

import java.util.ArrayList;
import java.util.List;
import database.queryObject.WhereElement;

public class PostorderTraveler implements IPostorderTravelel{

	public List<WhereElement> postorderTraversal(WhereElement root) {
		PostOrderBinaryTreeIterator iterator = new PostOrderBinaryTreeIteratorImpl(root);  
		List<WhereElement> results = new ArrayList<WhereElement>();  
		while (iterator.hasNext()) {  
			results.add(iterator.next());  
		}  
		return results;  
	}
}
