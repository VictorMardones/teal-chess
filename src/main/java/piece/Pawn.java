package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import board.Move.CaptureMove;
import board.Move.NormalMove;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public final class Pawn extends Piece {

    // TODO: Actually use this field
    private boolean isFirstMove;

    private static final int FORWARD_MOVE = 8;
    private static final int FIRST_MOVE = 16;
    private static final int LEFT_CAPTURE = 7;
    private static final int RIGHT_CAPTURE = 9;

    private static final int[] MOVE_OFFSET = {LEFT_CAPTURE, FORWARD_MOVE, RIGHT_CAPTURE, FIRST_MOVE};

    public Pawn(int position, Alliance alliance) {
        super(position, alliance);
    }

    // TODO: Refactor this code when the pawn is implemented completely
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final var legalMoves = Arrays.stream(getMoveOffsets())
                .filter(offset -> BoardUtils.isInsideBoard(getDestination(offset)))
                .filter(offset -> !isIllegalMove(getDestination(offset)))
                .mapToObj(offset -> handleOffset(offset, board))
                .filter(Objects::nonNull)
                .toList();

        return ImmutableList.copyOf(legalMoves);
    }

    private int getDestination(int offset) {
        return position + getAlliance().getDirection() * offset;
    }

    private Move handleOffset(int offset, Board board) {
        var destination = getDestination(offset);

        return switch (offset) {
            case FIRST_MOVE -> createFirstMove(board, destination);
            case FORWARD_MOVE -> createForwardMove(board, destination);
            case LEFT_CAPTURE, RIGHT_CAPTURE -> createCaptureMove(board, destination);
            default -> null;
        };
    }

    private Move createFirstMove(Board board, int destination) {
        if (isFirstMovePossible(board)) {
            return new NormalMove(board, this, destination);
        }

        return null;
    }

    private Move createForwardMove(Board board, int destination) {
        if (!board.getTile(destination).isOccupied()) {
            // TODO: Deal with promotions
            return new NormalMove(board, this, destination);
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

    int[] getMoveOffsets() {
        return MOVE_OFFSET;
    }

    @Override
    protected boolean isIllegalMove(final int destination) {
        return Math.abs(BoardUtils.getColumn(position) - BoardUtils.getColumn(destination)) > 1;
    }

    private boolean isFirstMovePossible(final Board board) {
        return !board.getTile(position + 8 * alliance.getDirection()).isOccupied()
                && !board.getTile(position + 16 * alliance.getDirection()).isOccupied();
    }

    @Override
    public String toString() {
        return PieceType.PAWN.getPieceName();
    }
}
