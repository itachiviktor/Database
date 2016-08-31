package database.queryObject.create;

public class Create implements Executable{
	private Executable exec;

	public void execute() {
		exec.execute();
	}

	public Executable getExec() {
		return exec;
	}

	public void setExec(Executable exec) {
		this.exec = exec;
	}
		
}