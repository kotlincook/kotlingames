package board

import board.Direction.*
import java.lang.IllegalArgumentException

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

data class CellImpl(override val i: Int, override val j: Int) : Cell

open class SquareBoardImpl(override val width: Int) : SquareBoard {

    val squareBoard = List<Cell>(width * width) {
        c -> CellImpl(1 + c / width, 1 + c % width)
    }

    private fun validate(i: Int): Boolean {
        return i in 1..width
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if (validate(i) && validate(j)) {
            squareBoard[(i-1)*width + j-1]
        } else null
    }

    override fun getCell(i: Int, j: Int): Cell {
        val cell = getCellOrNull(i, j)
        return cell ?: throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> {
        return squareBoard
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.mapIndexedNotNull { _, j -> getCellOrNull(i, j) }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.mapIndexedNotNull { _, i -> getCellOrNull(i, j) }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            LEFT -> getCellOrNull(i, j - 1)
            RIGHT -> getCellOrNull(i, j + 1)
            UP -> getCellOrNull(i - 1, j)
            DOWN -> getCellOrNull(i + 1, j)
        }
    }

}


class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {

    val gameBoard = HashMap<Cell, T?>()

    override fun get(cell: Cell): T? {
        return gameBoard.get(cell)
    }

    override fun set(cell: Cell, value: T?) {
        gameBoard.set(cell, value)
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return getAllCells().filter { cell -> predicate(gameBoard[cell]) }
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return filter(predicate).firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return find(predicate) != null
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return getAllCells().all { cell -> predicate(gameBoard[cell]) }
    }

    override fun toString() =
        (1..4).joinToString("\n") { i ->
            (1..4).joinToString(" ") { j ->
                "${get(getCell(i, j)) ?: "-"}"
            }
        }

}
