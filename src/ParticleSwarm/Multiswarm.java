package ParticleSwarm;

import java.util.Arrays;
import java.util.Random;

/**
 * Represents a collection of {@link Swarm}.
 *
 * @author Donato Rimenti
 *
 */
public class Multiswarm {

    private Swarm[] swarms;
    private long[] bestPosition;
    private double bestFitness = Double.NEGATIVE_INFINITY;
    private Random random = new Random();
    private FitnessFunction fitnessFunction;

    public Multiswarm(int numSwarms, int particlesPerSwarm, FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
        this.swarms = new Swarm[numSwarms];
        for (int i = 0; i < numSwarms; i++) {
            swarms[i] = new Swarm(particlesPerSwarm);
        }
    }

    public void mainLoop() {
        for (Swarm swarm : swarms) {
            for (Particle particle : swarm.getParticles()) {
                long[] particleOldPosition = particle.getPosition().clone();
                particle.setFitness(fitnessFunction.getFitness(particleOldPosition));

                if (particle.getFitness() > particle.getBestFitness()) {
                    particle.setBestFitness(particle.getFitness());
                    particle.setBestPosition(particleOldPosition);

                    if (particle.getFitness() > swarm.getBestFitness()) {
                        swarm.setBestFitness(particle.getFitness());
                        swarm.setBestPosition(particleOldPosition);

                        if (swarm.getBestFitness() > bestFitness) {
                            bestFitness = swarm.getBestFitness();
                            bestPosition = swarm.getBestPosition().clone();
                        }

                    }
                }

                long[] position = particle.getPosition();
                long[] speed = particle.getSpeed();

                position[0] += speed[0];
                position[1] += speed[1];

                speed[0] = getNewParticleSpeedForIndex(particle, swarm, 0);
                speed[1] = getNewParticleSpeedForIndex(particle, swarm, 1);
            }
        }
    }

    private int getNewParticleSpeedForIndex(Particle particle, Swarm swarm, int index) {
        return (int) ((Constants.INERTIA_FACTOR * particle.getSpeed()[index])
                + (randomizePercentage(Constants.COGNITIVE_WEIGHT)
                * (particle.getBestPosition()[index] - particle.getPosition()[index]))
                + (randomizePercentage(Constants.SOCIAL_WEIGHT)
                * (swarm.getBestPosition()[index] - particle.getPosition()[index]))
                + (randomizePercentage(Constants.GLOBAL_WEIGHT)
                * (bestPosition[index] - particle.getPosition()[index])));
    }

    private double randomizePercentage(double value) {
        return random.nextDouble() * value;
    }

    public long[] getBestPosition() {
        return bestPosition;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(bestFitness);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + Arrays.hashCode(bestPosition);
        result = prime * result + ((fitnessFunction == null) ? 0 : fitnessFunction.hashCode());
        result = prime * result + ((random == null) ? 0 : random.hashCode());
        result = prime * result + Arrays.hashCode(swarms);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Multiswarm other = (Multiswarm) obj;
        if (Double.doubleToLongBits(bestFitness) != Double.doubleToLongBits(other.bestFitness))
            return false;
        if (!Arrays.equals(bestPosition, other.bestPosition))
            return false;
        if (fitnessFunction == null) {
            if (other.fitnessFunction != null)
                return false;
        } else if (!fitnessFunction.equals(other.fitnessFunction))
            return false;
        if (random == null) {
            if (other.random != null)
                return false;
        } else if (!random.equals(other.random))
            return false;
        return Arrays.equals(swarms, other.swarms);
    }

    @Override
    public String toString() {
        return "Multiswarm [swarms=" + Arrays.toString(swarms) + ", bestPosition=" + Arrays.toString(bestPosition)
                + ", bestFitness=" + bestFitness + ", random=" + random + ", fitnessFunction=" + fitnessFunction + "]";
    }

}