public class MinesweeperCell {
    boolean isOpen, isFlag, isMine;

    void open() {
        if (!isFlag)
            isOpen = true;
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

    void bang() {
        isMine = true;
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
}
