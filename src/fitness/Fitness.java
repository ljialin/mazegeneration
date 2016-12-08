package fitness;

/**
 * Created by Jialin Liu on 08/12/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class Fitness {
  public static double squareDist(double[] current, double[] target) {
    double sd = 0.0;
    assert (current.length == target.length);
    for (int i=0; i<current.length; i++) {
      sd += (current[i] - target[i])*(current[i] - target[i]);
    }
    return sd;
  }
}
