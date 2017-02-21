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
  int width;
  int height;
  Random rdm;
  boolean stopped;

  public Builder(IntVector2d pos, IntVector2d dir) {
    this.pos = new IntVector2d(pos);
    this.dir = new IntVector2d(dir);
    this.rdm = new Random();
    this.stopped = false;
    expand();
  }

  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
    this.range = Math.min(width,height)/4;
    randomiseDist();
  }

  public void randomiseDist() {
    if (range == 0) {
      this.dist = rdm.nextInt(8)+2;
    } else {
      this.dist = rdm.nextInt(range)+2;
    }
    if (this.dist%2==0) {
      this.dist++;
    }
  }

  public void expand() {
    if (!stopped) {
      if (this.dist <= 0) {
        randomise();
      } else {
        IntVector2d newPos = pos.addToNew(dir);
        if (newPos.x >= width || newPos.x < 0
            || newPos.y >= height || newPos.y < 0) {
          System.out.println(pos + "+" + dir + "=" + newPos +
          " with old dist=" + dist);
          randomise();
          System.out.println(pos + "+" + dir +
              " with new dist=" + dist);
        } else if (MapBuilder.checkFilled(newPos) == true) {
          if (MapBuilder.checkFilledNeighbors(pos)>1) {
            stopped = true;
            System.out.println("Stopped at pos=" + pos + " with dir=" + dir +
              " and dist=" + dist);
          } else {
            randomise();
          }
        } else {
          MapBuilder.fillSquare(pos);
          System.out.println("Filled : " + pos + "+" + dir + "=" + newPos +
              " with old dist=" + dist);
          this.pos.add(dir);
          MapBuilder.markBuilder(pos);
          this.dist--;
        }
      }
    }
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
