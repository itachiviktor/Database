package database.queryObject;

public class WhereNode implements WhereElement{
	
	WhereElement leftChild;
	WhereElement rightChild;
	int id;
	
	Operators operator;
	
	public WhereNode parent;
	/*A gyökérnek nincs szülője*/
	
	boolean not = false;/*eza azt jelenti az operátorra nézve, hogy OR Not, azaz van e hozzá not.*/
	
	public WhereNode(WhereNode parent) {
		this.parent = parent;
	}

	public boolean execute() {
		if(operator == Operators.AND){
			if(not){
				return (leftChild.execute() && !rightChild.execute());
			}
			return (leftChild.execute() && rightChild.execute());
		}else if(operator == Operators.OR){
			if(not){
				System.out.println("asd");
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