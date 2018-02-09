import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class KrestikiNoliki {
    private GameLogic gameLogic;
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new KrestikiNoliki();
    }

    public KrestikiNoliki() {
        try {
            System.out.println("Игра крестики-нолики!");

            // инициализация поля
            int size = userInput("Введите размер поля", 3, 9);
            int winLength = userInput("Введите длину выигрышной комбинации", 3, size);
            gameLogic = new GameLogic(size, winLength);

            // чем будет играть юзер
            int userDot = userInput("Вы будете играть 'X'(0) или 'O'(1)", 0, 1);
            if (userDot == 0) {
                gameLogic.setDotUser('X');
                gameLogic.setDotAi('O');
            } else {
                gameLogic.setDotUser('O');
                gameLogic.setDotAi('X');
            }

            // игровой цикл
            printBoard();
            while (true) {
                humanTurn(); // ход игрока
                if (gameLogic.isPlayerWin()) {
                    printBoard();
                    System.out.println("Вы выиграли!!!");
                    break;
                }
                // поле заполнено?
                if (gameLogic.isBoardFilled()) {
                    printBoard();
                    System.out.println("Игра окончилась ничьей.");
                    break;
                }
                aiTurn(); // ход ИИ
                if (gameLogic.isAiWin()) {
                    printBoard();
                    System.out.println("Вы проиграли!!!");
                    break;
                }
                // поле заполнено?
                if (gameLogic.isBoardFilled()) {
                    printBoard();
                    System.out.println("Игра окончилась ничьей.");
                    break;
                }
                printBoard();
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так: " + e.getMessage());
        }
    }

    /**
     * запрос хода ИИ
     */
    private void aiTurn() {
        gameLogic.makeAiTurn();
    }

    /**
     * запрос хода игрока
     */
    private void humanTurn() {
        int x, y;
        do {
            y = userInput("Ваш ход, строка", 1, gameLogic.getFieldHeight());
            x = userInput("Ваш ход, столбец", 1, gameLogic.getFieldSize());
            x--;
            y--;
        } while (!gameLogic.makeHumanTurn(x, y));
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
        String fullmessage = String.format("%s (%d..%d): ", message, min, max);
        int chislo;
        while (true) {
            try {
                System.out.println(fullmessage);
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
     * вывод игрового поля вида
     * | 1 2 3
     * --------
     * 1| . . .
     * 2| . . .
     * 3| . . .
     */
    private void printBoard() {
        ArrayList<Character> board = gameLogic.getBoard();
        int width = gameLogic.getFieldSize();
        int height = gameLogic.getFieldHeight();
        System.out.print(" |");
        for (int i = 1; i <= width; i++)
            System.out.print(" " + i);
        System.out.println();
        for (int i = 0; i <= width; i++)
            System.out.print("--");
        for (int i = 0; i < board.size(); i++) {
            if (i % width == 0) {
                // новая строка
                System.out.println();
                System.out.print((i / width + 1) + "|");
            }
            System.out.print(" " + board.get(i));
        }
        System.out.println();
    }
}
