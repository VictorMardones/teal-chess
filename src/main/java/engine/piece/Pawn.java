package engine.piece;

import engine.board.Board;
import engine.board.Coordinate;
import engine.board.Tile;
import engine.move.Move;
import engine.move.PawnCaptureMove;
import engine.move.PawnJump;
import engine.move.PawnMove;
import engine.player.Alliance;
import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * The pawn piece. It only moves forward (depending on the side) and can eat other pieces
 * diagonally. A very weak piece, but it can be promoted when getting to the last rank at the
 * opposite side.
 */
@Getter
@AllArgsConstructor
@ToString(includeFieldNames = false)
@Slf4j
public class Pawn implements JumpingPiece {

    private static final int[][] WHITE_OFFSETS = {{-1, 1}, {0, 1}, {1, 1}, {0, 2}};
    private static final int[][] BLACK_OFFSETS = calculateBlackOffsets();

    private static int[][] calculateBlackOffsets() {
        return Arrays.stream(WHITE_OFFSETS)
                .map(offset -> new int[] {offset[0], Alliance.BLACK.getDirection() * offset[1]})
                .toArray(int[][]::new);
    }

    private Coordinate position;
    private Alliance alliance;
    private boolean firstMove;

    public Pawn(Coordinate position, Alliance alliance) {
        this(position, alliance, true);

        log.debug("Black offsets: " + Arrays.deepToString(BLACK_OFFSETS));
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public int[][] getMoveOffsets() {
        return switch (getAlliance()) {
            case BLACK -> WHITE_OFFSETS;
            case WHITE -> BLACK_OFFSETS;
        };
    }

    @Override
    public Optional<Move> createMove(Tile destination, Board board) {
        if (isCaptureMove(destination)) {
            return createCaptureMove(board, destination.getCoordinate());
        }

        if (isFirstMovePossible(board)) {
            return createJumpMove(board, destination.getCoordinate());
        }

        return createForwardMove(board, destination.getCoordinate());
    }

    private boolean isCaptureMove(Tile destination) {
        return !getPosition().sameColumnAs(destination.getCoordinate());
    }

    private Optional<Move> createCaptureMove(Board board, Coordinate destination) {
        final var capturablePiece = board.getTile(destination).getPiece();

        if (capturablePiece.isPresent() && isEnemyOf(capturablePiece.get())) {
            return Optional.of(new PawnCaptureMove(board, this, destination, capturablePiece.get()));
        }

        return Optional.empty();
    }

    private Optional<Move> createJumpMove(Board board, Coordinate destination) {
        return Optional.of(new PawnJump(board, this, destination));
    }

    private boolean isFirstMovePossible(final Board board) {

        var forward = position.down(alliance.getDirection());
        var destination = position.down(2 * alliance.getDirection());

        if (forward.isEmpty() || destination.isEmpty()) {
            return false;
        }

        return isFirstMove()
                && board.getTile(forward.get()).getPiece().isEmpty()
                && board.getTile(destination.get()).getPiece().isEmpty();
    }

    private Optional<Move> createForwardMove(Board board, Coordinate destination) {
        if (board.getTile(destination).getPiece().isPresent()) {
            return Optional.empty();
        }

        // TODO: Deal with promotions
        return Optional.of(new PawnMove(board, this, destination));
    }

    @Override
    public Pawn move(final Move move) {
        return new Pawn(move.getDestination(), alliance, false);
    }
}
