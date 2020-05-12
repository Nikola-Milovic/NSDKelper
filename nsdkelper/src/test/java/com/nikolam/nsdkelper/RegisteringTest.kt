package com.nikolam.nsdkelper


import org.junit.Before
import org.junit.Test
import kotlin.random.Random


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RegisteringTest {
    @Test
   fun `registering Service, should call success callback, when successfully registered a service`() {

    }

    @Before
    fun setUp() {
        val rand = Random
        var serviceName : String = "NsdTest"
        for (i in 0..3) {
            serviceName += rand.nextInt(10).toString()
        }
    }
}
