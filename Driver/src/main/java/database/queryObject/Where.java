package database.queryObject;

import java.util.ArrayList;
import java.util.List;

import datastructure.Instance;
import datastructure.TileMap;

public class Where {
	String bal;
	String jobb;
	String op;
	TileMap inst;
	
	WhereElement root;
	
	public Where() {
		inst = new TileMap();
	}
	
	public TileMap execute(TileMap list){
		TileMap result = new TileMap(list.getMapName());
		
		boolean validInstance = false;
		
		for(int i=0;i<list.size();i++){
			Integer id = list.get(i).id;
			
			
			
			
			
			/*
			if(op.equals("<")){
				if(id < Integer.parseInt(jobb)){
					inst.add(list.get(i));
				}
			}else if(op.equals(">")){
				if(id > Integer.parseInt(jobb)){
					inst.add(list.get(i));
				}
			}else{
				if(id == Integer.parseInt(jobb)){
					inst.add(list.get(i));
				}
			}*/
		}
		return result;
	}
}
