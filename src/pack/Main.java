package pack;

import java.io.*;
import java.util.Vector;

/**
 * Created by Vinicius on 4/3/16.
 */
public class Main {

    private static int N_PARTICLES = 30;
    public static int N_DIM = 30;
    public static final int N_ITE = 10000;
    public static final int N_TESTES = 30;

//    public static final String FILENAME = "./GlobalFitnessSphereWVariation.txt";
//    public static final String FILENAME = "./FocalFitnessSphereWVariation.txt";
//    public static final String FILENAME = "./LocalFitnessSphereWVariation.txt";
//    public static final String FILENAME = "./GlobalFitnessROSENBROCKWVariation.txt";
//    public static final String FILENAME = "./FocalFitnessROSENBROCKWVariation.txt";
//    public static final String FILENAME = "./LocalFitnessROSENBROCKWVariation.txt";
//    public static final String FILENAME = "./GlobalFitnessRRWVariation.txt";
//    public static final String FILENAME = "./FocalFitnessRRWVariation.txt";
//    public static final String FILENAME = "./LocalFitnessRRWVariation.txt";
    public static final String FILENAME = "./iterations.txt";


    public static void main(String[] args) throws IOException {
        File file = new File(Main.FILENAME);
        FileWriter fw;
        BufferedWriter bw;
        fw = new FileWriter(file.getAbsolutePath());
        bw = new BufferedWriter(fw);

//        float[] mediaFit = new float[N_ITE];
//        for(int i = 0; i < N_TESTES; i++) {
//            PSO pso = new PSO(N_DIM, N_PARTICLES, Function.ROTATED_RASTRIGIN);
//            pso.setIndex(0);
//            pso.start(PSO.LOCAL);
//            float[] mf = pso.getMediaFit();
//            for(int j = 0; j < N_ITE; j++) {
//                mediaFit[j] += mf[j];
//            }
//        }

        for(int j = 1; j <= N_ITE; j++) {
//            mediaFit[j] /= N_TESTES;
            bw.write(String.valueOf(j) + "\n");
        }
        bw.close();

    }
}