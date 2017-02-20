package generators;

import tools.IntVector2d;
import tools.Tools;

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
public class Spawner {
  IntVector2d pos;
  ArrayList<Builder> builders;
  int width;
  int height;

  static final  IntVector2d[] DIRECTIONS = new IntVector2d[]{
      new IntVector2d(1,0),
      new IntVector2d(0,1),
      new IntVector2d(-1,0),
      new IntVector2d(0,-1)
  };

  public Spawner(int x, int y) {
    pos = new IntVector2d(x,y);
    builders = new ArrayList<>();
    int nbBuilders = new Random().nextInt(3)+2; // 2-4 nbBuilders
    int[] dirIdx = Tools.generateRandomNumbers(DIRECTIONS.length-1, nbBuilders);
    for (int i=0;i<nbBuilders;i++) {
      Builder builder = new Builder(pos, DIRECTIONS[dirIdx[i]]);
      builders.add(builder);
    }
  }

  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void expand() {
    for (Builder builder: builders) {
      builder.expand();
      if (builder.pos.x == this.width-1 || builder.pos.y == this.height-1) {
        builder.randomise();
      }
    }
  }


}
