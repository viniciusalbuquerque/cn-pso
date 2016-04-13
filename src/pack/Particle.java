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
    private double fitness;

    private List<Particle> neighboors;

    public Particle (int numDim) {

        this.fitness = 999999999;
        this.position = new double[numDim];
        this.velocity = new double[numDim];
        this.bestPosition = new double[numDim];
        this.globalPosition = new double[numDim];

        for(int i = 0; i < numDim; i++) {
            this.position[i] = (Math.random() * 100 * Math.pow(-1, Math.round(Math.random())));
            this.velocity[i] = (Math.random() * 10);
        }

        bestPosition = this.position;
        globalPosition = bestPosition;

    }

    public double[] newVelocity() {
        double[] newVelocity = new double[this.position.length];
        for(int i = 0; i < this.velocity.length; i++) {
            double R1 = Math.random();
            double R2 = Math.random();
            newVelocity[i] = (W * this.velocity[i] + c1 * R1 * Math.random() * (this.bestPosition[i] - this.position[i]) +
                    c2 * R2 * Math.random() * (this.globalPosition[i] - this.position[i]));
        }
        return newVelocity;
    }

    public double[] newPosition() {
        double[] newPosition = new double[this.position.length];
        for (int i = 0; i < this.position.length; i++) {
            double pos = this.position[i] + this.velocity[i];
            if(pos > 100) {
                newPosition[i] = (100.0);
            } else if(pos < -100) {
                newPosition[i] = (-100.0);
            } else {
                newPosition[i] = (pos);
            }
        }
        return newPosition;
    }

    public double calculateFitness(int type) {
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
        double fit = calculateFitness(function);
        if(fit < this.fitness) {
            bestPosition = position;
            this.fitness = fit;
        }
    }

    public double getFitness() { return this.fitness; }


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
