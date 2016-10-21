package datastructure;

import java.util.List;
import java.util.Map;

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
	private ClassDefinition entity;
	
	public ClassDefinitionProvider(List<ClassDefinition> classes, List<TileMap> maps) {		
		string = new ClassDefinition("String", maps, classes);
		number = new ClassDefinition("Number", maps, classes);
		bool = new ClassDefinition("Boolean", maps, classes);
		
		point = new ClassDefinition("Point", maps, classes);
		point.getAttributes().put("x", "Number");
		point.getAttributes().put("y", "Number");
		
		size = new ClassDefinition("Size", maps, classes);
		size.getAttributes().put("width", "Number");
		size.getAttributes().put("height", "Number");
		
		vector = new ClassDefinition("Vector", maps, classes);
		vector.getAttributes().put("dx", "Number");
		vector.getAttributes().put("dy", "Number");
		
		rectangle = new ClassDefinition("Rectangle", maps, classes);
		rectangle.getAttributes().put("location", "Point");
		rectangle.getAttributes().put("size", "Size");
		
		position = new ClassDefinition("Position", maps, classes);
		position.getAttributes().put("location", "Point");
		position.getAttributes().put("direction", "Vector");
		
		
		node = new ClassDefinition("Node", maps, classes);
		node.getAttributes().put("location", "Point");
		node.getAttributes().put("direction", "Vector");
		node.getAttributes().put("next", "Node");
		node.getAttributes().put("isFirst", "Boolean");
		
		entity = new ClassDefinition("Entity", maps, classes);
		entity.getAttributes().put("x", "Number");
		entity.getAttributes().put("y", "Number");
		entity.getAttributes().put("width", "Number");
		entity.getAttributes().put("height", "Number");
		entity.getAttributes().put("image", "String");
		
		classes.add(string);
		classes.add(number);
		classes.add(bool);
		classes.add(point);
		classes.add(size);
		classes.add(vector);
		classes.add(rectangle);
		classes.add(position);
		classes.add(node);
		classes.add(entity);
	}
	
	/**
	 * This method can create and add a new Class definition to a List.
	 * */
	public void addClassDefinition(List<ClassDefinition> classes){
		/*Ezt még ki kell gondolni.*/
	}
}
