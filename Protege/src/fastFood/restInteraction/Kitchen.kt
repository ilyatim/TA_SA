package fastFood.restInteraction

import fastFood.workers.Chef
import fastFood.workers.Worker

class Kitchen {
    private val workersList = arrayListOf<Chef>()

    fun addNewWorker(worker: Chef) {
        println("You took a new employee to the kitchen - ${worker.fullname}")
        workersList.add(worker)
    }
    fun makeOrder(order: Order) {
        println("Order - ${order.getOrderNumber()} is ready")
    }
    fun getWorkersList() = workersList
    fun removeWorker(id: Int)  {
        println("you fired the kitchen worker - $id - ${workersList[id].fullname}")
        workersList.removeAt(id)
    }
}