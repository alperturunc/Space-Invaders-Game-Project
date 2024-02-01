
public enum Levels {
	Novice("Level 1",1),
	Intermediate("Level 2",2),
	Advance("Level 3",3);
	
	private String description;
	private int levelNumber;
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	public int getLevelNumber() {
		return levelNumber;
	}
	
	Levels(String string,int integer){
		setDescription(string);
		setLevelNumber(integer);
	}
	
	
	

}
