package move;

import board.Board;
import piece.Piece;
import piece.Rook;

public final class QueenSideCastleMove extends CastleMove {

    public QueenSideCastleMove(Board board, Piece piece, int destination, Rook rook, int rookDestination) {
        super(board, piece, destination, rook, rookDestination);
    }

    @Override
    public String toString() {
        return "0-0-0";
    }
}
