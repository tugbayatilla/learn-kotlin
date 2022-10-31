import java.util.UUID

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}

fun funArray() {
    var arr1: Array<String> = arrayOf("CSharp", "Kotlin")

    for (arr in arr1) {
        println(arr)
    }
}

fun funArray2(vararg input: String): Unit { // Unit is used for void
    input.forEach { println(it) }
}


fun funArray3(vararg input: String): Unit {
    input.forEach { item -> println(item) } //lambda function
}

fun funArgument(vararg input: String): Unit {
    for (i in input) {
        println(i)
    }
}

// Class Definitions

class Person1(_firstName: String, _lastName: String) {
    val firstName: String
    val lastName: String

    init {
        firstName = _firstName
        lastName = _lastName
    }
}

fun instantiateOfPerson1() {
    val person = Person1("Tugbay", "Atilla")
    person.firstName
    person.lastName
}


class Person2(_firstName: String, _lastName: String) {
    val firstName: String = _firstName
    val lastName: String = _lastName
}

fun instantiateOfPerson2() {
    val person = Person2("Tugbay", "Atilla")
    person.firstName
    person.lastName
}

class Person3(val firstName: String, val lastName: String) {
}

fun instantiateOfPerson3() {
    val person = Person3("Tugbay", "Atilla")
    person.firstName
    person.lastName
}

class Person4(val firstName: String, val lastName: String) {
    init {
        println("init 1")
    }

    constructor() : this("Tugbay", "Atilla") {
        println("secondary constructor")
    }

    init {
        println("init 2")
    }
}

fun instantiateOfPerson4() {
    val person = Person4()
    person.firstName
    person.lastName

    // init 1
    // init 2
    // secondary constructor
}

interface PersonInterface

class Jason : PersonInterface {
}

fun funClass(person: PersonInterface): Unit {
    var message = if (person is Jason) "the is Jason" else "who?"
    println(message)
}

//public(default), private, internal, protected
class Person(val firstName: String = "Tugbay", val lastName: String = "Atilla") {

    //public(default), private, internal, protected
    var nickname: String? = null //nullable property definition
        set(value) { //explicit setter: no need to define to set value to property
            field = value
            println("the new nickname is $value")
        }
        get() { //explicit getter: no need to define to get value from property
            println("the returned value is $field")
            return field
        }

    fun printInfo() {
        val nickNameToPrint = if (nickname == null) "no nickname" else nickname
        val nickNameToPrint2 = nickname ?: "no nickname" //alternative - object null check operator is ?:

        println("$firstName ($nickNameToPrint) $lastName")
    }
}

fun instantiateOfPerson() {
    val person = Person()
    person.firstName
    person.lastName
    person.nickname = "Sprite"

    person.printInfo();
}


interface YetAnotherInterfaceWithoutParanthesis

class YetAnotherClassWithoutParanthesis : YetAnotherInterfaceWithoutParanthesis


interface PersonInfoProvider {
    val providerInfo: String

    fun printInfo(person: Person) {
        //println("personInfoProvider")
        println(providerInfo)
        person.printInfo()
    }
}

// abstract
abstract class AbstractBasicInfoProvider : PersonInfoProvider {

}

class BasicInfoProvider : PersonInfoProvider {
//    override fun printInfo(person: Person) { // instead of keeping here, move to the interface
//        println("basicInfoProvider")
//        person.printInfo()
//    }

    override val providerInfo: String
        get() = "basicProviderInfo"

    override fun printInfo(person: Person) {
        super.printInfo(person)
        println("additional print statement")
    }
}

fun instantiateOfBasicInfoProvider() {
    val provider = BasicInfoProvider()

    provider.printInfo(Person())
}


interface SessionInfoProvider {
    fun getSessionId(): String
}

class BasicInfoProvider2 : PersonInfoProvider, SessionInfoProvider {

    override val providerInfo: String
        get() = "basicProviderInfo"

    override fun printInfo(person: Person) {
        super.printInfo(person)
        println("additional print statement")
    }

    override fun getSessionId(): String {
        return "session"
    }
}

fun usage_Of_BasicInfoProvider2() {
    val provider = BasicInfoProvider2()

    provider.printInfo(Person())
    provider.getSessionId()
}

fun checkTypes(infoProvider: PersonInfoProvider) {
    if (infoProvider !is SessionInfoProvider) {
        println("not a session provider")
    } else {
        println("is a session provider")
        //(infoProvider as SessionInfoProvider).getSessionInfo() // no need to cast because of if
        infoProvider.getSessionId()
    }

}

open class BasicInfoProvider3 : PersonInfoProvider, SessionInfoProvider {

    override val providerInfo: String
        get() = "basicProviderInfo"

