package datastructure;

import java.util.List;

public class InstanceProviderFromMap {
	
	List<Instance> terkep;
	
	public InstanceProviderFromMap(List<Instance> terkep) {
		this.terkep = terkep;
	}
	
	public Instance provide(Integer id){
		/*Listában tárolnám a térképelemeket.Minden lista azon az indexen
		 szerepelne, ami az id-je.*/
		return terkep.get(id);
		
	}
}
