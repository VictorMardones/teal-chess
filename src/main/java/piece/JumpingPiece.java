package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.ArrayList;
import java.util.List;

public abstract class JumpingPiece extends Piece {

    protected JumpingPiece(int position, Alliance alliance) {
        super(position, alliance);
    }

    abstract int[] getMoveOffsets();

    //TODO: refactor the logic to make it more readable
    @Override
    public List<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final var candidate : getMoveOffsets()) {
            var candidateDestination = position + candidate;

            if (BoardUtils.isInsideBoard(candidateDestination)) {

                if (isIllegalMove(candidateDestination)) {
                    continue;
                }

                final var candidateDestinationTile = board.getTile(candidateDestination);

                if (!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestination));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getAlliance();

                    if (alliance != pieceAlliance) {
                        legalMoves.add(new Move.AttackingMove(board, this,
                                candidateDestination, pieceAtDestination));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
}
