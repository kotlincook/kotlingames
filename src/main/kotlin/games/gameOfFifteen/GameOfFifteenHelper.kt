package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val listAndSwaps = ListAndSwaps(permutation, 0).insertSort()
    return listAndSwaps.swaps % 2 == 0
}

data class ListAndSwaps(val items: List<Int>, val swaps: Int)

fun ListAndSwaps.insertSort(): ListAndSwaps {
    return when {
        items.size <= 1 -> this
        else -> {
            val first = items.first()
            val listAndSwaps = ListAndSwaps(items.drop(1), 0).insertSort()
            val (low, high) = listAndSwaps.items.partition { it < first }
            ListAndSwaps(low + first + high, listAndSwaps.swaps + low.size)
        }
    }
}