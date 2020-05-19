package ParticleSwarm.fitness

import ParticleSwarm.fitness.FitnessFunction

class LolFitnessFunction : FitnessFunction {
    override fun getFitness(particlePosition: LongArray?): Double {
        val health = particlePosition!![0]
        val armor = particlePosition[1]

        if (health < 0 && armor < 0) {
            return (-(health * armor)).toDouble()
        } else if (health < 0) {
            return health.toDouble()
        } else if (armor < 0) {
            return armor.toDouble()
        }

        val cost = health * 2.5 + armor * 18

        return if (cost > 3600) {
            3600 - cost
        } else {
            val fitness = health * (100 + armor) / 100
            fitness.toDouble()
        }
    }
}