package pack;

/**
 * Created by Vinicius on 4/10/16.
 */
public class Function {

    public static final int SPHERE = 0x10;
    public static final int ROTATED_RASTRIGIN = 0X11;
    public static final int ROSENBROCK = 0X12;


     public static double sphereFunction(double[] position) {
        double result = 0;
        for(int i = 0; i < position.length; i++) {
            result += Math.pow(position[i],2);
        }
        return result;
    }

    public static double rotatedRastriginFunction(double[] position) {
        double result = 0;
        double An = 100;
        double A = 20;

        for(int i = 1; i < position.length; i++) {
            result += Math.pow(position[i], 2) - A * Math.cos(2*Math.PI*position[i]);
        }
        result += An;
        return result;

    }

    public static double rosenbrockFunction(double[] position) {
        double result = 0;
        for(int i = 1; i < position.length - 1; i++) {
            result += 100 * Math.pow((position[i - 1] - Math.pow(position[i],2)),2) -
                    Math.pow(position[i] - 1,2);
        }
        return result;
    }


}
