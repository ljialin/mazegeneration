package maze;

import generators.Spawner;

import java.util.*;

/**
 * Created by Jialin Liu on 08/12/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public abstract class Maze {
  protected final static int ACCESSIBLE = 1;
  protected final static int NON_ACCESSIBLE = 0;
  protected final static int HUGE_POSITIVE = 1000000;
  protected static HashMap<Integer, int[]> idxCellHashMap;

  protected int[][] maze;
  protected int width;
  protected int height;
  protected ArrayList<Integer> accessibleCellIdx;

  protected ArrayList<Integer> distDistribution;

  protected int[][] distance;
  protected int[][] previousCell;
  protected int maxDist = HUGE_POSITIVE;

  public Maze(int[][] maze) {
    this.maze = maze;
    this.accessibleCellIdx = new ArrayList<>();
    this.idxCellHashMap = new HashMap<>();
    fillIdxCellHashMap();
    updateNbAccessibleCell();
    width = maze[0].length;
    height = maze.length;
  }

  public void clearMaze() {
    for (int i=0;i<maze.length;i++) {
      for (int j = 0; j < maze[0].length; j++) {
        maze[i][j] = 0;
      }
    }
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
    maxDist = maze.length*maze[0].length;
//    clearDist();
  }

  public void clearDist() {
    previousCell = new int[accessibleCellIdx.size()][accessibleCellIdx.size()];
    distance = new int[accessibleCellIdx.size()][accessibleCellIdx.size()];
    for (int i=0;i<accessibleCellIdx.size();i++) {
      clearDist(i);
    }
  }

  public void clearDist(int idx) {
    maxDist = maze.length*maze[0].length;
    for (int j=0;j<accessibleCellIdx.size();j++) {
      distance[idx][j] = maxDist;
      previousCell[idx][j] = -1;
    }
    distance[idx][idx] = 0;
    previousCell[idx][idx] = idx;
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
//    clearDist();
//    for (int i=0;i<this.accessibleCellIdx.size();i++) {
//      shortestPath(i);
//    }
    this.distDistribution = new ArrayList<>();
    for (int i=0;i<this.accessibleCellIdx.size();i++) {
      int[] point1 = getCellPosByIdx(accessibleCellIdx.get(i));
      for (int j=i+1;j<this.accessibleCellIdx.size();j++) {
        int[] point2 = getCellPosByIdx(accessibleCellIdx.get(j));
        int dist1 = calShortestPath(point1[0], point1[1], point2[0], point2[1]);
//        int dist2 = Math.min(this.distance[i][j], this.distance[j][i]);
        distDistribution.add(dist1);
//        if (dist1 == maxDist) {
//          System.out.println("No path found between point " + point1[0] + "," + point1[1] + " to point " + point2[0] + "," + point2[1]);
//        }
      }
    }
    return distDistribution;
  }

  public HashMap<Integer,Integer> hist() {
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
    if (histOcc.containsKey(maxDist)) {
      System.out.println("No path found between " + histOcc.get(maxDist) + " pairs of points");
    }
    return histOcc;
  }

  public static HashMap<Integer,Double> hist(HashMap<Integer,Integer> histOcc, int nbPairs) {
    HashMap<Integer,Double> hist = new HashMap<>();
    for (Map.Entry<Integer, Integer> entry : histOcc.entrySet()) {
      int key = entry.getKey();
      int occ = entry.getValue();
      hist.put(key, (double) occ/nbPairs);
    }
    return hist;
  }

  public int getNbPairs() {
    return distDistribution.size();
  }

  public double expactedDist() {
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
    double entropy = 0.0;
    for (Map.Entry<Integer, Integer> entry : histOcc.entrySet()) {
      double key = entry.getKey();
      double freq = (double) entry.getValue()/distDistribution.size();
      entropy += key * freq;
    }
    return entropy;
  }


  public void shortestPath(int idx) {
    ArrayList<Integer> unvisited = initUnvisited();
    unvisited.remove(idx);
    while (!unvisited.isEmpty()) {
      int selectedIdx = findMinDist(idx, unvisited);
      previousCell[idx][selectedIdx] = -1;
      unvisited.remove(new Integer(selectedIdx));

      ArrayList<Integer> neighbors = neighbor(selectedIdx);
      for (Integer neighbor : neighbors) {
        int alternate = distance[idx][selectedIdx] + 1;
        if (alternate < distance[idx][neighbor]) {
          distance[idx][neighbor] = alternate;
          previousCell[idx][neighbor] = selectedIdx;
          System.out.println(" ================== updated");
        }
      }
    }
  }

  public ArrayList<Integer> initUnvisited() {
    ArrayList<Integer> unvisited = new ArrayList<>();
    for (int i=0;i<this.accessibleCellIdx.size();i++) {
      unvisited.add(i);
    }
    return unvisited;
  }

  public ArrayList<Integer> neighbor(int idx) {
    int cellIdx = this.accessibleCellIdx.get(idx);

    int x = this.idxCellHashMap.get(cellIdx)[0];
    int y = this.idxCellHashMap.get(cellIdx)[1];
    ArrayList<Integer> neighbors = new ArrayList<>();
    if (x>0) {
      if (y>0) {
        int toAddIdx = findIdx((x-1)*maze[0].length+y-1);
        if (toAddIdx>=0) {
          neighbors.add(toAddIdx);
        }
      }
      if (y<maze[0].length-1) {
        int toAddIdx = findIdx((x-1)*maze[0].length+y+1);
        if (toAddIdx>=0) {
          neighbors.add(toAddIdx);
        }
      }
    }
    if (x<maze.length-1) {
      if (y>0) {
        int toAddIdx = findIdx((x+1)*maze[0].length+y-1);
        if (toAddIdx>=0) {
          neighbors.add(toAddIdx);
        }
      }
      if (y<maze[0].length-1) {
        int toAddIdx = findIdx((x+1)*maze[0].length+y+1);
        if (toAddIdx>=0) {
          neighbors.add(toAddIdx);
        }
      }
    }
    return neighbors;
  }

  public int findIdx(int value) {
    for (int i=0;i<this.accessibleCellIdx.size();i++) {
      if (accessibleCellIdx.get(i)==value) {
        return i;
      }
    }
    return -1;
  }

  public int findMinDist(int idx, ArrayList<Integer> unvisited) {
    int minDist = maxDist;
    int idxDest = 0;
    for (int i=0;i<unvisited.size();i++) {
      int idxAccessibleCell = unvisited.get(i);
      if (distance[idx][idxAccessibleCell]<minDist) {
        System.out.println(idxDest + " replaced by " +idxAccessibleCell);
        minDist = distance[idx][idxAccessibleCell];
        idxDest = idxAccessibleCell;
      }
    }
    return idxDest;
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
			  distance[i][j] = maxDist;
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
	  int x = maxDist;
	  
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
    int length = maxDist;
    
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

  //http://www.contralogic.com/2d-pac-man-style-maze-generation/
  public void generatorWithoutDeadEnd () {
    clearMaze();
    Random rdm = new Random();
    int nbBuilders = rdm.nextInt(5);
    int x,y;
    Spawner[] spawners = new Spawner[5];
    for (int i=0; i<nbBuilders; i++) {
      x = rdm.nextInt(width);
      y = rdm.nextInt(height);
      maze[x][y] = 1;
      spawners[i] = new Spawner(x,y);
    }
    int t = 0;
    while (t < 100) {

    }
  }

}
