package launcher;

import board.Board;

public final class Chess {
    public static void main(String[] args) {
        final var board = Board.createStandardBoard();
        System.out.println(board);
    }
}
