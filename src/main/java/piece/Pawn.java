package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import board.Move.MajorMove;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Pawn extends Piece {

    // TODO: Actually use this field
    private boolean isFirstMove;

    private final static int[] CANDIDATE_MOVE_OFFSET = { 7, 8, 9, 16};

    public Pawn(final int position, final Alliance alliance) {
        super(position, alliance);
        isFirstMove = false;
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final var currentCandidateOffset: getMoveOffsets()) {
            var candidateDestination = position + getAlliance().getDirection() * currentCandidateOffset;

            if (!BoardUtils.isInsideBoard(candidateDestination)) {
                continue;
            }

            if (currentCandidateOffset == 8 && !board.getTile(candidateDestination).isOccupied()) {
                // TODO: Improve the logic here
                legalMoves.add(new MajorMove(board, this, candidateDestination));
            } else if (currentCandidateOffset == 16 && isFirstMove &&
                    (BoardUtils.getRow(position) == 2 && isBlack()) ||
                    (BoardUtils.getRow(position) == 6 && isWhite())) {
                final int forwardCoordinate = position + 8 * alliance.getDirection();

                if (!board.getTile(forwardCoordinate).isOccupied()
                        && !board.getTile(candidateDestination).isOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestination));
                }
            } else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.getColumn(position) == 7  && isWhite()) ||
                    (BoardUtils.getColumn(position) == 0  && isBlack()))) {
                if (board.getTile(candidateDestination).isOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestination).getPiece();
                }
            } else if (currentCandidateOffset == 9) {

            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    int[] getMoveOffsets() {
        return CANDIDATE_MOVE_OFFSET;
    }

    @Override
    protected boolean isIllegalMove(final int destination) {
        return false;
    }
}
