/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.game;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: See if this class can be immutable
/** A mutable class that holds the current state of the game. */
class GameState {

  @Getter(AccessLevel.PACKAGE)
  @Setter(AccessLevel.PACKAGE)
  private Turn currentTurn;

  TurnMemento save() {
    return new TurnMemento(currentTurn);
  }

  void load(final TurnMemento turnMemento) {
    currentTurn = turnMemento.state();
  }
}
