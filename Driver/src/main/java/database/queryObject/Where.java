package database.queryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import datastructure.Instance;
import datastructure.TileMap;

public class Where {
	public List<Instance> result;
	
	private WhereElement root;
	
	public Where() {
		result = new ArrayList<Instance>();
	}
	
	public List<Instance> execute(List<Instance> list){
		result = new ArrayList<Instance>();
		
		
		
		for(int i=0;i<list.size();i++){
			
			//root.setCheckInstance(list.get(i));/*ezzel minden levélnek beállítódik majd a jelenleg vizsgált isnatcne*/
			
			List<WhereElement> res;
			if(root instanceof WhereNode){
				res = Postorder.postorderTraversal(root);
			}else{
				res = new ArrayList<WhereElement>();
				res.add(root);
			}
			
			
			
			for(int j=0;j<res.size();j++){
				res.get(j).setCheckInstance(list.get(i));
			}
			
			
			Stack<Boolean> resHeap = new Stack<Boolean>();
			
			for(int j=0;j<res.size();j++){
				if(res.get(j) instanceof WhereLetter){
					heapLoad(resHeap, res.get(j).execute());
				}else{
					heapLoad(resHeap, (WhereNode)res.get(j));
				}
			}
			
			if(resHeap.peek()){
				result.add(list.get(i));
				//System.out.println(true);
			}else{
				//System.out.println(false);
			}
		}
		
		return result;
	}
	
	public static void heapLoad(Stack<Boolean> heap, boolean value){
		heap.push(value);
	}
	
	public static void heapLoad(Stack<Boolean> heap, WhereNode node){
		boolean left = heap.pop();
		boolean right = heap.pop();
		
		heap.push(node.execute(left, right));
	}

	public WhereElement getRoot() {
		return root;
	}

	public void setRoot(WhereElement root) {
		this.root = root;
	}
	
	
}
