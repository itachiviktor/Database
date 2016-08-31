package database.queryObject;

public class WhereLetter implements WhereElement{
	
	int right;
	Operators op;
	boolean ret;
	
	String left;
	String[] leftattr;
	
	int id;
	
	public WhereLetter() {
		if(left != null){
			leftattr = left.split("\\.");
		}
	}

	public boolean execute() {
		return ret;
		/*if(op == Operators.EQ){
			return left == right;
		}else if(op == Operators.GE){
			return left >= right;
		}else if(op == Operators.GT){
			return left > right;
		}else if(op == Operators.LE){
			return left <= right;
		}else if(op == Operators.LT){
			return left < right;
		}else if(op == Operators.NE){
			return left != right;
		}
		
		return false;*/
	}
	

	@Override
	public String toString() {
		return "" + id;
	}

	public WhereElement getLeftChild() {
		return null;
	}

	public WhereElement getRightChild() {
		return null;
	}

	public void setRightChild(WhereElement child) {
		
	}

	public void setLeftChild(WhereElement child) {
		
	}
}