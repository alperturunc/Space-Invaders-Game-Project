
public abstract class SpaceObject implements SpaceObjectMethods{
	private int objectX;
	private int objectY;
	
	
	public SpaceObject() {
		
	}
	public SpaceObject(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public int getX() {
		return objectX;
	}
	public void setX(int x) {
		this.objectX = x;
	}
	public void setY(int y) {
		this.objectY = y;
	}
	public int getY() {
		return objectY;
	}
	

}
