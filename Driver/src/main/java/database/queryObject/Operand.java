package database.queryObject;

import java.util.List;

import datastructure.Instance;

public class Operand {
	public String longOperand;
	
	public String[] attributes;
	public Boolean boolValue;
	public Number numberValue;
	public String className;
	
	public Select select;
	
	public boolean isLeftOperand;
	
	public Operand(String longOperand, boolean isLeftOperand) {
		this.isLeftOperand = isLeftOperand;
		
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
	
	public Operand(Select selectOperand, boolean isLeftOperand) {
		this.isLeftOperand = isLeftOperand;
		this.select = selectOperand;
	}
	
	public <T> T getOperand(Instance instance){
		if(select != null){
			List<Instance> inst = select.execute();
			if(inst.size() == 1){
				return (T)inst.get(0);
			}
		}
		
		if(attributes != null){
			Instance actual = instance;
			boolean act;
			for(int i=1;i<attributes.length;i++){
				act = actual.hasThisAttribute(attributes[i]);
				if(act){
					if(attributes[i].equals("id")){
						return (T)actual.id;
					}else if(attributes[i].equals("zindex")){
						return (T)actual.zindex;
					}else if(attributes[i].equals("zlayer")){
						return (T)actual.zlayer;
					}else{
						actual = actual.getAttribute(attributes[i]);
					}
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
				if(isLeftOperand){
					return (T) instance;
				}else{
					return (T) className;
				}
			}
		}
	}
}
