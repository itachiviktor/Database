package database.queryObject;

import java.util.ArrayList;
import java.util.List;

import database.InMemoryDatabase;
import datastructure.Instance;

public class WhereLetter implements WhereElement{
	
	int right;
	Operators operator;
	boolean ret;
	
	Instance leftInstanceValue;
	Boolean leftBoolValue;
	Number leftNumberValue;
	
	Instance rightInstanceValue;
	Boolean rightBoolValue;
	Number rightNumberValue;
	
	String rightOperandClassName;
	
	public Operand leftOperand;
	public Operand rightOperand;
	
	public Instance selectedInstance;
	
	int id;
	
	
	public WhereLetter(Operand leftOperand, Operand rightOperand, Operators operator) {
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
		this.operator = operator;
		
	}
	
	
	public WhereLetter() {
		// TODO Auto-generated constructor stub
	}
	
	public void setSelectedInstance(Instance inst){
		this.selectedInstance = inst;

		leftInstanceValue = null;
		leftBoolValue = null;
		leftNumberValue = null;
		
		rightInstanceValue = null;
		rightBoolValue = null;
		rightNumberValue = null;
		
		if(leftOperand.getOperand(inst) instanceof Boolean){
			leftBoolValue = leftOperand.getOperand(inst);
		}else if(leftOperand.getOperand(inst) instanceof Number){
			leftNumberValue = leftOperand.getOperand(inst);
		}else if(leftOperand.getOperand(inst) instanceof Instance){
			leftInstanceValue = leftOperand.getOperand(inst);
		}
		
		if(rightOperand.getOperand(inst) instanceof Boolean){
			rightBoolValue = rightOperand.getOperand(inst);
		}else if(rightOperand.getOperand(inst) instanceof Number){
			rightNumberValue = rightOperand.getOperand(inst);
		}else if(rightOperand.getOperand(inst) instanceof Instance){
			rightInstanceValue = rightOperand.getOperand(inst);
		}
	}
	
	
	public boolean execute() {
		/*itt kell kiszámolni*/
		
		if(leftInstanceValue != null && rightInstanceValue != null){
			return leftInstanceValue.operate(rightInstanceValue, operator);
		}
		
		
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