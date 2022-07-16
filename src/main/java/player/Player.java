package player;

import board.Board;
import board.Move;
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

    public Player(Board board, King king, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.legalMoves = legalMoves;
        this.king = king;
    }

    /**
     * Used to check if a specific move can be performed.
     * @param move The move to check.
     * @return True if the move is legal.
     */
    public boolean isMoveLegal(final Move move) {
        return legalMoves.contains(move);
    }

    /**
     * Checks if the player is in check, which means that the king must be protected.
     * @return True if the player is in check
     */
    public boolean isInCheck() {
        return false;
    }

    /**
     * Checks if the player is in checkmate, which means the game is lost.
     * @return True if the player is in checkmate
     */
    public boolean isInCheckmate() {
        return false;
    }

    /**
     * Checks if the player is in stalemate, which means that game ends in a tie.
     * @return True if the player is in stalemate
     */
    public boolean inInStalemate() {
        return false;
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
