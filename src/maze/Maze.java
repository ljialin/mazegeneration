package maze;

import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jialin Liu on 08/12/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public abstract class Maze {
  private final static int ACCESSIBLE = 1;
  private final static int NON_ACCESSIBLE = 0;
  private final static int HUGE_POSITIVE = 1000000;
  protected static HashMap<Integer, int[]> idxCellHashMap;

  protected int[][] maze;
  protected ArrayList<Integer> accessibleCellIdx;

  protected ArrayList<Integer> distDistribution;

  public Maze(int[][] maze) {
    this.maze = maze;
    this.accessibleCellIdx = new ArrayList<>();
    this.idxCellHashMap = new HashMap<>();
    fillIdxCellHashMap();
    updateNbAccessibleCell();
  }

  public void updateNbAccessibleCell() {
    if (this.accessibleCellIdx == null) {
      this.accessibleCellIdx = new ArrayList<>();
    }
    accessibleCellIdx.clear();
    for (int i=0;i<maze.length;i++) {
      for (int j = 0; j < maze[0].length; j++) {
        if (this.maze[i][j] == ACCESSIBLE) {
          this.accessibleCellIdx.add(i * maze[0].length + j);
//          System.out.println("Point " + i + "," + j + " is " + maze[i][j] + " accessible");
        }
      }
    }
  }

  public void updateMaze(int[] bits) {
    assert (bits.length == maze.length*maze[0].length);
    for (int i=0;i<maze.length;i++) {
      for (int j = 0; j < maze[0].length; j++) {
        maze[i][j] = bits[i * maze[0].length + j];
      }
    }
    updateNbAccessibleCell();
  }

  public void fillIdxCellHashMap() {
    if (this.idxCellHashMap == null) {
      this.idxCellHashMap = new HashMap<>();
    }
    for (int i=0;i<maze.length;i++) {
      for (int j = 0; j < maze[0].length; j++) {
        int[] pos = new int[]{i, j};
        this.idxCellHashMap.put(i * maze[0].length + j, pos);
      }
    }
  }

  public Maze(int dimX, int dimY) {
    this.maze = new int[dimX][dimY];
    this.accessibleCellIdx = new ArrayList<>();
    this.idxCellHashMap = new HashMap<>();
    fillIdxCellHashMap();
  }

  public Maze(int dim) {
    this.maze = new int[dim][dim];
    this.accessibleCellIdx = new ArrayList<>();
    this.idxCellHashMap = new HashMap<>();
    fillIdxCellHashMap();
  }

  public int getWidth() {
    return this.maze[0].length;
  }

  public int getHeight() {
    return this.maze.length;
  }

  public static HashMap<Integer, int[]> getIdxCellHashMap() {
    return idxCellHashMap;
  }

  public int[][] getMaze() {
    return maze;
  }

  public ArrayList<Integer> getAccessibleCellIdx() {
    return accessibleCellIdx;
  }

  public int getNbAccessibleCell() {
    return this.accessibleCellIdx.size();
  }

  public int[] getCellPosByIdx(int idx) {
    return idxCellHashMap.get(idx);
  }

  public String toString(){

	  String st = "";
	  for(int i=0;i<maze.length;i++)
	  {
		  for(int j=0;j<maze[0].length;j++)
		  {
			  if(maze[i][j]==NON_ACCESSIBLE)
			  st += "0";
			  else
				  st+=" ";
		  }
		  st += "\n";
	  }
	  return st;
  }

  public ArrayList<Integer> distDistribution() {
    this.distDistribution = new ArrayList<>();
//    System.out.println("==============" + this.accessibleCellIdx.size());
    for (int i=0;i<this.accessibleCellIdx.size();i++) {
      int[] point1 = getCellPosByIdx(accessibleCellIdx.get(i));
      for (int j=i+1;j<this.accessibleCellIdx.size();j++) {
        int[] point2 = getCellPosByIdx(accessibleCellIdx.get(j));
        int dist = calShortestPath(point1[0], point1[1], point2[0], point2[1]);
        if (dist<HUGE_POSITIVE) {
          distDistribution.add(dist);
        }
//        System.out.println("Point " + point1[0] + "," + point1[1] + " to point " + point2[0] + "," + point2[1] + " is " + dist);
      }
    }
    return distDistribution;
  }

  public HashMap<Integer,Double> hist() {
    distDistribution();
    HashMap<Integer,Integer> histOcc = new HashMap<>();
    for (int i=0;i<distDistribution.size();i++) {
      int key = distDistribution.get(i);
      int occ = 0;
      if (histOcc.containsKey(key)) {
        occ = histOcc.get(key);
      }
      histOcc.put(key, occ+1);
    }
    HashMap<Integer,Double> hist = new HashMap<>();
    for (Map.Entry<Integer, Integer> entry : histOcc.entrySet()) {
      int key = entry.getKey();
      int occ = entry.getValue();
      hist.put(key, (double) occ/distDistribution.size());
    }
    return hist;
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
			  distance[i][j] = HUGE_POSITIVE;
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
			  Cell next = minPos(distance,visited);
			  if(next==null)
				  continue;
			  
			  visited[next.x][next.y] = true;
			  
			 // System.out.println(next);
			  
			  List<Cell> neighbors = getUnvisitedPos(next.x,next.y,visited);
			  
	//		  System.out.println(neighbors.size());
			  for(Cell n : neighbors)
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
  
  private Cell minPos(int[][] dist, boolean[][] visited)
  {
	  int x = HUGE_POSITIVE;
	  
	  Cell minPos = null;
	  
	  for(int i=0;i<dist.length;i++)
		  for(int j=0;j<dist[0].length;j++)
		  {
			  if(!visited[i][j] && dist[i][j]<x)
			  {
				  minPos = new Cell(i,j);
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
  
  protected List<Cell> getUnvisitedPos(int x, int y, boolean[][] visited)
  {
	  int[] bothX = {x-1,x,x+1};
	  int[] bothY = {y-1,y,y+1};
	  
	  List<Cell> unvisited = new ArrayList();
	  
	  for(int i : bothX)
		  for(int j : bothY)
		  {
			  if(i!=x && j!=y)
				  continue;
			  try{
				//  System.out.println(maze[i][j]+" "+i+" "+j);
				  if(maze[i][j]==ACCESSIBLE && !visited[i][j])
				  {
					  unvisited.add(new Cell(i,j));
					//  System.out.println(i+" "+j);
				  }
			  }catch(Exception e){}
		  }
	  
	//  System.out.println(unvisited.size());
	  
	  return unvisited;
  }
  
  private boolean contain(ArrayList<Cell> visited, int x, int y)
  {
	  for(Cell maze : visited)
	  {
		  if(maze.x == x && maze.y == y)
			  return true;
	  }
	  return false;
  }

  public abstract void plot();

}
