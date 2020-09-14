@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import java.time.temporal.TemporalAmount
import kotlin.math.*

// Урок 3: циклы
// Максимальное количество баллов = 9
// Рекомендуемое количество баллов = 7
// Вместе с предыдущими уроками = 16/21

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая (2 балла)
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    fun noLoops(count: Int, current: Int): Int {
        return if (current / 10 != 0) noLoops(count + 1, current / 10)
        else count
    }
    return noLoops(1, n)
}

/**
 * Простая (2 балла)
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    fun noLoops(n1: Int, n2: Int, limit: Int, current: Int): Int {
        return if (current == limit) n1
        else noLoops(n2 + n1, n1, limit, current + 1)
    }
    return when (n) {
        in 1..2 -> 1
        else -> noLoops(1, 1, n, 2)
    }
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    tailrec fun noLoops(number: Int): Int {
        if (n % number == 0) return number
        return noLoops(number + 1)
    }
    return noLoops(2)
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    tailrec fun noLoops(number: Int): Int{
        if (n % number == 0) return number
        return noLoops(number - 1)
    }
    return noLoops(n - 1)
}

/**
 * Простая (2 балла)
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    tailrec fun noLoops(counter: Int, number: Int): Int{
        if (number == 1) return counter
        return noLoops(counter + 1, if (number % 2 == 0) number / 2 else number * 3 + 1)
    }
    return noLoops(0, x)
}

/**
 * Средняя (3 балла)
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    fun noLoops(a: Int, b: Int): Int {
        if (a % b == 0) return b
        if (b % a == 0) return a
        return if (a >= b) noLoops(a % b, b)
        else noLoops(a, b % a)
    }
    return m / noLoops(m, n) * n
}

/**
 * Средняя (3 балла)
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    fun noLoops(a: Int, b: Int): Int {
        if (a % b == 0) return b
        if (b % a == 0) return a
        return if (a >= b) noLoops(a % b, b)
        else noLoops(a, b % a)
    }
    return noLoops(m, n) == 1
}

/**
 * Средняя (3 балла)
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val sq1 = sqrt(n.toDouble())
    val sq2 = sqrt(m.toDouble())
    val n1 = sq1.toInt()
    val n2 = sq2.toInt()
    return sq1 % 1 == 0.0 || sq2 % 1 == 0.0 || n1 - n2 >= 1
}

/**
 * Средняя (3 балла)
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var number = n
    fun noLoops(current: Int): Int {
        if (number == 0) return current
        val x = number % 10
        number /= 10
        return noLoops(current * 10 + x)
    }
    return noLoops(0)
}

/**
 * Средняя (3 балла)
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var coef = 1
    var halfnumber = 0
    fun noLoops(current: Int): Boolean {
        if (current / coef < 1) return halfnumber == current
        if (current / coef < 10) return halfnumber == (current / 10)
        coef *= 10
        halfnumber = halfnumber * 10 + (current % 10)
        return noLoops(current / 10)
    }
    return noLoops(n)
}

/**
 * Средняя (3 балла)
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    fun noLoops(number: Int, toCompare: Int): Boolean {
        if (number == 0) return false
        if (number % 10 != toCompare) return true
        return noLoops(number / 10, toCompare)
    }
    return noLoops(n, n % 10)
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    fun checkBullshit(number: Double): Double {
        if (abs(number) <= 2 * PI) return number
        return if (number >= 0) checkBullshit(number - 2 * PI)
        else checkBullshit(number + 2 * PI)
    }

    val newX = checkBullshit(x)
    tailrec fun noLoops(prev: Double, counter: Int, sum: Double): Double {
        val current = (-1) * prev * newX * newX / (2 * counter * (2 * counter + 1))
        if (abs(current) < eps) return sum + current
        return noLoops(current, counter + 1, sum + current)
    }
    return noLoops(newX, 1, newX)
}


/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    fun checkBullshit(number: Double): Double {
        if (abs(number) <= 2 * PI) return number
        return if (number >= 0) checkBullshit(number - 2 * PI)
        else checkBullshit(number + 2 * PI)
    }

    val newX = checkBullshit(x)
    fun noLoops(prev: Double, counter: Int, sum: Double): Double {
        val current = (-1) * prev * newX * newX / (2 * counter * (2 * counter - 1))
        if (abs(current) < eps) return sum + current
        return noLoops(current, counter + 1, sum + current)
    }
    return noLoops(1.0, 1, 1.0)
}

/**
 * Сложная (4 балла)
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    fun find(number: Int, toFind: Int, coef: Int, count: Int): Int{
        if (count == toFind) return number / coef
        return find(number % coef, toFind, coef / 10, count + 1)
    }

    tailrec fun noLoops(number: Int, amount: Int, coef: Int, counter: Int): Int {
        val square = number * number
        var newcoef = coef
        var newamount = amount
        if (square / coef >= 10) {
            newcoef *= 10
            newamount++
        }
        if (n in counter..(counter + newamount)) {
            return find(square, n - counter, newcoef, 1)
        }
        return noLoops(number + 1, newamount, newcoef, counter + newamount)
    }
    return noLoops(1, 1, 1, 0)
}

/**
 * Сложная (5 баллов)
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    fun find(number: Int, toFind: Int, coef: Int, count: Int): Int{
        if (count == toFind) return number / coef
        return find(number % coef, toFind, coef / 10, count + 1)
    }

    tailrec fun noLoops(prev1: Int, prev2: Int, amount: Int, coef: Int, counter: Int): Int {
        val current = prev1 + prev2
        var newcoef = coef
        var newamount = amount
        if (current / coef >= 10) {
            newcoef *= 10
            newamount++
        }
        if (n in counter..(counter + newamount)) {
            return find(current, n - counter, newcoef, 1)
        }
        return noLoops(prev2, current, newamount, newcoef, counter + newamount)
    }
    return when (n) {
        in 1..2 -> 1
        else -> noLoops(1, 1, 1, 1, 2)
    }
}