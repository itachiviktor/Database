package datastructure;

public class AttributeChecker {
	public static boolean existAttribute(Instance object, String attributeName){
		if(attributeName.equals("id") || attributeName.equals("className") || attributeName.equals("zindex")
				|| attributeName.equals("zlayer")){
			return true;
		}else{
			for(String x : object.classType.getAttributes().keySet()){
				if(attributeName.equals(x)){
					return true;
				}
			}
		}
		
		return false;
	}
}
