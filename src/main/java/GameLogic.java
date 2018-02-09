import java.util.ArrayList;

/**
 * игровая логика
 *
 * @author DSerov
 * @version dated 07 feb, 2018
 */
public class GameLogic {
    private final char DOT_X = 'X';
    private final char DOT_O = 'O';
    private final char DOT_EMPTY = '.';
    private final char DOT_MASK = ' ';

    /**
     * массив игрового поля
     */
    private ArrayList<Character> board;

    /**
     * сиволы, которыми играет игрок
     */
    private char dotUser;

    /**
     * символы, которыми играет ИИ
     */
    private char dotAi;

    /**
     * размер игрового поля
     */
    private int fieldSize;

    /**
     * длина выигрышной игровой последовательности
     */
    private int winSequenceLength;

    GameLogic(int fieldSize, int winSequenceLength) throws Exception {
        this.fieldSize = fieldSize;
        this.winSequenceLength = winSequenceLength;
        initBoard();
    }

    public void setDotUser(char dotUser) {
        this.dotUser = dotUser;
    }

    public void setDotAi(char dotAi) {
        this.dotAi = dotAi;
    }

    /**
     * проинициализируем игровое поле умолчательными значениями
     */
    private void initBoard() throws Exception {
//        char[] test =
//                {DOT_X, DOT_O, DOT_EMPTY, DOT_EMPTY,
//                        DOT_X, DOT_EMPTY, DOT_EMPTY, DOT_O,
//                        DOT_EMPTY, DOT_EMPTY, DOT_EMPTY, DOT_EMPTY,
//                        DOT_O, DOT_O, DOT_EMPTY, DOT_EMPTY};

        if (fieldSize == 0)
            throw new Exception("Неверные параметры инициализации игрового поля");
        board = new ArrayList<>(fieldSize * fieldSize);
        for (int i = 0; i < fieldSize * fieldSize; i++)
            board.add(DOT_EMPTY);
//            board.add(test[i]);
    }

