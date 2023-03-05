import java.util.Scanner

fun main() {

    val reader = Scanner(System.`in`)
    val number = (0..9999).random()
    var numberCopy = number
    val numberArray = Array<Int>(4){0}

    var n = 0
    var m = 0

    var i = 3
    while(i > -1)
    {
        numberArray[i] = numberCopy % 10
        numberCopy /= 10
        i -= 1
    }

    println("Please enter a 4 digit number.")
    var success = false

    while(true)
    {
        var userGuess = reader.nextInt();

        if(userGuess > 9999 || userGuess < 0)
        {
            println("Not a valid Number")
            continue
        }

        var userGuessArray = Array<Int>(4){0}

        i = 3
        while(i > -1)
        {
            userGuessArray[i] = userGuess % 10
            userGuess /= 10
            i -= 1
        }

        for(x in 0..3)
        {
            if(userGuessArray[x] == numberArray[x])
            {
                n++
                m++
            }
            else
            {
                for(y in 0..3)
                {
                    if (userGuessArray[x] == numberArray[y])
                    {
                        n++
                        break
                    }
                }
            }
        }

        println("$n:$m")

        if(m == 4)
        {
            success = true
        }
        m = 0
        n = 0

        if(success)
        {
            println("The correct number was $number!")
            break
        }
    }
}