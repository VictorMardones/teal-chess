package board;

import lombok.NoArgsConstructor;
import piece.Piece;
import player.Alliance;

import java.util.Map;

/**
 * The game board, made of 8x8 tiles.
 */
public class Board {

    private Board(Builder builder) {
    }

    public Tile getTile(final int coordinate) {
        return null;
    }

    @NoArgsConstructor
    public static class Builder {

        Map<Integer, Piece> boardConfig;

        Alliance nextTurn;


        public Board build() {
            return new Board(this);
        }

    }
}
