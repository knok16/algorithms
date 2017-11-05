import org.junit.Test
import java.util.*

class RandomGeneratorTest {
    @Test
    fun test() {
        val bound = 1e6.toInt()
        val count = 900000
        val random = Random()
        val used = mutableSetOf<Int>()

        var empty = 0

        for (i in 1..count) {
            while (!used.add(random.nextInt(bound))) empty++
        }

        println(empty)
    }
}