/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.gui;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.*;

class BoardPanel extends JPanel {

  public static final Dimension INITIAL_SIZE = new Dimension(500, 500);

  private final transient Table table;
  private final List<TilePanel> boardTiles;

  BoardPanel(final Table table) {
    super(new GridLayout(8, 8));
    this.table = table;

    boardTiles = new ArrayList<>();

    IntStream.range(Board.MIN_TILES, Board.MAX_TILES)
        .mapToObj(Coordinate::of)
        .map(coordinate -> new TilePanel(table, coordinate))
        .forEach(
            tilePanel -> {
              boardTiles.add(tilePanel);
              add(tilePanel);
            });

    setPreferredSize(INITIAL_SIZE);
    validate();
  }

  public void drawBoard(final Board board) {
    removeAll();

    table
        .getBoardDirection()
        .traverse(boardTiles)
        .forEach(
            tilePanel -> {
              tilePanel.drawTile(board);
              add(tilePanel);
            });

    validate();
    repaint();
  }
}
