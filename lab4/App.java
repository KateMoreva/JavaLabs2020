import java.util.Scanner;

public final class App {

    public static void main(String[] args) {
        DataBase.addTestDataToTable();
        System.out.println("Вы работаете с базой данных магазина." +
                "Введите команду или получите справку (help).");
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine())
        {
            Commands.execute(in.nextLine());
        }
        DataBase.main(args);
    }
}