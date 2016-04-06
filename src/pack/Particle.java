package pack;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Vinicius on 4/3/16.
 */
public class Particle {

    public static final int GLOBAL = 0x00;
    public static final int LOCAL = 0x01;
    public static final int FOCAL = 0x02;

    public static final int SPHERE = 0x10;
    public static final int ROTATED_RASTRIGIN = 0X11;
    public static final int ROSENBROCK = 0X12;

    private double c1 = 2.05;
    private double c2 = 2.05;

    private List<Double> velocity;
    private List<Double> position;
    private List<Double> bestPosition;
    private List<Double> globalPosition;

    public Particle () {

    }

    public Particle(List<Double> velocity, List<Double> initialPosition) {
        this.velocity = velocity;
        this.position = initialPosition;
    }


    public List<Double> newVelocity() {
        List<Double> newVelocity = new ArrayList<>();
        for(int i = 0; i < this.velocity.size(); i++) {
            newVelocity.add(this.velocity.get(i) + c1 * Math.random() * (this.bestPosition.get(i) - this.position.get(i)) +
                    c2 * Math.random() * (this.globalPosition.get(i) - this.position.get(i)));
        }
        return newVelocity;
    }

    public List<Double> newPosition() {
        List<Double> newPosition = new ArrayList<>();
        for (int i = 0; i < this.position.size(); i++) {
            newPosition.add(this.position.get(i) + this.velocity.get(i));
        }
        return newPosition;
    }

    public List<Double> getVelocity() {
        return velocity;
    }

    public void setVelocity(List<Double> velocity) {
        this.velocity = velocity;
    }

    private double sphereFunction() {
        double result = 0;
        for(int i = 0; i < this.position.size(); i++) {
            result += Math.pow(this.position.get(i),2);
        }
        return result;
    }

    private double rotatedRastriginFunction() {
        double result = 0;
        double An = 100;
        double A = 20;

        for(int i = 1; i < this.position.size(); i++) {
            result += Math.pow(this.position.get(i), 2) - A * Math.cos(2*Math.PI*this.position.get(i));
        }
        result += An;
        return result;

    }

    private double rosenbrockFunction() {
        double result = 0;
        for(int i = 1; i < this.position.size() - 1; i++) {
            result += 100 * Math.pow((this.position.get(i - 1) - Math.pow(this.position.get(i),2)),2) -
                    Math.pow(this.position.get(i) - 1,2);
        }
        return result;
    }

    public void calculateFunction(int type) {
        switch (type) {
            case SPHERE:
                sphereFunction();
                break;
            case ROTATED_RASTRIGIN:
                rotatedRastriginFunction();
                break;
            case ROSENBROCK:
            default:
                rosenbrockFunction();
                break;
        }
    }

    private void globalTopology() {

    }

    private void localTopology() {

    }

    private  void focalTopology() {

    }

    public void updateGlobalPosition(int topology) {
        switch (topology) {
            case LOCAL:
                localTopology();
                break;
            case FOCAL:
                focalTopology();
                break;
            case GLOBAL:
            default:
                globalTopology();
                break;
        }
    }
}
