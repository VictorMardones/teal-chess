package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import board.Move.AttackingMove;
import board.Move.MajorMove;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.ArrayList;
import java.util.List;

/**
 * The knight piece.
 * It moves in an L shape.
 */
public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_OFFSETS = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(int position, Alliance alliance) {
        super(position, alliance);
    }

    //TODO: refactor the logic to make it more readable
    @Override
    public List<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final var candidate: CANDIDATE_MOVE_OFFSETS) {
            var candidateDestinationCoordinate = position + candidate;

            if (BoardUtils.isValidCoordinate(candidateDestinationCoordinate)) {

                if (BoardUtils.getTileColor(candidateDestinationCoordinate) == BoardUtils.getTileColor(position)) {
                    continue;
                }

                final var candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if (!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getAlliance();

                    if (alliance != pieceAlliance) {
                        legalMoves.add(new AttackingMove(board, this,
                                candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
}
