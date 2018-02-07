import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class KrestikiNoliki {
    private GameLogic gameLogic;
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new KrestikiNoliki();
    }

    public KrestikiNoliki() {
        System.out.println("Игра крестики-нолики!");

        // инициализация поля
        int width = userInput("Введите ширину поля (3..10): ", 3, 10);
        int height = userInput("Введите высоту поля (3..10): ", 3, 10);
        int winLength = userInput("Введите длину выигрышной комбинации: ", 3, Math.max(width, height));
        gameLogic = new GameLogic(width, height, winLength);

        // чем будет играть юзер
        int userDot = userInput("Вы будете играть 'X'(0) или 'O'(1) (введите 0 или 1): ", 0, 1);
        if (userDot == 0) {
            gameLogic.setDotUser('X');
            gameLogic.setDotAi('O');
        } else {
            gameLogic.setDotUser('O');
            gameLogic.setDotAi('X');
        }

        // печатаем поле
        printMap();

        System.out.println("End game!");
    }

    /**
     * Запрос пользовательского ввода
     *
     * @param message отображаемое сообщение
     * @param min     минимум
     * @param max     максимум
     * @return int результат
     */
    private int userInput(String message, int min, int max) {
        int chislo = 0;
        while (true) {
            try {
                System.out.println(message);
                chislo = scanner.nextInt();
                if (chislo < min) {
                    System.out.println("Слишком маленькое число");
                } else if (chislo > max) {
                    System.out.println("Слишком большое число");
                } else
                    break;
            } catch (InputMismatchException e) {
                // ошибка типа
                System.out.println("Это не было целым числом");
                scanner.next();
            }
        }
        return chislo;
    }

    /**
     * вывод игрового поля
     */
    private void printMap() {
        int map[][] = gameLogic.getMap();
        System.out.println(Arrays.deepToString(map));
    }
}
