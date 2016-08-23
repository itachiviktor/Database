package datastructure;

import java.util.List;

public class InstanceMaker {
	public int id = 0;
	
	public StringPrimitiv makeString(String value, List<ClassDefinition> classes, List<Instance> terkep){
		StringPrimitiv szov = new StringPrimitiv("String", classes, terkep);
		szov.setAttribute(value);
		szov.id = this.id;
		
		id += 1;
		terkep.add(szov);
		
		return szov;
	}
	
	public NumberPrimitiv makeNumber(Number value, List<ClassDefinition> classes, List<Instance> terkep){
		NumberPrimitiv szov = new NumberPrimitiv("Number", classes, terkep);
		szov.setAttribute(value);
		szov.id = this.id;
		id += 1;
		terkep.add(szov);
		
		return szov;
	}
	
	public BooleanPrimitiv makeBoolean(Boolean value, List<ClassDefinition> classes, List<Instance> terkep){
		BooleanPrimitiv szov = new BooleanPrimitiv("Boolean", classes, terkep);
		szov.setAttribute(value);
		szov.id = this.id;
		id += 1;
		terkep.add(szov);
		
		return szov;
	}
	
	public Instance makeInstance(List<ClassDefinition> classes, List<Instance> terkep){
		/*Ennek kellene a többiféle obektumot legyártania*/
		Instance inst = new Instance("asd", classes, terkep);
		return null;
	}
}