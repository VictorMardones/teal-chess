package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.BoardUtils;
import engine.move.Move;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * A piece that can move in a specific set of spaces.
 */
public interface JumpingPiece extends Piece {

    int[] getMoveOffsets();

    @Override
    default Collection<Move> calculateLegalMoves(final Board board) {

        // TODO: Remove these non-null filters, change how the methods work
        return Arrays.stream(getMoveOffsets())
                .map(offset -> getPosition() + offset)
                .filter(BoardUtils::isInsideBoard)
                .filter(this::isInMoveRange)
                .mapToObj(board::getTile)
                .filter(Objects::nonNull)
                .filter(tile -> PieceUtils.isAccessible(this, tile))
                .map(tile -> PieceUtils.createMove(this, tile, board))
                .filter(Objects::nonNull)
                .collect(ImmutableList.toImmutableList());
    }
}
