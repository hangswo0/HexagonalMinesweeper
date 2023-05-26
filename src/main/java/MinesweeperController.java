public class MinesweeperController {

    private MinesweeperModel model;
    private MinesweeperView view;

    public MinesweeperController(MinesweeperModel model, MinesweeperView view) {
        this.model = model;
        this.view = view;
    }

    void setView(MinesweeperView view) {
        this.view = view;
    }

    void startNewGame() {
        int[] gameSettings = view.getGameSettings();
        try {
            model.startGame(gameSettings[0], gameSettings[1]);
        } catch (Exception e) {
            model.startGame();
        }
        view.init();
    }

    void onLeftClick(int strIndex, int cellIndex) {
        model.openCell(strIndex, cellIndex);
        view.synkWithModel();
        if (model.isWin()) {
            view.showWinMessage();
            startNewGame();
        } else if (model.isGameOver()) {
            view.showGameOverMessage();
            startNewGame();
        }
    }

    void onRightClick(int strIndex, int cellIndex) {
        model.setFlag(strIndex, cellIndex);
        boolean state = false;
        if (model.getCell(strIndex, cellIndex).flagged())
            state = true;
        view.blockCell(strIndex, cellIndex, state);
        view.synkWithModel();
    }
}
