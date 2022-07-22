package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.BoardService;
import engine.move.CaptureMove;
import engine.move.MajorPieceMove;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

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
    private BoardService boardService;

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    // TODO: Refactor this code when the pawn is implemented completely
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        return Arrays.stream(PawnOffset.values())
                .filter(pawnOffset -> boardService.isInside(getDestination(pawnOffset)))
                .filter(pawnOffset -> isInMoveRange(getDestination(pawnOffset)))
                .map(pawnOffset -> handleOffset(pawnOffset, board))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(ImmutableList.toImmutableList());
    }

    private int getDestination(PawnOffset pawnOffset) {
        return position + getAlliance().getDirection() * pawnOffset.offset;
    }

    private Optional<Move> handleOffset(PawnOffset pawnOffset, Board board) {
        var destination = getDestination(pawnOffset);

        return switch (pawnOffset) {
            case FIRST_MOVE -> createFirstMove(board, destination);
            case FORWARD_MOVE -> createForwardMove(board, destination);
            case LEFT_CAPTURE, RIGHT_CAPTURE -> createCaptureMove(board, destination);
        };
    }

    private Optional<Move> createFirstMove(Board board, int destination) {
        if (!isFirstMovePossible(board)) {
            return Optional.empty();
        }

        return Optional.of(new MajorPieceMove(board, this, destination));
    }

    private Optional<Move> createForwardMove(Board board, int destination) {
        if (board.getTile(destination).getPiece().isPresent()) {
            return Optional.empty();
        }

        // TODO: Deal with promotions
        return Optional.of(new MajorPieceMove(board, this, destination));
    }

    private Optional<Move> createCaptureMove(Board board, int destination) {
        final var capturablePiece = board.getTile(destination).getPiece();

        if (capturablePiece.isPresent() && isEnemy(capturablePiece.get())) {
            return Optional.of(new CaptureMove(board, this, destination, capturablePiece.get()));
        }

        return Optional.empty();
    }

    @Override
    public boolean isInMoveRange(final int destination) {
        return Math.abs(boardService.getColumn(position) - boardService.getColumn(destination)) <= 1;
    }

    private boolean isFirstMovePossible(final Board board) {
        return board.getTile(position + 8 * alliance.getDirection()).getPiece().isEmpty()
                && board.getTile(position + 16 * alliance.getDirection()).getPiece().isEmpty();
    }

    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestination(), alliance, boardService);
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
