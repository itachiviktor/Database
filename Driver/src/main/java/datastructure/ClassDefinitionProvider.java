package datastructure;

import java.util.List;

/**
 * This class is a Service. By using this class, user can make a class 
 * definition, and in the constructor this class defines the basic 
 * data structures. 
 */
public class ClassDefinitionProvider {
	
	private ClassDefinition string;
	private ClassDefinition number;
	private ClassDefinition bool;
	private ClassDefinition point;
	private ClassDefinition size;
	private ClassDefinition vector;
	private ClassDefinition rectangle;
	private ClassDefinition position;
	private ClassDefinition node;
	
	public ClassDefinitionProvider(List<ClassDefinition> classes) {		
		string = new ClassDefinition("String");
		number = new ClassDefinition("Number");
		bool = new ClassDefinition("Boolean");
		
		point = new ClassDefinition("Point");
		point.getAttributes().put("x", "Number");
		point.getAttributes().put("y", "Number");
		
		size = new ClassDefinition("Size");
		size.getAttributes().put("width", "Number");
		size.getAttributes().put("height", "Number");
		
		vector = new ClassDefinition("Vector");
		vector.getAttributes().put("dx", "Number");
		vector.getAttributes().put("dy", "Number");
		
		rectangle = new ClassDefinition("Rectangle");
		rectangle.getAttributes().put("location", "Point");
		rectangle.getAttributes().put("size", "Size");
		
		position = new ClassDefinition("Position");
		position.getAttributes().put("location", "Point");
		position.getAttributes().put("direction", "Vector");
		
		
		node = new ClassDefinition("Node");
		node.getAttributes().put("location", "Point");
		node.getAttributes().put("direction", "Vector");
		node.getAttributes().put("next", "Node");
		node.getAttributes().put("isFirst", "Boolean");
		
		
		classes.add(string);
		classes.add(number);
		classes.add(bool);
		classes.add(point);
		classes.add(size);
		classes.add(vector);
		classes.add(rectangle);
		classes.add(position);
		classes.add(node);
	}
	
	/**
	 * This method can create and add a new Class definition to a List.
	 * */
	public void addClassDefinition(List<ClassDefinition> classes){
		/*Ezt még ki kell gondolni.*/
	}
}
