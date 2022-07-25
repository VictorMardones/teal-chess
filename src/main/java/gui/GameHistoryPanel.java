/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package gui;

import engine.board.Board;
import java.awt.*;
import javax.swing.*;

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

    private void redo(final Board board, final MoveLog moveLog) {}
}
