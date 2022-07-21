package engine.move;

import engine.board.Board;
import engine.piece.Piece;
import engine.piece.Rook;

abstract class CastleMove extends Move {
    protected final Rook rook;
    protected final int rookDestination;

    public CastleMove(Board board, Piece piece, int destination, Rook rook, int rookDestination) {
        super(board, piece, destination);
        this.rook = rook;
        this.rookDestination = rookDestination;
        castling = true;
    }

    @Override
    public Board execute() {
        final var builder = new Board.Builder(board.getWhitePlayer().getKing(), board.getBlackPlayer().getKing());

        board.getCurrentPlayer().getActivePieces().stream()
                .filter(activePiece -> !piece.equals(activePiece) && !piece.equals(rook))
                .forEach(builder::withPiece);

        board.getCurrentPlayer().getOpponent().getActivePieces()
                .forEach(builder::withPiece);

        builder.withPiece(piece.movePiece(this))
                .withPiece(new Rook(rookDestination, rook.getAlliance()))
                .withNextTurn(board.getCurrentPlayer().getOpponent().getAlliance());

        return builder.build();
    }
}
