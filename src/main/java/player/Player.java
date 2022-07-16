package player;

import board.Board;
import board.Move;
import com.google.common.collect.ImmutableList;
import piece.King;
import piece.Piece;

import java.util.Collection;

/**
 * The entity that controls the pieces in one side of the board.
 * It can be controlled either by a human or an AI.
 */
public abstract class Player {

    protected final Board board;
    protected final King king;
    protected final Collection<Move> legalMoves;
    private final boolean inCheck;
    private Boolean noEscapeMoves;

    public Player(Board board, King king, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.legalMoves = legalMoves;
        this.king = king;

        inCheck = !Player.calculateAttacksOnTile(king.getPosition(), opponentMoves).isEmpty();
    }

    private static Collection<Move> calculateAttacksOnTile(int kingPosition, Collection<Move> moves) {
        var attackMoves = moves.stream()
                .filter(move -> kingPosition == move.getDestination())
                .toList();

        return ImmutableList.copyOf(attackMoves);
    }

    /**
     * Used to check if a specific move can be performed.
     * @param move The move to check
     * @return True if the move is legal
     */
    public boolean isMoveLegal(final Move move) {
        return legalMoves.contains(move);
    }

    /**
     * Checks if the player is in check, which means that the king must be protected.
     * @return True if the player is in check
     */
    public boolean isInCheck() {
        return inCheck;
    }

    /**
     * Checks if the player is in checkmate, which means the game is lost.
     * This happens when the king is in check and there are no escape moves.
     * @return True if the player is in checkmate
     */
    public boolean isInCheckmate() {
        return isInCheck() && hasNoEscapeMoves();
    }

    private boolean hasNoEscapeMoves() {
        if (noEscapeMoves == null) {
            noEscapeMoves = legalMoves.stream()
                    .map(this::makeMove)
                    .noneMatch(transition -> transition.getMoveStatus().isDone());
        }

        return noEscapeMoves;
    }

    /**
     * Checks if the player is in stalemate, which means that game ends in a tie.
     * This happens when the king isn't in check and there are no escape moves.
     * @return True if the player is in stalemate
     */
    public boolean inInStalemate() {
        return !isInCheck() && hasNoEscapeMoves();
    }

    public boolean isCastled() {
        return false;
    }

    public MoveTransition makeMove(Move move){
        return null;
    }

    /**
     * Obtains the player's current pieces on the board.
     * @return The player's active pieces
     */
    public abstract Collection<Piece> getActivePieces();

    /**
     * Obtains the player's side.
     * @return The player's alliance
     */
    public abstract Alliance getAlliance();

    /**
     * Obtains the player in the other side of the board.
     * @return The opponent
     */
    public abstract Player getOpponent();
}
