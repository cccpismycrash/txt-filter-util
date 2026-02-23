import java.util.ArrayList;

/**
 * Класс для расчёта статистики.
 *
 * <p> Класс был создан для обеспечения статистическими характеристиками отфильтрованных данных
 * класса {@link ViewHandler}.
 *
 * <p> В классе содержится логика для нахождения:
 * <ul>
 *      <li>Суммы элементов числового массива.</li>
 *      <li>Среднего элементов числового массива.</li>
 *      <li>Минимального элемента числового массива.</li>
 *      <li>Максимального элемента числового массива.</li>
 *      <li>Длины наименьшей строки строкового массива.</li>
 *      <li>Длины наибольшей строки строкового массива.</li>
 * </ul>
 */

public class Statistics {

    /**
     * Считает количество элементов массива.
     *
     * @param arrayList Массив элементов любого типа.
     * @return Длина массива в формате {@code int}.
     */

    public static <T> int getSizeArray(ArrayList<T> arrayList) {
        return arrayList.size();
    }

    /**
     * Считает сумму числовых элементов списка.
     *
     * @param arrayList Массив числовых элементов.
     * @return Сумма в формате {@code double}.
     */

    public static <T extends Number> double getSum(ArrayList<T> arrayList) {
        double result = 0d;
        for (T element : arrayList) {
            result += element.doubleValue();
        }
        return result;
    }

    /**
     * Считает среднее числовых элементов списка.
     *
     * <p> Ответственность за валидность передаваемого массива передается на вышестоящий уровень абстракции.
     *
     * @param arrayList Массив числовых элементов.
     * @return Среднее в формате {@code double}.
     */

    public static <T extends Number> double getMean(ArrayList<T> arrayList) {
        double result = getSum(arrayList);
        return result / arrayList.size();
    }

    /**
     * Находит минимальный элемент числового массива.
     *
     * @param arrayList Массив числовых элементов.
     * @return Минимальное значение в формате {@code double}.
     */

    public static <T extends Number> double getMinValue(ArrayList<T> arrayList) {
        double result = Double.MAX_VALUE;
        for (T element : arrayList) {
            if (element.doubleValue() < result) {
                result = element.doubleValue();
            }
        }
        return result;
    }

    /**
     * Находит максимальный элемент числового массива.
     *
     * @param arrayList Массив числовых элементов.
     * @return Максимальное значение в формате {@code double}.
     */

    public static <T extends Number> double getMaxValue(ArrayList<T> arrayList) {
        double result = Double.MIN_VALUE;
        for (T element : arrayList) {
            if (element.doubleValue() > result) {
                result = element.doubleValue();
            }
        }
        return result;
    }

    /**
     * Находит длину самой короткой строки в строковом массиве.
     *
     * @param arrayList Массив строковых элементов.
     * @return Длина наименьшей строки в формате {@code int}.
     */

    public static int getMinSizeString(ArrayList<String> arrayList) {
        int result = Integer.MAX_VALUE;
        for (String element : arrayList) {
            if (element.length() < result) {
                result = element.length();
            }
        }
        return result;
    }

    /**
     * Находит длину самой длинной строки в строковом массиве.
     *
     * @param arrayList Массив строковых элементов.
     * @return Длина наибольшей строки в формате {@code int}.
     */

    public static int getMaxSizeString(ArrayList<String> arrayList) {
        int result = 0;
        for (String element : arrayList) {
            if (element.length() > result) {
                result = element.length();
            }
        }
        return result;
    }
}