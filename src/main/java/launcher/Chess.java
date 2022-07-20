package launcher;

import engine.board.Board;
import gui.Table;

public final class Chess {
    public static void main(String[] args) {
        final var board = Board.createStandardBoard();
        System.out.println(board);

        var table = new Table();
    }
}
