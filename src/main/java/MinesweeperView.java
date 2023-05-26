import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinesweeperView extends JFrame {

    MinesweeperModel model;
    MinesweeperController controller;

    public void init() {
        setTitle("HexagonalMinesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setSize(400, 400);
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(createBoard(7));
        add(panel);
        setVisible(true);
    }

    int[] getGameSettings() {
        return new int[]{5, 8}; //доделать
    }

    private JButton createBoard(int strCount) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int[] xStartCoordinates = {50, 70, 90, 90, 70, 50}; //это координаты ПЕРВОГО блока в строке, они меняются только при переходе на новую строку
                int[] yStartCoordinates = {300, 280, 300, 320, 340, 320};
                for (int strInd = strCount / 2; strInd >= 0; strInd--) { //строим последовательно строчки от серединной вверх
                    if (strInd < strCount / 2) //если это не серединная строка, то координаты начальной клетки меняются
                        for (int i = 0; i < xStartCoordinates.length; i++) {
                            xStartCoordinates[i] += 20; //это обеспечивает ровное смещение клеток, чтобы строчки блоков ложились ровно
                            yStartCoordinates[i] -= 40;
                        }
                    int[] xCoordinates = new int[6];
                    System.arraycopy(xStartCoordinates, 0, xCoordinates, 0, xStartCoordinates.length);
                    int blockCount = (strCount / 2 + 1) + strInd; //количество блоков в строке зависит от её индекса
                    while (blockCount != 0) { //выполняем этот цикл, пока не нарисуем все блоки в строке
                        g.setColor(Color.GRAY);
                        g.fillPolygon(xCoordinates, yStartCoordinates, 6);
                        g.setColor(Color.BLACK);
                        g.drawPolygon(xCoordinates, yStartCoordinates, 6);
                        blockCount--;
                        for (int num = 0; num < 6; num++) { //координаты каждого следующего блока в строке немного смещаются
                            xCoordinates[num] += 40;
                        }
                    }//достроить нижнюю часть поля
                }
            }
        };
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    void synkWithModel() { //синхронизация с моделью

    }

    void blockCell(int strInd, int cellInd, boolean block) {
        JButton[][] buttons = new JButton[model.getSideCount()][model.getSideCount()];
        JButton button = buttons[strInd][cellInd];
        if (block) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    e.consume();
                }
            });
        } else {
            for (MouseListener listener : button.getMouseListeners()) {
                button.removeMouseListener(listener);
            }
        }
    }

    void showWinMessage() {
        JWindow window = new JWindow();
        JLabel label = new JLabel("Поздравляем! Вы победили!");
        JButton button = new JButton("ОК");
    }

    void showGameOverMessage() {
        JWindow window = new JWindow();
        JLabel label = new JLabel("Игра окончена! Вы проиграли!");
        JButton button = new JButton("ОК");
    }
}
