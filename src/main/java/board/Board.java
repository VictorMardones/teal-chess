package board;

import com.google.common.collect.ImmutableList;
import lombok.NoArgsConstructor;
import piece.Piece;
import player.Alliance;

import java.util.List;
import java.util.Map;

/**
 * The game board, made of 8x8 tiles.
 */
public class Board {

    private final List<Tile> gameBoard;

    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
    }

    private List<Tile> createGameBoard(final Builder builder) {
        final var tiles = new Tile[BoardUtils.MAX_TILES];

        for (var i = 0; i < BoardUtils.MAX_TILES; i++) {
            tiles[i] = Tile.create(i, builder.boardConfig.get(i));
        }

        return ImmutableList.copyOf(tiles);
    }

    public Tile getTile(final int coordinate) {
        return null;
    }

    @NoArgsConstructor
    public static class Builder {

        Map<Integer, Piece> boardConfig;

        Alliance nextTurn;

        public Builder withPiece(final Piece piece) {
            boardConfig.put(piece.getPosition(), piece);
            return this;
        }

        public Builder withNextTurn(final Alliance nextTurn) {
            this.nextTurn = nextTurn;
            return this;
        }

        public Board build() {
            return new Board(this);
        }

    }
}
