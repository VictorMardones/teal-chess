package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Piece;
import engine.piece.Rook;

public class QueenSideCastleMove extends CastleMove {

    public QueenSideCastleMove(
            Board board, Piece piece, Coordinate destination, Rook rook, Coordinate rookDestination) {
        super(board, piece, destination, rook, rookDestination);
    }

    @Override
    public String toString() {
        return "0-0-0";
    }
}
