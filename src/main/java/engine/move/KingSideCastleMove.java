package engine.move;

import engine.board.Board;
import engine.piece.Piece;
import engine.piece.Rook;

public final class KingSideCastleMove extends CastleMove {

    public KingSideCastleMove(Board board, Piece piece, int destination, Rook rook, int rookDestination) {
        super(board, piece, destination, rook, rookDestination);
    }

    @Override
    public String toString() {
        return "0-0";
    }
}
