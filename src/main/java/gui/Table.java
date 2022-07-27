/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package gui;

import com.google.common.collect.Lists;
import engine.board.Board;
import engine.board.Tile;
import engine.move.Move;
import engine.piece.Piece;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Table {

    private static final Dimension SIZE = new Dimension(700, 600);

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private final TakenPiecesPanel takenPiecesPanel;

    private final GameHistoryPanel gameHistoryPanel;

    @Getter
    @Setter
    private Board chessboard;

    private final MoveLog moveLog;

    @Getter
    @Setter
    private Tile sourceTile;

    @Getter
    @Setter
    private Tile destinationTile;

    @Getter
    @Setter
    private Piece selectedPiece;

    @Getter
    private BoardDirection boardDirection;

    @Getter
    private boolean highlightLegals;

    public Table() {
        chessboard = Board.createStandardBoard();
        boardDirection = BoardDirection.NORMAL;
        highlightLegals = true;

        gameFrame = new JFrame("Chess game, made in Java");
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setSize(SIZE);

        gameFrame.setJMenuBar(createMenuBar());

        boardPanel = new BoardPanel(this);
        takenPiecesPanel = new TakenPiecesPanel();
        gameHistoryPanel = new GameHistoryPanel();

        moveLog = new MoveLog();

        gameFrame.add(boardPanel, BorderLayout.CENTER);

        gameFrame.add(takenPiecesPanel, BorderLayout.WEST);
        gameFrame.add(gameHistoryPanel, BorderLayout.EAST);

        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        final var menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createPreferencesMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        final var fileMenu = new JMenu("File");

        final var openPGN = new JMenuItem("Load PGN file");
        openPGN.addActionListener(e -> log.info("Open PGN file!"));
        fileMenu.add(openPGN);

        final var exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        fileMenu.add(exit);

        return fileMenu;
    }

    private JMenu createPreferencesMenu() {
        final var preferencesMenu = new JMenu("Preferences");

        final var flipBoard = new JMenuItem("Flip Board");
        flipBoard.addActionListener(e -> {
            boardDirection = boardDirection.opposite();
            boardPanel.drawBoard(chessboard);
        });

        final var highlightCheckbox = new JCheckBoxMenuItem("Highlight Legal Moves", highlightLegals);
        highlightCheckbox.addActionListener(e -> highlightLegals = highlightCheckbox.isSelected());

        preferencesMenu.add(flipBoard);
        preferencesMenu.add(highlightCheckbox);
        return preferencesMenu;
    }

    public void resetSelection() {
        sourceTile = null;
        destinationTile = null;
        selectedPiece = null;
    }

    public void update() {
        gameHistoryPanel.redo(getChessboard(), moveLog);
        takenPiecesPanel.redo(moveLog);
        boardPanel.drawBoard(getChessboard());
    }

    public void addMoveToLog(Move move) {
        moveLog.add(move);
    }

    public enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

        abstract BoardDirection opposite();
    }
}
