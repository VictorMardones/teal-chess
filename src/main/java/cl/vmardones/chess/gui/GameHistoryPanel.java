/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.gui;

import cl.vmardones.chess.engine.player.Player;
import java.awt.*;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

class GameHistoryPanel extends JPanel {

  private static final Dimension INITIAL_SIZE = new Dimension(150, 400);
  public static final int ROW_HEIGHT = 25;
  private final DataModel model;
  private final JScrollPane scrollPane;

  public GameHistoryPanel() {
    super(new BorderLayout());
    model = new DataModel();

    final var table = new JTable(model);
    table.setRowHeight(ROW_HEIGHT);
    table.setDefaultRenderer(Object.class, createCenteredRenderer());

    scrollPane = new JScrollPane(table);
    scrollPane.setColumnHeaderView(table.getTableHeader());
    scrollPane.setPreferredSize(INITIAL_SIZE);

    add(scrollPane, BorderLayout.CENTER);
    setVisible(true);
  }

  private TableCellRenderer createCenteredRenderer() {
    final var centeredRenderer = new DefaultTableCellRenderer();
    centeredRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    return centeredRenderer;
  }

  public void redo(final Player currentPlayer, final MoveLog moveLog) {

    final var lastMove = moveLog.getLastMove();

    if (lastMove.isPresent()) {
      final var moveText = lastMove.get().toString();

      switch (lastMove.get().getPiece().getAlliance()) {
        case WHITE -> model.setValueAt(
            moveText + checkmateHash(currentPlayer), model.getLastRowIndex() + 1, 0);
        case BLACK -> model.setValueAt(
            moveText + checkmateHash(currentPlayer), model.getLastRowIndex(), 1);
      }
    }

    final var vertical = scrollPane.getVerticalScrollBar();
    vertical.setValue(vertical.getMaximum());
  }

  private String checkmateHash(final Player currentPlayer) {
    if (currentPlayer.isInCheckmate()) {
      return "#";
    } else if (currentPlayer.isInCheck()) {
      return "+";
    }

    return "";
  }

  private static class DataModel extends DefaultTableModel {

    public DataModel() {
      super(new Vector<>(List.of("White", "Black")), 0);
    }

    void clear() {
      dataVector = new Vector<>(0);
    }

    @Override
    public void setValueAt(final Object aValue, final int row, final int column) {
      if (row > getLastRowIndex()) {
        addRow(new Vector<>(List.of(aValue, "")));
      } else {
        super.setValueAt(aValue, row, column);
      }
    }

    int getLastRowIndex() {
      return getRowCount() - 1;
    }
  }
}
