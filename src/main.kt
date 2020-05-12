import AntColonyAlgorithm.algorithm.ant.AntColonyOptimization
import ParticleSwarm.LolFitnessFunction
import ParticleSwarm.Multiswarm
import java.util.*


fun main() {
    val scanner = Scanner(System.`in`)
    println("Run algorithm:")
    println("1 - Ant Colony Algorithm")
    println("2 - Multi Swarm Algorithm")
    println("3 - ")
    when (scanner.nextInt()) {
        1 -> {
            val antColony = AntColonyOptimization(21)
            antColony.startAntOptimization()
        }
        2 -> {
            val multiswarm = Multiswarm(50, 1000, LolFitnessFunction())
            for (i in 0..999) {
                multiswarm.mainLoop()
            }
            println("Best fitness found: ${multiswarm.bestFitness } [${multiswarm.bestPosition[0]}, ${multiswarm.bestPosition[1]}]")
            println(1080.toLong() == multiswarm.bestPosition[0])
            println(50.toLong() == multiswarm.bestPosition[1])
            println(1620 == multiswarm.bestFitness.toInt())
        }
        3 -> {

        }
        else -> println("Unknown option")
    }
    scanner.close()
}