package fastFood

import fastFood.menu.Product
import fastFood.restInteraction.Order
import java.time.LocalDateTime

fun main() {
    val restaurant = Restaurant()
    restaurant.happyBirthday()
    println("Salary of director - ${restaurant.director.salary}")
    println("Birthday of our director - ${restaurant.director.birthDate}")
    println("Average salary of employees in the kitchen - ${restaurant.avgSalaryOnKitchen()}")
    println("Average salary of employees in the cash box - ${restaurant.avgSalaryOnCashBox()}")
    println("Average age of employees in the restaurant - ${restaurant.avgEmployeesAge()}")
    val order = Order(1)
    order.addNewOrder(Product.HAMBURGER)
    order.addNewOrder(Product.HAMBURGER)
    order.addNewOrder(Product.COCA_COLA)
    order.addNewOrder(Product.FRENCH_FRIES)
    restaurant.newOrder(restaurant.cashBox.getCashiers(0)!!, order)
}