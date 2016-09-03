package database.queryObject.postorder;

import java.util.List;
import database.queryObject.WhereElement;

public interface IPostorderTravelel {
	public List<WhereElement> postorderTraversal(WhereElement root);
}
