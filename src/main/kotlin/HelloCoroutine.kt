import kotlinx.coroutines.*
import kotlin.random.Random

var counter: Int = 0

fun main() {
    sampleCoroutineExceptionHandler()
    sampleAsyncCoroutineExceptionHandler()
}

private fun sampleAsyncCoroutineExceptionHandler() {
    runBlocking {
        val defered = GlobalScope.async {
            delay(1000L)
            println("throwing exception from async")
            throw ArithmeticException("exception in async")
        }

        try {
            defered.await()
        } catch (e: ArithmeticException) {
            println("Caught ArithmeticException ${e.localizedMessage}")
        }
    }
}

private fun sampleCoroutineExceptionHandler() {
    runBlocking {
        val myExceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
            println("Exception thrown in one of the children: ${exception.localizedMessage}")
        }

        val job = GlobalScope.launch(myExceptionHandler) {
            println("Throwing exception from launch")
            throw IndexOutOfBoundsException("exception in coroutine") // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
        }
        job.join()
    }
}

private fun sampleChangeCorroutineDispatcherThread() {
    runBlocking {
        launch(Dispatchers.Default) {
            withContext(Dispatchers.IO) {
                println("Start IO ")
                delay(1000L)
                println("End IO ")
            }
            println("Back to Default ")
        }
    }
}

private fun asyncDiferredCoroutineSample() {
    runBlocking {
        println("Start runBlocking")
        val deferredNumber1 = async { getRandNumber(1000, "first") }
        val deferredNumber2 = async { getRandNumber(2000, "second") }
        println("Processing data...  ")
        delay(500L)
        println("Waitting for data...  ")

        val number1 = deferredNumber1.await()
        val number2 = deferredNumber2.await()

        println("The sum of $number1 + $number2 is ${number1 + number2}")
    }
}

suspend fun getRandNumber(delay: Long, tag: String): Int {
    delay(delay)
    val randomNumber = Random.nextInt(100)
    println("$tag-Random number is $randomNumber")
    return randomNumber
}

private fun launchTwoCoroutinesBeforeSleepButFastThanSleep() {
    GlobalScope.launch { fatMessage() } // 3
    GlobalScope.launch { fastMessage() } // 2
    print("Hello, ") // 1
    Thread.sleep(2000L)
    println("there have been ${counter} coroutines") // 4
}

fun fatMessage() {
    Thread.sleep(1000L)
    println("Long Message: Corountines are cool")
    counter++
}

suspend fun fastMessage() {
    delay(500L)
    println("World!")
    counter++
}

/**
 * This is a sample of how sunBlocking works
 * The laundhes inside the runBlocking will be executed in order 1,2,3
 * But each delay suspended function inside the coroutines scopes appers in the order 1,2,3
 * It´s because all coroutines are executed in paralell launching the suspended functions that takes some seconds more
 * when all the suspended functions finish then the runBlocking finish and print "After runBlocking"
 */
private fun sunBlockingSample() {
    println("Before runBlocking") // 0
    println("-------------------------")
    runBlocking {
        launch {
            delay(2000L) // 2
            println("From Blocking ")
        }

        GlobalScope.launch {  // 1
            delay(1000L)
            println("From GlobalScope")
        }

        coroutineScope {
            launch {   // 3
                delay(3000L)
                println("From CoroutineScope")
            }
        }
    }
    println("-------------------------")
    println("After runBlocking") // 4
}

/**
 * This is a sample of how UI happens first and then GlobalScope finish
 * global scope start before Ui print "Hello, " and finish after 1900L then print "Coroutines World!
 */
private fun sampleHowUIHappendsFirstGlobalScopeFinish() {
    GlobalScope.launch {
        delay(1900L)
        println("Coroutines World!")
    }
    print("Hello, ")
    Thread.sleep(2000L)
}
