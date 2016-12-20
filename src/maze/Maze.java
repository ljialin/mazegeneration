package maze;

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

  protected int calShortestPath(int x1, int y1, int x2, int y2) {
    int length = Integer.MAX_VALUE;
    return length;
  }
}
