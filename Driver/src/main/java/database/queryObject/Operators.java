package database.queryObject;

public enum Operators {
	OR,AND,EQ,LT,GT,LE,GE,NE,NOT,COLLIDE,CLOSEST,IS;

	@Override
	public String toString() {
		if(this == Operators.OR){
			return "or";
		}else if(this == Operators.AND){
			return "and";
		}else if(this == Operators.EQ){
			return "eq";
		}else if(this == Operators.LT){
			return "lt";
		}else if(this == Operators.GT){
			return "gt";
		}else if(this == Operators.LE){
			return "le";
		}else if(this == Operators.GE){
			return "ge";
		}else if(this == Operators.NE){
			return "ne";
		}else if(this == Operators.NOT){
			return "not";
		}else if(this == Operators.COLLIDE){
			return "collide";
		}else if(this == Operators.IS){
			return "is";
		}else{
			return "something else";
		}
	}
	
	
}
