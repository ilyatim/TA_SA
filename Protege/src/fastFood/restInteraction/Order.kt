package fastFood.restInteraction

import fastFood.menu.Product

class Order(private val orderNumber: Int) {
    private val orderList = arrayListOf<Product>()

    fun getOrderNumber() = orderNumber
    fun getOrderList() = orderList
    fun addNewOrder(product: Product) = orderList.add(product)
}