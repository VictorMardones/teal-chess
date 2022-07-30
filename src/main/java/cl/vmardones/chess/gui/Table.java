/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.gui;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Tile;
import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.Piece;
import cl.vmardones.chess.io.FontLoader;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.google.common.collect.Lists;
import java.awt.*;
import java.awt.event.WindowStateListener;
import java.util.List;
import javax.swing.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Table {

    private static final Dimension INITIAL_SIZE = new Dimension(700, 600);
    private static final String FONT_PATH = "NotoSans-Regular.ttf";

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
    private boolean highlightLegals;

    private boolean darkTheme;

    @Getter
    private BoardDirection boardDirection;

    public Table(final boolean darkTheme, final boolean highlightLegals, final boolean flipBoard) {
        this.darkTheme = darkTheme;
        this.highlightLegals = highlightLegals;
        boardDirection = flipBoard ? BoardDirection.FLIPPED : BoardDirection.NORMAL;

        reloadTheme();

        setUIFont(FontLoader.load(FONT_PATH));

        chessboard = Board.createStandardBoard();
        boardDirection = BoardDirection.NORMAL;

        gameFrame = new JFrame("Chess game, made in Java");
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setSize(INITIAL_SIZE);

        gameFrame.setJMenuBar(createMenuBar());

        boardPanel = new BoardPanel(this);
        takenPiecesPanel = new TakenPiecesPanel();
        gameHistoryPanel = new GameHistoryPanel();

        moveLog = new MoveLog();

        gameFrame.add(new SquarePanel<>(boardPanel), BorderLayout.CENTER);

        gameFrame.add(takenPiecesPanel, BorderLayout.WEST);
        gameFrame.add(gameHistoryPanel, BorderLayout.EAST);

        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameFrame.addWindowStateListener(maximizeListener());

        gameFrame.setVisible(true);
        gameFrame.pack();
    }

    private WindowStateListener maximizeListener() {
        return e -> {
            if (e.getOldState() == MAXIMIZED_BOTH && e.getNewState() == NORMAL) {
                boardPanel.setPreferredSize(BoardPanel.INITIAL_SIZE);
                gameFrame.pack();
            }
        };
    }

    private void setUIFont(Font font) {

        var keys = UIManager.getLookAndFeel().getDefaults().keys();

        while (keys.hasMoreElements()) {

            var key = keys.nextElement();
            var value = UIManager.get(key);

            if (value instanceof Font) {
                UIManager.put(key, font);
            }
        }
    }

    private void reloadTheme() {
        if (darkTheme) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }

        if (gameFrame != null) {
            SwingUtilities.updateComponentTreeUI(gameFrame);
        }
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
        openPGN.addActionListener(e -> log.debug("Open PGN file!"));
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

        final var darkThemeCheckbox = new JCheckBoxMenuItem("Dark Theme", darkTheme);
        darkThemeCheckbox.addActionListener(e -> {
            darkTheme = darkThemeCheckbox.isSelected();
            reloadTheme();
        });

        preferencesMenu.add(flipBoard);
        preferencesMenu.add(highlightCheckbox);
        preferencesMenu.add(darkThemeCheckbox);
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
