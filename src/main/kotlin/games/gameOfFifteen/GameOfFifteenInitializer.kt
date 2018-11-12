package games.gameOfFifteen

import java.util.*

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized first 15 cells on a board
     * (the last cell is empty)
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    private val random = Random()
    override val initialPermutation by lazy {
        val list = mutableListOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)
        for (counter in 0..200) {
            val idx1 = random.nextInt(15)
            val idx2 = random.nextInt(15)
            if (idx1 != idx2) {
                val temp = list[idx1]
                list[idx1] = list[idx2]
                list[idx2] = temp
            }
            if (counter > 100 && isEven(list)) break
        }
        list.toList()
    }
}
