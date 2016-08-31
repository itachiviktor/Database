package database.queryObject.insert;

import java.util.ArrayList;
import java.util.List;

import database.InMemoryDatabase;
import datastructure.ClassDefinition;
import datastructure.Instance;
import datastructure.InstanceMaker;


public class TreeNode {
	/*A parsertől attrinútumnevet és primitivvaluet kell kapnia, vagy másik objektumot.*/
	
	public String className;
	public String attributeName;
	
	public String stringPrimitiv;
	public Number numberPrimitiv;
	public Boolean booleanPrimitiv;
	
	public List<TreeNode> children;
	
	public TreeNode parent;
	
	public ClassDefinition nodeClassDefinition;
	
	public InMemoryDatabase db;
	
	public InstanceMaker maker;
	
	public TreeNode(InMemoryDatabase db,  InstanceMaker maker, String className) {
		this.db = db;
		this.maker = maker;
		this.className = className;
		init(db.getClasses());
	}
	
	public TreeNode(InMemoryDatabase db, InstanceMaker maker, TreeNode parent, String attributeName) {
		this.parent = parent;
		this.attributeName = attributeName;
		this.db = db;
		this.maker = maker;
		init(db.getClasses());
	}
	
	
	
	public void init(List<ClassDefinition> classes){
		children = new ArrayList<TreeNode>();
		
		if(parent != null){
			ClassDefinition parentClassDefinition = null;
			/*a szülőnek az osztályleíróját keresem itt*/
			for(int i=0;i<classes.size();i++){
				if(parent.getClassName().equals(classes.get(i).className)){
					/*ekkor megvan a szülőnek a ClassDefinitionja*/
					parentClassDefinition = classes.get(i);
					break;
				}
			}
			
			/*Itt a saját osztálynevünket tudjuk meg Stringben.*/
			for(String x : parentClassDefinition.attributes.keySet()){
				if(x.equals(this.attributeName)){
					/*Megvan a saját osztályunk neve*/
					this.className = parentClassDefinition.attributes.get(x);
					break;
				}
			}
			
			/*Itt a saját osztályunk leíróját tudjuk meg*/
			for(int i=0;i<classes.size();i++){
				if(classes.get(i).className.equals(this.className)){
					this.nodeClassDefinition = classes.get(i);
					break;
				}
			}
			
		}else{
			/*Itt a saját osztályunk leíróját tudjuk meg*/
			for(int i=0;i<classes.size();i++){
				if(classes.get(i).className.equals(this.className)){
					this.nodeClassDefinition = classes.get(i);
					break;
				}
			}
		}
	}
	
	public void setPrimitiv(String value){
		
		if(nodeClassDefinition.className.equals("String")){
			this.stringPrimitiv = value;
		}else if(nodeClassDefinition.className.equals("Number")){
			if(value.contains(".")){
				this.numberPrimitiv = Float.valueOf(value);
			}else{
				this.numberPrimitiv = Integer.valueOf(value);
			}
			
		}else if(nodeClassDefinition.className.equals("Boolean")){
			this.booleanPrimitiv = Boolean.valueOf(value);
		}else{
			/*Ilyenkor dobni kell valami hibár, mert nem primitiv típus*/
		}
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	
	public List<TreeNode> getInsertation() {
		return children;
	}

	public void setInsertation(List<TreeNode> insertation) {
		this.children = insertation;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	public List<TreeNode> getSortedTreeList(){		
		List<TreeNode> result = new ArrayList<TreeNode>();
		
		for(int i=0;i<children.size();i++){
			result.add(children.get(i));
		}
		
		for(int i=0;i<children.size();i++){
			result.addAll(children.get(i).getSortedTreeList());
		}
		
		
		
		return result;
	}
	
	public Values getValue(){
		Values value = new Values("azeroth", db,  maker);
		value.className = this.className;
		value.ownClass = this.nodeClassDefinition;
		
		if(this.stringPrimitiv != null){
			value.stringPrimitive = this.stringPrimitiv;
		}else if(this.numberPrimitiv != null){
			value.numberPrimitive = this.numberPrimitiv;
		}else if(this.booleanPrimitiv != null){
			value.booleanPrimitive = this.booleanPrimitiv;
		}else{
			for(int i=0;i<children.size();i++){
				value.children.add(children.get(i).getValue());
				value.childAttributeNames.add(children.get(i).attributeName);
			}
		}
		
		return value;
	}
	
	public String ownDetails(){
		StringBuilder sb = new StringBuilder();
		if(this.stringPrimitiv != null){
			sb.append("ClassName: " + this.className + " attributeName: " + attributeName + " value: " + stringPrimitiv + System.lineSeparator());
		}else if(this.numberPrimitiv != null){
			sb.append("ClassName: " + this.className + " attributeName: " + attributeName +  " value: " + numberPrimitiv + System.lineSeparator());
		}else if(this.booleanPrimitiv != null){
			sb.append("ClassName: " + this.className + " attributeName: " + attributeName +  " value: " + booleanPrimitiv + System.lineSeparator());
		}else{
			sb.append("ClassName: " + this.className + " attributeName: " + attributeName + System.lineSeparator());
		}
		
		return sb.toString(); 
	}
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(this.stringPrimitiv != null){
			sb.append("ClassName: " + this.className + " attributeName: " + attributeName + " value: " + stringPrimitiv + System.lineSeparator());
		}else if(this.numberPrimitiv != null){
			sb.append("ClassName: " + this.className + " attributeName: " + attributeName +  " value: " + numberPrimitiv + System.lineSeparator());
		}else if(this.booleanPrimitiv != null){
			sb.append("ClassName: " + this.className + " attributeName: " + attributeName +  " value: " + booleanPrimitiv + System.lineSeparator());
		}else{
			sb.append("ClassName: " + this.className + " attributeName: " + attributeName + System.lineSeparator());
		}

		for(int i=0;i<children.size();i++){
			sb.append(children.get(i).toString());
		}
		return sb.toString();
	}
}