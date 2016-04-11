package pack;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Vinicius on 4/3/16.
 */
public class Main {

    private static int N_PARTICLES = 30;
    private static int N_DIM = 30;
    public static final int N_ITE = 10000;

    public static void main(String[] args) throws IOException {

        PSO pso = new PSO(N_DIM, N_PARTICLES, Function.SPHERE);
        pso.start(PSO.GLOBAL);

    }

}
