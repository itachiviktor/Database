package database.queryObject.insert;

import java.util.List;

import database.InMemoryDatabase;
import datastructure.ClassDefinition;
import datastructure.InstanceMaker;

public class TreeBuilder {
	public TreeNode root;
	
	public TreeNode actualNode;/*Ez a Node az amibe jelenleg beszúrhatunk gyereket, vagy a szülőjébe mozoghatunk.*/
	

	public InMemoryDatabase db;
	
	public InstanceMaker maker;
	
	public TreeBuilder(InMemoryDatabase db, InstanceMaker maker) {
		this.db = db;
		this.maker = maker;
	}
	
	public void makeRoot(String className, String insertedMapName){
		root = new TreeNode(db, maker, className, insertedMapName);
		
		actualNode = root;
	}
	
	public void makeChildren(String attributeName, String primitiveValue){
		TreeNode newNode = new TreeNode(db, maker, actualNode, attributeName);
		newNode.setAttributeName(attributeName);
		/*állítsuk be a primitivvaluet, ha van, ha nincs akkor null az érték már meghíváskor is*/
		if(primitiveValue != null){
			newNode.setPrimitiv(primitiveValue);
		}
		actualNode.children.add(newNode); /*Itt adjuk hozzá a fához.*/
	}
	
	public void makeChildren(String attributeName){
		/*Ha nincs a paraméterlistában primitivValue, az azt jelenti, hogy ez egy objektum lesz.*/
		/*Ha objektumgyereket adunk neki, akkor itt a gyerek lesz az aktuális pont a fában.*/
		TreeNode newNode = new TreeNode(db, maker, actualNode, attributeName);
		newNode.setAttributeName(attributeName);
		
		actualNode.children.add(newNode); /*Itt adjuk hozzá a fához.*/
		
		actualNode = newNode;/*Itt állítjuk az új aktuális Nodeot a létrehozott gyerekre.*/
		
	}
	
	public void moveToParent(){
		actualNode = actualNode.getParent();
	}
	
	
	
	@Override
	public String toString() {
		return root.toString();
	}
}