package pack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinicius on 4/3/16.
 */
public class Particle {

    private double c1 = 2.05;
    private double c2 = 2.05;
    private double W = 0.8;

    private double[] velocity;
    private double[] position;
    private double[] bestPosition;
    private double[] globalPosition;
    private float fitness;

    private List<Particle> neighboors;

    private float VEL_MAX;
    private float VEL_MIN;
    private float POS_MAX;
    private float POS_MIN;

    public Particle (int numDim, int function) {

        this.fitness = 999999999;
        this.position = new double[numDim];
        this.velocity = new double[numDim];
        this.bestPosition = new double[numDim];
        this.globalPosition = new double[numDim];

        defineParams(function);

        for(int i = 0; i < numDim; i++) {
            this.position[i] = (Math.random() * POS_MAX * Math.pow(-1, Math.round(Math.random())));
            this.velocity[i] = (Math.random() * VEL_MAX * Math.pow(-1, Math.round(Math.random())));
        }

        bestPosition = this.position;
        globalPosition = bestPosition;
    }

    private void defineParams(int function) {
        switch (function) {
            case Function.SPHERE:
                VEL_MAX = 10f;
                VEL_MIN = -10f;
                POS_MAX = 100f;
                POS_MIN = -100f;
//                System.out.println("SPHERE");
                break;
            case Function.ROTATED_RASTRIGIN:
                VEL_MAX = 10f;
                VEL_MIN = -10f;
                POS_MAX = 100f;
                POS_MIN = -100f;
//                System.out.println("RR");
                break;
            case Function.ROSENBROCK:
            default:
                VEL_MAX = 0.5f;
                VEL_MIN = -0.5f;
                POS_MAX = 5.12f;
                POS_MIN = -5.12f;
//                System.out.println("ROB");
                break;
        }
    }

    private double verifyPositionRange(double pos) {
        if(pos > POS_MAX) {
            return POS_MAX;
        } else if (pos < POS_MIN) {
            return POS_MIN;
        }
        return pos;
    }

    private double verifyVelocityRange(double vel) {
        if(vel > VEL_MAX) {
            return  VEL_MAX;
        } else if (vel < VEL_MIN) {
            return VEL_MIN;
        }
        return vel;
    }

    public double[] newVelocity() {
        W -= W/Main.N_ITE;
        double[] newVelocity = new double[this.position.length];
        for(int i = 0; i < this.velocity.length; i++) {
            double R1 = Math.random();
            double R2 = Math.random();
            double vel = (W * this.velocity[i] + c1 * R1 * Math.random() * (this.bestPosition[i] - this.position[i]) +
                    c2 * R2 * Math.random() * (this.globalPosition[i] - this.position[i]));

            newVelocity[i] = verifyVelocityRange(vel);

        }
        return newVelocity;
    }

    public double[] newPosition() {
        double[] newPosition = new double[this.position.length];
        for (int i = 0; i < this.position.length; i++) {
            double pos = this.position[i] + this.velocity[i];
            newPosition[i] = verifyPositionRange(pos);
        }
        return newPosition;
    }

    public float calculateFitness(int type) {
        switch (type) {
            case Function.SPHERE:
                return Function.sphereFunction(this.position);
            case Function.ROTATED_RASTRIGIN:
                return Function.rotatedRastriginFunction(this.position);
            case Function.ROSENBROCK:
            default:
                return Function.rosenbrockFunction(this.position);
        }
    }

    public void updateBestPosition(int function) {
        float fit = calculateFitness(function);
        if(fit < this.fitness) {
            bestPosition = position;
            this.fitness = fit;
        }
    }

    public float getFitness() { return this.fitness; }


    public double getMedia() {
        double media = 0;
        for(double d : this.position) {
            media += d;
        }
        return media / this.position.length;
    }

    public void addNeighboor(Particle particle) {
        if(neighboors == null || neighboors.size() == 0) {
            neighboors = new ArrayList<>();
        }
        neighboors.add(particle);
    }


    public double[] getBestPosition() {
        return bestPosition;
    }

    public void setGlobalPosition(double[] globalPosition) {
        this.globalPosition = globalPosition;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public void setNewVelocity() {
        setVelocity(newVelocity());
    }

    public void setNewPosition() {
        setPosition(newPosition());
    }

    public void setPosition(double[] pos) {
        this.position = pos;
    }

    public List<Particle> getNeighboors() {
        if(neighboors == null) {
            neighboors = new ArrayList<>();
        }
        return neighboors;
    }

    public double[] getPosition() {
        return position;
    }
    public double[] getGlobalPosition() {return this.globalPosition; }
}