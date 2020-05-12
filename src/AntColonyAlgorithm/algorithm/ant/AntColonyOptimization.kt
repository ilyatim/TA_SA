package AntColonyAlgorithm.algorithm.ant

import java.util.*
import java.util.function.Consumer
import java.util.stream.IntStream
import kotlin.math.abs
import kotlin.math.pow


class AntColonyOptimization(noOfCities: Int) {
    private val c = 1.0             //indicates the original number of trails
    private val alpha = 1.0         //controls the pheromone importance
    private val beta = 5.0          //controls the distance priority
    private val evaporation = 0.5   //shows the percent how much the pheromone is evaporating in every iteration
    private val q = 500.0           //provides information about the total amount of pheromone left on the trail by each Ant
    private val antFactor = 0.8     //tells us how many ants we'll use per city
    private val randomFactor = 0.01

    private val maxIterations = 1000

    private val numberOfCities: Int
    private val numberOfAnts: Int

    private val graph: Array<DoubleArray>
    private val trails: Array<DoubleArray>

    private val ants: MutableList<Ant> = ArrayList()

    private val random = Random()
    private val probabilities: DoubleArray

    private var currentIndex = 0
    private var bestTourOrder: IntArray? = null
    private var bestTourLength = 0.0

    private fun generateRandomMatrix(n: Int): Array<DoubleArray> {
        val randomMatrix =
            Array(n) { DoubleArray(n) }
        IntStream.range(0, n)
            .forEach { i: Int ->
                IntStream.range(0, n)
                    .forEach { j: Int ->
                        randomMatrix[i][j] = abs(random.nextInt(100) + 1).toDouble()
                    }
            }
        return randomMatrix
    }

        fun startAntOptimization() {
        IntStream.rangeClosed(1, 3)
            .forEach { i: Int ->
                println("Attempt #$i")
                solve()
            }
    }

    private fun solve(): IntArray {
        setupAnts()
        clearTrails()
        IntStream.range(0, maxIterations)
            .forEach { _: Int ->
                moveAnts()
                updateTrails()
                updateBest()
            }
        println("Best length: " + (bestTourLength - numberOfCities))
        println("Best order: " + bestTourOrder!!.contentToString())
        return bestTourOrder?.clone()!!
    }

    private fun setupAnts() {
        IntStream.range(0, numberOfAnts)
            .forEach { _: Int ->
                ants.forEach(Consumer { ant: Ant ->
                    ant.clear()
                    ant.visitCity(-1, random.nextInt(numberOfCities))
                })
            }
        currentIndex = 0
    }

    private fun moveAnts() {
        IntStream.range(currentIndex, numberOfCities - 1)
            .forEach { _: Int ->
                ants.forEach(Consumer { ant: Ant ->
                    ant.visitCity(
                        currentIndex,
                        selectNextCity(ant)
                    )
                })
                currentIndex++
            }
    }

    private fun selectNextCity(ant: Ant): Int {
        val t = random.nextInt(numberOfCities - currentIndex)
        if (random.nextDouble() < randomFactor) {
            val cityIndex = IntStream.range(0, numberOfCities)
                .filter { i: Int -> i == t && !ant.visited(i) }
                .findFirst()
            if (cityIndex.isPresent) {
                return cityIndex.asInt
            }
        }
        calculateProbabilities(ant)
        val r = random.nextDouble()
        var total = 0.0
        for (i in 0 until numberOfCities) {
            total += probabilities[i]
            if (total >= r) {
                return i
            }
        }
        throw RuntimeException("There are no other cities")
    }

    private fun calculateProbabilities(ant: Ant) {
        val i = ant.trail[currentIndex]
        var pheromone = 0.0
        for (l in 0 until numberOfCities) {
            if (!ant.visited(l)) {
                pheromone += trails[i][l].pow(alpha) * (1.0 / graph[i][l]).pow(beta)
            }
        }
        for (j in 0 until numberOfCities) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0
            } else {
                val numerator = trails[i][j].pow(alpha) * (1.0 / graph[i][j]).pow(beta)
                probabilities[j] = numerator / pheromone
            }
        }
    }

    private fun updateTrails() {
        for (i in 0 until numberOfCities) {
            for (j in 0 until numberOfCities) {
                trails[i][j] *= evaporation
            }
        }
        for (a in ants) {
            val contribution = q / a.trailLength(graph)
            for (i in 0 until numberOfCities - 1) {
                trails[a.trail[i]][a.trail[i + 1]] += contribution
            }
            trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution
        }
    }

    private fun updateBest() {
        if (bestTourOrder == null) {
            bestTourOrder = ants[0].trail
            bestTourLength = ants[0]
                .trailLength(graph)
        }
        for (a in ants) {
            if (a.trailLength(graph) < bestTourLength) {
                bestTourLength = a.trailLength(graph)
                bestTourOrder = a.trail.clone()
            }
        }
    }

    private fun clearTrails() {
        IntStream.range(0, numberOfCities)
            .forEach { i: Int ->
                IntStream.range(0, numberOfCities)
                    .forEach { j: Int -> trails[i][j] = c }
            }
    }

    init {
        graph = generateRandomMatrix(noOfCities)
        numberOfCities = graph.size
        numberOfAnts = (numberOfCities * antFactor).toInt()
        trails = Array(numberOfCities) { DoubleArray(numberOfCities) }
        probabilities = DoubleArray(numberOfCities)
        IntStream.range(0, numberOfAnts)
            .forEach { _: Int -> ants.add(Ant(numberOfCities)) }
    }
}