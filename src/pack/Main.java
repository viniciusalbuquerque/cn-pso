package pack;

import java.io.*;

/**
 * Created by Vinicius on 4/3/16.
 */
public class Main {

    private static int N_PARTICLES = 30;
    private static int N_DIM = 30;
    public static final int N_ITE = 10000;

    public static final String FILENAME = "./GlobalFitnessSphere.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(Main.FILENAME);
        FileWriter fw;
        BufferedWriter bw;
        fw = new FileWriter(file.getAbsolutePath());
        bw = new BufferedWriter(fw);

        PSO pso = new PSO(N_DIM, N_PARTICLES, Function.ROSENBROCK);
        pso.start(PSO.GLOBAL);

    }

}
