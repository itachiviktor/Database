package database.queryObject.delete;

import java.util.List;

import database.InMemoryDatabase;
import database.LoadedDatabase;
import database.queryObject.From;
import database.queryObject.Where;
import datastructure.Instance;

public class Delete {
	public From from;
	public Where where;
	public InMemoryDatabase db;
	
	String[] selectAttributes;/*selectattributes[0] az mindig classname.*/
	
	public Delete(InMemoryDatabase db, String selectObject) {
		this.db = db;
		selectAttributes = selectObject.split("\\.");/*mine.stone.location.x ezt itt táolja darabonként*/
	}
	
	public void execute(){
		List<Instance> deletableObjects = where.execute(from.execute(db));
		List<Instance> database = db.getMapByName(from.map).getMap();
		for(int i=0;i<database.size();i++){
			for(int j=0;j<deletableObjects.size();j++){
				if(database.get(i) == deletableObjects.get(j)){
					database.get(i).delete(database);
					
					/*Itt jön az, hogyha törlés volt, akkor az indexeket a lyukas helyekre kell betolni.*/
					for(int k=0;k<database.size();k++){
						if(database.get(k).id != k){//Ha az idje nem egyenlő az indexével a listában
							for(int l=0;l<database.size();l++){
								for(String x : database.get(l).attributes.keySet()){
									if(database.get(l).attributes.get(x) == database.get(k).id){
										database.get(l).attributes.replace(x, k);
									}
								}
							}
							database.get(k).id = k;
						}
					}
				}
			}
		}
	}
	
	public From getFrom() {
		return from;
	}


	public void setFrom(From from) {
		this.from = from;
	}


	public Where getWhere() {
		return where;
	}


	public void setWhere(Where where) {
		this.where = where;
	}
	
}
