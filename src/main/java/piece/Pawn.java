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
import java.util.stream.Collectors;

public final class Pawn extends Piece {

    // TODO: Actually use this field
    private boolean isFirstMove;

    private static final int FORWARD_MOVE = 8;
    private static final int FIRST_MOVE = 16;
    private static final int LEFT_CAPTURE = 7;
    private static final int RIGHT_CAPTURE = 9;

    private static final int[] CANDIDATE_MOVE_OFFSET = { LEFT_CAPTURE, FORWARD_MOVE, RIGHT_CAPTURE, FIRST_MOVE };

    public Pawn(final int position, final Alliance alliance) {
        super(position, alliance);
        isFirstMove = false;
    }

    // TODO: Refactor this code when the pawn is implemented completely
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final var legalMoves = Arrays.stream(getMoveOffsets())
                .map(offset -> position + getAlliance().getDirection() * offset)
                .filter(BoardUtils::isInsideBoard)
                .filter(destination -> !isIllegalMove(destination))
                // TODO: Fix the next part
                //.map(handleOffset(offset, board, destination))


                .mapToObj(board::getTile)
                .filter(tile -> PieceUtils.isAccessible(this, tile))
                .map(tile -> PieceUtils.createMove(this, tile, board))
                .toList();

        return ImmutableList.copyOf(legalMoves);
    }

    private Move handleOffset(int offset, Board board, int destination) {
        switch (offset) {
            case FIRST_MOVE -> {
                if ((BoardUtils.getRow(position) == 2 && isBlack()) ||
                        (BoardUtils.getRow(position) == 6 && isWhite())) {
                    final int forwardCoordinate = position + 8 * alliance.getDirection();

                    if (!board.getTile(forwardCoordinate).isOccupied()
                            && !board.getTile(destination).isOccupied()) {
                        return new NormalMove(board, this, destination);
                    }
                }
            }
            case FORWARD_MOVE -> {
                if (!board.getTile(destination).isOccupied()) {
                    // TODO: Deal with promotions
                    return new NormalMove(board, this, destination);
                }
            }
            case LEFT_CAPTURE, RIGHT_CAPTURE -> {
                if (!isIllegalMove(destination) && board.getTile(destination).isOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(destination).getPiece();

                    if (!sameAliance(pieceOnCandidate)) {
                            return new CaptureMove(board, this, destination, pieceOnCandidate);
                        }
                    }
                }
            }

        return null;
    }

    int[] getMoveOffsets() {
        return CANDIDATE_MOVE_OFFSET;
    }

    @Override
    protected boolean isIllegalMove(final int destination) {
        return Math.abs(BoardUtils.getColumn(position) - BoardUtils.getColumn(destination)) > 1;
    }
}
