package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.BoardUtils;
import engine.move.CaptureMove;
import engine.move.MajorPieceMove;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;

/**
 * The pawn piece.
 * It only moves forward (depending on the side) and can eat other pieces diagonally.
 * A very weak piece, but it can be promoted when getting to the last rank at the opposite side.
 */
@Getter
@AllArgsConstructor
public final class Pawn implements Piece {

    private int position;
    private Alliance alliance;

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    // TODO: Refactor this code when the pawn is implemented completely
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        return Arrays.stream(PawnOffset.values())
                .filter(pawnOffset -> BoardUtils.isInsideBoard(getDestination(pawnOffset)))
                .filter(pawnOffset -> isInMoveRange(getDestination(pawnOffset)))
                .map(pawnOffset -> handleOffset(pawnOffset, board))
                .collect(ImmutableList.toImmutableList());
    }

    private int getDestination(PawnOffset pawnOffset) {
        return position + getAlliance().getDirection() * pawnOffset.offset;
    }

    private Move handleOffset(PawnOffset pawnOffset, Board board) {
        var destination = getDestination(pawnOffset);

        return switch (pawnOffset) {
            case FIRST_MOVE -> createFirstMove(board, destination);
            case FORWARD_MOVE -> createForwardMove(board, destination);
            case LEFT_CAPTURE, RIGHT_CAPTURE -> createCaptureMove(board, destination);
        };
    }

    private Move createFirstMove(Board board, int destination) {
        if (isFirstMovePossible(board)) {
            return new MajorPieceMove(board, this, destination);
        }

        return null;
    }

    private Move createForwardMove(Board board, int destination) {
        if (!board.getTile(destination).isOccupied()) {
            // TODO: Deal with promotions
            return new MajorPieceMove(board, this, destination);
        }

        return null;
    }

    private Move createCaptureMove(Board board, int destination) {
        if (board.getTile(destination).isOccupied()) {
            final var capturablePiece = board.getTile(destination).getPiece();

            if (isEnemy(capturablePiece)) {
                return new CaptureMove(board, this, destination, capturablePiece);
            }
        }

        return null;
    }

    @Override
    public boolean isInMoveRange(final int destination) {
        return Math.abs(BoardUtils.getColumn(position) - BoardUtils.getColumn(destination)) <= 1;
    }

    private boolean isFirstMovePossible(final Board board) {
        return !board.getTile(position + 8 * alliance.getDirection()).isOccupied()
                && !board.getTile(position + 16 * alliance.getDirection()).isOccupied();
    }

    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestination(), alliance);
    }

    @AllArgsConstructor
    private enum PawnOffset {
        FIRST_MOVE(8),
        FORWARD_MOVE(16),
        LEFT_CAPTURE(7),
        RIGHT_CAPTURE(9);

        private final int offset;
    }
}
