package generators;

import paint.PacManMazeDrawer;

import javax.swing.*;
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
    for (int i=0; i< nbSpawners; i++) {
      this.spawners[i] = new Spawner(rdm.nextInt(width), rdm.nextInt(height));
      this.spawners[i].setSize(width, height);
    }
  }

  public void expand() {
    for (int i=0; i<this.spawners.length; i++) {
      this.spawners[i].expand();
    }
  }

  public static void main(String[] args) {
    int[][] maze = new int[29][26];

    MapBuilder mb = new MapBuilder(5, 29, 26);

    JFrame frame = new JFrame();
    PacManMazeDrawer md = new PacManMazeDrawer(mb.grid);
    frame.add(md);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(0, 0, (int) (md.getWidth()), (int) (md.getHeight() + md.getWinSizeY()));
//      frame.setLocationRelativeTo(null);
    frame.setVisible(true);
//      Graphics2D g = (Graphics2D) gx;

    for (int i=0; i<10; i++) {
      mb.expand();
      frame.repaint(100, 0, 0, (int) (md.getWidth()), (int) (md.getHeight() + md.getWinSizeY()));
    }
  }
}
