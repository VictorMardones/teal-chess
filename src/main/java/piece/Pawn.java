package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import board.Move.NormalMove;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class Pawn extends Piece {

    // TODO: Actually use this field
    private boolean isFirstMove;

    private final static int FORWARD_MOVE = 8;
    private final static int FIRST_MOVE = 16;
    private final static int LEFT_CAPTURE = 7;
    private final static int RIGHT_CAPTURE = 9;

    private final static int[] CANDIDATE_MOVE_OFFSET = { LEFT_CAPTURE, FORWARD_MOVE, RIGHT_CAPTURE, FIRST_MOVE };

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
                .collect(Collectors.toList());

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
                    // TODO: Improve the logic here
                    return new NormalMove(board, this, destination);
                }
            }
            case LEFT_CAPTURE -> {
                if (!((BoardUtils.getColumn(position) == 7 && isWhite()) ||
                        (BoardUtils.getColumn(position) == 0 && isBlack()))) {
                    if (board.getTile(destination).isOccupied()) {
                        final Piece pieceOnCandidate = board.getTile(destination).getPiece();
                    }
                }
            }
            case RIGHT_CAPTURE -> {
                // TODO
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
