package generators;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jialin Liu on 17/01/2017.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
//http://www.contralogic.com/2d-pac-man-style-maze-generation/
public class Builder {
  int x;
  int y;
  ArrayList<int[][]> spawners;
  public Builder(int x, int y) {
    this.x = x;
    this.y = y;
    spawners = new ArrayList<>();
    int nbSpawners = new Random().nextInt(3)+2;
    for (int i=0;i<nbSpawners;i++) {
      spawners.add(n)
    }
  }
}
