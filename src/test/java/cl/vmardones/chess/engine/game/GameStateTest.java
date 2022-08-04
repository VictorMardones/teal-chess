/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameStateTest {

  GameState gameState;

  @Mock Turn firstTurn;

  @Mock Turn secondTurn;

  @BeforeEach
  void setUp() {
    gameState = new GameState();
    gameState.setCurrentTurn(firstTurn);
  }

  @Test
  void save() {
    assertThat(gameState.save().state()).isEqualTo(firstTurn);
  }

  @Test
  void load() {
    var firstState = gameState.save();
    gameState.setCurrentTurn(secondTurn);
    gameState.load(firstState);

    assertThat(gameState.getCurrentTurn()).isEqualTo(firstTurn).isNotEqualTo(secondTurn);
  }
}
