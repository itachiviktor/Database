package database.queryObject.create;

import database.InMemoryDatabase;
import database.LoadedDatabase;

public class CreateTest {

	public static void main(String[] args) {
		LoadedDatabase data = new LoadedDatabase();
		
		InMemoryDatabase asd = new InMemoryDatabase("db");
		
		for(int i=0;i<asd.getClasses().size();i++){
			System.out.println(asd.getClasses().get(i));
		}
		
		Create create = new Create();
		
		Database d = new Database("almakorteszilva");
		
		Map m = new Map("sw", asd);
		
		
		Class cl = new Class("Bomb", asd);
		AttributeDescriptor color = new AttributeDescriptor();
		color.attrName = "color";
		color.attrType = "String";
		//color.defValue = "piros";
		
		//cl.getAttributes().add(color);
		
		
		create.setExec(cl);
		create.execute();
		
		/*for(String x : asd.getMaps().keySet()){
			System.out.println(x);
			for(int i = 0;i<asd.getMaps().get(x).size();i++){
				System.out.println(asd.getMaps().get(x).get(i).toString());
			}
		}*/
		
		for(int i=0;i<asd.getMaps().size();i++){
			System.out.println(asd.getMaps().get(i).getMapName());
			for(int j=0;j<asd.getMaps().get(i).size();j++){
				System.out.println(asd.getMaps().get(i).get(j).toString());
			}
		}
		
		for(int i=0;i<asd.getClasses().size();i++){
			System.out.println(asd.getClasses().get(i).toString());
		}
		
		
		
		
		/*Database db = new Database();
		db.name = "alma";
		db.execute();*/

	}
	
}