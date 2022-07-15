package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public abstract class JumpingPiece extends Piece {

    protected JumpingPiece(int position, Alliance alliance, PieceType pieceType) {
        super(position, alliance, pieceType);
    }

    abstract int[] getMoveOffsets();

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        // TODO: Remove these non-null filters, change how the methods work
        final var legalMoves = Arrays.stream(getMoveOffsets())
                .map(offset -> position + offset)
                .filter(BoardUtils::isInsideBoard)
                .filter(destination -> !isIllegalMove(destination))
                .mapToObj(board::getTile)
                .filter(Objects::nonNull)
                .filter(tile -> PieceUtils.isAccessible(this, tile))
                .map(tile -> PieceUtils.createMove(this, tile, board))
                .filter(Objects::nonNull)
                .toList();

        return ImmutableList.copyOf(legalMoves);
    }
}
