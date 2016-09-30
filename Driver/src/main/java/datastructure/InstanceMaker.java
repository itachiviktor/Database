package datastructure;

import java.util.List;

public class InstanceMaker {
	public int id;
	public int[] zlayer;
	
	public InstanceMaker() {
		zlayer = new int[100]; /*100 szint lehet. ebben a tömbben tárolom az adott szinten aktuális
		indexet, amit kilehet osztani.*/
		for(int i=0;i<zlayer.length;i++){
			zlayer[i] = 0;
		}
	}
	
	
	public StringPrimitiv makeString(String value, List<ClassDefinition> classes, TileMap map){
		StringPrimitiv szov = new StringPrimitiv("String", classes, map);
		szov.setAttribute(value);
		szov.id = this.id;
		szov.zindex = 0;
		szov.zlayer = 0;
		
		id += 1;

		map.add(szov);
		
		return szov;
	}
	
	public NumberPrimitiv makeNumber(Number value, List<ClassDefinition> classes, TileMap map){
		NumberPrimitiv szov = new NumberPrimitiv("Number", classes, map);
		szov.setAttribute(value);
		szov.id = this.id;
		szov.zindex = 0;
		szov.zlayer = 0;
		
		id += 1;
		map.add(szov);
		
		return szov;
	}
	
	public BooleanPrimitiv makeBoolean(Boolean value, List<ClassDefinition> classes, TileMap map){
		BooleanPrimitiv szov = new BooleanPrimitiv("Boolean", classes, map);
		szov.setAttribute(value);
		szov.id = this.id;
		szov.zindex = 0;
		szov.zlayer = 0;
		
		id += 1;
		map.add(szov);
		
		return szov;
	}
	
	public Instance makeInstance(List<ClassDefinition> classes, TileMap map){
		/*Ennek kellene a többiféle obektumot legyártania*/
		Instance inst = new Instance("asd", classes, map);
		return null;
	}
}