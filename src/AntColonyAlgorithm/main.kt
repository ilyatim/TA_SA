package AntColonyAlgorithm

import AntColonyAlgorithm.algorithm.ant.AntColonyOptimization
import java.util.*


fun main() {
    val scanner = Scanner(System.`in`)
    println("Run algorithm:")
    println("1 - Ant Colony Algorithm")
    when (scanner.nextInt()) {
        1 -> {
            val antColony = AntColonyOptimization(21)
            antColony.startAntOptimization()
        }
        else -> println("Unknown option")
    }
    scanner.close()
}