package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import board.Move.CaptureMove;
import board.Move.NormalMove;
import board.Tile;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.*;
import java.util.stream.Collectors;

public abstract class JumpingPiece extends Piece {

    protected JumpingPiece(int position, Alliance alliance) {
        super(position, alliance);
    }

    abstract int[] getMoveOffsets();

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final var legalMoves = Arrays.stream(getMoveOffsets())
                .map(offset -> position + offset)
                .filter(BoardUtils::isInsideBoard)
                .filter(destination -> !isIllegalMove(destination))
                .mapToObj(board::getTile)
                .filter(tile -> PieceUtils.isAccessible(this, tile))
                .map(tile -> PieceUtils.createMove(this, tile, board))
                .toList();

        return ImmutableList.copyOf(legalMoves);
    }
}
