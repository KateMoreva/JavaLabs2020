import java.util.HashMap;
import java.util.Map;

class Commands {
    public interface Command {
        void execute(String[] args) throws Exception;
    }

    private static void putHelp(String[] args) throws Exception{
        if (args.length != 1) {
            throw new Exception("Неправильное число аргументов.");
        }
        System.out.println("Доступные команды:\n"
                + "shaw_all Вывод всей таблицы." + "\n"
                + "add Добавить товар в таблицу, в табице не может быть 2 товара с одинаковым именем" + "\n"
                + "delete Удалить товар из таблицы по имени." + "\n"
                + "price Узнать цену товара по его имени" + "\n"
                + "change_price Изменить цену товара" + "\n"
                + "filter_by_price Вывести товары в заданном ценовом диапазоне цен" + "\n"
                + "[Имя команды] -- help или -h  Вызов справки по каждой команде");
    }

    private static void putShawAll(String[] args) throws Exception{
        if (args.length == 1) {
          DataBase.printAllTable();
        }
        else {
            if (args[1].equals("--help") || (args[1].equals("-h"))) {
                System.out.println("Команда 'shaw_all' позволяет осуществить просмотр всей базы данных."
                        + "\n" + "Для просмотра введите команду и нажмите 'Enter'.");
            } else
                throw new Exception("Неправильное число аргументов.");
        }
    }
    private static void putAdd(String[] args) throws Exception{
        if (args.length == 3) {
            String name = args[1];
            int price = Integer.parseInt(args[2]);
            DataBase.addDataToTable(name, price);
        }
        else {
            if (args[1].equals("--help") || (args[1].equals("-h"))) {
                System.out.println("Команда 'add' позволяет осуществить добавление новой записи в базу данных."
                        + "\n" + "Для добавления введите команду, информацию о продукте и нажмите 'Enter'."
                        + "\n" + "Пример: add товар666 1050");
            } else
                throw new Exception("Неправильное число аргументов.");
        }
    }
    private static void putDel(String[] args) throws Exception{
        if (args.length == 2 && !(args[1].equals("--help") || (args[1].equals("-h")))) {
            String name = args[1];
            DataBase.deleteDataFromTable(name);
        }
        else {
            if (args[1].equals("--help") || (args[1].equals("-h"))) {
                System.out.println("Команда 'delete' позволяет осуществить удалаеие записи из базы данных по имени."
                        + "\n" + "Для удалаения введите команду, название продукта и нажмите 'Enter'."
                        + "\n" + "Пример: delete товар666");
            } else
                throw new Exception("Неправильное число аргументов.");
        }
    }
    private static void putPrice(String[] args) throws Exception{
        if (args.length == 2 && !(args[1].equals("--help") || (args[1].equals("-h")))) {
            String name = args[1];
            DataBase.priceByNameSearchInTable(name);
        }
        else {
            if (args[1].equals("--help") || (args[1].equals("-h"))) {
                System.out.println("Команда 'price' позволяет оузнать цену товара по его имени."
                        + "\n" + "Для просмотра стоимости введите команду, название продукта и нажмите 'Enter'."
                        + "\n" + "Пример: price товар777");
            } else
                throw new Exception("Неправильное число аргументов.");
        }
    }
    private static void putCangePrice(String[] args) throws Exception{
        if (args.length == 3) {
            String name = args[1];
            int price = Integer.parseInt(args[2]);
            DataBase.changeProductPrice(name, price);
        }
        else {
            if (args[1].equals("--help") || (args[1].equals("-h"))) {
                System.out.println("Команда 'change_price' позволяет изменить цену товара по его имени."
                        + "\n" + "Для просмотра стоимости введите команду, название продукта и нажмите 'Enter'."
                        + "\n" + "Пример: price товар777");
            } else
                throw new Exception("Неправильное число аргументов.");
        }
    }

    private static void putFilterPrice(String[] args) throws Exception{
        if (args.length == 3 ) {
            int price1 = Integer.parseInt(args[1]);
            int price2 = Integer.parseInt(args[2]);
            DataBase.priceSearchInTable(price1, price2);
        }
        else {
            if (args[1].equals("--help") || (args[1].equals("-h"))) {
                System.out.println("Команда 'filter_by_price' позволяет оузнать цену товара по его имени."
                        + "\n" + "Для просмотра стоимости введите команду, название продукта и нажмите 'Enter'."
                        + "\n" + "Пример: price товар777");
            } else
                throw new Exception("Неправильное число аргументов.");
        }
    }

    static void execute(String line){
        Map<String, Command> commandsMap = new HashMap<>();

        commandsMap.put("help", Commands::putHelp);
        commandsMap.put("shaw_all", Commands::putShawAll);
        commandsMap.put("add", Commands::putAdd);
        commandsMap.put("delete", Commands::putDel);
        commandsMap.put("price", Commands::putPrice);
        commandsMap.put("change_price", Commands::putCangePrice);
        commandsMap.put("filter_by_price", Commands::putFilterPrice);

        String[] args = line.split(" ");

        Command command = commandsMap.get(args[0]);

        try {
            if (command != null) {
                command.execute(args);
            } else {
                throw new Exception("Несуществующая команда.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Для получения справки по командам введите 'help'.");
        }
    }
}