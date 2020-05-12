package ParticleSwarm

data class Particle(var position: LongArray,
                    var speed: LongArray) {

    var fitness = 0.0
    var bestPosition: LongArray? = null
    var bestFitness = Double.NEGATIVE_INFINITY

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        var temp: Long = java.lang.Double.doubleToLongBits(bestFitness)
        result = prime * result + (temp xor (temp ushr 32)).toInt()
        result = prime * result + (bestPosition?.contentHashCode() ?: 0)
        temp = java.lang.Double.doubleToLongBits(fitness)
        result = prime * result + (temp xor (temp ushr 32)).toInt()
        result = prime * result + position.contentHashCode()
        result = prime * result + speed.contentHashCode()
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as Particle
        if (java.lang.Double.doubleToLongBits(bestFitness) != java.lang.Double.doubleToLongBits(other.bestFitness)) return false
        if (!other.bestPosition?.let { bestPosition?.contentEquals(it) }!!) return false
        if (java.lang.Double.doubleToLongBits(fitness) != java.lang.Double.doubleToLongBits(other.fitness)) return false
        if (!position.contentEquals(other.position)) return false
        return speed.contentEquals(other.speed)
    }

}