package datastructure;

public class InstanceProviderFromMap {
	
	private TileMap map;
	
	public InstanceProviderFromMap(TileMap map) {
		this.map = map;
	}
	
	public Instance provide(Integer id){
		/*Listában tárolnám a térképelemeket.Minden lista azon az indexen
		 szerepelne, ami az id-je.*/
		
		
		//return map.get(id);ezel a megoldással id-nek a listában való indexxel megkellett egyeznie.
		
		
		return map.getByIdValue(id);
	}
}
