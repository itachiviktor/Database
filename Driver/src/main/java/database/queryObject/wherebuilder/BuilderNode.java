package database.queryObject.wherebuilder;

import database.queryObject.Operators;
import database.queryObject.WhereLetter;
import database.queryObject.WhereNode;

public class BuilderNode {
	private int deep;
	private Boolean side;
	private WhereLetter operand;/*a kettő közül az egyik biztosan null értéket fog felvenni.*/
	private Operators operator;
	
	private BuilderNode leftChild;
	private BuilderNode rightChild;
	
	private WhereNode node;/*Ez csak csomópont elemeknek lesz*/
	
	public BuilderNode(int deep, Boolean side, WhereLetter operand, Operators operator) {
		super();
		this.deep = deep;
		this.side = side;
		this.operand = operand;
		this.operator = operator;
	}
	
	public int getDeep() {
		return deep;
	}
	public void setDeep(int deep) {
		this.deep = deep;
	}
	public Boolean getSide() {
		return side;
	}
	public void setSide(Boolean side) {
		this.side = side;
	}
	
	public WhereLetter getOperand() {
		return operand;
	}

	public void setOperand(WhereLetter operand) {
		this.operand = operand;
	}

	public Operators getOperator() {
		return operator;
	}
	public void setOperator(Operators operator) {
		this.operator = operator;
	}

	public BuilderNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(BuilderNode leftChild) {
		this.leftChild = leftChild;
	}

	public BuilderNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(BuilderNode rightChild) {
		this.rightChild = rightChild;
	}
	
	
	
	/*Ez a metódus csak azoknál hívható, amelyik csomópont, nem pedig levelek.*/
	public WhereNode getNode() {
		return node;
	}

	public void setNode(WhereNode node) {
		this.node = node;
	}

	public void buildMyself(){
		if(leftChild != null && rightChild != null){
			if(leftChild.operand == null && rightChild.operand == null){
				this.node = new WhereNode(leftChild.getNode(), rightChild.getNode());
			}else if(leftChild.operator == null && rightChild.operator == null){
				this.node = new WhereNode(leftChild.operand, rightChild.operand);
			}else if(leftChild.operand == null && rightChild.operand != null){
				this.node = new WhereNode(leftChild.getNode(), rightChild.operand);
			}else if(leftChild.operand != null && rightChild.operand == null){
				this.node = new WhereNode(leftChild.operand, rightChild.getNode());
			}
		
			this.node.setOperator(this.operator);
		}
	}
	
	
	@Override
	public String toString() {
		return "deep: " + this.deep + " side: " + this.side; 
	}
}