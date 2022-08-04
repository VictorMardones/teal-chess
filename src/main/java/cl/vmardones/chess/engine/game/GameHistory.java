/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.game;

import java.util.ArrayList;
import java.util.List;

class GameHistory {

  private final List<TurnMemento> history = new ArrayList<>();

  void add(final TurnMemento state) {
    history.add(state);
  }

  TurnMemento get(final int index) {
    return history.get(index);
  }
}
