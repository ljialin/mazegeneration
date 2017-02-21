package paint;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jialin Liu on 21/02/2017.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class JCanvas extends JComponent {
  float width;
  float height;
  int dimX;
  int dimY;
  int[][] grid;
  final float winSizeX = 20;
  final float winSizeY = 20;

  public JCanvas(int[][] grid) {
    this.grid = grid;
    this.dimX = grid.length;
    this.dimY = grid[0].length;
    this.width = winSizeX*this.dimX;
    this.height = winSizeY*this.dimY;

    System.out.println(dimX + " " + dimY);
  }

  public int getWidth() {
    return (int) width;
  }

  public int getHeight() {
    return (int) height;
  }

  public int getDimX() {
    return dimX;
  }

  public int getDimY() {
    return dimY;
  }

  public int[][] getGrid() {
    return grid;
  }

  public float getWinSizeX() {
    return winSizeX;
  }

  public float getWinSizeY() {
    return winSizeY;
  }

  public void setGrid(int[][] grid) {
    this.grid = grid;
  }

  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(Color.BLACK);
    g.fillRect(0, 0,(int) (width+winSizeX),(int) (height+winSizeY));

    for (int i=0; i<this.dimX; i++) {
      for (int j = 0; j < this.dimY; j++) {
        if(this.grid[i][j]>0) {
          g.setColor(Color.BLUE);
          g.fillRect((int) (winSizeX*i), (int) (winSizeY*j), (int) (winSizeX), (int) (winSizeY));
        } else if (this.grid[i][j]==-1) {
          g.setColor(Color.RED);
          g.fillRect((int) (winSizeX*i), (int) (winSizeY*j), (int) (winSizeX), (int) (winSizeY));
        } else if (this.grid[i][j]==-2) {
          g.setColor(Color.GREEN);
          g.fillRect((int) (winSizeX * i), (int) (winSizeY * j), (int) (winSizeX), (int) (winSizeY));
        }
      }
    }
  }


}
