import org.junit.Test

class ObjectTests {

    @Test
    fun builder_object_test() {
        val person = Person
            .builder()
            .name("Sambo Chea")
            .id(10)
            .build()

        println(person)
    }
}

data class Person(val id: Long? = null, val name: String? = null) {
    companion object {
        fun builder(): PersonBuilder {
            return PersonBuilder()
        }
    }
}

class PersonBuilder {
    private var id: Long? = null
    private var name: String? = null

    fun id(id: Long?) = apply { this.id = id }
    fun name(name: String?) = apply { this.name = name }

    fun build(): Person {
        return Person(id, name)
    }


}