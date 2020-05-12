package ParticleSwarm

import java.util.*

data class Swarm(var numParticles: Int) {
    var bestPosition: LongArray? = null
    val particles: Array<Particle?> = arrayOfNulls(numParticles)
    var bestFitness = Double.NEGATIVE_INFINITY
    private val random: Random = Random()

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        val temp: Long = java.lang.Double.doubleToLongBits(bestFitness)
        result = prime * result + (temp xor (temp ushr 32)).toInt()
        result = prime * result + (bestPosition?.contentHashCode() ?: 0)
        result = prime * result + particles.contentHashCode()
        result = prime * result + random.hashCode()
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as Swarm
        if (java.lang.Double.doubleToLongBits(bestFitness) != java.lang.Double.doubleToLongBits(other.bestFitness)) return false
        if (!other.bestPosition?.let { bestPosition?.contentEquals(it) }!!) return false
        if (!particles.contentEquals(other.particles)) return false
        if (random != other.random) return false
        return true
    }

    init {
        for (i in 0 until numParticles) {
            val initialParticlePosition = longArrayOf(
                random.nextInt(Constants.PARTICLE_UPPER_BOUND).toLong(),
                random.nextInt(Constants.PARTICLE_UPPER_BOUND).toLong()
            )
            val initialParticleSpeed = longArrayOf(
                random.nextInt(Constants.PARTICLE_UPPER_BOUND).toLong(),
                random.nextInt(Constants.PARTICLE_UPPER_BOUND).toLong()
            )
            particles[i] = Particle(initialParticlePosition, initialParticleSpeed)
        }
    }
}