package database.queryObject.update;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import database.InMemoryDatabase;
import database.collision.Collision;
import database.collision.DoublePoint;
import datastructure.Instance;

public class Move {
	
	private Collision collision;
	private Point newLocationPoint;/*erre a pontra akar mozogni az entitással.*/
	
	private DoublePoint playerLocation;
	
	public Move() {
		this.collision = new Collision();
		this.newLocationPoint = new Point();
	}
	
	
	
	public List<Instance> execute(List<Instance> whereInstances, List<Instance> mapInstances){
		
		for(int i=0;i<whereInstances.size();i++){
			Instance inst = whereInstances.get(i);
			if(inst.hasThisAttribute("x") && inst.hasThisAttribute("y") && inst.hasThisAttribute("width") &&
					inst.hasThisAttribute("height")){
				
				List<Instance> tiles = new ArrayList<Instance>();/*ebbe a listába belerakom azokat az instanmceokat ,
				amikkel ütközhet.*/
				
				
				/*itt végig kell iterálni az összes pályaelemen, ami ráadásul ezen a layeren van, és solid.*/
				for(int j=0;j<mapInstances.size();j++){
					if(mapInstances.get(j).hasThisAttribute("x") && mapInstances.get(j).hasThisAttribute("y") &&
							mapInstances.get(j).hasThisAttribute("width") &&
							mapInstances.get(j).hasThisAttribute("height") && mapInstances.get(j).zlayer == inst.zlayer
							&& mapInstances.get(j).id != inst.id && mapInstances.get(j).hasThisAttribute("solid") &&
							(Boolean)mapInstances.get(j).getAttribute("solid").getValue()){
						
						tiles.add(mapInstances.get(j));
					}
					
				}
				
				List<Rectangle> tilesRectangle = new ArrayList<Rectangle>();
				/*Ebben a listában van benne minden pályaelem amivel ütközhet, kivéve saját maga.*/
				for(int j=0;j<tiles.size();j++){
					tilesRectangle.add(new Rectangle((Integer)tiles.get(j).getAttribute("x").getValue(), 
							(Integer)tiles.get(j).getAttribute("y").getValue(),
							(Integer)tiles.get(j).getAttribute("width").getValue(), 
							(Integer)tiles.get(j).getAttribute("height").getValue()));
				}
				
				
				DoublePoint db = new DoublePoint(this.newLocationPoint.x, this.newLocationPoint.y);
				Rectangle rectes = new Rectangle((Integer)inst.getAttribute("x").getValue(), 
						(Integer)inst.getAttribute("y").getValue(), (Integer)inst.getAttribute("width").getValue(),
						(Integer)inst.getAttribute("height").getValue());

				List<DoublePoint> maybePoint = new ArrayList<DoublePoint>();
				
				maybePoint.add(db);
				int maybeIndex = 0;
				boolean finded = false;
				boolean findFree = true;
				
				while(!finded){
					
					findFree = true;
					for(int k=0;k<tilesRectangle.size();k++){
						playerLocation = collision.newLocation(rectes, tilesRectangle.get(k), maybePoint.get(maybeIndex));
						if(playerLocation != null){
							maybePoint.add(playerLocation);
							
							findFree = false;
						}
					}
					
					if(findFree){
						inst.getAttribute("x").setValue(maybePoint.get(maybeIndex).getX());
						inst.getAttribute("y").setValue(maybePoint.get(maybeIndex).getY());
						System.out.println(maybePoint.get(maybeIndex).getX());
						System.out.println(maybePoint.get(maybeIndex).getY());
						
						finded = true;
					}
					maybeIndex++;
				}
				
				
			}
		}
		
		return null;
	}



	public Point getNewLocationPoint() {
		return newLocationPoint;
	}



	public void setNewLocationPoint(Point newLocationPoint) {
		this.newLocationPoint = newLocationPoint;
	}
	
	
}
