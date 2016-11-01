package database.queryObject.insert;

public class InsertNode {
	private String value;
	private int moveToParentStack = 0;
	
	public InsertNode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getMoveToParentStack() {
		return moveToParentStack;
	}

	public void riseStackValue(){
		this.moveToParentStack++;
	}
	
	public void callMoveToParent(Insert insert){
		for(int i=0;i<this.moveToParentStack;i++){
			insert.moveToParent();
		}
	}
}