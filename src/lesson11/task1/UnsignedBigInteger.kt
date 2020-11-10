package lesson11.task1

import java.lang.ArithmeticException
import java.lang.IllegalArgumentException

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная, общая ценность в баллах -- 32.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
class UnsignedBigInteger : Comparable<UnsignedBigInteger> {
    var value: String
        set(input) {
            if (input.filterNot { it in '0'..'9' }.isNotEmpty() ||
                (input.first() == '0' && input.length > 1)
            )
                throw IllegalArgumentException()
            field = input
        }

    private class MinMax(first: UnsignedBigInteger, second: UnsignedBigInteger) {
        var min: String
        var max: String

        init {
            if (first > second) {
                min = second.value.reversed()
                max = first.value.reversed()
            } else {
                min = first.value.reversed()
                max = second.value.reversed()
            }
        }
    }

    private class Divider() {
        var remainder = UnsignedBigInteger(0)

        data class DivisionResult(var quotient: Int, var remainder: UnsignedBigInteger)

        fun findDividor(dividend: UnsignedBigInteger, divider: UnsignedBigInteger): DivisionResult {
            val result = DivisionResult(0, dividend)
            while (result.remainder > divider) {
                result.remainder = result.remainder - divider
                result.quotient++
            }
            if (result.remainder == divider) {
                result.remainder = UnsignedBigInteger(0)
                result.quotient++
            }
            return result
        }

        fun divide(dividend: UnsignedBigInteger, divider: UnsignedBigInteger): UnsignedBigInteger {
            var result = ""
            if (dividend < divider) {
                remainder = dividend
                return UnsignedBigInteger(0)
            }
            var part = ""
            for (digit in dividend.value) {
                part += digit
                val res = findDividor(UnsignedBigInteger(part), divider)
                if (res.quotient != 0) {
                    result += res.quotient
                    part = res.remainder.toString()
                } else if (result.isNotEmpty()) result += res.quotient
            }
            remainder = UnsignedBigInteger(part)
            return UnsignedBigInteger(result)
        }
    }

    private fun zero(amount: Int): String {
        if (amount == 0) return ""
        var output = ""
        for (i in 0 until amount)
            output += '0'
        return output
    }

    private fun simpleOperation(input: UnsignedBigInteger, operation: Int): UnsignedBigInteger {
        val numbers = MinMax(this, input)
        var result = ""
        var extra = 0
        for (position in numbers.max.indices) {
            val toAdd = numbers.min.getOrElse(position) { '0' }.toString().toInt() + extra
            val res = numbers.max[position].toString().toInt() + toAdd * operation
            if (res >= 0) {
                result += res % 10
                extra = res / 10
            } else {
                result += 10 + res
                extra = 1
            }
        }
        if (extra != 0) result += extra
        result = result.reversed()
        for (index in result.indices)
            if (result.getOrNull(index) == '0')
                result = result.substringAfter(result[index])
            else break
        return UnsignedBigInteger(result)
    }

    /**
     * Конструктор из строки
     */
    constructor(s: String) {
        value = s
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        value = i.toString()
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger = simpleOperation(other, 1)

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        if (this < other) throw ArithmeticException()
        return simpleOperation(other, -1)
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        val numbers = MinMax(this, other)
        var sum = UnsignedBigInteger(0)
        for ((coefficient, position) in numbers.min.indices.withIndex()) {
            var part = ""
            var extra = 0
            for (digit in numbers.max) {
                val partToAdd = numbers.min[position].toString().toInt() * digit.toString().toInt() + extra
                part += partToAdd % 10
                extra = partToAdd / 10
            }
            if (extra != 0) part += extra
            sum += UnsignedBigInteger(part.reversed() + zero(coefficient))
        }
        return sum
    }

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger {
        val divider = Divider()
        return divider.divide(this, other)
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger {
        val divider = Divider()
        divider.divide(this, other)
        return divider.remainder
    }

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean = other is UnsignedBigInteger && this.value == other.value

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int {
        if (this.value.length > other.value.length) return 1
        else if (this.value.length < other.value.length) return -1
        else {
            for (position in this.value.indices) {
                if (this.value[position].toInt() > other.value[position].toInt()) return 1
                if (this.value[position].toInt() < other.value[position].toInt()) return -1
            }
            return 0
        }
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = this.value

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int {
        if (this > UnsignedBigInteger(Int.MAX_VALUE)) throw ArithmeticException()
        return this.value.toInt()
    }

    override fun hashCode(): Int = value.hashCode()

}