package ParticleSwarm.fitness

interface FitnessFunction {
    fun getFitness(particlePosition: LongArray?): Double
}