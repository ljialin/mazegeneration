package maze;

public class Cell {
	public int x, y;
	
	
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		
	}
	
	public boolean equals(Cell second)
	{
		return x==second.x && y==second.y;
	}
	
	public String toString()
	{
		return this.x+" "+this.y;
	}
	
}
