import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class HexagonalMinesweeper extends JFrame {

    final String TITLE_OF_PROGRAM = "HexagonalMinesweeper";
    final String SIGN_OF_FLAG = "F";
    final int START_LOCATION = 200;
    final int BLOCK_SIZE = 30; //размер блокаа 30 на 30 пикселей
    final int FIELD_DX = 6; //расширение по горизонтали; ширина рамок
    final int FIELD_DY = 45; //расширение по вертикали; ширина заголовка окна
    final int[] COLORS_OF_NUMBERS = {0x0000FF, 0x008000, 0xFF0000, 0x800000, 0x0};
    Random random = new Random(); //рандомно расставляет бомбы
    int numbersOfMines;
    int fieldSize;
    int countOpenedCells; //переменная хранит открытое кол-во ячеек
    boolean youWon, bangMine;
    int bangX, bangY;

    public static void main(String[] args) {
        new HexagonalMinesweeper();
    }

    HexagonalMinesweeper() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, fieldSize * BLOCK_SIZE + FIELD_DX, fieldSize * BLOCK_SIZE + FIELD_DY);
        setResizable(true);
        //setIconImage();
        Canvas canvas = new Canvas();

    }
}
