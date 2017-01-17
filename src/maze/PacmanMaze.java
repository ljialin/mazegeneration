package maze;

import paint.PacManMazeDrawer;

import javax.swing.*;

/**
 * Created by Jialin Liu on 08/12/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class PacmanMaze extends Maze {
  public PacmanMaze(int dim) {
    super(dim);
  }

  public PacmanMaze(int dimX, int dimY) {
    super(dimX, dimY);
  }

  public PacmanMaze(int[][] maze) {
    super(maze);
  }

  @Override
  public void plot() {
    JFrame frame = new JFrame();
    PacManMazeDrawer md = new PacManMazeDrawer(maze);
    frame.add(md);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(0,0, (int) (md.getWidth()),(int) (md.getHeight()+md.getWinSizeY()));
      frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  @Override
  public void updateMaze(int[] bits) {
    assert (bits.length == maze.length*maze[0].length/2);
    for (int i=0;i<maze.length;i++) {
      for (int j = 0; j < maze[0].length/2; j++) {
        maze[i][j] = bits[i * maze[0].length/2 + j];
        maze[i][maze[0].length-1-j] = bits[i * maze[0].length/2 + j];
      }
    }
    updateNbAccessibleCell();
  }

  @Override
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
}
