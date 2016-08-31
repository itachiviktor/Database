package database.queryObject.create;

import java.util.ArrayList;
import java.util.List;
import database.InMemoryDatabase;
import datastructure.ClassDefinition;

public class Class implements Executable{
	private String name;
	private ClassDefinition classdefi;
	
	private List<AttributeDescriptor> attributes;
	
	private InMemoryDatabase memory;
	
	public Class(String name, InMemoryDatabase memory) {
		this.name = name;
		this.memory = memory;
		
		this.attributes = new ArrayList<AttributeDescriptor>();
		
	}
	
	public void execute(){
		classdefi = new ClassDefinition(name, memory.getMaps(), memory.getClasses());
		
		for(int i=0;i<attributes.size();i++){
			classdefi.setAttribute(attributes.get(i).attrName, attributes.get(i).attrType);
			if(attributes.get(i).defValue != null){
				//classdefi.defaultValues.put(attributes.get(i).attrName, attributes.get(i).defValueS);
				
				classdefi.setAttributeDefaultValue(attributes.get(i).attrName, attributes.get(i).defValue);
			}
			//classdefi.getAttributes().put(attributes.get(i).attrName, attributes.get(i).attrType);
		}
		
		memory.getClasses().add(classdefi);
	}
	
	public List<AttributeDescriptor> getAttributes() {
		return attributes;
	}
}
