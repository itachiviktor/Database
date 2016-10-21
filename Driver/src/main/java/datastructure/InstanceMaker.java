package datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	
	public void refreshLayerTable(List<Instance> map){
		Map<Integer, Integer> layers = new HashMap<Integer, Integer>();
		
		for(int i=0;i<map.size();i++){
			if(layers.containsKey(map.get(i).zlayer)){
				/*ha van már ilyen layerú elem felvéve*/
				
				if(layers.get(map.get(i).zlayer) < map.get(i).zindex ){
					layers.replace(map.get(i).zlayer, map.get(i).zindex);
				}
			}else{
				/*ha még nincs benne, akkor beletesszük.*/
				layers.put(map.get(i).zlayer, map.get(i).zindex);
			}
		}
		
		for(int i=0;i<zlayer.length;i++){
			if(layers.containsKey(i)){
				zlayer[i] = layers.get(i);
			}else{
				zlayer[i] = 0;
			}
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