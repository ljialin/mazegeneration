package tools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jialin Liu on 20/02/2017.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class Tools {

  /**
   * Generate distinct random numbers in [lowerBound,upperBound]
   * @param lowerBound
   * @param upperBound
   * @param n
   * @return
   */
  public static int[] generateRandomNumbers(int lowerBound, int upperBound, int n) {
    int[] generated = generateRandomNumbers(upperBound, n);
    for (int i=0; i<n; i++) {
      generated[i] += lowerBound;
    }
    return generated;
  }

  /**
   * Generate distinct random numbers in [0,upperBound]
   * @param upperBound
   * @param n
   * @return
   */
  public static int[] generateRandomNumbers(int upperBound, int n) {
    int[] generated = new int[n];
    ArrayList<Integer> list = new ArrayList<>();
    for (int i=0; i<=upperBound; i++) {
      list.add(new Integer(i));
    }
    Collections.shuffle(list);
    for (int i=0; i<n; i++) {
      generated[i] = list.get(i);
    }
    return generated;
  }
}
