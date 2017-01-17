package test;

import maze.Maze;
import maze.PacmanMaze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Jialin Liu on 17/01/2017.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class HalfMazeTest {
  public static int panality = 29*26;

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
      HashMap<Integer, Double> histObj = Maze.hist(pacmanMaze.hist(), pacmanMaze.getNbPairs());
      // New generated
      PacmanMaze newmaze = new PacmanMaze(29, 26);
      newmaze.fillIdxCellHashMap();

      Random rdm = new Random();
      int dim = 29*26/2;
      System.out.println("dim="+dim);
      int[] bits = new int[dim];
      for (i=0; i < bits.length; i++) {
        bits[i] = rdm.nextDouble() > 0.5 ? 0 : 1;
      }
      newmaze.updateMaze(bits);
      HashMap<Integer, Integer> hist = newmaze.hist();

      double bestSoFar = sqrtErr(hist, newmaze.getNbPairs(), histObj);
      System.out.println("Init with sqrtErr " + bestSoFar);

      int t = 0;
      while (t < 10000) {
        int idx = rdm.nextInt(dim);
        bits[idx] = 1-bits[idx];
        newmaze.updateMaze(bits);
        hist = newmaze.hist();
        double f = sqrtErr(hist, newmaze.getNbPairs(), histObj);
        System.out.println("Iteration " + t + " new maze : sqrtErr=" + f);
        System.out.println(newmaze.getMaze().toString());
        if (f<=bestSoFar) {
          bestSoFar = f;
        } else {
          bits[idx] = 1-bits[idx];
          newmaze.updateMaze(bits);
        }
        t++;
        System.out.println("Iteration " + t + " bestSoFar maze : sqrtErr=" + bestSoFar);
        System.out.println(newmaze.getMaze().toString());
      }


    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  public static double sqrtErr(double obj, double matated) {
    return Math.pow((obj-matated),2);
  }

  public static double sqrtErr(HashMap<Integer,Integer> hist1, int nbPair, HashMap<Integer,Double> hist2) {
    double sqrtErr = 0.0;
    HashMap<Integer, Double> newhist = Maze.hist(hist1, nbPair);

    for (Map.Entry<Integer, Double> entry : newhist.entrySet()) {
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
    if (hist1.containsKey(29*26)) {
      sqrtErr -= panality*hist1.get(29*26);
    }
    return sqrtErr;
  }
}
