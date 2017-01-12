package optimiser.eas;

import java.util.Random;

/**
 * Created by Jialin Liu on 08/12/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class Individual {
  private int[] genome;
  private Random rdm = new Random();

  public Individual(int length) {
    this.genome = new int[length];
    randomInit();
  }

  public void randomInit() {
    for (int i=0;i<this.genome.length;i++) {
      this.genome[i] = rdm.nextDouble()>0.5?1:0;
    }
  }

  public int[] getGenome() {
    return genome;
  }
}
