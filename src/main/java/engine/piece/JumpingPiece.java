package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.BoardUtils;
import engine.move.Move;
import engine.player.Alliance;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * A piece that can move in a specific set of spaces.
 */
public abstract class JumpingPiece extends Piece {

    protected JumpingPiece(int position, Alliance alliance, PieceType pieceType) {
        super(position, alliance, pieceType);
    }

    abstract int[] getMoveOffsets();

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        // TODO: Remove these non-null filters, change how the methods work
        return Arrays.stream(getMoveOffsets())
                .map(offset -> position + offset)
                .filter(BoardUtils::isInsideBoard)
                .filter(this::isLegalMove)
                .mapToObj(board::getTile)
                .filter(Objects::nonNull)
                .filter(tile -> PieceUtils.isAccessible(this, tile))
                .map(tile -> PieceUtils.createMove(this, tile, board))
                .filter(Objects::nonNull)
                .collect(ImmutableList.toImmutableList());
    }
}
