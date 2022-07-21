package gui;

import engine.board.Board;
import engine.board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class BoardPanel extends JPanel {

    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);

    private final Table table;
    final List<TilePanel> boardTiles;

    BoardPanel(Table table) {
        super(new GridLayout(8, 8));
        this.table = table;

        boardTiles = new ArrayList<>();

        IntStream.range(BoardUtils.MIN_TILES, BoardUtils.MAX_TILES)
                .mapToObj(i -> new TilePanel(table, this, i))
                .forEach(tilePanel -> {
                    boardTiles.add(tilePanel);
                    add(tilePanel);
                });

        setPreferredSize(BOARD_PANEL_DIMENSION);
        validate();
    }

    public void drawBoard(final Board board) {
        removeAll();

        table.getBoardDirection().traverse(boardTiles)
                .forEach(tilePanel -> {
                    tilePanel.drawTile(board);
                    add(tilePanel);
                });

        validate();
        repaint();
    }
}
