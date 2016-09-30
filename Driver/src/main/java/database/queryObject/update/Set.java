package database.queryObject.update;

import java.util.ArrayList;
import java.util.List;

import datastructure.Instance;

public class Set {
	public List<String> attributes; /*Itt tárolom azokat az attribútumokat, amiket frissíteni kell*/
	public List<Object> values;
	
	public Set() {
		this.attributes = new ArrayList<String>();
		this.values = new ArrayList<Object>();
	}
	
	public void setAttribute(String attribute){
		attributes.add(attribute);
	}
	
	public void setValue(Object value){
		values.add(value);
	}
	
	public List<Instance> execute(List<Instance> instances){
		List<Instance> modified = new ArrayList<Instance>();
		
		instances:for(int i=0;i<instances.size();i++){
			attributes:for(int j=0;j<attributes.size();j++){
				if(values.get(j) instanceof Number){
					if(attributes.get(j).contains(".")){
						String[] attr = attributes.get(j).split("\\.");
						Instance inst = instances.get(i);
						
						for(int k=0;k<attr.length;k++){
							if(inst.hasThisAttribute(attr[k])){
								inst = inst.getAttribute(attr[k]);
							}else{
								continue attributes;/*ez azt jelenti, hogy x attribútuma van, de y
								nincs, akkor amelyik van azt beupdateli*/
							}
						}
						inst.setValue((Number)values.get(j));
					}else{
						if(instances.get(i).hasThisAttribute(attributes.get(j))){
							instances.get(i).getAttribute(attributes.get(j)).setValue((Number)values.get(j));
						}
					}
				}else if(values.get(j) instanceof String){
					if(attributes.get(j).contains(".")){
						String[] attr = attributes.get(j).split("\\.");
						Instance inst = instances.get(i);
						
						for(int k=0;k<attr.length;k++){
							if(inst.hasThisAttribute(attr[k])){
								inst = inst.getAttribute(attr[k]);
							}else{
								continue attributes;/*ez azt jelenti, hogy x attribútuma van, de y
								nincs, akkor amelyik van azt beupdateli*/
							}
						}
						inst.setValue((String)values.get(j));
					}else{
						if(instances.get(i).hasThisAttribute(attributes.get(j))){
							instances.get(i).getAttribute(attributes.get(j)).setValue((String)values.get(j));
						}
					}
				}else if(values.get(j) instanceof Boolean){
					if(attributes.get(j).contains(".")){
						String[] attr = attributes.get(j).split("\\.");
						Instance inst = instances.get(i);
						
						for(int k=0;k<attr.length;k++){
							if(inst.hasThisAttribute(attr[k])){
								inst = inst.getAttribute(attr[k]);
							}else{
								continue attributes;/*ez azt jelenti, hogy x attribútuma van, de y
								nincs, akkor amelyik van azt beupdateli*/
							}
						}
						inst.setValue((Boolean)values.get(j));
					}else{
						if(instances.get(i).hasThisAttribute(attributes.get(j))){
							instances.get(i).getAttribute(attributes.get(j)).setValue((Boolean)values.get(j));
						}
					}
				}
			}
		}
		
		
		/*Itt kellene a módosításokat kivitelezni.*/
		return null;
	}
}
