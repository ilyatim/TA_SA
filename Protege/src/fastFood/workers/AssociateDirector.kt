package fastFood.workers

import java.util.*

data class AssociateDirector(
    override val fullname: String,
    override var age: Int,
    override var id: Int,
    override var salary: Int,
    override val birthDate: Date
) : Director(fullname, age, id, salary, birthDate)