package piece;

import board.Board;
import board.Move;
import board.Move.CaptureMove;
import board.Move.NormalMove;
import board.Tile;

public class PieceUtils {

    private PieceUtils() {
        throw new IllegalStateException("You cannot instantiate me!");
    }

    /**
     * Checks if the given piece can get the destination.
     * This happens only if the destination is free or has a piece that can be captured.
     * @param piece The piece we're currently using.
     * @param destination The target destination.
     * @return True if the piece can get to the destination.
     */
    public static boolean isAccessible(final Piece piece, final Tile destination) {
        return !destination.isOccupied() && !piece.sameAliance(destination.getPiece());
    }

    /**
     * Creates a move, based on the piece and the destination.
     * @param piece The piece we're moving.
     * @param destination The destination tile.
     * @param board The current game board.
     * @return A normal or capture move, depending on the destination.
     * Null otherwise.
     */
    public static Move createMove(final Piece piece, final Tile destination, final Board board) {
        if (!destination.isOccupied()) {
            return new NormalMove(board, piece, destination.getCoordinate());
        }

        final var capturablePiece = destination.getPiece();

        if (!piece.sameAliance(capturablePiece)) {
            return new CaptureMove(board, piece, destination.getCoordinate(), capturablePiece);
        }

        // TODO: Replace null with IllegalMove or an exception
        return null;
    }
}
