package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.BoardService;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * A piece that can move in a specific set of spaces.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class JumpingPiece implements Piece {

    protected int position;
    protected Alliance alliance;
    protected BoardService boardService;

    abstract int[] getMoveOffsets();

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        return Arrays.stream(getMoveOffsets())
                .map(offset -> getPosition() + offset)
                .filter(getBoardService()::isInside)
                .filter(this::isInMoveRange)
                .mapToObj(board::getTile)
                .filter(tile -> isAccessible(this, tile))
                .map(tile -> createMove(this, tile, board))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(ImmutableList.toImmutableList());
    }
}
