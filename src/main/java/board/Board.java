package board;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import piece.*;
import player.Alliance;
import player.BlackPlayer;
import player.Player;
import player.WhitePlayer;

import java.util.*;

/**
 * The game board, made of 8x8 tiles.
 */
public class Board {

    private final List<Tile> gameBoard;
    @Getter
    private final Collection<Piece> whitePieces;
    @Getter
    private final Collection<Piece> blackPieces;

    @Getter
    private final Player currentPlayer;
    @Getter
    private final WhitePlayer whitePlayer;
    @Getter
    private final BlackPlayer blackPlayer;

    private Board(Builder builder) {
        gameBoard = createGameBoard(builder);

        whitePieces = calculateActivePieces(gameBoard, Alliance.WHITE);
        blackPieces = calculateActivePieces(gameBoard, Alliance.BLACK);

        final Collection<Move> whiteLegalMoves = calculateLegalMoves(whitePieces);
        final Collection<Move> blackLegalMoves = calculateLegalMoves(blackPieces);

        whitePlayer = new WhitePlayer(this, builder.whiteKing, whiteLegalMoves, blackLegalMoves);
        blackPlayer = new BlackPlayer(this, builder.blackKing, blackLegalMoves, whiteLegalMoves);

        currentPlayer = null;
    }

    private List<Tile> createGameBoard(final Builder builder) {
        final var tiles = new Tile[BoardUtils.MAX_TILES];

        for (var i = 0; i < BoardUtils.MAX_TILES; i++) {
            tiles[i] = Tile.create(i, builder.boardConfig.get(i));
        }

        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard() {
        var builder = new Builder();

        var blackKing = new King(4, Alliance.BLACK);
        var whiteKing = new King(60, Alliance.WHITE);

        builder.withPiece(new Rook(0, Alliance.BLACK))
                .withPiece(new Knight(1, Alliance.BLACK))
                .withPiece(new Bishop(2, Alliance.BLACK))
                .withPiece(new Queen(3, Alliance.BLACK))
                .withPiece(blackKing)
                .withBlackKing(blackKing)
                .withPiece(new Bishop(5, Alliance.BLACK))
                .withPiece(new Knight(6, Alliance.BLACK))
                .withPiece(new Rook(7, Alliance.BLACK));

        for (var i = 8; i <= 15; i++) {
            builder.withPiece(new Pawn(i, Alliance.BLACK));
        }

        for (var i = 48; i <= 55; i++) {
            builder.withPiece(new Pawn(i, Alliance.WHITE));
        }

        builder.withPiece(new Rook(56, Alliance.WHITE))
                .withPiece(new Knight(57, Alliance.WHITE))
                .withPiece(new Bishop(58, Alliance.WHITE))
                .withPiece(new Queen(59, Alliance.WHITE))
                .withPiece(whiteKing)
                .withWhiteKing(whiteKing)
                .withPiece(new Bishop(61, Alliance.WHITE))
                .withPiece(new Knight(62, Alliance.WHITE))
                .withPiece(new Rook(63, Alliance.WHITE));

        return builder.withNextTurn(Alliance.WHITE)
                .build();
    }

    public Tile getTile(final int coordinate) {
        return gameBoard.get(coordinate);
    }

    private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
        var pieces = gameBoard.stream()
                .filter(Tile::isOccupied)
                .map(Tile::getPiece)
                .filter(tile -> tile.getAlliance() == alliance)
                .toList();

        return ImmutableList.copyOf(pieces);
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final var piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        final var builder = new StringBuilder();

        for (var i = 0; i < BoardUtils.MAX_TILES; i++) {
            final var tileText = gameBoard.get(i).toString();
            builder.append(String.format("%s  ", tileText));

            if ((i + 1) % BoardUtils.NUMBER_OF_ROWS == 0) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    public static class Builder {

        private Map<Integer, Piece> boardConfig;
        private Alliance nextTurn;
        private King whiteKing;
        private King blackKing;

        public Builder() {
            boardConfig = new HashMap<>();
        }

        public Builder withPiece(final Piece piece) {
            boardConfig.put(piece.getPosition(), piece);
            return this;
        }

        public Builder withNextTurn(final Alliance nextTurn) {
            this.nextTurn = nextTurn;
            return this;
        }

        public Builder withWhiteKing(final King whiteKing) {
            this.whiteKing = whiteKing;
            return this;
        }

        public Builder withBlackKing(final King blackKing) {
            this.blackKing = blackKing;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
