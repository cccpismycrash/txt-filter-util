import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Класс для обработки файлов с входными данными и сохранения результатов обработки.
 *
 * <p> В классе содержатся два статических поля с регулярными выражениями для обнаружения целых и вещественных чисел.
 * Также содержатся три статических метода для:
 * <ul>
 *      <li>Загрузки данных из входного файла.</li>
 *      <li>Фильтрации данных по типам из загруженных данных.</li>
 *      <li>Сохранения результата фильтрации в выходных файлах.</li>
 * </ul>
 *
 * Фильтрация данных происходит при помощи регулярных выражений по аналогии с классом {@link java.util.Scanner}.
 *
 *<p> Для целочисленных типов предполагается, что они могут быть как положительными, так и отрицательными и помещаются
 * в тип {@code long}.
 *
 *<p> Для строковых типов предполагается, что они могут быть отрицательными и положительными, разделителем может быть
 * как точка, так и запятая, могут быть записаны в обычном и экспоненциальном виде и помещаются в тип {@code double}.
 *
 *<p> Для строковых типов предполагается, что они могут состоять как из букв, так из знаков препинания и
 * прочих символов.
 */

public class FileProcessor {

    private static final String integerPattern = "^[-+]?\\d+$";
    private static final String floatPattern = "^[-+]?[\\d]+[.,][\\d]+(?:[eE][+-]?[\\d]+)?$";

    /**
     * Загружает данные из файла по переданному пути.
     *
     * <p> Чтение происходит при помощи {@link FileReader} путём формирования строки из всех символов, входящих в файл.
     *
     * @param path Путь входного файла в формате {@code String}.
     * @return Содержимое входного файла в формате {@code String}.
     */

    public static String loadFile(String path) {
        String data = null;

        try (FileReader fr = new FileReader(path)) {
            data = fr.readAllAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    /**
     * Фильтрует входные данные по трём спискам, соответствующим своим типам.
     *
     * <p> Входная строка разбивается на строки по разделителю {@code \n}.
     *
     * <p>Далее проверяется соответствие каждой строки паттерну целого или вещественного числа.
     * Строки, не соответствующие ни одному паттерну, определяются как строковый тип данных. Строки приводятся к
     * соответствующему типу и добавляются в список.
     *
     * @param data Содержимое входного файла в формате {@code String}.
     * @param integerList Список целочисленных типов {@code ArrayList<Long>}.
     * @param floatList Список вещественных типов {@code ArrayList<Double>}.
     * @param stringList Список строковых типов {@code ArrayList<String>}.
     */

    public static void filter(
            String data,
            ArrayList<Long> integerList,
            ArrayList<Double> floatList,
            ArrayList<String> stringList
    ) {
        String[] rows = data.split("\n");

        for (String row : rows) {
            String trimRow = row.trim();
            if (Pattern.matches(integerPattern, trimRow)) {
                long numInt = Long.parseLong(trimRow);
                integerList.add(numInt);
            } else if (Pattern.matches(floatPattern, trimRow)) {
                trimRow = trimRow.replace(",", ".");
                double numFloat = Double.parseDouble(trimRow);
                floatList.add(numFloat);
            } else {
                stringList.add(trimRow);
            }
        }
    }

    /**
     * Записывает данные в файлы по заданному пути.
     *
     * <p> Если переданный на вход список не содержит ни одного элемента, то возвращается {@code false}, иначе запись
     * продолжается.
     *
     * <p> Если директории или файла по заданному пути не существует, они создаются при
     * помощи {@link FileSystemManager}.
     *
     * <p> Запись производится построчно при помощи {@link BufferedWriter} и {@link FileWriter}.
     *
     * <p> Если не возникло ошибок во время записи, то возвращается {@code true}.
     *
     * @param dataList Список с отфильтрованными данными {@code ArrayList<T>}.
     * @param outputPathStr Путь для записи файла с выходными данными в формате {@code String}.
     * @param appendFlag Режим добавления выходных данных в существующие файлы в формате {@code boolean}. Если принимает
     * {@code false}, то существующий файл будет перезаписан, если {@code true}, данные будут добавлены в существующий
     * файл.
     * @return Возвращает {@code true}, если данные были успешно записаны в файл, и {@code false} в ином случае.
     */

    public static <T> boolean writeFile(
            ArrayList<T> dataList,
            String outputPathStr,
            boolean appendFlag
    ) {
        Path outputPath = Paths.get(outputPathStr);

        if (dataList.isEmpty()) {
            return false;
        } else {
            if (!Files.exists(outputPath)) {
                try {
                    FileSystemManager.createFileAndDirs(outputPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPathStr, appendFlag))) {
                for (T element : dataList) {
                    bw.write(element.toString());
                    bw.newLine();
                }
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}