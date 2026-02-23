import java.util.ArrayList;

/**
 * Класс для формирования ответа и вывода в консоль результата работы утилиты.
 *
 * <p> Класс основан на формировании отдельных кусков вывода и работе с ними, из которых строится финальный ответ.
 *
 * <p> Любое успешное завершение программы сопровождается уведомлением.
 *
 * <p> Класс содержит следующие статические строковые поля:
 * <ul>
 *      <li>Финальный вывод. Содержит уведомление об успешном выполнении программы.</li>
 *      <li>Вывод по целым типам. Изначально пустое.</li>
 *      <li>Вывод по вещественным типам. Изначально пустое.</li>
 *      <li>Вывод по строковым типам. Изначально пустое.</li>
 * </ul>
 *
 * При включении режима краткой или полной статистики, к строковым выводам по соответствующим типам сопровождается
 * применением соответствующих статических методов. Например, при включенном режиме полной статистики к полям,
 * отвечающим за свой тип, будут присоединены шаблоны вывода сначала по краткой статистике, потом по полной статистике
 * с рассчитанными значениями статистик. В ситуации, когда не нужно выводить статистику, в консоль будет выведено
 * только уведомление об успешном выполнении программы.
 *
 * <p> Содержание краткой статистики для всех типов:
 * <ul>
 *      <li>Количество распознанных элементов данного типа.</li>
 * </ul>
 *
 * <p> Содержание полной статистики по числовым типам:
 * <ul>
 *      <li>Количество распознанных элементов данного типа.</li>
 *      <li>Минимальное значение.</li>
 *      <li>Максимальное значение.</li>
 *      <li>Сумма.</li>
 *      <li>Среднее.</li>
 * </ul>
 *
 * <p> Содержание полной статистики по строковому типу:
 * <ul>
 *      <li>Количество распознанных элементов данного типа.</li>
 *      <li>Длина наименьшей строки.</li>
 *      <li>Длина наибольшей строки.</li>
 * </ul>
 *
 * <p> После формирования выводов по типам вызываются статические методы для формирования итогового вывода и его
 * непосредственного вывода в саму консоль соответственно.
 *
 * <p> Расчёт статистики производится при помощи {@link Statistics}.
 */

public class ViewHandler {

    private static String finalView = "The program was successfully executed.\n\n";

    private static String baseIntegers = "";
    private static String baseFloats = "";
    private static String baseStrings = "";

    /**
     * Добавляет к статическому полю с выводом по целочисленному типу шаблон вывода краткой статистики.
     *
     * <p> При формировании шаблона предварительно рассчитывается статистика в виде количества распознанных элементов
     * данного типа.
     *
     * <p> Ответственность за валидность передаваемого массива передается в вышестоящую логику.
     *
     * @param integerList Массив целочисленных элементов.
     */

    public static void addSimpleStatsIntegers(ArrayList<Long> integerList) {
        int countElements = Statistics.getSizeArray(integerList);

        String result = "Integers:\n    - Number of elements:    %s\n".formatted(countElements);
        baseIntegers = baseIntegers.concat(result);
    }

    /**
     * Добавляет к статическому полю с выводом по вещественному типу шаблон вывода краткой статистики.
     *
     * <p> При формировании шаблона предварительно рассчитывается статистика в виде количества распознанных элементов
     * данного типа.
     *
     * <p> Ответственность за валидность передаваемого массива передается в вышестоящую логику.
     *
     * @param floatList Массив вещественных элементов.
     */

    public static void addSimpleStatsFloats(ArrayList<Double> floatList) {
        int countElements = Statistics.getSizeArray(floatList);

        String result = "Floats:\n    - Number of elements:    %s\n".formatted(countElements);
        baseFloats = baseFloats.concat(result);
    }

    /**
     * Добавляет к статическому полю с выводом по строковому типу шаблон вывода краткой статистики.
     *
     * <p> При формировании шаблона предварительно рассчитывается статистика в виде количества распознанных элементов
     * данного типа.
     *
     * <p> Ответственность за валидность передаваемого массива передается в вышестоящую логику.
     *
     * @param stringList Массив строковых элементов.
     */

    public static void addSimpleStatsStrings(ArrayList<String> stringList) {
        int countElements = Statistics.getSizeArray(stringList);

        String result = "Strings:\n    - Number of elements:    %s\n".formatted(countElements);
        baseStrings = baseStrings.concat(result);
    }

    /**
     * Добавляет к статическому полю с выводом по целочисленному типу шаблон вывода полной статистики.
     *
     * <p> При формировании шаблона предварительно рассчитывается статистика в виде минимального и максимального
     * значения, суммы элементов массива и их среднее.
     *
     * <p> Ответственность за валидность передаваемого массива передается в вышестоящую логику.
     *
     * @param integerList Массив целочисленных элементов.
     */

    public static void addFullStatsIntegers(ArrayList<Long> integerList) {
        double min = Statistics.getMinValue(integerList);
        double max = Statistics.getMaxValue(integerList);
        double sum = Statistics.getSum(integerList);
        double mean = Statistics.getMean(integerList);

        String result = ("""
                    Extended statistics:
                        - Min:               %.0f
                        - Max:               %.0f
                        - Sum:               %.0f
                        - Mean:              %.4f
                
                """).formatted(min, max, sum, mean);
        baseIntegers = baseIntegers.concat(result);
    }

    /**
     * Добавляет к статическому полю с выводом по вещественному типу шаблон вывода полной статистики.
     *
     * <p> При формировании шаблона предварительно рассчитывается статистика в виде минимального и максимального
     * значения, суммы элементов массива и их среднее.
     *
     * <p> Ответственность за валидность передаваемого массива передается в вышестоящую логику.
     *
     * @param floatList Массив вещественных элементов.
     */

    public static void addFullStatsFloats(ArrayList<Double> floatList) {
        double min = Statistics.getMinValue(floatList);
        double max = Statistics.getMaxValue(floatList);
        double sum = Statistics.getSum(floatList);
        double mean = Statistics.getMean(floatList);

        String result = ("""
                    Extended statistics:
                        - Min:               %.4f
                        - Max:               %.4f
                        - Sum:               %.4f
                        - Mean:              %.4f
                
                """).formatted(min, max, sum, mean);
        baseFloats = baseFloats.concat(result);
    }

    /**
     * Добавляет к статическому полю с выводом по строковому типу шаблон вывода полной статистики.
     *
     * <p> При формировании шаблона предварительно рассчитывается статистика в виде длины наименьшей и наибольшей
     * строки.
     *
     * <p> Ответственность за валидность передаваемого массива передается в вышестоящую логику.
     *
     * @param stringList Массив строковых элементов.
     */

    public static void addFullStatsStrings(ArrayList<String> stringList) {
        int min = Statistics.getMinSizeString(stringList);
        int max = Statistics.getMaxSizeString(stringList);

        String result = ("""
                    Extended statistics:
                        - Shortest length:   %d
                        - Longest length:    %d
                """).formatted(min, max);
        baseStrings = baseStrings.concat(result);
    }

    /**
     * Объединяет поля с выводами по всем типам в финальный вывод.
     */

    public static void createFinalView() {
        finalView = finalView.concat(baseIntegers);
        finalView = finalView.concat(baseFloats);
        finalView = finalView.concat(baseStrings);
        finalView = finalView.strip();
    }

    /**
     * Выводит финальный вывод в консоль.
     */

    public static void showFinalView() {
        System.out.println(finalView);
    }
}