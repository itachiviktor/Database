package datastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class represents a List which contains Instance objects.Simply this is a Map.
 * A Database contains a List of TileMap.When the user execute a CREATE MAP query, then create an object 
 * from this class.
 * */
public class TileMap {
	private List<Instance> map;
	private InstanceMaker maker;/*Ebben tartom nyilván, hogy ehhez a maphez hanayadik id-t lehet kiosztani, és a
	zindex, zlayerek is mapenként eltérőek.*/
	private String mapName;
	
	
	public TileMap(String mapName) {
		this.mapName = mapName;
		map = new ArrayList<Instance>();
		maker = new InstanceMaker();
	}
	
	public TileMap() {
		map = new ArrayList<Instance>();
		maker = new InstanceMaker();
	}
	
	public void add(Instance inst){
		map.add(inst);
	}
	
	public void remove(int index){
		map.remove(index);
	}
	
	public void remove(Object obj){
		map.remove(obj);
	}
	
	public int size(){
		return map.size();
	}
	
	public Instance get(int index){
		return map.get(index);
	}
	
	public boolean isEmpty(){
		return map.isEmpty();
	}
	
	public boolean contains(Object obj){
		return map.contains(obj);
	}
	
	public void addAll(Collection<? extends Instance> c){
		map.addAll(c);
	}
	
	


	public List<Instance> getMap() {
		return map;
	}


	public void setMap(List<Instance> map) {
		this.map = map;
	}


	public InstanceMaker getMaker() {
		return maker;
	}


	public void setMaker(InstanceMaker maker) {
		this.maker = maker;
	}


	public String getMapName() {
		return mapName;
	}


	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	
	
}
