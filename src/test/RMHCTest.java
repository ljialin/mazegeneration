package test;

import maze.PacmanMaze;

import java.io.*;
import java.util.*;

/**
 * Created by Jialin Liu on 12/01/2017.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class RMHCTest {
  public static void main(String[] args) {
    String filename = "./mazes/pacman/level3.txt";
    int[][] maze = new int[29][26];

    // Reading

    FileReader fr;
    try {
      fr = new FileReader(filename);

      BufferedReader br = new BufferedReader(fr);
      String line;
      int i = 0;
      //		      System.out.println(filename);
      while ((line = br.readLine()) != null) {
        System.out.println(line);
        int j = 0;
        for (char ch : line.toCharArray()) {
          maze[i][j] = (int) (ch - '0');
          j++;
        }
        i++;
      }
      br.close();

      // Objective
      PacmanMaze pacmanMaze = new PacmanMaze(maze);
      pacmanMaze.fillIdxCellHashMap();
      pacmanMaze.updateNbAccessibleCell();
      HashMap<Integer, Double> histObj = pacmanMaze.hist();

      // New generated
      PacmanMaze newmaze = new PacmanMaze(29, 26);
      newmaze.fillIdxCellHashMap();

      Random rdm = new Random();
      int dim = 29*26;
      int[] bits = new int[dim];
      for (i=0; i < bits.length; i++) {
        bits[i] = rdm.nextDouble() > 0.5 ? 0 : 1;
      }
      int t = 0;
      newmaze.updateNbAccessibleCell();
      HashMap<Integer, Double> hist = newmaze.hist();

      double bestSoFar = sqrtErr(hist, histObj);
      System.out.println("Init with sqrtErr " + bestSoFar);

      while (t < 10000) {
        int idx = rdm.nextInt(dim);
        bits[idx] = 1-bits[idx];
        newmaze.updateMaze(bits);
        hist = newmaze.hist();
        double f = sqrtErr(hist, histObj);
        if (f<=bestSoFar) {
          bestSoFar = f;
        } else {
          bits[idx] = 1-bits[idx];
          newmaze.updateMaze(bits);
        }
        t++;
        System.out.println("Iteration " + t + " sqrtErr " + bestSoFar);
      }

      System.out.println(newmaze.getMaze().toString());

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  public static double sqrtErr(HashMap<Integer,Double> hist1, HashMap<Integer,Double> hist2) {
    double sqrtErr = 0.0;

    for (Map.Entry<Integer, Double> entry : hist1.entrySet()) {
      int key = entry.getKey();
      if (hist2.containsKey(key)) {
        sqrtErr += Math.pow(entry.getValue()-hist2.get(key),2);
        hist2.remove(key);
      } else {
        sqrtErr += Math.pow(entry.getValue(),2);
      }
    }
    for (Map.Entry<Integer, Double> entry : hist2.entrySet()) {
      sqrtErr += Math.pow(entry.getValue(),2);
    }
    return sqrtErr;
  }
}
