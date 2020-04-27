package fastFood.workers

import java.util.*

interface Worker {
    val fullname: String
    var age: Int
    val id: Int
    var salary: Int
    val birthDate: Date
}