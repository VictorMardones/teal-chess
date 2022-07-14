package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SlidingPiece extends Piece {

    protected SlidingPiece(int position, Alliance alliance) {
        super(position, alliance);
    }

    abstract int[] getMoveVectors();

    //TODO: refactor the logic to make it more readable
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateOffset : getMoveVectors()) {

            int candidateDestination = position;

            while (BoardUtils.isInsideBoard(candidateOffset)) {

                if (isIllegalMove(candidateDestination)) {
                    break;
                }

                candidateDestination += candidateOffset;

                if (BoardUtils.isInsideBoard(candidateDestination)) {

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

                        break;
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
}
