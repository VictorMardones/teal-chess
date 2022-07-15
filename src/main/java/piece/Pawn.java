package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import board.Move.MajorMove;
import player.Alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_OFFSET = {8};

    public Pawn(int position, Alliance alliance) {
        super(position, alliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final var currentCandidateOffset: getMoveOffsets()) {
            var candidateDestination = position + getAlliance().getDirection() * currentCandidateOffset;

            if (!BoardUtils.isInsideBoard(candidateDestination)) {
                continue;
            }

            if (currentCandidateOffset == 8 && !board.getTile(candidateDestination).isOccupied()) {
                // TODO: Improve the logic here
                legalMoves.add(new MajorMove(board, this, candidateDestination));
            }
        }
    }

    int[] getMoveOffsets() {
        return CANDIDATE_MOVE_OFFSET;
    }

    @Override
    protected boolean isIllegalMove(int destination) {
        return false;
    }
}
