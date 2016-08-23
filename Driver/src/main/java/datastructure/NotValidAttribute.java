package datastructure;

public class NotValidAttribute extends Exception{
	
	public NotValidAttribute(String attributeType, String type) {
		super("Excepted result type is " + type + " but gained " + attributeType);
	}
}
