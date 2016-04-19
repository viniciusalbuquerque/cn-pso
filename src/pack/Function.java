package pack;

/**
 * Created by Vinicius on 4/10/16.
 */
public class Function {

    public static final int SPHERE = 0x10;
    public static final int ROTATED_RASTRIGIN = 0X11;
    public static final int ROSENBROCK = 0X12;


    public static float sphereFunction(double[] position) {
        float result = 0;
        for(int i = 0; i < position.length; i++) {
            result += Math.pow(position[i],2);
        }
        return result;
    }

    public static float rotatedRastriginFunction(double[] position) {
        float result = 10 * Main.N_DIM;
        double A = 100;

        for(int i = 0; i < position.length; i++) {
            result += Math.pow(position[i], 2) - A * Math.cos(2*Math.PI*position[i]);
        }
        return result;

    }

    public static float rosenbrockFunction(double[] position) {
        float result = 0;
        for(int i = 1; i < position.length; i++) {
            result += Math.pow((1 - position[i]),2) + (100 * Math.pow((position[i-1] - Math.pow(position[i],2)),2));
        }
        return result;
//        double result = 0;
//        for(int i = 1; i < position.length - 1; i++) {
//            result += 100 * Math.pow((position[i - 1] - Math.pow(position[i],2)),2) -
//                    Math.pow(position[i] - 1,2);
//        }
//        System.out.println(result);
//        return result;
    }


}