package ParticleSwarm

class CarsFuelFitnessFunction : FitnessFunction {
    override fun getFitness(particlePosition: LongArray?): Double {

        val fuel = particlePosition!![0]
        val speed = particlePosition[1]
        if (fuel < 0 && speed < 0) {
            return -(fuel * speed).toDouble()
        } else if (fuel < 0) {
            return fuel.toDouble()
        } else if (speed < 0) {
            return speed.toDouble()
        }
        if (fuel > 100) return (100 - speed).toDouble()
        else {
            val action = (fuel * (100 + speed) / 100)
            return action.toDouble()
        }
    }

}