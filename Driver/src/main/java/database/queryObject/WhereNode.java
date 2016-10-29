package database.queryObject;

import datastructure.Instance;

public class WhereNode implements WhereElement{
	
	private WhereElement leftChild;
	private WhereElement rightChild;
	
	private Operators operator;
	
	public WhereNode parent;
	/*A gyökérnek nincs szülője*/
	
	boolean not = false;/*eza azt jelenti az operátorra nézve, hogy OR Not, azaz van e hozzá not.*/
	
	public WhereNode(WhereElement leftChild, WhereElement rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public boolean execute() {
		if(operator == Operators.AND){
			if(not){
				return (leftChild.execute() && !rightChild.execute());
			}
			return (leftChild.execute() && rightChild.execute());
		}else if(operator == Operators.OR){
			if(not){
				return (leftChild.execute() || !rightChild.execute());
			}
			return (leftChild.execute() || rightChild.execute());
		}
		
		return false;
	}
	
	public boolean execute(boolean left, boolean right){
		if(operator == Operators.AND){
			if(not){
				return (left && !right);
			}
			return (left && right);
		}else if(operator == Operators.OR){
			if(not){
				return (left || !right);
			}
			return (left || right);
		}
		
		return false;
	}
	
	

	public WhereElement getLeftChild() {
		return this.leftChild;
	}

	public WhereElement getRightChild() {
		return this.rightChild;
	}

	public void setRightChild(WhereElement child) {
		this.rightChild = child;
	}

	public void setLeftChild(WhereElement child) {
		this.leftChild = child;
	}
	


	public Operators getOperator() {
		return operator;
	}

	public void setOperator(Operators operator) {
		this.operator = operator;
	}

	public void setCheckInstance(Instance instance) {
		
		/*System.out.println(leftChild);
		System.out.println(rightChild);
		leftChild.setCheckInstance(instance);
		rightChild.setCheckInstance(instance);
		System.out.println("lefutott");*/
	}
	
	@Override
	public String toString() {
		return "Op: " + this.operator;
	}
}