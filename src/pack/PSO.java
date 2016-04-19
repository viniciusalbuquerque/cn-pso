package pack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinicius on 4/6/16.
 */
public class PSO {

    public static final int GLOBAL = 0x00;
    public static final int LOCAL = 0x01;
    public static final int FOCAL = 0x02;

    private List<Particle> particles;
    private double[] gBest;
    private float globalFitness;
    private int function;

    private float[] mediaFit;
    private int index;

    public PSO(int numDim, int numPart, int function) throws IOException {

        this.function = function;
        this.particles = new ArrayList<>();
        this.globalFitness = 999999999;
        for(int i = 0; i < numPart; i++) {
            Particle particle = new Particle(numDim, function);
            this.particles.add(particle);
            if(gBest == null) {
                gBest = new double[particle.getBestPosition().length];
                gBest = particle.getBestPosition();
            }
        }

        mediaFit = new float[Main.N_ITE];
    }

    private void calculateInfo() {
        for(Particle particle : this.particles) {
            particle.setNewVelocity();
            particle.setNewPosition();
            particle.updateBestPosition(this.function);
        }
    }

    private void globalTopology() throws IOException {
        for (int it = 0; it < Main.N_ITE; it++) {
            calculateInfo();
            for (Particle p : this.particles) {
                if (p.getFitness() < this.globalFitness) {
                    gBest = p.getBestPosition();
                    globalFitness = p.getFitness();
                }
                p.setGlobalPosition(gBest);
            }
//            System.out.println(globalFitness);
            mediaFit[index] = globalFitness;
            index++;
        }
    }

    private void localTopology() throws IOException {
        for(int i = 0; i < this.particles.size(); i++) {
            Particle left;
            Particle right;
            Particle center = this.particles.get(i);
            if(i == 0) {
                left = this.particles.get(this.particles.size() - 1);
                right = this.particles.get(i+1);
            } else if(i == this.particles.size() - 1) {
                left = this.particles.get(i-1);
                right = this.particles.get(0);
            } else {
                left = this.particles.get(i - 1);
                right = this.particles.get(i + 1);
            }
            center.addNeighboor(left);
            center.addNeighboor(right);

        }

        for(int i = 0; i < Main.N_ITE; i++) {
            calculateInfo();
            compareGlobalNeighbors();
            checkGBest();
        }

    }

    private void compareGlobalNeighbors() {
        for(Particle part : this.particles) {
            double fitness = part.calculateFitness(this.function);
            for(Particle neigh : part.getNeighboors()) {
                double newFitness = neigh.calculateFitness(this.function);
                if(newFitness < fitness) {
                    fitness = newFitness;
                    part.setGlobalPosition(neigh.getPosition());
                }
            }
            for(Particle neigh : part.getNeighboors()) {
                neigh.setGlobalPosition(part.getGlobalPosition());
            }
        }

//        double fitness = part.calculateFitness(this.function);
//        int index = -1;
//        double[] gb = part.getPosition();
//        for(int j = 0; j < part.getNeighboors().size(); j++) {
//            Particle p = part.getNeighboors().get(j);
//            double newFitness = p.calculateFitness(this.function);
//            if(newFitness < fitness) {
//                fitness = newFitness;
//                index = j;
//                gb = p.getPosition();
//            }
//
//            if(newFitness < globalFitness) {
//                globalFitness = newFitness;
//                System.out.println(globalFitness);
////                bw.write(String.valueOf(globalFitness) + "\n");
//            }
//        }
//
//        if(index != -1) {
//            part.setGlobalPosition(gb);
//        }
//
//        for(Particle particle : part.getNeighboors()) {
//            particle.setGlobalPosition(gb);
//        }
    }
    private  void focalTopology() throws IOException {
        Particle p = this.particles.get(0);

        for(Particle particle : this.particles) {
            if(p != particle) {
                p.addNeighboor(particle);
            }
        }

        for(int i = 0; i < Main.N_ITE; i++) {
            calculateInfo();
            compareGlobalNeighbors();
            checkGBest();
        }
    }

    private void checkGBest() {
        for(Particle part : this.particles) {
            float fit = part.getFitness();
            if(fit < globalFitness) {
                globalFitness = fit;
                this.gBest = part.getBestPosition();
            }
        }
        mediaFit[index] = globalFitness;
        index++;
//        System.out.println(this.globalFitness);
    }

    public void start(int topology) throws IOException {
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


//        System.out.println(globalFitness);

    }

    public double getMedia() {
        double media = 0;
        for(Particle p : this.particles) {
            media += p.getMedia();
        }
        return media/this.particles.size();
    }

    public double getGlobalFitness() {
        return globalFitness;
    }

    public float[] getMediaFit() {
        return mediaFit;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}