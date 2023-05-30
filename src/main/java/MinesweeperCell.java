public class MinesweeperCell {
    boolean isOpen, isFlag, isMine;
    int countOpenedCells, countBombNear;

    void open() {
        if (!isFlag)
            isOpen = true;
        //if (!isMine) countOpenedCells++; //если мины нет, то счетчик открытых ячеек увеличивается на 1
    }

    void setEmpty() {
        isMine = false;
    }

    void setMined() {
        isMine = true;
    }

    boolean opened() {
        return isOpen;
    }

    boolean closed() {
        return !isOpen;
    }

    boolean mined() {
        return isMine;
    }

    boolean empty() {
        return !isMine;
    }

    boolean flagged() {
        return isFlag;
    }

    boolean notFlagged() {
        return !isFlag;
    }

    int getCountBomb() {
        return countBombNear;
    }
}
