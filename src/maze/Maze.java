package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Jialin Liu on 08/12/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public abstract class Maze {
  protected int[][] maze;

  public Maze(int[][] maze) {
    this.maze = maze;
  }

  public Maze(int dimX, int dimY) {
    this.maze = new int[dimX][dimY];
  }

  public Maze(int dim) {
    this.maze = new int[dim][dim];
  }

  public abstract void plot();

  protected abstract int getWidth();

  protected abstract int getHeight();
  
  public String toString(){
	  
	  String st = "";
	  for(int i=0;i<maze.length;i++)
	  {
		  for(int j=0;j<maze[0].length;j++)
		  {
			  if(maze[i][j]==0)
			  st += "0";
			  else
				  st+=" ";
		  }
		  st += "\n";
	  }
	  return st;
  }
  
  
  /*
   * 
   * http://cs.fit.edu/~ryan/java/programs/graph/Dijkstra-java.html
   */
  public int[][] calShortestPath(int x1, int y1)
  {
	  int[][] distance = new int[maze.length][maze[0].length];
	  boolean[][] visited = new boolean[maze.length][maze[0].length];
	  
	  for(int i=0;i<distance.length;i++)
		  for(int j=0;j<distance[0].length;j++)
		  {
			  distance[i][j] = 10000;
			  visited[i][j] = false;
			  
//			  if(maze[i][j]==0)
//				  visited[i][j] = true;
		  }
	  
//	  visited[x1][y1] = true;
	  distance[x1][y1] = 0;
	  
	  for(int i=0;i<maze.length;i++)
	  {
		  for(int j=0;j<maze[0].length;j++)
		  {
			  MazePosition next = minPos(distance,visited);
			  if(next==null)
				  continue;
			  
			  visited[next.x][next.y] = true;
			  
			 // System.out.println(next);
			  
			  List<MazePosition> neighbors = getUnvisitedPos(next.x,next.y,visited);
			  
	//		  System.out.println(neighbors.size());
			  for(MazePosition n : neighbors)
			  {
			//	  System.out.print(n+": ");
//			//	  System.out.println(distance[next.x][next.y]);
				  distance[n.x][n.y] = distance[next.x][next.y]+1;
				  //System.out.println(n);
				  int dist = distance[n.x][n.y] + 1; //weight = 1;
				  
				  if(distance[n.x][n.y]>dist)
				  {
					  distance[n.x][n.y] = dist;
					  
//					  if(maze[i][j] == 0)
//						  distance[i][j] = 10000;
				  }
			  }
		  }
	  }
	  
	  return distance;
  }
  
  private MazePosition minPos(int[][] dist, boolean[][] visited)
  {
	  int x = 10000;
	  
	  MazePosition minPos = null;
	  
	  for(int i=0;i<dist.length;i++)
		  for(int j=0;j<dist[0].length;j++)
		  {
			  if(!visited[i][j] && dist[i][j]<x)
			  {
				  minPos = new MazePosition(i,j);
	//			  System.out.println(minPos);
				  x = dist[i][j];
			  }
		  }
	  return minPos;
  }

  public int calShortestPath(int x1, int y1, int x2, int y2) {
    int length = Integer.MAX_VALUE;
    
    int [][] allsp = calShortestPath(x1, y1);
    length = allsp[x2][y2];
//    ArrayList<MazePosition> visited = new ArrayList();
//    MazePosition origin = new MazePosition(x1,y1);
//    MazePosition destination = new MazePosition(x2,y2);
//    
//    Stack<MazePosition> queue = new Stack();
//    queue.add(origin);
//    System.out.println(origin.x+" "+origin.y);
//    visited.add(origin);
//    
//    int step = 0;
//    while(!queue.isEmpty())
//    {
//    	MazePosition newNode = queue.pop();
//    	List<MazePosition> child = null;
//    	
//    	while((child = getUnvisitedPos(newNode.x,newNode.y,visited)).size() >0)
//    	{
//    		
//    		queue.add(new MazePosition(child.get(0).x,child.get(0).y));
//    		System.out.println(child.get(0).x+" "+child.get(0).y);
//    		step++;
//    		if(child.get(0).equals(destination))
//    		{
//    			int tmpStep = step;
//    			if(step < length)
//    				length = step;
//    			//step = 0;
//    			System.out.println("found " + step);
//    		}
//    		else
//    			visited.add(new MazePosition(child.get(0).x,child.get(0).y));
//    	}
//    	
//    	
//    }
//    
    
    
    return length;
  }
  
  protected List<MazePosition> getUnvisitedPos(int x, int y, boolean[][] visited)
  {
	  int[] bothX = {x-1,x,x+1};
	  int[] bothY = {y-1,y,y+1};
	  
	  List<MazePosition> unvisited = new ArrayList();
	  
	  for(int i : bothX)
		  for(int j : bothY)
		  {
			  if(i!=x && j!=y)
				  continue;
			  try{
				//  System.out.println(maze[i][j]+" "+i+" "+j);
				  if(maze[i][j]==1 && !visited[i][j])
				  {
					  unvisited.add(new MazePosition(i,j));
					//  System.out.println(i+" "+j);
				  }
			  }catch(Exception e){}
		  }
	  
	//  System.out.println(unvisited.size());
	  
	  return unvisited;
  }
  
  private boolean contain(ArrayList<MazePosition> visited, int x, int y)
  {
	  for(MazePosition maze : visited)
	  {
		  if(maze.x == x && maze.y == y)
			  return true;
	  }
	  return false;
  }
}
