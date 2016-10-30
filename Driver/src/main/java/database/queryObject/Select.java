package database.queryObject;

import java.util.ArrayList;
import java.util.List;
import database.InMemoryDatabase;
import datastructure.Instance;

public class Select implements IQueryObject{
	public From from;
	public Where where;
	public OrderBy orderby;
	public List<Instance> result;
	public InMemoryDatabase db;
	public int limit = -1;
	
	String selectObject;
	String[] selectAttributes;/*selectattributes[0] az mindig classname.*/
	
	public Select(InMemoryDatabase db, String selectObject) {
		this.db = db;
		
		this.selectObject = selectObject;
		selectAttributes = this.selectObject.split("\\.");/*mine.stone.location.x ezt itt táolja darabonként*/
		
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

	public OrderBy getOrderby() {
		return orderby;
	}

	public void setOrderby(OrderBy orderby) {
		this.orderby = orderby;
	}

	public List<Instance> getResult() {
		return result;
	}

	public void setResult(List<Instance> result) {
		this.result = result;
	}

	public List<Instance> execute(){
		if(limit > -1){
			/*Ha nincs limit a lekérdezésben, akkor az alapértelmezett értéke -1.*/
			List<Instance> executed;
			
			if(where != null){
				executed = orderby.execute(where.execute(from.execute(db)));
			}else{
				executed = orderby.execute(from.execute(db));
			}
			
			List<Instance> result = new ArrayList<Instance>();
			/*Itt az a lényeg, hogy csak annyi elemet rakunk bele a lekérdezés eredményébe,
			 amennyit a limit megenged.*/
			for(int i=0;i<executed.size();i++){
				if(i < limit){
					result.add(executed.get(i));
				}
			}
			
			if(this.selectAttributes.length > 1){
				return selectTheAttributes(result, this.selectAttributes);
			}else{
				return result;
			}
		}
		
		if(orderby != null){
			if(where != null){
				if(this.selectAttributes.length > 1){
					return selectTheAttributes(orderby.execute(where.execute(from.execute(db))), this.selectAttributes);
				}else{
					return orderby.execute(where.execute(from.execute(db)));
				}
			}else{
				if(this.selectAttributes.length > 1){
					return selectTheAttributes(orderby.execute(from.execute(db)), this.selectAttributes);
				}else{
					return orderby.execute(from.execute(db));	
				}
			}
		}else{
			if(where != null){
				if(this.selectAttributes.length > 1){
					return selectTheAttributes(where.execute(from.execute(db)), this.selectAttributes);
				}else{
					return where.execute(from.execute(db));
				}
				
			}else{
				if(this.selectAttributes.length > 1){
					return selectTheAttributes(from.execute(db), this.selectAttributes);
				}else{
					return from.execute(db);
				}
			}
		}
	}
	
	private List<Instance> selectTheAttributes(List<Instance> returned, String[] attributes){
		List<Instance> selectedResult = new ArrayList<Instance>();
		
		outer:for(int i=0;i<returned.size();i++){
			Instance root = returned.get(i);
			inner:for(int j=1;j<attributes.length;j++){
				if(root.hasThisAttribute(attributes[j]) || attributes[j].equals("id") || attributes.equals("className") ||
						attributes[j].equals("zindex") || attributes[j].equals("zlayer")){
					if(attributes[j].equals("id")){
						continue outer;
					}else if(attributes[j].equals("className")){
						continue outer;
					}else if(attributes[j].equals("zindex")){
						continue outer;
					}else if(attributes[j].equals("zlayer")){
						continue outer;
					}else{
						root = root.getAttribute(attributes[j]);
					}
				}else{
					continue outer;
				}
			}
			selectedResult.add(root);
		}
		return selectedResult;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
}
