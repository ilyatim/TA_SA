package fastFood.workers

import java.util.*

data class Chef(override val fullname: String,
                override var age: Int,
                override val id: Int,
                override var salary: Int,
                override val birthDate: Date
) : Worker