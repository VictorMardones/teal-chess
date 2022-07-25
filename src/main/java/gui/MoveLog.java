/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package gui;

import engine.move.Move;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MoveLog {

    @Getter
    private final List<Move> moves = new ArrayList<>();

    public void add(final Move move) {
        moves.add(move);
    }

    public int size() {
        return moves.size();
    }

    public void clear() {
        moves.clear();
    }

    public Move remove(final int index) {
        return moves.remove(index);
    }

    public boolean remove(final Move move) {
        return moves.remove(move);
    }
}
