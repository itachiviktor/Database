package database.queryObject;

import java.util.List;

import database.InMemoryDatabase;
import datastructure.Instance;

public class Operand {
	public String longOperand;
	
	public String[] attributes;
	public Boolean boolValue;
	public Number numberValue;
	public String stringValue;
	public String className;/*Mine,Stone,Line*/
	
	public Select select;
	public Boolean methodCall = false;
	public Select param1select;
	public Select param2select;
	public Number param1number;
	public Number param2number;
	
	public boolean isLeftOperand;
	
	public Instance memoryInstance;
	
	public Operand(String longOperand, boolean isLeftOperand) {
		this.isLeftOperand = isLeftOperand;
		
		if(longOperand.contains(".")){
			attributes = longOperand.split("\\.");
		}else{
			if(longOperand.equalsIgnoreCase("true") || longOperand.equalsIgnoreCase("false")){
				boolValue = Boolean.valueOf(longOperand);
			}else if(longOperand.matches("[0-9]+") || longOperand.matches("-[0-9]+")){
				numberValue = Integer.parseInt(longOperand);
			}else{
				stringValue = longOperand;
				className = longOperand;
			}
		}
	}
	
	public Operand(Select selectOperand, boolean isLeftOperand) {
		this.isLeftOperand = isLeftOperand;
		this.select = selectOperand;
	}
	
	public Operand(boolean isLeftOperand, InMemoryDatabase db, int... params){
		this.isLeftOperand = isLeftOperand;
		if(params.length == 2){
			this.memoryInstance = db.getMemoryMap().get(db.addMemoryPoint(params[0], params[1]));
		}else if(params.length == 4){
			this.memoryInstance = db.getMemoryMap().get(db.addMemoryRectangle(params[0], params[1], params[2], params[3]));
		}
	}
	
	/*Ha függvényhívás van az operandusban, akkor az alábbi 4 metódus közül legalább az egyiket meg kell hívni.*/
	public void setMethodParameters(Number num, Select sel){
		this.param1number = num;
		this.param2select = sel;
		this.methodCall = true;
	}
	
	public void setMethodParameters(Number num, Number num2){
		this.param1number = num;
		this.param2number = num2;
		this.methodCall = true;
	}
	
	public void setMethodParameters(Select sel, Number num){
		this.param2number = num;
		this.param1select = sel;
		this.methodCall = true;
	}
	
	public void setMethodParameters(Select sel1, Select sel2){
		this.param1select = sel1;
		this.param2select = sel2;
		this.methodCall = true;
	}
	
	
	public <T> T getOperand(Instance instance){
		if(this.methodCall){
			if(attributes != null){
				
				Instance actual = instance;
				boolean act;
				/*azért egytől indul a ciklus, mert mondjuk az első mine attribútum az csak szimbolikus*/
				for(int i=1;i<attributes.length;i++){
					act = actual.hasThisAttribute(attributes[i]);
					if(act){
						if(attributes[i].equals("id")){
							//return (T)actual.id;
						}else if(attributes[i].equals("zindex")){
							//return (T)actual.zindex;
						}else if(attributes[i].equals("zlayer")){
							//return (T)actual.zlayer;
						}else{
							actual = actual.getAttribute(attributes[i]);
						}
					}else{
						return null;/*Ha nullal tér vissza, akkor ez azt jelenti, hogy valamelyik has feltétel false*/
					}
				}
				if(param1number != null){
					if(param2number != null){
						return (T)actual.distanceFrom((Double)param1number, (Double)param2number);
					}else if(param2select != null){
						List<Instance> result = param2select.execute();
						
						return (T)actual.distanceFrom((Double)param1number, (Double)result.get(0).getValue());
					}
				}else{
					List<Instance> result1 = param1select.execute();
					if(param2number != null){
						return (T)actual.distanceFrom((Double)result1.get(0).getValue(), (Double)param2number);
					}else if(param2select != null){
						List<Instance> result2 = param2select.execute();
						return (T)actual.distanceFrom((Double)result1.get(0).getValue(), (Double)result2.get(0).getValue());
					}
				}
				
				
			}else{
			
					if(param1number != null){
						if(param2number != null){
							return (T)instance.distanceFrom((Double)param1number, (Double)param2number);
						}else if(param2select != null){
							List<Instance> result = param2select.execute();
							return (T)instance.distanceFrom((Double)param1number, (Double)result.get(0).getValue());
						}
					}else{
						List<Instance> result1 = param1select.execute();
						if(param2number != null){
							return (T)instance.distanceFrom((Double)result1.get(0).getValue(), (Double)param2number);
						}else if(param2select != null){
							List<Instance> result2 = param2select.execute();
							return (T)instance.distanceFrom((Double)result1.get(0).getValue(), (Double)result2.get(0).getValue());
						}
					}
				
				return null; /*Ilyenkor pl numbernak akarja a distanceFrom metódusát meghívni, ami neki nincs.*/
			}
		}
		
		if(select != null){
			List<Instance> inst = select.execute();
			if(inst.size() == 1){
				return (T)inst.get(0);
			}
		}
		
		if(attributes != null){
			Instance actual = instance;
			boolean act;
			/*azért egytől indul a ciklus, mert mondjuk az első mine attribútum az csak szimbolikus*/
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
			if(memoryInstance != null){
				return (T)memoryInstance;
			}else if(boolValue != null){
				return (T)boolValue;
			}else if(numberValue != null){
				return (T)numberValue;
			}else{
				/*StringValue != null*/
				if(isLeftOperand){
					/*ilyenkor csak mine van ott ezért az átadott Instance objektum kell oda*/
					return (T) instance;
				}else{
					return (T) className;
				}
			}
		}
	}
}
