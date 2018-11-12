package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import games.game.Game
import java.lang.IllegalStateException

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'
 * (or choosing the corresponding run configuration).
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        var p = 0
        for (i in 1..4) {
            for (j in 1..4) {
                if (p < 15) {
                    board.set(board.getCell(i, j), initializer.initialPermutation[p++])
                }
            }
        }
    }

    override fun canMove(): Boolean {
        return true
    }

    override fun hasWon(): Boolean {
        for (i in 1..4) {
            for (j in 1..4) {
                if (i == 4 && j == 4) return true
                if (get(i,j) != 4*(i-1)+j) return false
            }
        }
        throw IllegalStateException()
    }

    override fun processMove(direction: Direction) {
        val freeCell = findFreeCell()
        val neighborCell = when (direction) {
            Direction.RIGHT -> leftNeighbor(freeCell)
            Direction.LEFT -> rightNeighbor(freeCell)
            Direction.UP -> lowerNeighbor(freeCell)
            Direction.DOWN -> upperNeighbor(freeCell)
        }
        if (neighborCell != null) {
            board.set(freeCell, board[neighborCell])
            board.set(neighborCell, null)
        }
    }

    fun leftNeighbor(cell: Cell): Cell? {
        return if (cell.j > 1) board.getCell(cell.i, cell.j-1) else null
    }

    fun rightNeighbor(cell: Cell): Cell? {
        return if (cell.j < 4) board.getCell(cell.i, cell.j+1) else null
    }

    fun upperNeighbor(cell: Cell): Cell? {
        return if (cell.i > 1) board.getCell(cell.i-1, cell.j) else null
    }

    fun lowerNeighbor(cell: Cell): Cell? {
        return if (cell.i < 4) board.getCell(cell.i+1, cell.j) else null
    }

    private fun findFreeCell(): Cell {
        return board.find { it == null }!!
    }

    override fun get(i: Int, j: Int): Int? {
        return board[board.getCell(i, j)]
    }

}