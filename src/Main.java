import java.util.ArrayList;

public class Main {

    static void main(String[] args) {

//  -- Объявление и инициализация результирующих массивов --

        ArrayList<Long> integerList = new ArrayList<>();
        ArrayList<Double> floatList = new ArrayList<>();
        ArrayList<String> stringList = new ArrayList<>();

//  -- Объявление флагов, путей и массива с путями к входным данным --

        String outputPathIntegers;
        String outputPathFloats;
        String outputPathStrings;

        boolean appendFlag;

        boolean simpleStatsFlag;
        boolean fullStatsFlag;

        boolean integersWritten;
        boolean floatsWritten;
        boolean stringsWritten;

        ArrayList<String> pathList;

//  -- Обработка аргументов, переданных на вход утилиты --

        try {
            ArgumentsHandler.parseArgs(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

//  -- Загрузка входных данных и их фильтрация в цикле --

        pathList = ArgumentsHandler.getDataPaths();
        for (String path : pathList) {
            String data = FileProcessor.loadFile(path);
            FileProcessor.filter(data, integerList, floatList, stringList);
        }

//  -- Извлечение выходных путей для отфильтрованных данных и их запись в файлы --

        outputPathIntegers = ArgumentsHandler.getPathIntegers();
        outputPathFloats = ArgumentsHandler.getPathFloats();
        outputPathStrings = ArgumentsHandler.getPathStrings();
        appendFlag = ArgumentsHandler.isAppendFlag();

        integersWritten = FileProcessor.writeFile(integerList, outputPathIntegers, appendFlag);
        floatsWritten = FileProcessor.writeFile(floatList, outputPathFloats, appendFlag);
        stringsWritten = FileProcessor.writeFile(stringList, outputPathStrings, appendFlag);

//  -- Формирование вывода в консоль --

        simpleStatsFlag = ArgumentsHandler.isSimpleStatsFlag();
        fullStatsFlag = ArgumentsHandler.isFullStatsFlag();

        if (simpleStatsFlag) {
            if (integersWritten) {
                ViewHandler.addSimpleStatsIntegers(integerList);
            }

            if (floatsWritten) {
                ViewHandler.addSimpleStatsFloats(floatList);
            }

            if (stringsWritten) {
                ViewHandler.addSimpleStatsStrings(stringList);
            }
        }

        if (fullStatsFlag) {
            if (integersWritten) {
                ViewHandler.addFullStatsIntegers(integerList);
            }

            if (floatsWritten) {
                ViewHandler.addFullStatsFloats(floatList);
            }

            if (stringsWritten) {
                ViewHandler.addFullStatsStrings(stringList);
            }
        }
        ViewHandler.createFinalView();
        ViewHandler.showFinalView();
    }
}