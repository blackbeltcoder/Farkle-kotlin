package dojo.farkle

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

fun <T> List<T>.hasAll(items: List<T>): Boolean {
    var startIdx = 0
    items.forEach {
        val idx = this.slice(startIdx until this.size).indexOf(it)
        if (idx == -1) {
            return false
        }
        startIdx += (1 + idx)
    }
    return true
}

fun <T> List<T>.subtract(items: List<T>): List<T> {
    return items.fold(this.toMutableList()) { acc, it ->
            acc.remove(it)
            acc
    }.toList()
}

val scores: List<Pair<List<Int>, Int>> = listOf(
        listOf(1, 1, 1, 1, 1) to 4000,
        listOf(2, 2, 2, 2,2) to 800,
        listOf(3, 3, 3, 3, 3) to 1200,
        listOf(4, 4, 4, 4,4) to 1600,
        listOf(5, 5, 5, 5, 5) to 2000,
        listOf(6, 6, 6, 6, 6) to 2400,
        listOf(1, 1, 1, 1) to 2000,
        listOf(2, 2, 2, 2) to 400,
        listOf(3, 3, 3, 3) to 600,
        listOf(4, 4, 4, 4) to 800,
        listOf(5, 5, 5, 5) to 1000,
        listOf(6, 6, 6, 6) to 1200,
        listOf(1, 1, 1) to 1000,
        listOf(2, 2, 2) to 200,
        listOf(3, 3, 3) to 300,
        listOf(4, 4, 4) to 400,
        listOf(5, 5, 5) to 500,
        listOf(6, 6, 6) to 600,
        listOf(1, 1) to 200,
        listOf(5, 5) to 100,
        listOf(1) to 100,
        listOf(5) to 50
)


object Farkle {
    fun score(dice: List<Int>): Int {
        val bestScore = scores.firstOrNull { dice.hasAll(it.first) }
        return when (bestScore) {
            null -> 0
            else -> bestScore.second + score(dice.subtract(bestScore.first))
        }
    }
}


class ListTest {
    @Test
    fun `Given a list check that hasAll works`() {
        assertThat(listOf(1, 2, 3, 4, 5).hasAll(listOf(1)), equalTo(true))
        assertThat(listOf(1, 2, 3, 4, 5).hasAll(listOf(2)), equalTo(true))
        assertThat(listOf(1, 2, 3, 4, 5).hasAll(listOf(3)), equalTo(true))
        assertThat(listOf(1, 2, 3, 4, 5).hasAll(listOf(4)), equalTo(true))
        assertThat(listOf(1, 2, 3, 4, 5).hasAll(listOf(5)), equalTo(true))
        assertThat(listOf(1, 2, 3, 4, 5).hasAll(listOf(6)), equalTo(false))
    }
}

class FarkleTest {

    @Test
    fun `Given 2 3 4 6 6 Then Scorer should return zero`() {
        assertThat(Farkle.score(listOf(2, 3, 4, 6, 6)), equalTo(0))
    }

    @Test
    fun `Given 1 2 3 4 6 6 Then Scorer should return 100`() {
        assertThat(Farkle.score(listOf(2, 3, 4, 6, 1)), equalTo(100))
    }

    @Test
    fun `Given 2 3 4 5 6 Then Scorer should return 50`() {
        assertThat(Farkle.score(listOf(2, 3, 4, 5, 6)), equalTo(50))
    }

    @Test
    fun `Given dice containing 2 ones Then Scorer should return 200`() {
        assertThat(Farkle.score(listOf(1, 1, 2, 3, 6)), equalTo(200))
    }


    @Test
    fun `Given dice containing 2 x five Then Scorer should return 100`() {
        assertThat(Farkle.score(listOf(2, 3, 5, 5, 6)), equalTo(100))
    }

    @Test
    fun `Given dice containing triple 111 Then Scorer should return 1000`() {
        assertThat(Farkle.score(listOf(1, 1, 1, 2, 3)), equalTo(1000))
    }

    @Test
    fun `Given dice containing triple twos Then Scorer should return 200`() {
        assertThat(Farkle.score(listOf(2, 2, 2, 3, 4)), equalTo(200))
    }

    @Test
    fun `Given dice containing triple threes Then Scorer should return 300`() {
        assertThat(Farkle.score(listOf(2, 3, 3, 3, 4)), equalTo(300))
    }

    @Test
    fun `Given dice containing triple fours Then Scorer should return 400`() {
        assertThat(Farkle.score(listOf(2, 3, 4, 4, 4)), equalTo(400))
    }

    @Test
    fun `Given dice containing triple five Then Scorer should return 500`() {
        assertThat(Farkle.score(listOf(2, 3, 5, 5, 5)), equalTo(500))
    }

    @Test
    fun `Given dice containing triple six Then Scorer should return 600`() {
        assertThat(Farkle.score(listOf(2, 6, 6, 6, 3)), equalTo(600))
    }

    @Test
    fun `Given dice 1 1 1 5 1 Then Scorer should return 2050`() {
        assertThat(Farkle.score(listOf(1, 1, 1, 5, 1)), equalTo(2050))
    }

    @Test
    fun `Given dice 2 3 4 6 2  Then Scorer should return zero`() {
        assertThat(Farkle.score(listOf(2,3,4,6,2)), equalTo(0))
    }

    @Test
    fun `Given dice 3,4,5,3,3  Then Scorer should return 350`() {
        assertThat(Farkle.score(listOf(3,4,5,3,3)), equalTo(350))
    }

    @Test
    fun `Given dice 3,3,5,3,3  Then Scorer should return 650`() {
        assertThat(Farkle.score(listOf(3,3,5,3,3)), equalTo(650))
    }

    @Test
    fun `Given dice 3,3,3,3,3  Then Scorer should return 1200`() {
        assertThat(Farkle.score(listOf(3,3,3,3,3)), equalTo(1200))
    }


}