    /**
     * FIXME: тут бы возвращать копию объекта, чтоб его не изменили снаружи класса
     *
     * @return ArrayList
     */
    public ArrayList<Character> getBoard() {
        return board;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getFieldHeight() {
        return fieldSize;
    }

    /**
     * проверка, что ячейка пуста
     *
     * @param x int
     * @param y int
     * @return boolean
     */
    private boolean isCellEmpty(int x, int y) {
        return (x >= 0 || y >= 0 || x < fieldSize || y < fieldSize) &&
                (board.get(y * fieldSize + x).equals(DOT_EMPTY));
    }


    public boolean makeHumanTurn(int x, int y) {
        if (isCellEmpty(x, y)) {
            board.set(y * fieldSize + x, dotUser);
            return true;
        }
        return false;
    }

    /**
     * Выиграл юзер?
     *
     * @return boolean
     */
    public boolean isPlayerWin() {
        return (isWinCombination(board, dotUser));
    }

    /**
     * Выиграл ИИ?
     *
     * @return boolean
     */
    public boolean isAiWin() {
        return (isWinCombination(board, dotAi));
    }

    /**
     * проверка, что указанный символ - выиграл
     *
     * @param board расклад, для которого идет расчет
     * @param dot   char игрок или ии
     * @return boolean true - выиграл
     */
    private boolean isWinCombination(ArrayList<Character> board, char dot) {
        for (int i = 0; i < board.size(); i++) {
            if (isWinHorizontal(board, i, dot) ||
                    isWinVertical(board, i, dot) ||
                    isWinDiagonal(board, i, dot) ||
                    isWinAntiDiagonal(board, i, dot))
                return true;
        }
        return false;
    }

    /**
     * Ищем выигрышную обратную диагональ
     *
     * @param board расклад, для которого идет расчет
     * @param index int стартовая точка
     * @param dot   char искомый символ
     * @return boolean
     */
    private boolean isWinAntiDiagonal(ArrayList<Character> board, int index, char dot) {
        int x = index % fieldSize;
        int y = index / fieldSize;
        if (x < (winSequenceLength - 1) || y > (fieldSize - winSequenceLength))
            return false; // выигрышная антидиагональ не существует
        // есть шанс
        for (int i = 0; i < winSequenceLength; i++) {
            if (board.get(index) == dot) {
                index += (fieldSize - 1);
            } else
                return false;
        }
        return true;
    }

    /**
     * Ищем выигрышную прямую диагональ
     *
     * @param board расклад, для которого идет расчет
     * @param index int стартовая точка
     * @param dot   char искомый символ
     * @return boolean
     */
    private boolean isWinDiagonal(ArrayList<Character> board, int index, char dot) {
        int x = index % fieldSize;
        int y = index / fieldSize;
        if (x > (fieldSize - winSequenceLength) || y > (fieldSize - winSequenceLength))
            return false; // выигрышная диагональ не существует
        // есть шанс
        for (int i = 0; i < winSequenceLength; i++) {
            if (board.get(index) == dot) {
                index += (fieldSize + 1);
            } else
                return false;
        }
        return true;
    }

    /**
     * Ищем выигрышную горизонталь
     *
     * @param board расклад, для которого идет расчет
     * @param index int стартовая точка
     * @param dot   char искомый символ
     * @return boolean
     */
    private boolean isWinHorizontal(ArrayList<Character> board, int index, char dot) {
        int x = index % fieldSize;
        if (x > (fieldSize - winSequenceLength))
            return false; // выигрышная горизонталь не существует
        // есть шанс
        for (int i = 0; i < winSequenceLength; i++) {
            if (board.get(index) == dot) {
                index++;
            } else
                return false;
        }
        return true;
    }

    /**
     * Ищем выигрышную вертикаль
     *
     * @param board расклад, для которого идет расчет
     * @param index int стартовая точка
     * @param dot   char искомый символ
     * @return boolean
     */
    private boolean isWinVertical(ArrayList<Character> board, int index, char dot) {
        int y = index / fieldSize;
        if (y > (fieldSize - winSequenceLength))
            return false; // выигрышная вертикаль не существует
        // есть шанс
        for (int i = 0; i < winSequenceLength; i++) {
            if (board.get(index) == dot) {
                index += fieldSize;
            } else
                return false;
        }
        return true;
    }

    /**
     * Проверка, заполнена ли доска?
     *
     * @return boolean
     */
    public boolean isBoardFilled() {
        for (Character cell : board) {
            if (cell == DOT_EMPTY) return false;
        }
        return true;
    }

    /**
     * расчет хода ИИ
     */
    public void makeAiTurn() {
        Move move;
        if (fieldSize < 4)
            move = MiniMax(board, dotAi);
        else {
            // попытка сократить число обрабатываемых ячеек
            ArrayList<Integer> list;
            list = maskFreeCells();
            move = MiniMax(board, dotAi);
            unmaskFreeCells(list);
        }
        board.set(move.index, dotAi);
    }

    /**
     * хранит индексы пустых клеток, рассматривать которые нет необходимости
     *
     * @return ArrayList
     */
    private ArrayList<Integer> maskFreeCells() {
        ArrayList<Integer> list = new ArrayList<>();
        int x, y, x1, y1;
        boolean found;
        int maxIndex = board.size();
        for (int i = 0; i < maxIndex; i++) {
            if (board.get(i) != DOT_EMPTY) continue;
            found = true;
            x = i % fieldSize;
            y = i / fieldSize;
            x1 = x - 1;
            y1 = y - 1;
            if (x1 >= 0 && x1 < fieldSize && y1 >= 0 && y1 < fieldSize)
                found &= (board.get(y1 * fieldSize + x1) == DOT_EMPTY);
            x1 = x;
            y1 = y - 1;
            if (x1 >= 0 && x1 < fieldSize && y1 >= 0 && y1 < fieldSize)
                found &= (board.get(y1 * fieldSize + x1) == DOT_EMPTY);
            x1 = x + 1;
            y1 = y - 1;
            if (x1 >= 0 && x1 < fieldSize && y1 >= 0 && y1 < fieldSize)
                found &= (board.get(y1 * fieldSize + x1) == DOT_EMPTY);
            x1 = x + 1;
            y1 = y;
            if (x1 >= 0 && x1 < fieldSize && y1 >= 0 && y1 < fieldSize)
                found &= (board.get(y1 * fieldSize + x1) == DOT_EMPTY);
            x1 = x + 1;
            y1 = y + 1;
            if (x1 >= 0 && x1 < fieldSize && y1 >= 0 && y1 < fieldSize)
                found &= (board.get(y1 * fieldSize + x1) == DOT_EMPTY);
            x1 = x;
            y1 = y + 1;
            if (x1 >= 0 && x1 < fieldSize && y1 >= 0 && y1 < fieldSize)
                found &= (board.get(y1 * fieldSize + x1) == DOT_EMPTY);
            x1 = x - 1;
            y1 = y + 1;
            if (x1 >= 0 && x1 < fieldSize && y1 >= 0 && y1 < fieldSize)
                found &= (board.get(y1 * fieldSize + x1) == DOT_EMPTY);
            x1 = x - 1;
            y1 = y;
            if (x1 >= 0 && x1 < fieldSize && y1 >= 0 && y1 < fieldSize)
                found &= (board.get(y1 * fieldSize + x1) == DOT_EMPTY);
            if (found)
                list.add(i);
        }
        for (int i : list)
            board.set(i, DOT_MASK);
        return list;
    }

    private void unmaskFreeCells(ArrayList<Integer> list) {
        for (int i : list)
            board.set(i, DOT_EMPTY);
    }

    /**
     * вспомогательный класс
     */
    class Move {
        int score;
        int index;

        Move(int score, int index) {
            this.score = score;
            this.index = index;
        }

        Move() {
            this(0,0);
        }
    }

    private Move MiniMax(ArrayList<Character> newBoard, char dot) {
        // доступные клетки
        ArrayList<Integer> availSpots = getFreeCellsIndexes(newBoard);

        // проверка результата расчета
        if (isWinCombination(newBoard, dotUser)) {
            return new Move(-10, 0);
        } else if (isWinCombination(newBoard, dotAi)) {
            return new Move(10, 0);
        } else if (availSpots.size() == 0)
            return new Move();

        // расчет ходов
        ArrayList<Move> moves = new ArrayList<>();

        // расчет очков по всем доступным клеткам
        for (Integer item : availSpots) {
            // создадим объект для хода
//            Move move = new Move(0, item);

            // сделаем ход за текущего игрока
            newBoard.set(item, dot);

            // рассчитаем очки
            Move move = ((dot == dotAi) ? MiniMax(newBoard, dotUser) : MiniMax(newBoard, dotAi));
            move.index = item;

            // очистим клетку и сохраним результат
            newBoard.set(item, DOT_EMPTY);
            moves.add(move);
        }

        // выбор наилучшего хода
        Move bestMove = new Move();
        if (dot == dotAi) {
            bestMove.score = -10000;
            for (Move move : moves) {
                if (move.score > bestMove.score) {
                    bestMove.score = move.score;
                    bestMove.index = move.index;
                }
            }
        } else {
            bestMove.score = 10000;
            for (Move move : moves) {
                if (move.score < bestMove.score) {
                    bestMove.score = move.score;
                    bestMove.index = move.index;
                }
            }
        }

        return bestMove;
    }

    /**
     * @param board расклад, для которого идет расчет
     * @return ArrayList
     */
    private ArrayList<Integer> getFreeCellsIndexes(ArrayList<Character> board) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < board.size(); i++)
            if (board.get(i) == DOT_EMPTY)
                list.add(i);
        return list;
    }

}
