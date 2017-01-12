package optimiser.eas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jialin Liu on 08/12/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p/>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class RMHC extends EA {
  private Random rdm = new Random();

  public RMHC(int dim) {
    this.dim = dim;
    this.population = new ArrayList<>();
    randomInit();
  }

  public void randomInit() {
    if (this.population == null) {
      this.population = new ArrayList<>();
    }
    this.population.clear();
    this.population.add(new Individual(dim));
  }

  public void mutate() {
    int idx = rdm.nextInt(dim);
    this.population.get(0).getGenome()[idx] = 1 - this.population.get(0).getGenome()[idx];
  }
}
