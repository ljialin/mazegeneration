package tools;

/**
 * Created by Jialin Liu on 20/02/2017.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class IntVector2d {
  public int x;
  public int y;

  public IntVector2d(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public IntVector2d(IntVector2d newV) {
    this.x = newV.x;
    this.y = newV.y;
  }

  public void add(IntVector2d toAdd) {
    this.x += toAdd.x;
    this.y += toAdd.y;
  }
}
