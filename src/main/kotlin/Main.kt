package cinema

var numberRows = 9
var seatsPerRow = 9
var selectedRow = 0
var selectedSeat = 0
var purchasedTickets = 0
var currentIncome = 0
var totalIncome = 0
const val HIGHPRICE = 10
const val LOWPRICE = 8
//TODO save a variable for (numberRows * seatsPerRow)

//TODO Make the list dynamic and not limited to a 9x9 grid
var cinema: MutableList<MutableList<Char>> = MutableList(numberRows + 1) { MutableList(seatsPerRow + 1) { 'S' } }

/** TODO Suggestion from NotKamui "create a 1D List with methods to convert an index to 2D coordinates, and reciprocate"
 * class Table2D<T>(val width: Int, val height: Int, default: () -> T) {
    private val inner = MutableList<T>(width * height) { default()
    }
    private fun coordToIndex(x: Int, y: Int) = x + y * width
    operator fun get(x: Int, y: Int) = inner[coordToIndex(x, y)]
    operator fun set(x: Int, y: Int, value: T) {
        inner[coordToIndex(x, y)] = value
    }
}
 *
 * val seats = Table2D<Person?>(3, 3) { null }
    seats[1, 2] = Person("John")
    println(seats[1, 2])
 *
 */

fun main() {
    //Number of rows and seats per row
    println("Enter the number of rows: ")
    numberRows = readln().toInt()
    println("Enter the number of seats in each row: ")
    seatsPerRow = readln().toInt()
    // MENU
    do {
        println()
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        val chosenOption = readln()
        when (chosenOption) {
            "1" -> layoutCinema(cinema)
            "2" -> {
                buyTicket()
            }

            "3" -> statistics()
            "0" -> break
            else -> println("Invalid Input")
        }
    } while (chosenOption != "0") //FIXME Understand why this error is happening
}

// Table with rows and seats
fun layoutCinema(cinema: MutableList<MutableList<Char>>) {
    println()
    println("Cinema:")
    print("  ")
    var seatCount = 1
    repeat(seatsPerRow) {
        print("$seatCount ")
        seatCount++
    }
    println()
    for (row in 1..numberRows) {
        print("$row ")
        for (seat in 1..seatsPerRow) {
            print("${cinema[row][seat]} ")
        }
        println()
    }
}

//Ticket Price
fun buyTicket() { //TODO Try to make a Do-While instead of a recursion on buyticket()
    //Client chooses row and seat, each checking if option is available.
    println()
    println("Enter a row number:")
    selectedRow = readln().toInt() //TODO add an exception handler. NumberFormatException.
    if (selectedRow > numberRows) {
        println("Wrong Input!")
        return buyTicket()
    }
    println("Enter a seat number in that row:")
    selectedSeat = readln().toInt() //TODO add an exception handler. IndexOutOfBounds.
    if (selectedSeat > seatsPerRow) {
        println("Wrong Input!")
        return buyTicket()
    }
    println()

    //Checking if the seat is already occupied
    if (cinema[selectedRow][selectedSeat] == 'B') {
        println("That ticket has already been purchased!")
        return buyTicket()
    } else {
        cinema[selectedRow][selectedSeat] = 'B'
        purchasedTickets++

        //Printing ticket price -- suggested code by aaulia
        val price = if (numberRows * seatsPerRow <= 60 || selectedRow <= numberRows / 2) HIGHPRICE else LOWPRICE
        currentIncome += price
        println("Ticket Price: $price")
        return

        //TODO remember to delete OLD CODE
        /** OLD CODE
        val price = {
            if (numberRows * seatsPerRow <= 60 || selectedRow <= numberRows / 2) {
                currentIncome += HIGHPRICE
                println("Ticket Price: $${HIGHPRICE}")
            } else {
                currentIncome += LOWPRICE
                println("Ticket Price: $${LOWPRICE}")
            }
        }
        return price() **/
    }
}

//TODO Also for extra exercise, you can calculate the statistic from the cinema instead of using variables to keep track ticket bought and income.
fun statistics() {
// PURCHASED TICKETS (Number)
    // val purchasedTickets = cinema.flatten().count { it = 'B' }
    println()
    println("Number of purchased tickets: $purchasedTickets")
// PURCHASED TICKETS IN PERCENTAGE (2 DECIMAL)
    val ticketsPercentage = purchasedTickets * 100 / (numberRows * seatsPerRow).toDouble()
    val formatPercentage = "%.2f".format(ticketsPercentage)
    println("Percentage: ${formatPercentage}%")
// CURRENT INCOME ::  income from sold tickets
    println("Current income: $${currentIncome}")
// TOTAL INCOME :: If all the tickets are sold.
    totalIncome = if (numberRows * seatsPerRow <= 60) {
        numberRows * seatsPerRow * 10
    } else {
        if (numberRows % 2 == 0) {
            (numberRows / 2 * seatsPerRow) * 10 + (numberRows / 2 * seatsPerRow) * 8
        } else {
            (numberRows / 2 * seatsPerRow) * 10 + ((numberRows - seatsPerRow / 2) * seatsPerRow) * 8
        }
    }
    println("Total income: $${totalIncome}")
}