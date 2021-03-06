@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.IllegalArgumentException
import kotlin.math.max

// Урок 6: разбор строк, исключения
// Максимальное количество баллов = 13
// Рекомендуемое количество баллов = 11
// Вместе с предыдущими уроками (пять лучших, 2-6) = 40/54

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
class BullShitException(message: String?, private val reason: Any) : Exception(message) {
    override val message: String?
        get() = "Shity $reason caused ${super.message}"
}
fun checkMonth(number: Int, month: Int, year: Int): Boolean = number in 1..daysInMonth(month, year)
val months = mapOf(
    "января" to 1,
    "февраля" to 2,
    "марта" to 3,
    "апреля" to 4,
    "мая" to 5,
    "июня" to 6,
    "июля" to 7,
    "августа" to 8,
    "сентября" to 9,
    "октября" to 10,
    "ноября" to 11,
    "декабря" to 12
)

fun dateStrToDigit(str: String): String {
    val input = str.split(" ")
    var result = ""
    try {
        if (input.size != 3) throw BullShitException("invalid data input", "stupid user")
        val month = months[input[1]] ?: throw BullShitException("invalid month input", "stupid user")
        val year = input[2].toInt()
        if (checkMonth(input[0].toInt(), month, year))
            result = String.format("%02d.%02d.%d", input[0].toInt(), month, year)
    } catch (e: BullShitException) {
        result = ""
    } finally {
        return result
    }
}

/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    var result = ""
    try {
        val input = digital.split(".")
        if (input.size != 3) throw BullShitException("invalid data input", "stupid user")
        val month = months.filterValues { it == input[1].toInt() }.keys.firstOrNull()
        val year = input[2].toInt()
        if (month == null) throw BullShitException("invalid data input", "stupid user")
        if (checkMonth(input[0].toInt(), input[1].toInt(), year))
            result = String.format("%d %s %d", input[0].toInt(), month, year)
    } catch (e: BullShitException) {
        result = ""
    } finally {
        return result
    }
}

/**
 * Средняя (4 балла)
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val ignore = setOf<Char>('-', ' ', '(', ')')
    if (phone.contains('(')) {
        val first = phone.indexOf('(')
        val second = phone.indexOf(')')
        if (second - first <= 1) return ""
    }
    val input = phone.filter { !ignore.contains(it) }
    val toTest = input.filter { it != '+' }
    if (toTest.isEmpty()) return ""
    toTest.forEach {
        if (it !in '0'..'9') return ""
    }
    return input
}

/**
 * Средняя (5 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val content = jumps.split(' ')
    var maximum = -1
    for (fragment in content) {
        when (fragment) {
            "%" -> continue
            "-" -> continue
            else -> {
                try {
                    val number = fragment.toInt()
                    maximum = max(number, maximum)
                } catch (e: NumberFormatException) {
                    maximum = -1
                    break
                }
            }
        }
    }
    return maximum
}

/**
 * Сложная (6 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int = TODO()

/**
 * Сложная (6 баллов)
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int = TODO()

/**
 * Сложная (6 баллов)
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int = TODO()

/**
 * Сложная (6 баллов)
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше нуля либо равны нулю.
 */
fun mostExpensive(description: String): String {
    var maxCost = Pair("", -1.0)
    val products = description.split("; ")
    try {
        for (item in products) {
            val product = item.split(' ')
            if (product.size != 2) throw BullShitException("Wrong input", "user")
            val cost = product[1].toDouble()
            if (maxCost.second < cost) maxCost = Pair(product[0], cost)
        }
    } catch (e: BullShitException) {
        maxCost = Pair("", -1.0)
    } finally {
        return maxCost.first
    }
}

/**
 * Сложная (6 баллов)
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    fun parseRoman(symbols: String): Int {
        return when {
            symbols.all { it == 'I' } -> 1 * symbols.length
            symbols.length == 1 && symbols == "V" -> 5
            symbols.all { it == 'X' } -> 10 * symbols.length
            symbols.length == 1 && symbols == "L" -> 50
            symbols.all { it == 'C' } -> 100 * symbols.length
            symbols.length == 1 && symbols == "D" -> 500
            symbols.all { it == 'M' } -> 1000 * symbols.length
            else -> throw BullShitException("User", "Invalid input format")
        }
    }

    fun divideSequence(number: String): Pair<String, String> {
        var first = ""
        var second = ""
        var nextSequence = false
        number.reversed().forEach {
            if (it != number.last()) nextSequence = true
            if (nextSequence) second = it + second
            else first = it + first
        }
        return Pair(first, second)
    }

    fun getNumber(number: String, previous: Int, preprevious: Int): Int {
        if (number.isEmpty()) return 0
        val sequences = divideSequence(number)
        val last = parseRoman(sequences.first)
        return if (previous > last) {
            if (last <= preprevious) throw BullShitException("user", "Weird input")
            getNumber(sequences.second, last, previous) - last
        } else {
            getNumber(sequences.second, last, previous) + last
        }
    }
    return try {
        if (roman.isEmpty()) throw BullShitException("user", "Empty input")
        getNumber(roman, -1, -2)
    } catch (e: BullShitException) {
        -1
    }
}

/**
 * Очень сложная (7 баллов)
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    fun checkCommands(command: String): Boolean {//возможно это не нужно, но пусть будет. Иногда работает быстрее
        val correctSymbols = listOf<Char>('+', '-', '>', '<', ' ', '[', ']')
        val incorrect = command.filter { !correctSymbols.contains(it) }
        if (incorrect.isNotEmpty()) return false
        val str1 = command.replace("[^]]".toRegex(), "")
        val str2 = command.replace("[^\\[]".toRegex(), "")
        if (str1.length != str2.length) return false
        val str3 = command.filter { it == '[' || it == ']' }
        var openedCount = 0
        for (symbol in str3) {
            if (symbol == ']') {
                if (openedCount == 0) return false
                else openedCount--
            } else
                openedCount++
        }
        if (openedCount != 0) return false
        return true
    }

    fun getIndexOfCycle(str: String, current: Int, seekFor: Char, opposite: Char): Int {
        var counter = current
        var oppositeCount = 1
        while (oppositeCount > 0) {
            counter += if (seekFor == ']') 1 else -1
            if (counter >= str.length || counter < 0) throw IllegalArgumentException()
            val symbol = str[counter]
            if (symbol == opposite) oppositeCount++
            else if (symbol == seekFor) oppositeCount--
        }
        return counter
    }

    val result = Array(cells) { 0 }
    var position = cells / 2
    if (!checkCommands(commands)) throw IllegalArgumentException()
    var current = 0
    var commandCounter = 0
    while (commandCounter < limit) {
        if (current >= commands.length) break
        when (commands[current]) {
            '>' -> position++
            '<' -> position--
            '+' -> result[position]++
            '-' -> result[position]--
            '[' -> {
                if (result[position] == 0) {
                    current = getIndexOfCycle(commands, current, ']', '[')
                } else if (commands[current + 1] == ']') return result.toList()
            }
            ']' -> {
                if (result[position] != 0) {
                    current = getIndexOfCycle(commands, current, '[', ']')
                }
            }
        }
        if (position >= cells || position < 0) throw IllegalStateException()
        current++
        commandCounter++
        if (current >= commands.length) break
    }
    return result.toList()
}
