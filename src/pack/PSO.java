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
    private double globalFitness;
    private int function;
    File file = new File("./media.txt");
    FileWriter fw;
    BufferedWriter bw;

    public PSO(int numDim, int numPart, int function) throws IOException {
        fw = new FileWriter(file.getAbsolutePath());
        bw = new BufferedWriter(fw);
        this.function = function;
        this.particles = new ArrayList<>();
        this.globalFitness = 999999999;
        for(int i = 0; i < numPart; i++) {
            Particle particle = new Particle(numDim);
            this.particles.add(particle);
            if(gBest == null) {
                gBest = new double[particle.getBestPosition().length];
                gBest = particle.getBestPosition();
            }
        }

    }

    private void calculateInfo() {
        for(Particle particle : this.particles) {
            particle.setNewVelocity();
            particle.setNewPosition();
            particle.updateBestPosition(this.function);
        }
    }

    private void globalTopology() throws IOException {
        for(int it = 0; it < Main.N_ITE; it++) {
            calculateInfo();
            for(Particle p : this.particles) {
                if(p.getFitness() < this.globalFitness) {
                    gBest = p.getBestPosition();
                    globalFitness = p.getFitness();
                    bw.write(String.valueOf(globalFitness) + "\n");
                }
                p.setGlobalPosition(gBest);
            }
        }
    }

    private void localTopology() {
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
            for(Particle part : this.particles) {
                compareGlobalNeighbors(part);
            }
        }

        checkGBest();

    }

    private void compareGlobalNeighbors(Particle part) {
        double fitness = part.calculateFitness(this.function);
        int index = -1;
        double[] gb = part.getPosition();
        for(int j = 0; j < part.getNeighboors().size(); j++) {
            Particle p = part.getNeighboors().get(j);
            double newFitness = p.calculateFitness(this.function);
            if(newFitness < fitness) {
                fitness = newFitness;
                index = j;
                gb = p.getPosition();
            }
        }

        if(index != -1) {
            part.setGlobalPosition(gb);
        }

        for(Particle particle : part.getNeighboors()) {
            calculateInfo();
            particle.setGlobalPosition(gb);
        }
    }
    private  void focalTopology() {
        Particle p = this.particles.get(0);

        for(Particle particle : this.particles) {
            if(p != particle) {
                particle.addNeighboor(p);
            } else {
                p.addNeighboor(particle);
            }
        }

        for(int i = 0; i < Main.N_ITE; i++) {
            calculateInfo();
            for(Particle part : this.particles) {
                compareGlobalNeighbors(part);
            }
        }
        checkGBest();
    }

    private void checkGBest() {
        for(Particle part : this.particles) {
            double fit = part.getFitness();
            if(fit < globalFitness) {
                globalFitness = fit;
                this.gBest = part.getBestPosition();
            }
        }
    }

    public void start(int topology) throws IOException {
//        for (int i = 0; i < Main.N_ITE; i++) {
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
//            if(i == 0) {
//                bw.write(String.valueOf(String.valueOf(globalFitness)) + "\n\n");
//            }
            double media = getMedia();
            bw.write(String.valueOf(media) + "\n");
//            System.out.println(media);
//            pw.write(String.valueOf(getMedia()) + "\n");
//        }

//        System.out.println("\n\n" + String.valueOf(globalFitness));
//        bw.write(String.valueOf("\n\n\n\n\n\n" + String.valueOf(globalFitness)));
        bw.close();

    }

    public double getMedia() {
        double media = 0;
        for(Particle p : this.particles) {
            media += p.getMedia();
        }
        return media/this.particles.size();
    }
}
