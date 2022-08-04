/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.gui;

import cl.vmardones.chess.engine.move.Move;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
class MoveLog {

  @Getter private final List<Move> moves = new ArrayList<>();

  private Move lastMove;

  void add(final Move move) {
    moves.add(move);
    lastMove = move;
  }

  int size() {
    return moves.size();
  }

  void clear() {
    moves.clear();
  }

  Move remove(final int index) {
    return moves.remove(index);
  }

  boolean remove(final Move move) {
    return moves.remove(move);
  }

  Optional<Move> getLastMove() {

    final var move = lastMove;
    lastMove = null;
    return Optional.ofNullable(move);
  }
}
