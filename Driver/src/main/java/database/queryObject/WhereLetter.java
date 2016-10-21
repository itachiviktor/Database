package database.queryObject;

import java.util.ArrayList;
import java.util.List;

import database.InMemoryDatabase;
import datastructure.Instance;

public class WhereLetter implements WhereElement{
	
	public Operators operator;
	
	public Instance leftInstanceValue;
	public Boolean leftBoolValue;
	public Number leftNumberValue;
	
	public Instance rightInstanceValue;
	public Boolean rightBoolValue;
	public Number rightNumberValue;
	
	public String leftOperandClassName;
	public String rightOperandClassName;
	
	public Operand leftOperand;
	public Operand rightOperand;
	
	public Instance selectedInstance;
	
	public WhereLetter(Operand leftOperand, Operand rightOperand, Operators operator) {
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
		this.operator = operator;
		
	}
	
	
	public WhereLetter() {
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean execute() {
		/*itt kell kiszámolni*/
		
		if(leftInstanceValue != null){
			if(rightInstanceValue != null){
				return leftInstanceValue.operate(rightInstanceValue, operator);
			}else if(rightBoolValue != null){
				return leftInstanceValue.operate(rightBoolValue, operator);
			}else if(rightNumberValue != null){
				return leftInstanceValue.operate(rightNumberValue, operator);
			}else if(rightOperandClassName != null){
				return leftInstanceValue.isOperator(rightOperandClassName);
			}
		}else if(leftBoolValue != null){
			if(rightBoolValue != null){
				if(operator == Operators.EQ){
					return rightBoolValue == leftBoolValue;
				}
			}else if(rightInstanceValue != null){
				return rightInstanceValue.operate(leftBoolValue, operator);
			}
		}else if(leftNumberValue != null){
			if(rightNumberValue != null){
				return NumberCompare.compare(leftNumberValue, rightNumberValue, operator);
			}else if(rightInstanceValue != null){
				return NumberCompare.compare(leftNumberValue, (Number)rightInstanceValue.getValue(), operator);
			}
		}
		//throw new NumberFormatException();
		
		return false;	
		
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


	
	public void setCheckInstance(Instance instance) {
		this.selectedInstance = instance;

		leftInstanceValue = null;
		leftBoolValue = null;
		leftNumberValue = null;
		leftOperandClassName = null;
		
		rightInstanceValue = null;
		rightBoolValue = null;
		rightNumberValue = null;
		rightOperandClassName = null;
		
		if(leftOperand.getOperand(instance) instanceof Boolean){
			leftBoolValue = leftOperand.getOperand(instance);
		}else if(leftOperand.getOperand(instance) instanceof Number){
			leftNumberValue = leftOperand.getOperand(instance);
		}else if(leftOperand.getOperand(instance) instanceof Instance){
			leftInstanceValue = leftOperand.getOperand(instance);
		}else if(leftOperand.getOperand(instance) instanceof String){
			leftOperandClassName = leftOperand.getOperand(instance);
		}
		
		if(rightOperand.getOperand(instance) instanceof Boolean){
			rightBoolValue = rightOperand.getOperand(instance);
		}else if(rightOperand.getOperand(instance) instanceof Number){
			rightNumberValue = rightOperand.getOperand(instance);
		}else if(rightOperand.getOperand(instance) instanceof Instance){
			rightInstanceValue = rightOperand.getOperand(instance);
		}else if(rightOperand.getOperand(instance) instanceof String){
			rightOperandClassName = rightOperand.getOperand(instance);
		}
	}
}