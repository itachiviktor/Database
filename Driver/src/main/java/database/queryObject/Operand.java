package database.queryObject;

import datastructure.Instance;

public class Operand {
	String longOperand;
	
	String[] attributes;
	Boolean boolValue;
	Number numberValue;
	String className;
	
	public Operand(String longOperand) {
		if(longOperand.contains(".")){
			attributes = longOperand.split("\\.");
		}else{
			if(longOperand.equalsIgnoreCase("true") || longOperand.equalsIgnoreCase("false")){
				boolValue = Boolean.valueOf(longOperand);
			}else if(longOperand.matches("[0-9]+")){
				numberValue = Integer.parseInt(longOperand);
			}else{
				className = longOperand;
			}
		}
	}
	
	public <T> T getOperand(Instance instance){
		if(attributes != null){
			Instance actual = instance;
			boolean act;
			for(int i=1;i<attributes.length;i++){
				act = actual.hasThisAttribute(attributes[i]);
				if(act){
					actual = actual.getAttribute(attributes[i]);
				}else{
					return null;/*Ha nullal tér vissza, akkor ez azt jelenti, hogy valamelyik has feltétel false*/
				}
			}
			return (T)actual;
		}else{
			if(boolValue != null){
				return (T)boolValue;
			}else if(numberValue != null){
				return (T)numberValue;
			}else{
				return (T) className;
			}
		}
	}
}
