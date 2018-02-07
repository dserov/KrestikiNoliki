/**
 * игровая логика
 *
 * @author DSerov
 * @version dated 07 feb, 2018
 */
public class GameLogic {
    private char DOT_X = 'X';
    private char DOT_O = 'O';
    private char DOT_EMPTY = '.';

    /**
     * массив игрового поля
     */
    private int map[][];

    /**
     * сиволы, которыми играет игрок
     */
    private char dotUser;
    /**
     * символы, которыми играет ИИ
     */
    private char dotAi;
    /**
     * ширина игрового поля
     */
    private int fieldWidth;
    /**
     * высота игрового поля
     */
    private int fieldHeight;
    /**
     * длина выигрышной игровой последовательности
     */
    private int winSequenceLength;

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public void setWinSequenceLength(int winSequenceLength) {
        this.winSequenceLength = winSequenceLength;
    }

    public GameLogic(int fieldWidth, int fieldHeight, int winSequenceLength) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.winSequenceLength = winSequenceLength;
        initMap();
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
    private void initMap() {
        for (int i = 0; i < fieldHeight; i++)
            for (int j = 0; j < fieldWidth; j++)
                map[i][j] = DOT_EMPTY;
    }

    public int[][] getMap() {
        return map.clone();
    }
}
