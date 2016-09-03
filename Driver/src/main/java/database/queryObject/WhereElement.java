package database.queryObject;

import datastructure.Instance;

public interface WhereElement {
	
	public boolean execute();
	public WhereElement getLeftChild();
	public WhereElement getRightChild();
	public void setRightChild(WhereElement child);
	public void setLeftChild(WhereElement child);
	public void setCheckInstance(Instance instance);

}
