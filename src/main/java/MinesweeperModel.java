import java.util.ArrayList;
import java.util.List;

public class MinesweeperModel {

    private int side; //количество блоков в одной стороне шестиугольника
    private boolean firstStep;
    private boolean gameOver;
    private MinesweeperCell[][] cells;
    private int mineCount;

    private static final int MIN_SIDE_SIZE = 4;
    private static final int MAX_SIDE_SIZE = 9;

    private static final int MIN_MINE_COUNT = 7;
    private static final int MAX_MINE_COUNT = 40; //возможно надо создать число, генерирующееся автоматически в зависимости от стороны поля

    public void startGame(int side, int mineCount) {
        int cellsCount = 1;
        int s = 1;
        while (s < side) {
            cellsCount += 6 * s;
            s++;
        }
        if (side >= MIN_SIDE_SIZE && side <= MAX_SIDE_SIZE)
            this.side = side;
        if (mineCount >= MIN_MINE_COUNT && mineCount <= MAX_MINE_COUNT && mineCount < cellsCount)
            this.mineCount = mineCount;
        this.firstStep = true;
        this.gameOver = false;
        this.cells = new MinesweeperCell[side * 2 - 1][side * 2 - 1];
        int startIndCellUp = 0;  //индекс, с которого начинаются ячейки в конкретной строке
        while (startIndCellUp < side * 2 - 1) { //этот цикл закончится, когда индекс ячейки В СТРОКЕ станет равен длине серединной строки
            for (int strIndexUp = side - 1; strIndexUp >= 0; strIndexUp--) //этот цикл перебирает индексы строк от серединной (включительно) до первой
                for (int cellIndex = startIndCellUp; cellIndex < side * 2 - 1; cellIndex++) //этот цикл перебирает индексы клетки В СТРОКЕ (у каждой строки разный начальный индекс)
                    cells[strIndexUp][cellIndex] = new MinesweeperCell();
            startIndCellUp++;
        }
        int startIndCellDown = 1;
        while (startIndCellDown < side * 2 - 1) { //здесь всё то же самое, но от серединной строки (не включительно) до последней
            for (int strIndexDown = side; strIndexDown < side * 2 - 1; strIndexDown++)
                for (int cellIndex = startIndCellDown; cellIndex < side * 2 - 1; cellIndex++)
                    cells[strIndexDown][cellIndex] = new MinesweeperCell();
            startIndCellDown++;
        }
    }

    public void startGame() {
        this.firstStep = true;
        this.gameOver = false;
        this.cells = new MinesweeperCell[MIN_SIDE_SIZE * 2 - 1][MIN_SIDE_SIZE * 2 - 1];
        int startIndCellUp = 0;
        while (startIndCellUp < MIN_SIDE_SIZE) {
            for (int strIndexUp = MIN_SIDE_SIZE - 1; strIndexUp >= 0; strIndexUp--)
                for (int cellIndex = startIndCellUp; cellIndex < MIN_SIDE_SIZE * 2 - 1; cellIndex++)
                    cells[strIndexUp][cellIndex] = new MinesweeperCell();
            startIndCellUp++;
        }
        int startIndCellDown = 1;
        while (startIndCellDown < MIN_SIDE_SIZE) {
            for (int strIndexDown = MIN_SIDE_SIZE; strIndexDown < MIN_SIDE_SIZE * 2 - 1; strIndexDown++)
                for (int cellIndex = startIndCellDown; cellIndex < MIN_SIDE_SIZE * 2 - 1; cellIndex++)
                    cells[strIndexDown][cellIndex] = new MinesweeperCell();
            startIndCellDown++;
        }
    }

    MinesweeperCell getCell(int strIndex, int cellIndex) {
        if (strIndex < 0 || strIndex > side * 2 - 2 || cellIndex < 0 || cellIndex > side * 2 - 2 || cells[strIndex][cellIndex] == null)
            return null;
        else
            return cells[strIndex][cellIndex];
    }

    void setFlag(int strInd, int cellInd) {
        if (cells[strInd][cellInd].closed() && cells[strInd][cellInd].notFlagged())
            cells[strInd][cellInd].flagged();
    }

    boolean isWin() {
        int startIndCellUp = 0;
        while (startIndCellUp < side) {
            for (int strIndexUp = side - 1; strIndexUp >= 0; strIndexUp--)
                for (int cellIndex = startIndCellUp; cellIndex < side * 2 - 1; cellIndex++) {
                    MinesweeperCell cell = cells[strIndexUp][cellIndex];
                    if (cell.closed() || (cell.notFlagged() || cell.mined()))
                        return false;
                }
            startIndCellUp++;
        }
        int startIndCellDown = 1;
        while (startIndCellDown < side) {
            for (int strIndexDown = side; strIndexDown < side * 2 - 1; strIndexDown++)
                for (int cellIndex = startIndCellDown; cellIndex < side * 2 - 1; cellIndex++) {
                    MinesweeperCell cell = cells[strIndexDown][cellIndex];
                    if (cell.closed() || (cell.notFlagged() && cell.mined()))
                        return false;
                }
            startIndCellDown++;
        }
        return true;
    }

    boolean isGameOver() {
        return gameOver;
    }

    public void openCell(int strIndex, int cellIndex) {
        MinesweeperCell cell = getCell(strIndex, cellIndex);
        if (cell.empty())
            return;
        cell.open();
        if (cell.mined()) {
            gameOver = true;
            return;
        }
        if (firstStep) {
            firstStep = false;
            mineGenerator();
        }
        int mineAround = countMinesAround(strIndex, cellIndex);
        if (mineAround == 0) {
            List<MinesweeperCell> neighbours = getNeighbours(strIndex, cellIndex);
            for (MinesweeperCell n : neighbours) {
                if (n.closed()) { //открываем соседние клетки
                    for (int i = 0; i < side * 2 - 1; i++)
                        for (int j = 0; j < side * 2 - 1; j++)
                            if (cells[i][j] == n)
                                openCell(i, j);
                }
            }
        }

    }

    public void mineGenerator() {
        int mines = 0;
        while (mines < mineCount) {
            int si = (int) (Math.random() * (side * 2 - 1)); //случайный индекс строки
            int ci = (int) (Math.random() * (side * 2 - 1)); //случайный индекс ячейски в строке
            if (cells[si][ci] != null /*&& cells[si][ci].empty() */) {
                cells[si][ci].mined();
                mines++;
            }
        }
    }

    List<MinesweeperCell> getNeighbours(int strIndex, int cellIndex) {
        List<MinesweeperCell> neighbours = new ArrayList<>();
        for (int row = strIndex - 1; row <= strIndex + 1; row++)
            for (int cell = cellIndex - 1; cell <= cellIndex - 1; cell++) {
                if (cells[row][cell] != cells[strIndex][cellIndex] && cells[row][cell] != null)
                    neighbours.add(cells[row][cell]);
            }
        return neighbours;
    }

    int countMinesAround(int strIndex, int cellIndex) {
        List<MinesweeperCell> neighbours = getNeighbours(strIndex, cellIndex);
        int count = 0;
        for (MinesweeperCell n : neighbours) {
            if (n.mined())
                count++;
        }
        return count;
    }

    //этот метод нужен для вьюхи
    int getSideCount() { //возвращает число всех строк (и столбцов) поля
        return side * 2 - 1;
    }
}
