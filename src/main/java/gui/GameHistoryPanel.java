/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package gui;

import engine.board.Board;
import engine.move.Move;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GameHistoryPanel extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;

    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100, 400);

    public GameHistoryPanel() {
        super(new BorderLayout());
        model = new DataModel();

        final var table = new JTable(model);
        table.setRowHeight(15);

        scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void redo(final Board board, final MoveLog moveLog) {
        var currentRow = 0;
        model.clear();

        for (var move : moveLog.getMoves()) {

            final var moveText = move.toString();

            if (move.getPiece().isWhite()) {
                model.setValueAt(moveText, currentRow, 0);
            } else {
                model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }

        if (moveLog.getMoves().size() > 0) {
            final var lastMove = moveLog.getMoves().get(moveLog.size() - 1);
            final var moveText = lastMove.toString();

            if (lastMove.getPiece().isWhite()) {
                model.setValueAt(moveText + calculateCheckAndCheckMateHash(board), currentRow, 0);
            } else {
                model.setValueAt(moveText + calculateCheckAndCheckMateHash(board), currentRow - 1, 1);
            }
        }

        final var vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private String calculateCheckAndCheckMateHash(Board board) {
        if (board.getCurrentPlayer().isInCheckmate()) {
            return "#";
        } else if (board.getCurrentPlayer().isInCheck()) {
            return "+";
        }

        return "";
    }

    private static class DataModel extends DefaultTableModel {

        private final List<Row> values;
        private static final String[] NAMES = {"White", "Black"};

        public DataModel() {
            this.values = new ArrayList<>();
        }

        public void clear() {
            values.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount() {
            if (values == null) {
                return 0;
            }

            return values.size();
        }

        @Override
        public int getColumnCount() {
            return NAMES.length;
        }

        @Override
        public Object getValueAt(int row, int column) {
            final var currentRow = values.get(row);

            if (column == 0) {
                return currentRow.getWhiteMove();
            }

            return currentRow.getBlackMove();
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            final Row currentRow;

            if (values.size() <= row) {
                currentRow = new Row();
                values.add(currentRow);
            } else {
                currentRow = values.get(row);
            }

            if (column == 0) {
                currentRow.setWhiteMove((String) aValue);
                fireTableRowsInserted(row, row);
            } else if (column == 1) {
                currentRow.setBlackMove((String) aValue);
                fireTableCellUpdated(row, column);
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return Move.class;
        }

        @Override
        public String getColumnName(int column) {
            return NAMES[column];
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Row {
        private String whiteMove;
        private String blackMove;
    }
}
