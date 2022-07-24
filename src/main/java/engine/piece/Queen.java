package engine.piece;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** The queen, strongest piece in the game. It can move horizontally, vertically and diagonally. */
@Getter
@AllArgsConstructor
@ToString(includeFieldNames = false)
public class Queen implements SlidingPiece {

    private static final int[][] MOVE_VECTORS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

    private Coordinate position;
    private Alliance alliance;
    private boolean firstMove;

    public Queen(Coordinate position, Alliance alliance) {
        this(position, alliance, true);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public int[][] getMoveVectors() {
        return new int[0][];
    }

    @Override
    public Queen move(final Move move) {
        return new Queen(move.getDestination(), alliance, false);
    }
}
