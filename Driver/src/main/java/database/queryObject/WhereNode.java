package database.queryObject;


public class WhereNode implements WhereElement{
	
	WhereElement leftChild;
	WhereElement rightChild;
	int id;
	
	Operators operator;
	
	public WhereNode() {
		
	}

	public boolean execute() {
		if(operator == Operators.AND){
			return (leftChild.execute() && rightChild.execute());
		}else if(operator == Operators.OR){
			return (leftChild.execute() || rightChild.execute());
		}
		
		return false;
	}
	
	public boolean execute(boolean left, boolean right){
		if(operator == Operators.AND){
			return (left && right);
		}else if(operator == Operators.OR){
			return (left || right);
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "" + id;
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
	


}