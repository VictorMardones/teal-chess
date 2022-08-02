package cl.vmardones.chess.engine.game;

import java.util.ArrayList;
import java.util.List;

public class GameHistory {

    private final List<TurnMemento> history = new ArrayList<>();

    public void add(final TurnMemento state){
        history.add(state);
    }

    public TurnMemento get(final int index){
        return history.get(index);
    }
}
