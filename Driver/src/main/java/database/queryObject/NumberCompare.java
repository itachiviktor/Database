package database.queryObject;

public class NumberCompare {
	public static boolean compare(Number left, Number right, Operators operator){
		if(operator == Operators.EQ){
			return left.doubleValue() == right.doubleValue();
		}else if(operator == Operators.GE){
			return left.doubleValue() >= right.doubleValue();
		}else if(operator == Operators.GT){
			return left.doubleValue() > right.doubleValue();
		}else if(operator == Operators.LE){
			return left.doubleValue() <= right.doubleValue();
		}else if(operator == Operators.NE){
			return left.doubleValue() != right.doubleValue();
		}else if(operator == Operators.LT){
			return left.doubleValue() < right.doubleValue();
		}
		
		return false;
	}
}
