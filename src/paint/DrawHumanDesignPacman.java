package paint;

import javax.swing.*;

import java.io.*;


/**
 * Created by Jialin Liu on 20/12/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class DrawHumanDesignPacman {
  public static void main(String[] args) {
    String filename = "./mazes/pacman/level4.txt";
    int[][] maze = new int[29][26];

    // Reading
    try{
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      String line;
      int i = 0;
//      System.out.println(filename);
      while ((line = br.readLine()) != null){
        System.out.println(line);
        int j = 0;
        for (char ch: line.toCharArray()) {
          maze[i][j] = (int) (ch - '0');
          j++;
        }
        i++;
      }
      br.close();
//      System.out.println(maze.toString());
      JFrame frame = new JFrame();
      PacManMazeDrawer md = new PacManMazeDrawer(maze);
      frame.add(md);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setBounds(0,0, (int) (md.getWidth()),(int) (md.getHeight()+md.getWinSizeY()));
//      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
//      Graphics2D g = (Graphics2D) gx;
    }
    catch (Exception e){
      System.out.println(e.toString());
    }
  }
}
