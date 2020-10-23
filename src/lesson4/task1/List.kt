@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.min
import kotlin.math.sqrt

// Урок 4: списки
// Максимальное количество баллов = 12
// Рекомендуемое количество баллов = 8
// Вместе с предыдущими уроками = 24/33

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая (2 балла)
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var sum = 0.0
    for (i in v)
        sum += i * i
    return sqrt(sum)
}

/**
 * Простая (2 балла)
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isNotEmpty()) list.sum() / list.size else 0.0

/**
 * Средняя (3 балла)
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.isEmpty()) return list
    val cantor: Double = list.sum() / list.size
    list.replaceAll {
        it - cantor
    }
    return list
}

/**
 * Средняя (3 балла)
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    if (a.isEmpty() || b.isEmpty()) return 0
    return a.zip(b) { a, b -> a * b }.sum()
}

/**
 * Средняя (3 балла)
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var result = 0
    var coef = 1
    p.forEach {
        result += coef * it
        coef *= x
    }
    return result
}

/**
 * Средняя (3 балла)
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    if (list.isEmpty()) return list
    var sum = list.sum()
    for (i in list.size - 1 downTo 0) {
        val x = list[i]
        list[i] = sum
        sum -= x
    }
    return list
}

/**
 * Средняя (3 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var number = n
    val list = mutableListOf<Int>()
    tailrec fun divider(counter: Int) {
        if (counter == number) return
        if (number % counter == 0) {
            list.add(counter)
            number /= counter
            divider(2)
            return
        }
        divider(counter + 1)
    }
    divider(2)
    list.add(number)
    return list
}

/**
 * Сложная (4 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String {
    val list = factorize(n).toString()
    return list.substring(1, list.length - 1).replace(", ", "*")
}

/**
 * Средняя (3 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val arr = mutableListOf<Int>()
    fun divider(number: Int) {
        if (number < base) {
            arr.add(number)
            return
        }
        arr.add(number % base)
        divider(number / base)
    }
    divider(n)
    arr.reverse()
    return arr.toList()
}

/**
 * Сложная (4 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    var result = ""
    convert(n, base).forEach {
        if (it % base > 9) result += ('a' + (it % base) - 10)
        else result += it % base
    }
    return result
}

/**
 * Средняя (3 балла)
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var result = 0
    var yamaha = 1
    digits.reversed().forEach {
        result += it * yamaha
        yamaha *= base
    }
    return result
}

/**
 * Сложная (4 балла)
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    val newStr = str.map {
        if (!it.isDigit()) (it - 'a') + 10
        else it.toString().toInt()
    }.toList()
    return decimal(newStr, base)
}

/**
 * Сложная (5 баллов)
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
val list =
    mapOf(Pair(1, "I"), Pair(5, "V"), Pair(10, "X"), Pair(50, "L"), Pair(100, "C"), Pair(500, "D"), Pair(1000, "M"))

fun roman(n: Int): String {
    var koef = 1
    var result = ""

    fun getRomanNumber(number: Int, times: Int): String {
        var output = ""
        for (i in 1..times)
            output += list[number]
        return output
    }

    fun divider(number: Int) {
        if (number == 0) return
        result += when (number % 10) {
            in 1..3 -> getRomanNumber(koef, number % 10)
            0 -> ""
            4 -> list[koef * 5] + list[koef]
            5 -> list[koef * 5]
            9 -> list[koef * 10] + list[koef]
            else -> getRomanNumber(koef, (number % 10) - 5) + list[koef * 5]
        }
        koef *= 10
        divider(number / 10)
    }
    divider(n)
    return result.reversed()
}

/**
 * Очень сложная (7 баллов)
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
val numbers =
    mapOf(
        Pair(1, "один"),
        Pair(2, "два"),
        Pair(3, "три"),
        Pair(4, "четыре"),
        Pair(5, "пять"),
        Pair(6, "шесть"),
        Pair(7, "семь"),
        Pair(8, "восемь"),
        Pair(9, "девять")
    )
val otherNumbers =
    mapOf(
        Pair(11, "одиннадцать"),
        Pair(12, "двенадцать"),
        Pair(13, "тринадцать"),
        Pair(14, "четырнадцать"),
        Pair(15, "пятнадцать"),
        Pair(16, "шестнадцать"),
        Pair(17, "семнадцать"),
        Pair(18, "восемнадцать"),
        Pair(19, "девятнадцать")
    )

fun russian(n: Int): String {

    fun getHundred(number: Int): String = when (number) {
        1 -> "сто"
        2 -> "двести"
        3, 4 -> numbers[number] + "ста"
        else -> numbers[number] + "сот"
    }

    fun getDozens(number: Int): String = when (number) {
        1 -> "десять"
        2, 3 -> numbers[number] + "дцать"
        4 -> "сорок"
        9 -> "девяносто"
        else -> numbers[number] + "десят"
    }

    fun getThousand(number: Int): String = when {
        number % 100 in 11..19 -> "тысяч"
        number % 10 == 1 -> "тысяча"
        number % 10 in 2..4 -> "тысячи"
        else -> "тысяч"
    }

    fun getNumber(number: Int): String {
        return when (number) {
            0 -> ""
            in 1..9 -> "${numbers[number]}"
            in 11..19 -> "${otherNumbers[number]}"
            10, in 20..99 -> {
                return if (number % 10 != 0) "${getDozens(number / 10)} ${numbers[number % 10]}"
                else getDozens(number / 10)
            }
            in 100..999 -> {
                var res = ""
                if (number % 10 != 0) res = " ${numbers[number % 10]}"
                if (number % 100 in 11..19) res = " ${otherNumbers[number % 100]}"
                if (number % 100 >= 20 || number % 100 == 10) res = " ${getDozens(number / 10 % 10)}$res"
                res = "${getHundred(number / 100)}$res"
                return res
            }
            else -> {
                var firstDigit = "" + getNumber(number / 1000)
                if (firstDigit[firstDigit.length - 1] == 'а' && firstDigit[firstDigit.length - 2] == 'в') {
                    firstDigit = firstDigit.substring(0..firstDigit.length - 2) + 'е'
                }
                if (firstDigit[firstDigit.length - 1] == 'н' && firstDigit[firstDigit.length - 2] == 'и') {
                    firstDigit = firstDigit.substring(0..firstDigit.length - 3) + "на"
                }
                val secondPart = getNumber(number % 1000)
                return "$firstDigit ${getThousand(number / 1000)}${if (secondPart.isNotEmpty()) " $secondPart" else ""}"
            }
        }
    }

    val result = getNumber(n)
    return if (result.isEmpty()) "" else result
}