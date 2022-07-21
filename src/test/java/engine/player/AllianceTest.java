package engine.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AllianceTest {

    static List<Player> players;
    static Player whitePlayer;
    static Player blackPlayer;

    @BeforeAll
    static void setUp() {
        whitePlayer = mock(Player.class);
        when(whitePlayer.getAlliance())
                .thenReturn(Alliance.WHITE);

        blackPlayer = mock(Player.class);
        when(blackPlayer.getAlliance())
                .thenReturn(Alliance.BLACK);

        players = List.of(blackPlayer, whitePlayer);
    }

    @Test
    void getWhiteDirection() {
        assertThat(Alliance.WHITE.getDirection())
                .isNegative()
                .isEqualTo(-1);
    }

    @Test
    void getBlackDirection() {
        assertThat(Alliance.BLACK.getDirection())
                .isPositive()
                .isEqualTo(1);
    }

    @Test
    void chooseWhitePlayer() {
        assertThat(Alliance.WHITE.choosePlayer(players))
                .isEqualTo(whitePlayer);
    }

    @Test
    void chooseBlackPlayer() {
        assertThat(Alliance.BLACK.choosePlayer(players))
                .isEqualTo(blackPlayer);
    }
}