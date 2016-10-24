package kulsomodositolekerdezesek;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import database.InMemoryDatabase;
import database.collision.Collision;
import database.collision.DoublePoint;
import database.queryObject.create.AttributeDescriptor;
import database.queryObject.create.Class;
import database.queryObject.create.Create;
import database.queryObject.insert.TreeBuilder;
import database.queryObject.insert.Values;
import datastructure.InstanceMaker;

public class Inserteles {

	private static DoublePoint playerLocation;
	
	public static void main(String[] args) {
		Collision collision = new Collision();
		
		DoublePoint db = new DoublePoint(100, 100);
		Rectangle rectes = new Rectangle(0,0,20,20);
		List<DoublePoint> maybePoint = new ArrayList<DoublePoint>();
		
		List<Rectangle> tiles = new ArrayList<Rectangle>();
		tiles.add(new Rectangle(200, 200, 20, 20));
		tiles.add(new Rectangle(50, 50, 100, 100));
		
		maybePoint.add(db);
		int maybeIndex = 0;
		boolean finded = false;
		boolean findFree = true;
		
		while(!finded){
			findFree = true;
			for(int i=0;i<tiles.size();i++){
				playerLocation = collision.newLocation(rectes, tiles.get(i), maybePoint.get(maybeIndex));
				
				if(playerLocation != null){
					maybePoint.add(playerLocation);
					
					findFree = false;
				}
			}
			
			if(findFree){
				System.out.println(maybePoint.get(maybeIndex).getX());
				System.out.println(maybePoint.get(maybeIndex).getY());	
				
				finded = true;
			}
			maybeIndex++;
		}	

	}

}
