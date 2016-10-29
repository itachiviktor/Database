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
	
	private List<String> attributeParameters;/*Ide jöne az attributumnév, típus, és a def érték*/
	
	public Class(String name, InMemoryDatabase memory) {
		this.name = name;
		this.memory = memory;
		
		this.attributes = new ArrayList<AttributeDescriptor>();
		this.attributeParameters = new ArrayList<String>();
		
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
	
	
	public void addAttributeParameter(String type){
		this.attributeParameters.add(type);
	}
	
	/**
	 * This method called, when the parser find , symbol.
	 * */
	public void insertAttribute(){
		AttributeDescriptor desc = new AttributeDescriptor();
		
		for(int i=0;i<this.attributeParameters.size();i++){
			if(i == 0){
				desc.attrType = this.attributeParameters.get(i);
			}else if(i == 1){
				desc.attrName = this.attributeParameters.get(i);
			}else if(i == 2){
				desc.defValue = this.attributeParameters.get(i);
			}
		}
		
		this.attributes.add(desc);
		this.attributeParameters.clear();
	}
}
