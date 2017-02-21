package generators;

import paint.JCanvas;
import tools.IntVector2d;
import tools.Tools;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * Created by Jialin Liu on 20/02/2017.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class MapBuilder {
  Spawner[] spawners;
  int width;
  int height;
  Random rdm;
  public static int[][] grid;

  public MapBuilder(int nbSpawners, int width, int height) {
    this.width = width;
    this.height = height;
    grid = new int[width][height];
    this.rdm = new Random();

    this.spawners = new Spawner[nbSpawners];
//    initSpawners();
    initSpawnersUniformly();
  }

  public void initSpawners() {
    int[] xs = Tools.generateRandomNumbers(1,spawners.length, spawners.length); //Tools.generateRandomNumbers(width, nbSpawners);
    int[] ys = Tools.generateRandomNumbers(1,spawners.length, spawners.length); //Tools.generateRandomNumbers(height, nbSpawners);

    for (int i=0; i< spawners.length; i++) {
      this.spawners[i] = new Spawner(width, height, width/(spawners.length+1) * xs[i], height/(spawners.length+1) * ys[i]);
      markSpawner(spawners[i].pos);
    }
  }

  public void initSpawnersUniformly() {
    int nb = (int) Math.sqrt(spawners.length);
    int idx = 0;
    for (int i=1; i<=nb; i++) {
      for (int j=1; j<=nb; j++){
        idx = (i-1)*nb + j-1;
        this.spawners[idx] = new Spawner(width, height, width/(nb+1) * i, height/(nb+1) * j);
        markSpawner(spawners[idx].pos);
      }
    }
    while (idx<spawners.length-1) {
      idx++;
      this.spawners[idx] = new Spawner(width, height);
      markSpawner(spawners[idx].pos);
    }
  }

  public void expand() {
    for (int i=0; i<this.spawners.length; i++) {
      this.spawners[i].expand();
    }
  }

  public static void fillSquare(IntVector2d pos) {
    if (grid[pos.x][pos.y] != -1) {
      grid[pos.x][pos.y] = 1;
    }
  }

  public static void markSpawner(IntVector2d pos) {
    grid[pos.x][pos.y] = -1;
  }

  public static void markBuilder(IntVector2d pos) {
    if (grid[pos.x][pos.y] != -1) {
      grid[pos.x][pos.y] = -2;
    }
  }

  public static boolean checkFilled(IntVector2d pos) {
    return (grid[pos.x][pos.y] != 0);
  }

  public static boolean checkFilled(int x, int y) {
    if (x>grid.length || x<0 || y>grid[0].length || y<0 ) {
      return false;
    }
    return (grid[x][y] != 0);
  }

  public static int checkFilledNeighbors(IntVector2d pos) {
    int filledNeighbors = 0;
    try {
      if(checkFilled(pos.x,pos.y + 1)) {
        filledNeighbors++;
      }
      if (checkFilled(pos.x,pos.y - 1)) {
        filledNeighbors++;
      }
      if (checkFilled(pos.x+1,pos.y)) {
        filledNeighbors++;
      }
      if (checkFilled(pos.x-1,pos.y)) {
        filledNeighbors++;
      }
    } catch (Exception e) {

    }
    return filledNeighbors;
  }

  public boolean isStopped() {
    boolean isStopped = true;
    for (Spawner spawner: spawners) {
      isStopped = isStopped && spawner.isStopped();
    }
    return isStopped;
  }

  @Override
  public String toString(){

    String st = "";
    for(int i=0;i<grid.length;i++)
    {
      for(int j=0;j<grid[0].length;j++)
      {
        if(grid[i][j]==1)
          st += "0";
        else
          st+=" ";
      }
      st += "\n";
    }
    return st;
  }

  public static void main(String[] args) throws InterruptedException {
    MapBuilder mb = new MapBuilder(9, 50, 50);
    JCanvas canvas = new JCanvas(mb.grid);
    JFrame frame = new JFrame("Maze generation");
    WindowAdapter wa = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    };
    frame.addWindowListener(wa);
    frame.getContentPane().add(canvas);
    frame.pack();
    frame.setBounds(0, 0, (int) (canvas.getWidth()+canvas.getWinSizeX()), (int) (canvas.getHeight()+canvas.getWinSizeY()));
    frame.setVisible(true);
    while (!mb.isStopped()) {
      mb.expand();
      canvas.setGrid(mb.grid);
      frame.repaint();
      canvas.repaint();
//      System.out.println(mb);
      Thread.sleep(100);
    }
  }
}
