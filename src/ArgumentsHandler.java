import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Класс для обработки и контроля правильности передаваемых входных аргументов.
 *
 * <p> Обработка аргументов производится при помощи цикла for-each и оператора switch.
 * <p> Класс состоит из статических полей и статического одного метода.
 * <p> Необходимые поля для работы утилиты доступны через геттеры.
 * <p> В статических полях содержатся:
 * <ul>
 *      <li>Массив с путями файлов входных данных.</li>
 *      <li>Флаги, отображающие режимы работы утилиты.</li>
 *      <li>Названия выходных файлов.</li>
 *      <li>Рабочая директория, откуда была запущена утилита.</li>
 *      <li>Путь для выходных данных.</li>
 * </ul>
 * <p> Обработка правильности входных аргументов опций производится при помощи регулярных выражений.
 *
 * <p> Выходные пути при указании соответствующей опции проверяются на возможность создания директорий и файлов
 * в случае их отсутствия. Если произошла ошибка при обработке входных параметров, пустые директории и файлы
 * не будут созданы. Проверка возможности создания директорий и файлов производится при
 * помощи {@link FileSystemManager}.
 */

public class ArgumentsHandler {

    private static final ArrayList<String> dataPaths = new ArrayList<>();

    private static final String dataPattern = ".+\\.txt$";
    private static final String outputRelativePattern = "^.?(?:[/\\\\][\\da-zA-Z_\\-. ]+[/\\\\]?)+$";
    private static final String outputFullPattern = "^[a-zA-Z]:(?:[/\\\\][\\da-zA-Z_\\-. ]+[/\\\\]?)+$";
    private static final String prefixPattern = "^[^\\\\/:*?\"<>|]+$";

    private static String baseIntegersName = "integers.txt";
    private static String baseFloatsName = "floats.txt";
    private static String baseStringsName = "strings.txt";

    private static final String workDir = System.getProperty("user.dir");

    private static String pathIntegers = null;
    private static String pathFloats = null;
    private static String pathStrings = null;

    private static boolean outputRelativeFlag = false;
    private static boolean outputFullFlag = false;
    private static String outputPath = null;

    private static boolean prefixFlag = false;

    private static boolean appendFlag = false;

    private static boolean simpleStatsFlag = false;
    private static boolean fullStatsFlag = false;

    /**
     * Метод, который обрабатывает аргументы {@code args} при запуске утилиты из консоли.
     *
     * <p> Метод нужен для контроля правильности передаваемых аргументов и формирования правильной
     * логики исполнения утилиты.
     *
     * <p> Допустимые опции:
     * <ul>
     *    <li>{@code --help} выводит в консоль опции и их описание.</li>
     *    <li>{@code -o} задает путь для файлов выходных данных, принимает аргумент, следующий
     *    после флага опции.</li>
     *    <li>{@code -p} задает префикс к базовому названию файлов выходных данных, принимает
     *    аргумент, следующий после флага опции.</li>
     *    <li>{@code -a} задает режим добавления в существующие файлы.</li>
     *    <li>{@code -s} задает режим вывода краткой статистики.</li>
     *    <li>{@code -f} задает режим вывода полной статистики.</li>
     * </ul>
     *
     * <p> После опций идут операнды - названия файлов с входными данными.
     *
     * <p> Результаты обработки сохраняются в статических полях, к которым можно обратиться при помощи геттеров.
     *
     * <p> Обработка исключений передаётся на вышестоящий уровень абстракции.
     *
     * @param args Аргументы, передаваемые при запуске утилиты через консоль.
     * @throws IllegalArgumentException Если были переданы некорректные аргументы.
     */

    public static void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--help":
                    String helpStr = """                          
                            Usage:
                                app.jar [-o <path>] [-p <prefix>] [-a] [-s | -f] [relative_path]data1.txt [[relative_path]data2.txt ...]
                            
                            Description:
                                Filters input files based on data type into integer, real, and string types.
                                The filtering results are saved as integers.txt, floats.txt, and strings.txt, respectively.
                            
                            Options:
                                --help                         Output help on using the utility.
                                -o, --output <path>            Specifies the output path for the utility's result. The passed path can be absolute or relative.
                                -p, --prefix <prefix>          Specifies a prefix for the name of the output files.
                                -a, --append                   Sets the mode for adding to existing files. If the mode is not specified, existing files will be overwritten.
                                -s                             Sets the mode for displaying brief statistics in the console.
                                -f                             Sets the mode for displaying complete statistics to the console.
                            
                            Examples:
                                app.jar -s -a -p sample- in1.txt
                                app.jar -o ./some/path -s -a in1.txt in2.txt in3.txt in4.txt
                                app.jar -o C:/Users/User/some/path -p new_ -f -a data1.txt data2.txt
                                app.jar in1.txt in2.txt in3.txt
                            """;
                    System.out.println(helpStr);
                    System.exit(0);

