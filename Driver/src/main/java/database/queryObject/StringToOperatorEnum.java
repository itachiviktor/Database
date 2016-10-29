package database.queryObject;

public class StringToOperatorEnum {
	
	public static Operators convertStringToEnum(String operator){
		if(operator.equals("<")){
			return Operators.LT;
		}else if(operator.equals("<=")){
			return Operators.LE;
		}else if(operator.equals(">")){
			return Operators.GT;
		}else if(operator.equals(">=")){
			return Operators.GE;
		}else if(operator.equals("=")){
			return Operators.EQ;
		}else if(operator.equals("<>")){
			return Operators.NE;
		}else if(operator.equalsIgnoreCase("is")){
			return Operators.IS;
		}else if(operator.equalsIgnoreCase("collide")){
			return Operators.COLLIDE;
		}else if(operator.equalsIgnoreCase("not")){
			return Operators.NOT;
		}
		
		return null;
	}
}
