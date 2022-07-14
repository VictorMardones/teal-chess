package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends Piece {

    private final static int[] CANDIDATE_MOVE_OFFSETS = { -8, -1, 1, 8};

    public Rook(int position, Alliance alliance) {
        super(position, alliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateOffset: CANDIDATE_MOVE_OFFSETS) {

            int candidateDestination = position;

            while (BoardUtils.isValidCoordinate(candidateOffset)) {

                if (!BoardUtils.sameRow(position, candidateDestination) &&
                        !BoardUtils.sameColumn(position, candidateDestination)) {
                    break;
                }

                candidateDestination += candidateOffset;

                if (BoardUtils.isValidCoordinate(candidateDestination)) {

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
