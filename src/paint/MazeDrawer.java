package paint;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

/**
 * Created by Jialin Liu on 20/12/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class MazeDrawer extends JComponent {
  int width = 26*20;
  int height = 29*20;
  int dimX;
  int dimY;
  float winSizeX;
  float winSizeY;
  int[][] maze;


  public MazeDrawer(int[][] maze) {
    this.maze = maze;
    this.dimY = maze.length;
    this.dimX = maze[0].length;

    winSizeX = this.width/dimX;
    winSizeY = this.height/dimY;

    System.out.println(dimX + " " + dimY);
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public void paintComponent(Graphics g) {
    g.setColor(Color.BLUE);
    g.fillRect(0, 0,(int) (getWidth()+getWinSizeX()),(int) (getHeight()+getWinSizeY()));
//    for (int i=0; i<this.dimX; i++) {
//      for (int j = 0; j < this.dimY; j++) {
//        if(this.maze[j][i]==0) {
//          g.setColor(Color.BLUE);
//          g.fillRect((int) (winSizeX*i), (int) (winSizeY*j), (int) (winSizeX), (int) (winSizeY));
//        }
//      }
//    }
    for (int i=0; i<this.dimX; i++) {
      for (int j = 0; j < this.dimY; j++) {
        if(this.maze[j][i]>0) {
          g.setColor(Color.BLACK);
          g.fillRect((int) (winSizeX*i-winSizeX*0.5), (int) (winSizeY*j-winSizeY*0.5), (int) (winSizeX*2), (int) (winSizeY*2));
        }
      }
    }
  }

  public float getWinSizeX() {
    return winSizeX;
  }

  public void setWinSizeX(float winSizeX) {
    this.winSizeX = winSizeX;
  }

  public float getWinSizeY() {
    return winSizeY;
  }

  public void setWinSizeY(float winSizeY) {
    this.winSizeY = winSizeY;
  }
}
