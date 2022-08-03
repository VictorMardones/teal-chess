/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.board;

import static cl.vmardones.chess.engine.board.Board.MAX_TILES;
import static cl.vmardones.chess.engine.board.Board.MIN_TILES;

import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.*;
import cl.vmardones.chess.engine.player.Alliance;
import com.google.common.collect.ImmutableList;
import java.util.*;
import java.util.stream.IntStream;

public class BoardService {

  /**
   * Creates a standard chessboard, which consists of a rank filled with 8 pawns on each side with a
   * formation of 8 major pieces behind.
   *
   * @return The standard chessboard
   */
  // TODO: Parse a text file to create the board
  public Board createStandardBoard() {

    final var whiteKing = new King(Coordinate.of("e1"), Alliance.WHITE);
    final var blackKing = new King(Coordinate.of("e8"), Alliance.BLACK);

    final var builder = Board.builder(whiteKing, blackKing);

    builder
        .piece(new Rook(Coordinate.of("a8"), Alliance.BLACK))
        .piece(new Knight(Coordinate.of("b8"), Alliance.BLACK))
        .piece(new Bishop(Coordinate.of("c8"), Alliance.BLACK))
        .piece(new Queen(Coordinate.of("d8"), Alliance.BLACK))
        .piece(blackKing)
        .piece(new Bishop(Coordinate.of("f8"), Alliance.BLACK))
        .piece(new Knight(Coordinate.of("g8"), Alliance.BLACK))
        .piece(new Rook(Coordinate.of("h8"), Alliance.BLACK));

    IntStream.range(8, 16)
        .mapToObj(Coordinate::of)
        .map(coordinate -> new Pawn(coordinate, Alliance.BLACK))
        .forEach(builder::piece);

    IntStream.range(48, 56)
        .mapToObj(Coordinate::of)
        .map(coordinate -> new Pawn(coordinate, Alliance.WHITE))
        .forEach(builder::piece);

    builder
        .piece(new Rook(Coordinate.of("a1"), Alliance.WHITE))
        .piece(new Knight(Coordinate.of("b1"), Alliance.WHITE))
        .piece(new Bishop(Coordinate.of("c1"), Alliance.WHITE))
        .piece(new Queen(Coordinate.of("d1"), Alliance.WHITE))
        .piece(whiteKing)
        .piece(new Bishop(Coordinate.of("f1"), Alliance.WHITE))
        .piece(new Knight(Coordinate.of("g1"), Alliance.WHITE))
        .piece(new Rook(Coordinate.of("h1"), Alliance.WHITE));

    return builder.build();
  }

  public String prettyPrint(final Board board) {
    final var builder = new StringBuilder();

    IntStream.range(MIN_TILES, MAX_TILES)
        .mapToObj(Coordinate::of)
        .map(coordinate -> String.format(getFormat(coordinate), board.getTile(coordinate)))
        .forEach(builder::append);

    return builder.toString();
  }

  private String getFormat(final Coordinate coordinate) {
    return (coordinate.index() + 1) % Board.SIDE_LENGTH == 0 ? "%s  \n" : "%s  ";
  }

  public Collection<Move> calculateLegals(final Board board, final Collection<Piece> pieces) {
    return pieces.stream()
        .map(piece -> piece.calculateLegals(board))
        .flatMap(Collection::stream)
        .collect(ImmutableList.toImmutableList());
  }
}