    override fun printInfo(person: Person) {
        super.printInfo(person)
        println("additional print statement")
    }

    override fun getSessionId(): String {
        return sessionIdPrefix
    }

    protected open val sessionIdPrefix: String = "prefix"
}

// make BasicInfoProvider3 as open
class FancyInfoProvider : BasicInfoProvider3() {
    override val providerInfo: String
        get() = "fancy info provider"

    override fun printInfo(person: Person) {
        super.printInfo(person)
        println("fancy print info")
    }

    override val sessionIdPrefix: String
        get() = "fancy session"
}

fun usage_Of_FancyInfoProvider() {
    val provider = FancyInfoProvider()

    //provider.sessionIdPrefix is not visible because of protected

    provider.printInfo(Person())
    provider.getSessionId()
}


//Object expression
fun usage_Of_ObjectExpression() {
    val provider = object : PersonInfoProvider {
        override val providerInfo: String
            get() = "New Info Provider"

        fun getSessionId() = "id"
    }

    provider.printInfo(Person())
    provider.getSessionId()
}

// Entity Factory

class Entity0 private constructor(val id: String) // cannot be instantiated


class Entity1 private constructor(val id: String) {
    companion object {
        fun create() = Entity1("id")
    }
}

fun usage_Of_Companion() {
    val entity = Entity1.Companion.create() //option 1
    val entity1 = Entity1.create() //option 2
}

class Entity2 private constructor(val id: String) {
    companion object Factory { //explicit definition
        fun create() = Entity2("id")
    }
}

fun usage_Of_Companion2() {
    val entity = Entity2.Factory.create() //option 1
    val entity1 = Entity2.create() //option 2
}


class Entity3 private constructor(val id: String) {
    companion object Factory {
        const val id = "id"
        fun create() = Entity3(id)
    }
}

fun usage_Of_Companion3() {
    val entity = Entity3.create()
    Entity3.id // like static property
}


interface IdProvider {
    fun getId(): String
}

class Entity4 private constructor(val id: String) {
    companion object Factory : IdProvider {

        fun create() = Entity4(getId())

        override fun getId(): String {
            return "123"
        }
    }
}

fun usage_Of_Companion4() {
    val entity = Entity4.create()
    Entity4.getId()
}

object EntityFactory5 {
    fun create() = Entity5("id", "name")
}

class Entity5(val id: String, val name: String) {
    override fun toString(): String {
        return "id:$id name:$name"
    }
}

fun usage_Of_Companion5() {
    val entity = EntityFactory5.create()
    println(entity)
}

// Enum Classes

enum class EntityType {
    EASY, MEDIUM, HARD; // interesting to use ;

    fun getFotmattedName() = name.lowercase().capitalize()
}

object EntityFactory6 {
    fun create(type: EntityType): Entity6 {
        val id = UUID.randomUUID().toString()
        val name = when (type) {
            EntityType.EASY -> "EASY"
            EntityType.MEDIUM -> type.getFotmattedName() // returns Medium
            EntityType.HARD -> type.name // another option: returns HARD
        }
        return Entity6(id, name)
    }
}

class Entity6(val id: String, val name: String) {
    override fun toString(): String {
        return "id:$id name:$name"
    }
}

fun usage_Of_Entity6() {
    val entity = EntityFactory6.create(EntityType.MEDIUM)
    println(entity)
}

// Sealed Class

enum class EntityType7 {
    HELP, EASY, MEDIUM, HARD; // interesting to use ;

    fun getFotmattedName() = name.lowercase().capitalize()
}

object EntityFactory7 {
    fun create(type: EntityType7): Entity7 {
        val id = UUID.randomUUID().toString()
        val name = when (type) {
            EntityType7.EASY -> "EASY"
            EntityType7.MEDIUM -> type.getFotmattedName() // returns Medium
            EntityType7.HARD -> type.name // another option: returns HARD
            EntityType7.HELP -> type.getFotmattedName() // returns Help
        }
        return when (type) {
            EntityType7.EASY -> Entity7.Easy(id, name)
            EntityType7.MEDIUM -> Entity7.Medium(id, name)
            EntityType7.HARD -> Entity7.Hard(id, name, 5.6f)
            EntityType7.HELP -> Entity7.Help
        }
    }
}

sealed class Entity7() { // sealed class cannot be instantiated directly
    object Help : Entity7() {
        val name = "Help"
    }

    data class Easy(val id: String, val name: String) :
        Entity7() //data class is useful when you want the data in the class

    data class Medium(val id: String, val name: String) : Entity7()
    data class Hard(val id: String, val name: String, val multiplier: Float) : Entity7()
}

