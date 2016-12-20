package maze;

public class MazePosition {
	public int x, y;
	
	
	public MazePosition(int x,int y){
		this.x = x;
		this.y = y;
		
	}
	
	public boolean equals(MazePosition second)
	{
		return x==second.x && y==second.y;
	}
	
	public String toString()
	{
		return this.x+" "+this.y;
	}
	
}
