/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */
package engine.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AllianceTest {

    List<Player> players;

    @Mock
    Player whitePlayer;

    @Mock
    Player blackPlayer;

    @Test
    void getWhiteDirection() {
        assertThat(Alliance.WHITE.getDirection()).isPositive().isEqualTo(1);
    }

    @Test
    void getBlackDirection() {
        assertThat(Alliance.BLACK.getDirection()).isNegative().isEqualTo(-1);
    }

    @Test
    void chooseWhitePlayer() {
        when(whitePlayer.getAlliance()).thenReturn(Alliance.WHITE);
        when(blackPlayer.getAlliance()).thenReturn(Alliance.BLACK);

        players = List.of(blackPlayer, whitePlayer);

        assertThat(Alliance.WHITE.choosePlayer(players)).isEqualTo(whitePlayer);
    }

    @Test
    void chooseBlackPlayer() {
        when(whitePlayer.getAlliance()).thenReturn(Alliance.WHITE);
        when(blackPlayer.getAlliance()).thenReturn(Alliance.BLACK);

        players = List.of(whitePlayer, blackPlayer);

        assertThat(Alliance.BLACK.choosePlayer(players)).isEqualTo(blackPlayer);
    }
}
