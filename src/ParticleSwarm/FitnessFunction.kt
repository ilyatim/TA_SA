package ParticleSwarm

interface FitnessFunction {
    fun getFitness(particlePosition: LongArray?): Double
}