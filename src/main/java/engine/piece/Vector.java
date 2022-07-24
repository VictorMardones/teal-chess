package engine.piece;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface Vector {

    int[] getVector();

    @Getter
    @AllArgsConstructor
    enum Diagonal implements Vector {
        UP_LEFT(new int[] {-1, 1}),
        UP_RIGHT(new int[] {1, 1}),
        DOWN_LEFT(new int[] {-1, -1}),
        DOWN_RIGHT(new int[] {1, -1});

        private final int[] vector;
    }

    @Getter
    @AllArgsConstructor
    enum Horizontal implements Vector {
        LEFT(new int[] {-1, 0}),
        RIGHT(new int[] {1, 0});

        private final int[] vector;
    }

    @Getter
    @AllArgsConstructor
    enum Vertical implements Vector {
        UP(new int[] {0, 1}),
        DOWN(new int[] {0, -1});

        private final int[] vector;
    }

    @Getter
    @AllArgsConstructor
    enum LShaped implements Vector {
        UP_UP_LEFT(new int[] {-1, 2}),
        UP_UP_RIGHT(new int[] {1, 2}),
        UP_LEFT_LEFT(new int[] {-2, 1}),
        UP_RIGHT_RIGHT(new int[] {2, 1}),
        DOWN_DOWN_LEFT(new int[] {-1, -2}),
        DOWN_DOWN_RIGHT(new int[] {1, -2}),
        DOWN_LEFT_LEFT(new int[] {-2, -1}),
        DOWN_RIGHT_RIGHT(new int[] {2, -1});

        private final int[] vector;
    }

    @Getter
    @AllArgsConstructor
    enum Jump implements Vector {
        UP(new int[] {0, 2}),
        DOWN(new int[] {0, -2});

        private final int[] vector;
    }
}
