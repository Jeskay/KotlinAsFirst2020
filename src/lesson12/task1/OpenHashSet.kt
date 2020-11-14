@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "хеш-таблица с открытой адресацией"
 *
 * Общая сложность задания -- сложная, общая ценность в баллах -- 20.
 * Объект класса хранит данные типа T в виде хеш-таблицы.
 * Хеш-таблица не может содержать равные по equals элементы.
 * Подробности по организации см. статью википедии "Хеш-таблица", раздел "Открытая адресация".
 * Методы: добавление элемента, проверка вхождения элемента, сравнение двух таблиц на равенство.
 * В этом задании не разрешается использовать библиотечные классы HashSet, HashMap и им подобные,
 * а также любые функции, создающие множества (mutableSetOf и пр.).
 *
 * В конструктор хеш-таблицы передаётся её вместимость (максимальное количество элементов)
 */
class OpenHashSet<T>(val capacity: Int) {

    /**
     * Массив для хранения элементов хеш-таблицы
     */
    internal val elements = Array<Any?>(capacity) { null }
    private val hashCodes: Array<Int?>
        get() = elements.filterNotNull().map { it.hashCode() }
            .toTypedArray()// вообще хз как красиво изменить массив без цикла

    private fun findSlot(): Int? {
        for (index in elements.indices)
            if (elements[index] == null) return index
        return null
    }

    /**
     * Число элементов в хеш-таблице
     */
    val size: Int get() = elements.filterNotNull().size

    /**
     * Признак пустоты
     */
    fun isEmpty(): Boolean = elements.filterNotNull().isEmpty()

    /**
     * Добавление элемента.
     * Вернуть true, если элемент был успешно добавлен,
     * или false, если такой элемент уже был в таблице, или превышена вместимость таблицы.
     */
    fun add(element: T): Boolean {
        if (this.size == capacity) return false
        //anti-collision check, mb I'l finish it somehow
        if (elements.contains(element)) return false
        if (hashCodes.contains(element.hashCode())) return false
        val index = findSlot() ?: return false
        elements[index] = element
        hashCodes[index] = element.hashCode()
        return true
    }

    /**
     * Проверка, входит ли заданный элемент в хеш-таблицу
     */
    operator fun contains(element: T): Boolean = elements.contains(element)

    /**
     * Таблицы равны, если в них одинаковое количество элементов,
     * и любой элемент из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if (other !is OpenHashSet<*>) return false
        if (this.size != other.size) return false
        for (element in elements)
            if (!other.elements.contains(element)) return false
        return true
    }

    override fun hashCode(): Int = hashCodes.filterNotNull().sum().hashCode()
}