                case "-o":
                case "--output":
                    if (outputRelativeFlag || outputFullFlag) {
                        throw new IllegalArgumentException("The -o option was passed twice. " +
                                "Use --help for usage information.");
                    }

                    i++;
                    if (Pattern.matches(outputFullPattern, args[i])) {
                        try {
                            Path pathToCheck = Paths.get(args[i]);

                            if (!Files.exists(pathToCheck)) {
                                FileSystemManager.checkDirsCreatable(pathToCheck);
                            }
                            outputFullFlag = true;
                            outputPath = pathToCheck.toString();
                        } catch (IOException e) {
                            throw new RuntimeException("Unable to create a directory at the specified path. " +
                                    "Use --help for usage information.", e);
                        }
                    } else if (Pattern.matches(outputRelativePattern, args[i])) {
                        try {
                            args[i] = args[i].replaceAll("^./", "");
                            Path pathToCheck = Paths.get(workDir, args[i]);

                            if (!Files.exists(pathToCheck)) {
                                FileSystemManager.checkDirsCreatable(pathToCheck);
                            }
                            outputRelativeFlag = true;
                            outputPath = pathToCheck.toString();
                        } catch (IOException e) {
                            throw new RuntimeException("Unable to create a directory at the specified path. " +
                                    "Use --help for usage information.", e);
                        }
                    } else {
                        throw new IllegalArgumentException("Incorrect output path format. " +
                                "Use --help for usage information.");
                    }
                    continue;

                case "-p":
                case "--prefix":
                    if (prefixFlag) {
                        throw new IllegalArgumentException("The -p option was passed twice. " +
                                "Use --help for usage information.");
                    }
                    i++;
                    if (Pattern.matches(prefixPattern, args[i])) {
                        prefixFlag = true;
                        baseIntegersName = args[i].concat(baseIntegersName);
                        baseFloatsName = args[i].concat(baseFloatsName);
                        baseStringsName = args[i].concat(baseStringsName);
                    } else {
                        throw new IllegalArgumentException("The passed prefix contains invalid characters. " +
                                "Use --help for usage information.");
                    }
                    continue;

                case "-a":
                case "--append":
                    if (appendFlag) {
                        throw new IllegalArgumentException("The -a option was passed twice. " +
                                "Use --help for usage information.");
                    }
                    appendFlag = true;
                    continue;

                case "-s":
                    if (simpleStatsFlag || fullStatsFlag) {
                        throw new IllegalArgumentException("The -s option was passed twice or was passed after the -f option. " +
                                "Use --help for usage information.");
                    }
                    simpleStatsFlag = true;
                    continue;

                case "-f":
                    if (simpleStatsFlag || fullStatsFlag) {
                        throw new IllegalArgumentException("The -f option was passed twice or was passed after the -s option. " +
                                "Use --help for usage information.");
                    }
                    simpleStatsFlag = true;
                    fullStatsFlag = true;
                    continue;

                default:
                    if (Pattern.matches(dataPattern, args[i])) {
                        Path filePath = Paths.get(workDir, args[i]);
                        if (Files.exists(filePath)) {
                            dataPaths.add(filePath.toString());
                        } else {
                            System.err.printf("The passed input file \"%s\" does not exist.\n", args[i]);
                        }
                    } else {
                        System.err.printf("Invalid operand passed \"%s\".\n", args[i]);
                    }
            }
        }

        if (!(outputFullFlag || outputRelativeFlag)) {
            outputPath = workDir;
        }

        try {
            Path pathToCheckIntegers = Paths.get(outputPath, baseIntegersName);
            Path pathToCheckFloats = Paths.get(outputPath, baseFloatsName);
            Path pathToCheckStrings = Paths.get(outputPath, baseStringsName);

            if (!Files.exists(pathToCheckIntegers)) {
                FileSystemManager.checkFileCreatable(pathToCheckIntegers);
            }
            if (!Files.exists(pathToCheckFloats)) {
                FileSystemManager.checkFileCreatable(pathToCheckFloats);
            }
            if (!Files.exists(pathToCheckStrings)) {
                FileSystemManager.checkFileCreatable(pathToCheckStrings);
            }

            pathIntegers = pathToCheckIntegers.toString();
            pathFloats = pathToCheckFloats.toString();
            pathStrings = pathToCheckStrings.toString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create a file at the specified path. " +
                    "Use --help for usage information.", e);
        }

        if (dataPaths.isEmpty()) {
            throw new IllegalArgumentException("The input data has not been transmitted. " +
                    "Use --help for usage information.");
        }
    }

    public static String getPathIntegers() {
        return pathIntegers;
    }

    public static String getPathFloats() {
        return pathFloats;
    }

    public static String getPathStrings() {
        return pathStrings;
    }

    public static ArrayList<String> getDataPaths() {
        return dataPaths;
    }

    public static boolean isSimpleStatsFlag() {
        return simpleStatsFlag;
    }

    public static boolean isFullStatsFlag() {
        return fullStatsFlag;
    }

    public static boolean isAppendFlag() {
        return appendFlag;
    }
}