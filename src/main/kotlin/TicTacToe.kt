import Player.O
import Player.X


enum class Player(val symbol: Char) {
    X('X'), O('O')
}


const val boardSize = 3
const val EMPTY_CELL = ' '
var currentPlayer: Player = X
val board = mutableListOf(mutableListOf<Char>())
var gameOver = false


fun main() {
    initBoard()
    printBoard()

    while (!gameOver) {
        println("Player ${currentPlayer.symbol} Enter the coordinates separated by comma: for example 1,1. Each number in range [1,3]")
        val input = readln()
        val coordinateDelimiter = ','
        if (input.isBlank() || !input.contains(coordinateDelimiter)) continue

        val (y, x) = input.split(coordinateDelimiter)
        val xInt = x.toIntOrNull()
        val yInt = y.toIntOrNull()

        if (skipIfNotNumbers(xInt, yInt)) continue

        if (skipIfOutOfRange(xInt, yInt)) continue

        val xIndex = xInt?.minus(1) ?: 1
        val yIndex = yInt?.minus(1) ?: 1

        if (skipFulledCell(yIndex, xIndex)) continue

        board[yIndex][xIndex] = currentPlayer.symbol
        printBoard()

        if (checkDiagonals() || checkHorizontals() || checkVerticals()) {
            println("Player ${currentPlayer.symbol} wins")
            gameOver = true
        } else if (isBoardFull()) {
            println("Game over, No Winners!")
            gameOver = true
        } else {
            currentPlayer = swapPlayer()
        }
    }
}

private fun skipFulledCell(yIndex: Int, xIndex: Int): Boolean {
    if (board[yIndex][xIndex] != EMPTY_CELL) {
        println("This cell is occupied! Choose another one!")
        return true
    }
    return false
}

private fun skipIfOutOfRange(xInt: Int?, yInt: Int?): Boolean {
    if (xInt !in 1..boardSize || yInt !in 1..boardSize) {
        println("Coordinates should be from 1 to 3!")
        return true
    }
    return false
}

private fun skipIfNotNumbers(xInt: Int?, yInt: Int?): Boolean {
    if (xInt == null || yInt == null) {
        println("You should enter numbers!")
        return true
    }
    return false
}

fun initBoard() {
    for (i in 0 until boardSize) {
        board.add(mutableListOf())
        for (j in 0 until boardSize) {
            board[i].add(' ')
        }
    }
}

fun printBoard() {
    println("-------------")
    for (i in 0 until boardSize) {
        for (j in 0 until boardSize) {
            print("| ${board[i][j]} ")
        }
        println("|")
        println("-------------")
    }
}

fun swapPlayer(): Player {
    return if (currentPlayer == X) O else X
}

fun isBoardFull(): Boolean {
    for (i in 0 until boardSize) {
        for (j in 0 until boardSize) {
            if (board[i][j] == ' ') {
                return false
            }
        }
    }
    return true
}

private fun isEmptyCel(yInt: Int, xInt: Int) = board[yInt][xInt] == EMPTY_CELL

fun checkDiagonals(): Boolean {
    return !isEmptyCel(0, 0) && board[0][0] == board[1][1] && board[1][1] == board[2][2]
            || !isEmptyCel(2, 0) && board[2][0] == board[1][1] && board[1][1] == board[0][2]
}

fun checkHorizontals(): Boolean {
    return !isEmptyCel(0, 0) && board[0][0] == board[0][1] && board[0][1] == board[0][2]
            || !isEmptyCel(1, 0) && board[1][0] == board[1][1] && board[1][1] == board[1][2]
            || !isEmptyCel(2, 0) && board[2][0] == board[2][1] && board[2][1] == board[2][2]
}

fun checkVerticals(): Boolean {
    return !isEmptyCel(0, 0) && board[0][0] == board[1][0] && board[1][0] == board[2][0]
            || !isEmptyCel(0, 1) && board[0][1] == board[1][1] && board[1][1] == board[2][1]
            || !isEmptyCel(0, 2) && board[0][2] == board[1][2] && board[1][2] == board[2][2]
}