fun usage_Of_Entity7() {
    val entity: Entity7 = EntityFactory7.create(EntityType7.MEDIUM)
    val message = when (entity) {
        Entity7.Help -> "Help class"
        is Entity7.Easy -> "Easy class"
        is Entity7.Medium -> "Medium class"
        is Entity7.Hard -> "Hard class"
    }

    println(message)
}


// Data classes
fun usage_Of_DataClasses7() {
    val entity = EntityFactory7.create(EntityType7.MEDIUM)
    val entity1 = EntityFactory7.create(EntityType7.MEDIUM)
    println(entity == entity1) // False, because id property value is different in the classes

    val entity2 = Entity7.Easy("id", "name")
    val entity3 = Entity7.Easy("id", "name")
    println(entity2 == entity3) // True, because the "data" inside classes are same.
    println(entity2 === entity3) // False, because they are NOT "exact" reference objects.
    println(entity2 === entity2) // True, because they are "exact" reference objects.

    val entity4 = Entity7.Easy("id", "name")
    val entity5 = entity4.copy()
    println(entity4 == entity5) // True, because we used copy function

    val entity6 = Entity7.Easy("id", "name")
    val entity7 = entity6.copy(name = "changed name")
    println(entity6 == entity7) // False, because we changed the name while copying the class


}

// Extension Methods

sealed class Entity8() { // sealed class cannot be instantiated directly

    data class Medium(val id: String, val name: String) : Entity8()

}

// here is the extension method for the Medium class in Entity8
// looks like javascript prototype
fun Entity8.Medium.printInfo() {
    println("Medium class: $id")
}

fun usage_Of_Entity8_ExtendMethod() {
    Entity8.Medium("id", "name").printInfo()
}


// Extension Properties

val Entity8.Medium.info: String
    get() = "some info from extension property "

fun usage_Of_Entity8_ExtendProperty() {
    Entity8.Medium("id", "name").info
}

// Advanced Functions

// high order functions

fun printFilteredStrings(list: List<String>, predicate: (String) -> Boolean) {
    list.forEach {
        if (predicate(it)) {
            println(it)
        }
    }
}

fun printFilteredStringsNullablePredicate(list: List<String>, predicate: ((String) -> Boolean)?) {
    list.forEach {
        if (predicate?.invoke(it) == true) {
            println(it)
        }
    }
}

// function definition as variable
val predicate: (String) -> Boolean = { it.startsWith("K") }

// function returns a function
// function returns another function with signature "(String) -> Boolean"
fun getPrintPredicate(): (String) -> Boolean {
    return { it.startsWith("K") }
}

fun usage_Of_highFilteredStrings() {
    val list = listOf("Kotlin", "Java", "c++")

    printFilteredStrings(list, { it.startsWith("K") })  // return Kotlin

    // if the lambda function is the last parameter of the function,
    // the lambda function can be used right after the function like below
    printFilteredStrings(list) { it.startsWith("K") }  // return Kotlin

    printFilteredStringsNullablePredicate(list, null) // return nothing

    printFilteredStringsNullablePredicate(list, predicate) // return Kotlin

    printFilteredStringsNullablePredicate(list, getPrintPredicate()) // return Kotlin
}


// generic functions

fun generics_without_null() {
    var list = listOf("CSharp", "Kotlin", "C++", "Javascript", "Java")

    list
        .filter { it.startsWith("J") }
        .forEach { println(it) }
}

fun generics_with_null() {
    var list = listOf("CSharp", "Kotlin", "C++", "Javascript", "Java", null, null)

    list
        .filterNotNull() //filters the null values
        .filter { it.startsWith("K") }
        .forEach { println(it) } // Kotlin
}

fun generics_with_null_and_map() {
    var list = listOf("CSharp", "Kotlin", "C++", "Javascript", "Java", null, null)

    list
        .filterNotNull() //filters the null values
        .filter { it.startsWith("J") }
        .map { it.length } // takes string and returns integer
        .forEach { println(it) } //returns 6
}

fun generics_with_null_and_take() {
    var list = listOf("CSharp", "Kotlin", "C++", "Javascript", "Java", null, null)

    list
        .filterNotNull() //filters the null values
        .take(3) // first 3
        // .takeLast(3) // last 3
        .forEach { println(it) } //returns 6
}


fun generics_with_null_and_associate() {
    var list = listOf("CSharp", "Kotlin", "C++", "Javascript", "Java", null, null)

    list
        .filterNotNull() //filters the null values
        .associate { it to it.length }
        .forEach { println("${it.value}, ${it.key}") } //6, Kotlin ....
}

fun generics_with_null_and_associate_map() {
    var list = listOf("CSharp", "Kotlin", "C++", "Javascript", "Java", null, null)

    val map = list
        .filterNotNull() //filters the null values
        .associate { it to it.length }

}




