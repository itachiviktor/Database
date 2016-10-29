package database.queryObject.create;

import java.util.List;

import database.queryObject.IQueryObject;
import datastructure.Instance;

public class Create implements IQueryObject{
	private Executable exec;

	public List<Instance> execute() {
		exec.execute();
		return null;
	}

	public Executable getExec() {
		return exec;
	}

	public void setExec(Executable exec) {
		this.exec = exec;
	}
		
}