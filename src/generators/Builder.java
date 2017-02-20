package generators;

import tools.IntVector2d;

import java.util.Random;

/**
 * Created by Jialin Liu on 20/02/2017.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class Builder {
  IntVector2d pos;
  IntVector2d dir;
  int dist;
  int range;
  Random rdm;


  public Builder(IntVector2d pos, IntVector2d dir) {
    this.pos = new IntVector2d(pos);
    this.dir = new IntVector2d(dir);
    this.rdm = new Random();
    MapBuilder.grid[pos.x][pos.y] = 1;
  }

  public void randomiseDist() {
    if (range == 0) {
      this.dist = rdm.nextInt(5);
    } else {
      this.dist = rdm.nextInt(range);
    }
  }

  public void expand() {
    if (this.dist==0) {
      randomise();
    }
    this.pos.add(this.dir);
    this.dist--;
  }

  public void randomise() {
    randomlyRotate();
    randomiseDist();
  }

  public void randomlyRotate() {
    dir.x = 1-Math.abs(dir.x);
    dir.y = 1-Math.abs(dir.y);
    if (rdm.nextDouble() < 0.5) {
      dir.x = -dir.x;
      dir.y = -dir.y;
    }
  }
}
