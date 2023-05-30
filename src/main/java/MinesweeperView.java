import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class MinesweeperView extends JFrame {
    MinesweeperModel model;
    MinesweeperController controller;
    JFrame frame;
    private JPanel panel;
    private final int IMAGE_HEIGHT = 50;
    private final int IMAGE_WIDTH = 43;
    private int size;

    MinesweeperView(MinesweeperModel model) {
        this.model = model;
    }

    void init(int side, int countBomb) {
        size = model.getSideCount();
        setTitle("HexagonalMinesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /*JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameMenuItem = new JMenuItem("New game");
        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startNewGame();
            }
        });
        gameMenu.add(newGameMenuItem);
        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);*/
        initPanel(size);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initPanel(int size) {
        panel = new JPanel(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                MinesweeperCell cell = model.getCell(i, j);
                if (cell == null)
                    continue;
                JPanel cellPanel = new JPanel();
                cellPanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
                int finalI = i;
                int finalJ = j;
                cellPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (cell.notFlagged()) {
                            if (e.getButton() == MouseEvent.BUTTON1)
                                controller.onLeftClick(finalI, finalJ);
                            else if (e.getButton() == MouseEvent.BUTTON3)
                                controller.onRightClick(finalI, finalJ);
                        }
                    }
                });
                cellPanel.add(new JLabel((Icon) getImage(getState(i, j))));
                panel.add(cellPanel);
            }
        }
        add(panel);
    }

    private String getState(int strInd, int cellInd) {
        MinesweeperCell cell = model.getCell(strInd, cellInd);
        String name = "";
        if (cell != null) {
            if (model.isGameOver() && cell.mined())
                name = "bombed";
            if (cell.closed())
                name = "closed";
            if (cell.opened()) {
                name = "opened";
                panel.repaint();
                if (cell.empty())
                    name = "zero";
                else if (cell.getCountBomb() > 0)
                    name = String.valueOf(cell.getCountBomb());
                else if (cell.mined())
                    name = "bombed";
            } else if (cell.flagged())
                name = "flagged";
        }
        return name;
    }

    private Image getImage(String name) {
        String fileName = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        return icon.getImage();
    }

    int[] getGameSettings() {
        JTextField cellsField = new JTextField();
        JTextField bombField = new JTextField();
        Object[] message = {"Side size (min 4, max 9):", cellsField, "Number of bomb (min 7):", bombField};
        int option = JOptionPane.showConfirmDialog(frame, message, "Game settings", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int cells = Integer.parseInt(cellsField.getText());
            int bombs = Integer.parseInt(bombField.getText());
            return new int[]{cells, bombs};
        } else
            return null;
    }

    void showWinMessage() {
        JOptionPane.showMessageDialog(frame, "You won! :D");
    }

    void showGameOverMessage() {
        JOptionPane.showMessageDialog(frame, "You lose! :(");
    }

}