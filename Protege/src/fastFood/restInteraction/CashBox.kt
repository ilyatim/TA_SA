package fastFood.restInteraction

import fastFood.workers.Cashier

class CashBox {
    private val cashiers = arrayListOf<Cashier>()

    val orders = mutableMapOf<Int, Order>()

    fun getWorkersList() = cashiers
    fun getCashiers(number: Int): Cashier? {
        if (number > cashiers.size) return null
        return cashiers[number]
    }
    fun addNewWorker(worker: Cashier) {
        println("You took a new employee to the cash box - ${worker.fullname}")
        cashiers.add(worker)
    }
    fun removeWorker(id: Int) {
        println("you fired the cash box worker - $id - ${cashiers[id].fullname}")
        cashiers.removeAt(id)
    }
    fun takeAnOrder(cashierNumber: Int, order: Order) {
        println("Order ${order.getOrderNumber()} is accepted by cashier - $cashierNumber ")
        orders[cashierNumber] = order
    }
}