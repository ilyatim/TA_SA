package fastFood

import fastFood.restInteraction.CashBox
import fastFood.restInteraction.Kitchen
import fastFood.restInteraction.Order
import fastFood.workers.AssociateDirector
import fastFood.workers.Cashier
import fastFood.workers.Chef
import fastFood.workers.Director
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class Restaurant {
    val director: Director
    val subDirector: AssociateDirector

    val kitchen = Kitchen()
    val cashBox = CashBox()

    init {
        director = Director("Mixalkov Andrey Sergeevich", 33, 110011, 200000, Date(1987, 4, 12))
        subDirector = AssociateDirector("Timofeev Ilya Sergeevich", 22, 110001, 150000, Date(1998, 5, 13))
        kitchen.addNewWorker(Chef("Antonov Fedor Vladimirovich", 22, 100001, 40000, Date(1998, 6, 22)))
        kitchen.addNewWorker(Chef("Vladimirov Vladimir Olegovich", 26, 100002, 50000, Date(1994, 7, 26)))
        kitchen.addNewWorker(Chef("Petrov Alexey Andreevich", 46, 100003, 35000, Date(1974, 12, 4)))
        cashBox.addNewWorker(Cashier("Kotchin Anton Sergeevich", 20, 120001, 35000, Date(2000, 5, 2)))
        cashBox.addNewWorker(Cashier("Agapova Valeria Antonovna", 19, 120002, 40000, Date(2001, 3, 22)))
    }
    fun addNewEmployerAtKitchen(worker: Chef) = kitchen.addNewWorker(worker)
    fun addNewEmployerAtCashBox(worker: Cashier) = cashBox.addNewWorker(worker)
    fun removeEmployerFromKitchen(workerId: Int) = kitchen.removeWorker(workerId)
    fun removeEmployerFromCashBox(workerId: Int) = cashBox.removeWorker(workerId)
    fun newOrder(cashier: Cashier, order: Order) {
        cashBox.takeAnOrder(cashier.id, order)
        Thread.sleep(500)
        kitchen.makeOrder(cashBox.orders.remove(cashier.id)!!)
    }
    fun happyBirthday() {
        var someoneWithBirthday = false
        val date = LocalDateTime.now()
        for (worker in kitchen.getWorkersList()) {
            if (worker.birthDate.month == date.month.value &&
                worker.birthDate.date == (date.dayOfMonth)) {
                someoneWithBirthday = true
                println("Today is birthday of ${worker.fullname}")
            }
        }
        for (worker in cashBox.getWorkersList()) {
            if (worker.birthDate.month == date.month.value &&
                worker.birthDate.date == (date.dayOfMonth)) {
                someoneWithBirthday = true
                println("Today is birthday of ${worker.fullname}")
            }
        }
        if (director.birthDate.month == date.month.value &&
            director.birthDate.date == (date.dayOfMonth)) {
            someoneWithBirthday = true
            println("Today is birthday of our director - ${director.fullname}")
        }
        if (subDirector.birthDate.month == date.month.value &&
            subDirector.birthDate.date == (date.dayOfMonth)) {
            someoneWithBirthday = true
            println("Today is birthday of our sub director - ${subDirector.fullname}")
        }
        if (!someoneWithBirthday) println("No one seems to have a birthday today")
    }
    fun avgSalaryOnKitchen(): Int {
        var sumSalary = 0
        for (worker in kitchen.getWorkersList()) sumSalary += worker.salary
        return sumSalary / kitchen.getWorkersList().size
    }
    fun avgSalaryOnCashBox(): Int {
        var sumSalary = 0
        for (worker in cashBox.getWorkersList()) sumSalary += worker.salary
        return sumSalary / cashBox.getWorkersList().size
    }
    fun avgEmployeesAge(): Int {
        val numberOfEmployees = 2 + kitchen.getWorkersList().size + cashBox.getWorkersList().size
        var age = director.age + subDirector.age
        for (worker in kitchen.getWorkersList()) age += worker.age
        for (worker in cashBox.getWorkersList()) age += worker.age
        return age / numberOfEmployees
    }
}