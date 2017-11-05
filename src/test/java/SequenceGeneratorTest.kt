import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.fail
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

interface Generator {
    fun generate(): Int
}

class GeneratorImpl(private val module: Int,
                    private val generator: Int,
                    seed: Int,
                    private val bound: Int = module) : Generator {
    private var current: Long = seed.toLong()
    private var generatedCount = 0

    override fun generate(): Int {
        if (generatedCount >= module - 1) throw RuntimeException("Cannot generate more")
        generatedCount++
        current = current * generator % module
        return if (current < bound) current.toInt() else generate()
    }
}

class GeneratorProvider(private val random: Random = Random()) {
    fun get(bound: Int): Generator {
        // TODO maybe cache it inside GeneratorProvider
        val (primes, leastFactors) = primesUpTo(bound + 100)
        val possibleModules = primes.takeLastWhile { it >= bound }
        // TODO assert that collection is not empty
        val module = possibleModules[random.nextInt(possibleModules.size)]
        val generators = getGenerators(module, leastFactors)
        // TODO assert that collection is not empty
        val generator = generators[random.nextInt(generators.size)]
        val seed = random.nextInt(bound - 1) + 1

        return GeneratorImpl(module, generator, seed, bound)
    }

    /**
     * 1 is not prime
     *
     * @param n last limit primes (inclusive)
     * @return all list of prime numbers up to n
     */
    private fun primesUpTo(n: Int): Pair<List<Int>, IntArray> {
        val primes = ArrayList<Int>()
        val leastFactor = IntArray(n + 1)
        leastFactor[1] = 1
        for (i in 2..n) {
            if (leastFactor[i] == 0) {
                primes.add(i)
                leastFactor[i] = i
            }
            var j = 0
            while (j < primes.size && primes[j] <= leastFactor[i] && primes[j] * i <= n) {
                leastFactor[i * primes[j]] = primes[j]
                j++
            }
        }
        return primes to leastFactor
    }

    private fun getGenerators(module: Int, leastFactors: IntArray, requiredGenerators: Int = 10): List<Int> {
        val result = mutableListOf<Int>()
        var generatorsCount = 0
        for (j in module - 1 downTo 2) {
            var k = module - 1
            while (k > 1 && pow(j, (module - 1) / leastFactors[k], module) != 1) {
                k /= leastFactors[k]
            }
            if (k == 1) {
                result.add(j)
                generatorsCount++
                if (generatorsCount >= requiredGenerators)
                    break
            }
        }
        return result
    }

    private fun pow(base: Int, power: Int, mod: Int): Int {
        var a = base
        var n = power
        var result = 1
        while (n > 0) {
            if (n and 1 == 1)
                result = (result.toLong() * a % mod).toInt()
            a = (a.toLong() * a % mod).toInt()
            n = n shr 1
        }
        return result
    }
}

class SequenceGeneratorTest {
    @Test
    fun generatorProvider() {
        val generatorProvider = GeneratorProvider()

        for (bound in 100..500)
            checkGenerator(generatorProvider.get(bound), bound)
    }

    @Test
    fun checkGenerator() {
        val seed = 721
        val module = 100003
        val generator = 99982
        val bound = 100000

        checkGenerator(GeneratorImpl(module, generator, seed, bound), bound)
    }

    private fun checkGenerator(generator: Generator, bound: Int) {
        val set = mutableSetOf<Int>()

        for (i in 1 until bound) {
            val next = generator.generate()
            assertTrue(next > 0L)
            assertTrue(next < bound)
            val unique = set.add(next)
            assertTrue(unique)
        }

        assertEquals(bound - 1, set.size)

        assertException(RuntimeException::class.java, {
            generator.generate()
        }, {
            assertEquals("Cannot generate more", it.message)
        })
    }

    private fun <T> assertException(expectedException: Class<T>, runnable: () -> Unit, assertion: (T) -> Unit) {
        try {
            runnable.invoke()
        } catch (e: Exception) {
            assertThat("Expected exception of type $expectedException", e, CoreMatchers.instanceOf(expectedException))
            assertion.invoke(expectedException.cast(e))
            return
        }

        fail("Exception expected")
    }

//    private fun factorize(i: Int, leastFactors: IntArray): MutableList<Int> = if (i <= 1)
//    ArrayList()
//    else {
//        val result = factorize(i / leastFactors[i], leastFactors)
//        result += leastFactors[i]
//        result
//    }
}
