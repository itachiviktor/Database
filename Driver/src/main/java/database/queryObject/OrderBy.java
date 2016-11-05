package database.queryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import datastructure.Instance;
import datastructure.NumberPrimitiv;
import datastructure.StringPrimitiv;

public class OrderBy {
	/*desc-csökken*/
	/*ascend-felmegy*/
	/*asc a default*/
	private static final Comparator<Instance> descendingByName = new Comparator<Instance>() {
		public int compare(Instance o1, Instance o2) {
			StringPrimitiv s1 = (StringPrimitiv) o1;
			StringPrimitiv s2 = (StringPrimitiv) o2;
			String szov1 = (String)o1.getValue();
			String szov2 = (String)o2.getValue();
			
			return szov1.compareTo(szov2);
		};
	};
	
	private static final Comparator<Instance> ascendingByName = new Comparator<Instance>() {
		public int compare(Instance o1, Instance o2) {
			StringPrimitiv s1 = (StringPrimitiv) o1;
			StringPrimitiv s2 = (StringPrimitiv) o2;
			String szov1 = (String)o1.getValue();
			String szov2 = (String)o2.getValue();
			
			return szov1.compareTo(szov2) * -1;
		};
	};
	
	private static final Comparator<Instance> descendingByNumber = new Comparator<Instance>() {
		public int compare(Instance o1, Instance o2) {
			NumberPrimitiv s1 = (NumberPrimitiv) o1;
			NumberPrimitiv s2 = (NumberPrimitiv) o2;
			Number num1 = (Number)o1.getValue();
			Number num2 = (Number)o2.getValue();
			Double doublenum1 = num1.doubleValue();
			Double doublenum2 = num2.doubleValue();
			
			return doublenum1.compareTo(doublenum2);
		};
	};
	
	private static final Comparator<Instance> ascendingByNumber = new Comparator<Instance>() {
		public int compare(Instance o1, Instance o2) {
			NumberPrimitiv s1 = (NumberPrimitiv) o1;
			NumberPrimitiv s2 = (NumberPrimitiv) o2;
			Number num1 = (Number)o1.getValue();
			Number num2 = (Number)o2.getValue();
			Double doublenum1 = num1.doubleValue();
			Double doublenum2 = num2.doubleValue();
			
			return doublenum1.compareTo(doublenum2) * -1;
		};
	};
	
	private List<Instance> instances;
	private String attributeNameBySorting;
	private String[] attributes;
	private OrderBySort sort;
	
	public OrderBy(String attributeString) {
		if(attributeString.contains(".")){
			attributes = attributeString.split("\\.");
		}
		sort = OrderBySort.ASC;
	}
	
	public OrderBy(String attributeString, OrderBySort sort) {
		if(attributeString.contains(".")){
			attributes = attributeString.split("\\.");
		}
		this.sort = sort;
	}
	
	public void setSort(OrderBySort sort){
		this.sort = sort;
	}
	
	public List<Instance> execute(List<Instance> where){
		
		List<Instance>resultList = new ArrayList<Instance>();
		int min = 0;
		while(where.size() > 0){
			min = 0;
			outer:for(int j=0;j<where.size();j++){
				
				if(attributes != null){
					/*ez azt jelenti, hogy nem csak egy szimpla mine van ott*/
					Instance one = where.get(min);
					Instance two = where.get(j);
					boolean fail = false;
					inner:for(int k = 1;k<attributes.length;k++){
						/*itt az attibútumokon ugrok végig*/
						if(attributes[k].equals("id")){
						
							if(sort == OrderBySort.ASC){
								if(fail == false && one.id.compareTo(two.id) == 1){
									min = j;
								}
							}else{
								if(fail == false && one.id.compareTo(two.id) == -1){
									min = j;
								}
							}
							continue outer;
						}else if(attributes[k].equals("zindex")){
							if(sort == OrderBySort.ASC){
								if(fail == false && one.zindex.compareTo(two.zindex) == 1){
									min = j;
								}
							}else{
								if(fail == false && one.zindex.compareTo(two.zindex) == -1){
									min = j;
								}
							}
							continue outer;
						}else if(attributes[k].equals("zlayer")){
							if(sort == OrderBySort.ASC){
								if(fail == false && one.zlayer.compareTo(two.zlayer) == 1){
									min = j;
								}
							}else{
								if(fail == false && one.zlayer.compareTo(two.zlayer) == -1){
									min = j;
								}
							}
							continue outer;
						}else{
							one = one.getAttribute(attributes[k]);
							two = two.getAttribute(attributes[k]);
						}
						
						
						if(one == null || two == null){
							fail = true;
							break;
						}
					}
					
					if(sort == OrderBySort.ASC){
						if(one == null && two != null){
							min = j;
							continue outer;
						}
						
						if(fail == false && one.compareTo(two) == 1){
							min = j;
						}
					}else{
						if(one == null && two != null){
							min = j;
							continue outer;
						}
						
						if(fail == false && one.compareTo(two) == -1){
							min = j;
						}
					}
					
				}else{
					if(sort == OrderBySort.ASC){
						if(where.get(min).compareTo(where.get(j)) == -1){
							min = j;
						}
					}else{
						if(where.get(min).compareTo(where.get(j)) == 1){
							min = j;
						}
					}
				}
			}

			resultList.add(where.get(min));
			where.remove(min);
		}
		return resultList;
	}
	
}
