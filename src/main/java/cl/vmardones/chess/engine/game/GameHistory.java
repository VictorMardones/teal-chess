/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.game;

import java.util.ArrayList;
import java.util.List;

public class GameHistory {

  private final List<TurnMemento> history = new ArrayList<>();

  public void add(final TurnMemento state) {
    history.add(state);
  }

  public TurnMemento get(final int index) {
    return history.get(index);
  }
}
