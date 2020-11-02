package lesson11.task1

import java.lang.ArithmeticException
import java.lang.IllegalArgumentException
import java.math.BigInteger

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
    var value: BigInteger
        set(input) {
            if (input.signum() == -1) throw IllegalArgumentException()
            field = input
        }

    /**
     * Конструктор из строки
     */
    constructor(s: String) {
        try {
            value = BigInteger(s)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException()
        }
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        value = i.toBigInteger()
    }

    /*
    * Конструктор из BigInteger
    * по-моему логично, что он должен быть, хотя можно было наследовать от него.
    * Но что-то мне не хочется копаться в джава коде BigInteger ради этого, тем более что джаву я не знаю
    */
    private constructor(bigInt: BigInteger) {
        value = bigInt
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger = UnsignedBigInteger(this.value + other.value)

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        if (this.value < other.value) throw ArithmeticException()
        return UnsignedBigInteger(this.value - other.value)
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger = UnsignedBigInteger(this.value * other.value)

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger = UnsignedBigInteger(this.value / other.value)

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = UnsignedBigInteger(this.value % other.value)

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean = other is UnsignedBigInteger && other.value == this.value

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int = this.value.compareTo(other.value)

    /**
     * Преобразование в строку
     */
    override fun toString(): String = this.value.toString()

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