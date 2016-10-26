package database.queryObject.alter;

public interface IAlterType {
	public void execute(String className);
	public void setStringAttributeName(String attributeName); /*ez a metódus állítja be azt amire átnevezzük az osztályt,
	vagy adattagot, stb*/
	
	public void setStringOptionalValue(String value); /*ez csak az addatribute és renameattribute esetén van,
	ugyanis akkor meg kell adni, hogy melyik attribútumot szeretnénk a fenti metódusssal beállított értékre
	átnevezni, vagy létrehozni.*/
	
}
