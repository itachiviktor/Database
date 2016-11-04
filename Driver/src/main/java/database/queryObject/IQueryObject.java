package database.queryObject;

import java.util.List;

import datastructure.Instance;

public interface IQueryObject {
	public List<Instance> execute();
	public Where getWhere();
}
